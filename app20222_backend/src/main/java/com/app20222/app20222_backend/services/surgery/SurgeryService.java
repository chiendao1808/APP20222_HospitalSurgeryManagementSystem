package com.app20222.app20222_backend.services.surgery;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.dtos.surgery.SurgeryCreateDTO;
import com.app20222.app20222_backend.dtos.surgery.SurgeryDetailDTO;
import com.app20222.app20222_backend.dtos.surgery.SurgeryUpdateDTO;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.enums.surgery.SurgeryStatusEnum;

@Service
public interface SurgeryService {

    /**
     * Create a surgery
     */
    void createSurgery(SurgeryCreateDTO createDTO);

    /**
     * Update a surgery
     */
    void updateSurgery(Long surgeryId, SurgeryUpdateDTO updateDTO);

    List<IGetListSurgery> getListSurgery(Long surgeryId, String surgeryName, Long patientId, Long diseaseGroupId, Long surgeryRoomId,
        SurgeryStatusEnum status, Date startedAt, Date estimatedEndAt);

    /**
     * Lấy thông tin chi tiết ca phẫu thuật
     */
    SurgeryDetailDTO getDetailSurgery(Long surgeryId);


    /**
     * Xóa ca phẫu thuật
     */
    void deleteSurgery(Long surgeryId);
}
