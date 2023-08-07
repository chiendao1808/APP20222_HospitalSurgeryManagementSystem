package com.app20222.app20222_backend.services.comboBox.impl;

import com.app20222.app20222_backend.dtos.comboBox.surgery.IComboBoxSurgeryRoleType;
import com.app20222.app20222_backend.dtos.common.ICommonIdCodeName;
import com.app20222.app20222_backend.repositories.comboBox.ComboBoxRepository;
import com.app20222.app20222_backend.services.comboBox.ComboBoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ComboBoxServiceImpl implements ComboBoxService {

    @Autowired
    private ComboBoxRepository comboBoxRepository;

    /*
     ==== COMBO BOX USER ===
    */
    @Override
    public List<ICommonIdCodeName> getComboBoxUser(String name) {
        return comboBoxRepository.getComboBoxUsers(name);
    }

    /*
      ==== COMBO BOX ROLE ======================================================================
    */
    @Override
    public List<ICommonIdCodeName> getComboBoxRole() {
        return comboBoxRepository.getComboBoxRoles();
    }

    /*
      ==== COMBO BOX DEPARTMENT =================================================================
    */

    @Override
    public List<ICommonIdCodeName> getComboBoxDepartment(String departmentName) {
        return comboBoxRepository.getComboBoxDepartment(departmentName);
    }

    /*
      ==== COMBO BOX PATIENT =================================================================
    */

    @Override
    public List<ICommonIdCodeName> getComboBoxPatient(String patientName) {
        return comboBoxRepository.getComboBoxPatient(patientName);
    }


    /*
      ==== COMBO BOX SURGERY =================================================================
     */

    /**
     * Lấy danh sách các vai trò trong ca phẫu thuật
     */
    @Override
    public List<IComboBoxSurgeryRoleType> getListSurgeryRoleType() {
        return comboBoxRepository.getListSurgeryRoleType();
    }

    @Override
    public List<ICommonIdCodeName> getListDiseaseGroup(String diseaseGroupName) {
        return comboBoxRepository.getListDiseaseGroup(diseaseGroupName);
    }

    /*
        ==== COMBO BOX SURGERY ROOM =================================================================
        */
    @Override
    public List<ICommonIdCodeName> getComboBoxSurgeryRoom(String surgeryRoomName) {
        return comboBoxRepository.getComboBoxSurgeryRoom(surgeryRoomName);
    }
}
