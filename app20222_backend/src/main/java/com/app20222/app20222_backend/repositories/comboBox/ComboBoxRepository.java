package com.app20222.app20222_backend.repositories.comboBox;

import com.app20222.app20222_backend.constants.sql.comboBox.SQLComboBox;
import com.app20222.app20222_backend.dtos.comboBox.surgery.IComboBoxSurgeryRoleType;
import com.app20222.app20222_backend.dtos.common.ICommonIdCodeName;
import com.app20222.app20222_backend.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComboBoxRepository extends JpaRepository<User, Long> {

     /*
      ==== COMBO BOX USER ===
     */
    @Query(nativeQuery = true, value = SQLComboBox.GET_COMBO_BOX_USER)
    List<ICommonIdCodeName> getComboBoxUsers(String name);

     /*
      ==== COMBO BOX ROLE ===
     */
    @Query(nativeQuery = true, value = SQLComboBox.GET_COMBO_BOX_ROLE)
    List<ICommonIdCodeName> getComboBoxRoles();

    /*
     ==== COMBO BOX PATIENT ====
     */
    @Query(nativeQuery = true, value = SQLComboBox.GET_COMBO_BOX_PATIENT)
    List<ICommonIdCodeName> getComboBoxPatient(String patientName);

     /*
      ==== COMBO BOX DEPARTMENT ===
     */
    @Query(nativeQuery = true, value = SQLComboBox.GET_COMBO_BOX_DEPARTMENT)
    List<ICommonIdCodeName> getComboBoxDepartment(String departmentName);

    /*
      ==== COMBO BOX SURGERY ===
     */

    @Query(nativeQuery = true, value = SQLComboBox.GET_LIST_SURGERY_ROLE)
    List<IComboBoxSurgeryRoleType> getListSurgeryRoleType();

     /*
      ==== COMBO BOX SURGERY ROOM ===
     */
    @Query(nativeQuery = true, value = SQLComboBox.GET_COMBO_BOX_SURGERY_ROOM)
    List<ICommonIdCodeName> getComboBoxSurgeryRoom(String surgeryRoomName);
}
