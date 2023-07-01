package com.app20222.app20222_backend.services.surgeryRoom.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.constants.message.errorKey.ErrorKey;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst.Resources;
import com.app20222.app20222_backend.dtos.surgeryRoom.IGetListSurgeryRoom;
import com.app20222.app20222_backend.dtos.surgeryRoom.SurgeryRoomCreateDTO;
import com.app20222.app20222_backend.dtos.surgeryRoom.SurgeryRoomUpdateDTO;
import com.app20222.app20222_backend.entities.surgeryRoom.SurgeryRoom;
import com.app20222.app20222_backend.enums.role.RoleLevelEnum;
import com.app20222.app20222_backend.exceptions.exceptionFactory.ExceptionFactory;
import com.app20222.app20222_backend.repositories.surgeryRoom.SurgeryRoomRepository;
import com.app20222.app20222_backend.services.permission.PermissionService;
import com.app20222.app20222_backend.services.surgeryRoom.SurgeryRoomService;
import com.app20222.app20222_backend.utils.auth.AuthUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SurgeryRoomServiceImpl implements SurgeryRoomService {

    private final SurgeryRoomRepository surgeryRoomRepository;
    private final ExceptionFactory exceptionFactory;


    public SurgeryRoomServiceImpl(SurgeryRoomRepository surgeryRoomRepository, PermissionService permissionService,
        ExceptionFactory exceptionFactory) {
        this.surgeryRoomRepository = surgeryRoomRepository;
        this.exceptionFactory = exceptionFactory;
    }

    @Override
    public void createSurgeryRoom(SurgeryRoomCreateDTO createDTO) {
        // Check create permission : admin/manager hospital
        if (AuthUtils.isRoleLevel(RoleLevelEnum.HOSPITAL_MANAGER)) {
            throw exceptionFactory.permissionDeniedException(ErrorKey.SurgeryRoom.PERMISSION_DENIED_ERROR_CODE, Resources.SURGERY_ROOM,
                MessageConst.PERMISSIONS_DENIED);
        }

        // Check code exists
        if (surgeryRoomRepository.existsByCode(createDTO.getCode())) {
            throw exceptionFactory.resourceExistedException(ErrorKey.SurgeryRoom.EXISTED_ERROR_CODE, Resources.SURGERY_ROOM,
                MessageConst.RESOURCE_EXISTED, ErrorKey.SurgeryRoom.CODE, createDTO.getCode());
        }
        SurgeryRoom surgeryRoom = new SurgeryRoom();
        surgeryRoom.setCreatedBy(AuthUtils.getCurrentUserId());
        surgeryRoom.setCreatedAt(new Date());
        BeanUtils.copyProperties(createDTO, surgeryRoom);
        surgeryRoomRepository.save(surgeryRoom);
    }

    @Override
    public void updateSurgeryRoom(Long id, SurgeryRoomUpdateDTO updateDTO) {
        // Check update permission : admin/manager hospital
        if (AuthUtils.isRoleLevel(RoleLevelEnum.HOSPITAL_MANAGER)) {
            throw exceptionFactory.permissionDeniedException(ErrorKey.SurgeryRoom.PERMISSION_DENIED_ERROR_CODE, Resources.SURGERY_ROOM,
                MessageConst.PERMISSIONS_DENIED);
        }
        // Check room exists
        SurgeryRoom surgeryRoom = surgeryRoomRepository.findById(id).orElseThrow(
            () -> exceptionFactory.resourceNotFoundException(ErrorKey.SurgeryRoom.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND,
                Resources.SURGERY_ROOM, ErrorKey.SurgeryRoom.ID, String.valueOf(id)));
        BeanUtils.copyProperties(updateDTO, surgeryRoom);
        surgeryRoom.setModifiedBy(AuthUtils.getCurrentUserId());
        surgeryRoom.setModifiedAt(new Date());
        surgeryRoomRepository.save(surgeryRoom);
    }

    @Override
    public List<IGetListSurgeryRoom> getListSurgeryRoom(Long id, String code, String name, Boolean currentAvailable) {
        return surgeryRoomRepository.getListSurgeryRoom(id, code, name, currentAvailable);
    }
}
