package com.app20222.app20222_backend.services.surgery.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import com.app20222.app20222_backend.constants.message.errorKey.ErrorKey;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst.Resources;
import com.app20222.app20222_backend.dtos.fileAttach.IGetFileAttach;
import com.app20222.app20222_backend.dtos.surgery.IGetDetailSurgery;
import com.app20222.app20222_backend.dtos.surgery.IGetSurgeryAssignment;
import com.app20222.app20222_backend.dtos.surgery.SurgeryCreateDTO;
import com.app20222.app20222_backend.dtos.surgery.SurgeryDetailDTO;
import com.app20222.app20222_backend.dtos.surgery.SurgeryRoleDTO;
import com.app20222.app20222_backend.dtos.surgery.SurgeryUpdateDTO;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.dtos.surgery.IGetOverlapSurgery;
import com.app20222.app20222_backend.entities.mail.Mail;
import com.app20222.app20222_backend.entities.surgery.Surgery;
import com.app20222.app20222_backend.entities.surgery.UserSurgery;
import com.app20222.app20222_backend.entities.surgeryRoom.SurgeryRoom;
import com.app20222.app20222_backend.enums.mail.MailTemplateEnum;
import com.app20222.app20222_backend.enums.permission.BasePermissionEnum;
import com.app20222.app20222_backend.enums.surgery.SurgeryStatusEnum;
import com.app20222.app20222_backend.exceptions.exceptionFactory.ExceptionFactory;
import com.app20222.app20222_backend.repositories.fileAttach.FileAttachRepository;
import com.app20222.app20222_backend.repositories.fileAttach.SurgeryFileRepository;
import com.app20222.app20222_backend.repositories.surgery.SurgeryRepository;
import com.app20222.app20222_backend.repositories.surgery.UserSurgeryRepository;
import com.app20222.app20222_backend.repositories.surgeryRoom.SurgeryRoomRepository;
import com.app20222.app20222_backend.repositories.users.UserRepository;
import com.app20222.app20222_backend.services.mail.MailService;
import com.app20222.app20222_backend.services.permission.PermissionService;
import com.app20222.app20222_backend.services.surgery.SurgeryService;
import com.app20222.app20222_backend.utils.DateUtils;
import com.app20222.app20222_backend.utils.StringUtils;
import com.app20222.app20222_backend.utils.auth.AuthUtils;

@Service
public class SurgeryServiceImpl implements SurgeryService {

    private final SurgeryRepository surgeryRepository;

    private final UserSurgeryRepository userSurgeryRepository;

    private final ExceptionFactory exceptionFactory;

    private final PermissionService permissionService;

    private final FileAttachRepository fileAttachRepository;

    private final SurgeryFileRepository surgeryFileRepository;

    private final UserRepository userRepository;

    private final SurgeryRoomRepository surgeryRoomRepository;

    private final MailService mailService;


    public static final String OVERLAP_ASSIGNEE = "assignee_id";

    public static final String OVERLAP_SURGERY_ROOM = "surgery_room_id";

    public static final String OVERLAP_PATIENT = "patient_id";

    public SurgeryServiceImpl(SurgeryRepository surgeryRepository, UserSurgeryRepository userSurgeryRepository, ExceptionFactory exceptionFactory,
        PermissionService permissionService, FileAttachRepository fileAttachRepository, SurgeryFileRepository surgeryFileRepository,
        UserRepository userRepository, SurgeryRoomRepository surgeryRoomRepository, MailService mailService){
        this.surgeryRepository = surgeryRepository;
        this.userSurgeryRepository = userSurgeryRepository;
        this.exceptionFactory = exceptionFactory;
        this.permissionService = permissionService;
        this.fileAttachRepository = fileAttachRepository;
        this.surgeryFileRepository = surgeryFileRepository;
        this.mailService = mailService;
        this.userRepository = userRepository;
        this.surgeryRoomRepository = surgeryRoomRepository;
    }

