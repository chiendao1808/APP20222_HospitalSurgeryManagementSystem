package com.app20222.app20222_backend.repositories.surgery_room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app20222.app20222_backend.entities.surgery_room.SurgeryRoom;

@Repository
public interface SurgeryRoomRepository extends JpaRepository<SurgeryRoom, Long> {

}
