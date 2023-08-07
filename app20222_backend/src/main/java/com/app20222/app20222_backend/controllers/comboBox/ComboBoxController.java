package com.app20222.app20222_backend.controllers.comboBox;

import com.app20222.app20222_backend.dtos.comboBox.surgery.IComboBoxSurgeryRoleType;
import com.app20222.app20222_backend.dtos.common.ICommonIdCodeName;
import com.app20222.app20222_backend.services.comboBox.ComboBoxService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/combo-box")
@Slf4j
public class ComboBoxController {

    @Autowired
    private ComboBoxService comboBoxService;

    /*
    ==== COMBO BOX USER ===
   */
    @GetMapping("/users/get-lst-user-name-code")
    @Operation(description = "Lấy ComboBox user : id, name, code")
    public List<ICommonIdCodeName> getComboBoxUser(@RequestParam(value = "name", required = false, defaultValue = "") String name) {
        return comboBoxService.getComboBoxUser(name);
    }

    /*
      ==== COMBO BOX ROLE ======================================================================
    */
    @GetMapping("/role/get-lst-role-name-code")
    @Operation(description = "Lấy ComboBox role : id, name, code")
    public List<ICommonIdCodeName> getComboBoxRole() {
        return comboBoxService.getComboBoxRole();
    }

    /*
      ==== COMBO BOX DEPARTMENT =================================================================
    */

    @GetMapping("/department/get-lst-department-name-code")
    @Operation(description = "Lấy ComboBox department : id, name, code")
    public List<ICommonIdCodeName> getComboBoxDepartment(
        @RequestParam(name = "name", required = false, defaultValue = "") String departmentName) {
        return comboBoxService.getComboBoxDepartment(departmentName);
    }

    /*
      ==== COMBO BOX PATIENT =================================================================
    */

    @GetMapping("/patient/get-lst-patient-name-code")
    @Operation(description = "Lấy ComboBox patient : id, name, code")
    public List<ICommonIdCodeName> getComboBoxPatient(@RequestParam(name = "name", required = false, defaultValue = "") String patientName) {
        return comboBoxService.getComboBoxPatient(patientName);
    }
    
    /*
      ==== COMBO BOX SURGERY =================================================================
     */

    @GetMapping("/surgery/get-lst-surgery-role-type")
    @Operation(description = "Lấy ComboBox surgery role type : type, name")
    public List<IComboBoxSurgeryRoleType> getListSurgeryRoleType() {
        return comboBoxService.getListSurgeryRoleType();
    }

    @GetMapping("/surgery/get-lst-disease-group")
    @Operation(description = "Lấy ComboBox disease group : id, name, code")
    public List<ICommonIdCodeName> getListDiseaseGroup(
        @RequestParam(name = "name", required = false, defaultValue = "") String diseaseGroupName) {
        return comboBoxService.getListDiseaseGroup(diseaseGroupName);
    }

    /*
    ==== COMBO BOX SURGERY ROOM =================================================================
    */
    @GetMapping("/surgery-room/get-surgery-room-name-code")
    @Operation(description = "Lấy ComboBox surgery room : id, name, code")
    public List<ICommonIdCodeName> getComboBoxSurgeryRoom(@RequestParam(name = "name", required = false, defaultValue = "") String surgeryRoomName) {
        return comboBoxService.getComboBoxSurgeryRoom(surgeryRoomName);
    }

}
