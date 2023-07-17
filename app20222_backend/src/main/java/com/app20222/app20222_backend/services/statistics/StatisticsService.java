package com.app20222.app20222_backend.services.statistics;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.dtos.statistics.IMonthSurgeryStatistics;
import com.app20222.app20222_backend.dtos.statistics.INumberSurgeryPreviewDTO;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;

@Service
public interface StatisticsService {

    INumberSurgeryPreviewDTO getPreviewNumberSurgery();

    List<IMonthSurgeryStatistics> getMonthSurgeryStatistics(Integer year);

    List<IGetListSurgery> getPreviewSurgeryList(Date startTime, Date endTime);
}
