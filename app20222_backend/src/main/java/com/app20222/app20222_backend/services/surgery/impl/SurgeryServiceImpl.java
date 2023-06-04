package com.app20222.app20222_backend.services.surgery.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import com.app20222.app20222_backend.constants.message.error_field.ErrorKey;
import com.app20222.app20222_backend.constants.message.message_const.MessageConst;
import com.app20222.app20222_backend.constants.message.message_const.MessageConst.Resources;
import com.app20222.app20222_backend.dtos.surgery.IGetDetailSurgery;
import com.app20222.app20222_backend.dtos.surgery.SurgeryCreateDTO;
import com.app20222.app20222_backend.dtos.surgery.SurgeryRoleDTO;
import com.app20222.app20222_backend.dtos.surgery.SurgeryUpdateDTO;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.dtos.surgery.IGetOverlapSurgery;
import com.app20222.app20222_backend.entities.surgery.Surgery;
import com.app20222.app20222_backend.entities.surgery.UserSurgery;
import com.app20222.app20222_backend.enums.permission.BasePermissionEnum;
import com.app20222.app20222_backend.enums.surgery.SurgeryStatusEnum;
import com.app20222.app20222_backend.exceptions.exception_factory.ExceptionFactory;
import com.app20222.app20222_backend.repositories.surgery.SurgeryRepository;
import com.app20222.app20222_backend.repositories.surgery.UserSurgeryRepository;
import com.app20222.app20222_backend.services.permission.PermissionService;
import com.app20222.app20222_backend.services.surgery.SurgeryService;
import com.app20222.app20222_backend.utils.StringUtils;
import com.app20222.app20222_backend.utils.auth.AuthUtils;

@Service
public class SurgeryServiceImpl implements SurgeryService {

    private final SurgeryRepository surgeryRepository;

    private final UserSurgeryRepository userSurgeryRepository;

    private final ExceptionFactory exceptionFactory;

    private final PermissionService permissionService;

    public static final String OVERLAP_ASSIGNEE = "Trùng Bác sĩ/ y tá phẫu thuật";

    public static final String OVERLAP_SURGERY_ROOM = "Trùng phòng phẫu thuật";

    public static final String OVERLAP_PATIENT = "Trùng bệnh nhân";

    public SurgeryServiceImpl(SurgeryRepository surgeryRepository, UserSurgeryRepository userSurgeryRepository, ExceptionFactory exceptionFactory,
        PermissionService permissionService){
        this.surgeryRepository = surgeryRepository;
        this.userSurgeryRepository = userSurgeryRepository;
        this.exceptionFactory = exceptionFactory;
        this.permissionService = permissionService;
    }

    @Transactional
    @Override
    public void createSurgery(SurgeryCreateDTO createDTO) {
        validateSurgeryOverlap(createDTO.getStartedAt(), createDTO.getEstimatedEndAt(), createDTO.getLstAssignment().stream().map(
            SurgeryRoleDTO::getAssigneeId).collect(Collectors.toSet()), createDTO.getSurgeryRoomId(), createDTO.getPatientId(), null);

        // Tạo surgery mới
        Surgery newSurgery = new Surgery();
        BeanUtils.copyProperties(createDTO, newSurgery);
        newSurgery.setCreatedBy(AuthUtils.getCurrentUserId());
        newSurgery.setCreatedAt(new Date());
        Long newSurgeryId = surgeryRepository.save(newSurgery).getId();

        // Lưu thông tin user - surgery
        List<UserSurgery> lstUserSurgery = createDTO.getLstAssignment().stream().map(
            item -> UserSurgery.builder().userId(item.getAssigneeId()).surgeryId(newSurgeryId).surgeryRoleType(item.getSurgeryRoleType())
                .build()).collect(Collectors.toList());
        userSurgeryRepository.saveAll(lstUserSurgery);
    }

