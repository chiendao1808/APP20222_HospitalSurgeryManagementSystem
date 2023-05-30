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
import com.app20222.app20222_backend.dtos.requests.surgery.SurgeryCreateDTO;
import com.app20222.app20222_backend.dtos.requests.surgery.SurgeryRoleDTO;
import com.app20222.app20222_backend.dtos.surgery.IGetOverlapSurgery;
import com.app20222.app20222_backend.entities.surgery.Surgery;
import com.app20222.app20222_backend.entities.surgery.UserSurgery;
import com.app20222.app20222_backend.exceptions.BadRequestException;
import com.app20222.app20222_backend.repositories.surgery.SurgeryRepository;
import com.app20222.app20222_backend.repositories.surgery.UserSurgeryRepository;
import com.app20222.app20222_backend.services.surgery.SurgeryService;
import com.app20222.app20222_backend.utils.StringUtils;
import com.app20222.app20222_backend.utils.auth.AuthUtils;

@Service
public class SurgeryServiceImpl implements SurgeryService {

    private final SurgeryRepository surgeryRepository;

    private final UserSurgeryRepository userSurgeryRepository;

    public static final String OVERLAP_ASSIGNEE = "Trùng Bác sĩ/ y tá phẫu thuật";

    public static final String OVERLAP_SURGERY_ROOM = "Trùng phòng phẫu thuật";

    public static final String OVERLAP_PATIENT = "Trùng bệnh nhân";

    public SurgeryServiceImpl(SurgeryRepository surgeryRepository, UserSurgeryRepository userSurgeryRepository){
        this.surgeryRepository = surgeryRepository;
        this.userSurgeryRepository = userSurgeryRepository;
    }

    @Transactional
    @Override
    public void createSurgery(SurgeryCreateDTO createDTO) {
        validateSurgeryOverlap(createDTO.getStartedAt(), createDTO.getEstimatedEndAt(), createDTO.getLstAssignment().stream().map(
            SurgeryRoleDTO::getAssigneeId).collect(Collectors.toSet()), createDTO.getSurgeryRoomId(), createDTO.getPatientId());

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


    /**
     * Kiểm tra xem ca phẫu thuật chờ thực hiện hoặc đang thực hiện được tạo các tiêu trí sau bị trùng trong cùng một khoảng thời gian hay không :
     * 1. Trùng một trong các bác sĩ/ nhân viên phụ trách phẫu thuật
     * 2. Trùng phòng phẫu thuật.
     * 3. Trùng bệnh nhân được chỉ định phẫu thuật.
     */
    private void validateSurgeryOverlap(Date startTime, Date estimatedEndTime, Set<Long> lstAssigneeId, Long surgeryRoomId,
        Long patientId) {
        List<String> overlapCause = new ArrayList<>();
        List<IGetOverlapSurgery> lstOverlapSurgery = surgeryRepository.getOverlapSurgery(startTime, estimatedEndTime);
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
        if(!ObjectUtils.isEmpty(overlapCause))
            throw new BadRequestException("Overlapped Surgery : " + String.join(", ", overlapCause));
    }
}
