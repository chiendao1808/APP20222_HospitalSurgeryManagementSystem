package com.app20222.app20222_backend.services.surgery;

import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.dtos.requests.surgery.SurgeryCreateDTO;

@Service
public interface SurgeryService {

    /**
     * Create a surgery
     */
    void createSurgery(SurgeryCreateDTO createDTO);

}
