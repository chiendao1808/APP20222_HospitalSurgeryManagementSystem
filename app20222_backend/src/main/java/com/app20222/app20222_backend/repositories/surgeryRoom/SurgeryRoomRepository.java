package com.app20222.app20222_backend.repositories.surgeryRoom;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.constants.sql.surgeryRoom.SQLSurgeryRoom;
import com.app20222.app20222_backend.dtos.surgeryRoom.IGetListSurgeryRoom;
import com.app20222.app20222_backend.entities.surgeryRoom.SurgeryRoom;

@Repository
public interface SurgeryRoomRepository extends JpaRepository<SurgeryRoom, Long> {

    Boolean existsByCode(String code);

    @Query(nativeQuery = true, value = SQLSurgeryRoom.GET_LIST_SURGERY_ROOM)
    List<IGetListSurgeryRoom> getListSurgeryRoom(Long id, String code, String name, Boolean currentAvailable);
}
