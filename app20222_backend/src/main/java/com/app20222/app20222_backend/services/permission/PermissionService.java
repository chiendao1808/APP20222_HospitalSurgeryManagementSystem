package com.app20222.app20222_backend.services.permission;

import java.util.Set;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.entities.patient.Patient;
import com.app20222.app20222_backend.entities.surgery.Surgery;
import com.app20222.app20222_backend.entities.users.User;
import com.app20222.app20222_backend.enums.permission.BasePermissionEnum;

@Service
public interface PermissionService {

    Set<Long> getLstViewableDepartmentId();

    User hasUserPermission(Long userId, BasePermissionEnum permission);


    Set<Long> getLstViewableSurgeryId();

    Surgery hasSurgeryPermission(Long surgeryId, BasePermissionEnum permission);

    Patient hasPatientPermission(Long patientId, BasePermissionEnum permission);

}