    @Transactional
    @Override
    public void updateSurgery(Long surgeryId, SurgeryUpdateDTO updateDTO) {
        //Check tồn tại surgery
        Surgery surgery = surgeryRepository.findById(surgeryId)
            .orElseThrow(() -> exceptionFactory.resourceNotFoundException(ErrorKey.Surgery.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND,
                Resources.SURGERY, ErrorKey.Surgery.ID, String.valueOf(surgeryId)));
        // Check quyền chỉnh sửa surgery của user đăng nhập
        permissionService.hasSurgeryPermission(surgery, BasePermissionEnum.EDIT);
        // Validate overlapped
        Set<Long> lstAssigneeId = updateDTO.getLstAssignment().stream().map(SurgeryRoleDTO::getAssigneeId).collect(Collectors.toSet());
        validateSurgeryOverlap(updateDTO.getStartedAt(), updateDTO.getEstimatedEndAt(), lstAssigneeId, updateDTO.getSurgeryRoomId(), null, surgeryId);

        // Cập nhật thông tin surgery
        BeanUtils.copyProperties(updateDTO, surgery);
        surgeryRepository.save(surgery);
        // Xóa toàn bộ assignment theo surgery id
        userSurgeryRepository.deleteAllBySurgeryId(surgeryId);
        // lưu assignment mới
        List<UserSurgery> lstUserSurgery = updateDTO.getLstAssignment().stream().map(
            item -> UserSurgery.builder().userId(item.getAssigneeId()).surgeryId(surgeryId).surgeryRoleType(item.getSurgeryRoleType())
                .build()).collect(Collectors.toList());
        userSurgeryRepository.saveAll(lstUserSurgery);
    }

    @Override
    public List<IGetListSurgery> getListSurgery(Long surgeryId, String surgeryName, Long patientId, Long diseaseGroupId, Long surgeryRoomId,
        SurgeryStatusEnum status, Date startedAt, Date estimatedEndAt) {
        // Lấy danh sách surgery mà user đăng nhập có quyền xem
        Set<Long> lstViewableSurgeryId = permissionService.getLstViewableSurgeryId();
        lstViewableSurgeryId.add(surgeryId);
        return surgeryRepository.getListSurgery(lstViewableSurgeryId, surgeryName, patientId, diseaseGroupId, surgeryRoomId, status.getValue(),
            startedAt, estimatedEndAt);
    }

    @Override
    public IGetDetailSurgery getDetailSurgery(Long surgeryId) {
        //Check tồn tại surgery
        surgeryRepository.findById(surgeryId)
            .orElseThrow(() -> exceptionFactory.resourceNotFoundException(ErrorKey.Surgery.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND,
                Resources.SURGERY, ErrorKey.Surgery.ID, String.valueOf(surgeryId)));
        return null;
    }

    /**
     * Kiểm tra xem ca phẫu thuật chờ thực hiện hoặc đang thực hiện được tạo các tiêu trí sau bị trùng trong cùng một khoảng thời gian hay không :
     * 1. Trùng một trong các bác sĩ/ nhân viên phụ trách phẫu thuật
     * 2. Trùng phòng phẫu thuật.
     * 3. Trùng bệnh nhân được chỉ định phẫu thuật.
     */
    private void validateSurgeryOverlap(Date startTime, Date estimatedEndTime, Set<Long> lstAssigneeId, Long surgeryRoomId,
        Long patientId, Long surgeryId) {
        List<String> overlapCause = new ArrayList<>();
        List<IGetOverlapSurgery> lstOverlapSurgery = surgeryRepository.getOverlapSurgery(startTime, estimatedEndTime);
        // Loại bỏ các bản ghi của ca phẫu thuật đang validate ( dành cho update )
        if(surgeryId != null){
            lstOverlapSurgery.removeIf(surgery -> Objects.equals(surgeryId, surgery.getSurgeryId()));
        }
        // Kiểm tra các điều kiện trùng lặp về danh sách bác/sĩ thực hiện, phòng phẫu thuật, bệnh nhân
        if (lstOverlapSurgery.stream()
            .anyMatch(item -> CollectionUtils.containsAny(StringUtils.convertStrLongArrayToSetLong(item.getLstAssigneeId()), lstAssigneeId))) {
            overlapCause.add(OVERLAP_ASSIGNEE);
        }
        if (surgeryRoomId != null
            && lstOverlapSurgery.stream().anyMatch(item -> Objects.equals(item.getSurgeryRoomId(), surgeryRoomId))) {
            overlapCause.add(OVERLAP_SURGERY_ROOM);
        }
        if (patientId != null &&
            lstOverlapSurgery.stream().anyMatch(item -> Objects.equals(item.getPatientId(), patientId))) {
            overlapCause.add(OVERLAP_PATIENT);
        }
        if (!ObjectUtils.isEmpty(overlapCause)) {
            throw exceptionFactory.overlappedException(ErrorKey.Surgery.OVERLAPPED_ERROR_CODE, MessageConst.RESOURCE_OVERLAPPED, Resources.SURGERY,
                String.join(",", overlapCause));
        }
    }
}
