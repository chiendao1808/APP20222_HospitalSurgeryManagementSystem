package com.app20222.app20222_backend.services.surgeryRoom;

import java.util.List;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.dtos.surgery_room.IGetListSurgeryRoom;
import com.app20222.app20222_backend.dtos.surgery_room.SurgeryRoomCreateDTO;
import com.app20222.app20222_backend.dtos.surgery_room.SurgeryRoomUpdateDTO;

@Service
public interface SurgeryRoomService {


    /**
     * Tạo phòng phẫu thuật
     */
    void createSurgeryRoom(SurgeryRoomCreateDTO createDTO);


    /**
     * Cập nhật thông tin phòng phẫu thuật
     */
    void updateSurgeryRoom(Long id, SurgeryRoomUpdateDTO updateDTO);


    /**
     * Lấy danh sách phòng phẫu thuật
     */
    List<IGetListSurgeryRoom> getListSurgeryRoom(Long id, String code, String name, Boolean currentAvailable);

}
