package com.app20222.app20222_backend.controllers.surgeryRoom;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app20222.app20222_backend.dtos.surgeryRoom.IGetListSurgeryRoom;
import com.app20222.app20222_backend.dtos.surgeryRoom.SurgeryRoomCreateDTO;
import com.app20222.app20222_backend.dtos.surgeryRoom.SurgeryRoomUpdateDTO;
import com.app20222.app20222_backend.services.surgeryRoom.SurgeryRoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/surgery-room")
@Slf4j
public class SurgeryRoomController {


    @Autowired
    private SurgeryRoomService surgeryRoomService;


    @PostMapping
    @Operation(description = "Tạo phòng phẫu thuật mới")
    public void createSurgeryRoom(@RequestBody @Validated SurgeryRoomCreateDTO createDTO) {
        surgeryRoomService.createSurgeryRoom(createDTO);
    }


    @PutMapping
    @Operation(description = "Cập nhật phòng phẫu thuật mới")
    public void updateSurgeryRoom(@RequestParam(name = "id") Long id,
        @RequestBody @Validated SurgeryRoomUpdateDTO updateDTO) {
        surgeryRoomService.updateSurgeryRoom(id, updateDTO);
    }

    @GetMapping("/get-list")
    @Operation(description = "Lấy danh sách phòng phẫu thuật")
    public List<IGetListSurgeryRoom> getListSurgeryRoom(
            @RequestParam(name = "id", required = false, defaultValue = "-1") Long id,
            @RequestParam(name = "code", required = false, defaultValue = "") String code,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "current_available", required = false, defaultValue = "true") Boolean currentAvailable
    ) {
        return surgeryRoomService.getListSurgeryRoom(id, code, name, currentAvailable);
    }


}
