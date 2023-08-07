package com.app20222.app20222_backend.services.comboBox;

import com.app20222.app20222_backend.dtos.comboBox.surgery.IComboBoxSurgeryRoleType;
import com.app20222.app20222_backend.dtos.common.ICommonIdCodeName;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComboBoxService {

     /*
      ==== COMBO BOX USER ===
     */
    List<ICommonIdCodeName> getComboBoxUser(String name);

     /*
      ==== COMBO BOX ROLE ====
     */
    List<ICommonIdCodeName> getComboBoxRole();

     /*
     * ==== COMBO BOX PATIENT =====
     */
    List<ICommonIdCodeName> getComboBoxPatient(String patientName);

     /*
      ==== COMBO BOX DEPARTMENT ===
     */
    List<ICommonIdCodeName> getComboBoxDepartment(String departmentName);

     /*
      ==== COMBO BOX SURGERY ===
     */
    List<IComboBoxSurgeryRoleType> getListSurgeryRoleType();

    List<ICommonIdCodeName> getListDiseaseGroup(String diseaseGroupName);

     /*
      ==== COMBO BOX SURGERY ROOM ===
     */
    List<ICommonIdCodeName> getComboBoxSurgeryRoom(String surgeryRoomName);

}