    @Transactional
    @Override
    public void createSurgery(SurgeryCreateDTO createDTO) {
        validateSurgeryOverlap(createDTO.getStartedAt(), createDTO.getEstimatedEndAt(), createDTO.getLstAssignment().stream().map(
            SurgeryRoleDTO::getAssigneeId).collect(Collectors.toSet()), createDTO.getSurgeryRoomId(), createDTO.getPatientId(), null);

        // Tạo surgery mới
        Surgery newSurgery = new Surgery();
        BeanUtils.copyProperties(createDTO, newSurgery);
        newSurgery.setCode(generateSurgeryCode());
        newSurgery.setCreatedBy(AuthUtils.getCurrentUserId());
        newSurgery.setCreatedAt(new Date());
        newSurgery = surgeryRepository.save(newSurgery);
        Long newSurgeryId = newSurgery.getId();

        // Lưu thông tin user - surgery
        List<UserSurgery> lstUserSurgery = createDTO.getLstAssignment().stream().map(
            item -> UserSurgery.builder().userId(item.getAssigneeId()).surgeryId(newSurgeryId).surgeryRoleType(item.getSurgeryRoleType())
                .build()).collect(Collectors.toList());
        lstUserSurgery = userSurgeryRepository.saveAll(lstUserSurgery);

        // Gửi thông báo qua email
       try{
           Set<Long> lstUserIdSendMail = lstUserSurgery.stream().map(UserSurgery::getUserId).collect(Collectors.toSet());
           List<String> lstEmailToSend = userRepository.findAllEmailByIdIn(lstUserIdSendMail);
           SurgeryRoom surgeryRoom = surgeryRoomRepository.findById(newSurgery.getSurgeryRoomId()).orElse(null);
           if(Objects.isNull(surgeryRoom)) return;
           MailTemplateEnum mailTemplate = MailTemplateEnum.CREATE_SURGERY_TO_USER_MAIL_TEMPLATE;
           String content = mailTemplate.getRawContent();
           content = content.replace("$SURGERY_NAME", newSurgery.getName());
           content = content.replace("$SURGERY_CODE", newSurgery.getCode());
           content = content.replace("$TIME", new SimpleDateFormat(DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM).format(newSurgery.getStartedAt()));
           content = content.replace("$SURGERY_ROOM", surgeryRoom.getName() + " - " + surgeryRoom.getAddress());
           Mail mail = Mail.builder()
               .subject(mailTemplate.getSubject())
               .content(content)
               .lstToAddress(lstEmailToSend)
               .isHasAttachments(false)
               .build();
           mailService.sendMail(mail);
       } catch (Exception ex) {
           ex.printStackTrace();
       }
    }

    @Transactional
    @Override
    public void updateSurgery(Long surgeryId, SurgeryUpdateDTO updateDTO) {
        // Check tồn tại surgery và  quyền chỉnh sửa surgery của user đăng nhập
        Surgery surgery = permissionService.hasSurgeryPermission(surgeryId, BasePermissionEnum.EDIT);
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
        // TODO : Gửi mail (đợi template)
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
    public SurgeryDetailDTO getDetailSurgery(Long surgeryId) {
        //Check tồn tại surgery
        permissionService.hasSurgeryPermission(surgeryId, BasePermissionEnum.VIEW);

        // Lấy thông tin chung của ca phẫu thuật
        IGetDetailSurgery iGetDetailSurgery = surgeryRepository.getDetailSurgery(surgeryId);
        // Lấy danh sách file attached
        List<IGetFileAttach> lstFileAttach = surgeryFileRepository.getListFileAttach(surgeryId);

        // Lấy danh sách assignment ca phẫu thuật
        List<IGetSurgeryAssignment> lstAssignments = surgeryRepository.getSurgeryAssignment(surgeryId);

        return new SurgeryDetailDTO(iGetDetailSurgery, lstFileAttach, lstAssignments);
    }

    @Transactional
    @Override
    public void deleteSurgery(Long surgeryId) {
        // Check tồn tại surgery và  quyền chỉnh sửa surgery của user đăng nhập
        permissionService.hasSurgeryPermission(surgeryId, BasePermissionEnum.DELETE);

        // Xóa các assignments của ca phẫu thuật (users_surgeries)
        userSurgeryRepository.deleteAllBySurgeryId(surgeryId);

        // xóa các file attach gắn với ca phẫu thuật (surgeries_files)
        surgeryRepository.deleteSurgeryFileBySurgeryId(surgeryId);

        // Xóa ca phẫu thuật
        surgeryRepository.deleteById(surgeryId);

        // TODO: Gửi mail (đợi template)

    }

    @Override
    public void switchSurgeryStatus(Long surgeryId, SurgeryStatusEnum status) {
        surgeryRepository.changeSurgeryStatus(surgeryId, status.getValue());
    }

    /**
     * Genarate  random surgery's code
     */
    private String generateSurgeryCode(){
        String baseCode = "CPT";
        Random random = new Random();
        String generatedCode = baseCode + (random.nextInt(900000) + 100000);
        while (surgeryRepository.existsByCode(generatedCode)){
            generatedCode = baseCode + (random.nextInt(900000) + 100000);
        }
        return generatedCode;
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
