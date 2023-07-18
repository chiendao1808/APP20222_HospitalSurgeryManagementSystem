package com.app20222.app20222_backend.services.statistics;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.dtos.statistics.INumberSurgeryPreviewDTO;
import com.app20222.app20222_backend.dtos.statistics.MonthlySurgeryStatistics;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;

@Service
public interface StatisticsService {

    /**
     * Lấy số ca phẫu thuật được thực hiện theo các mốc : tháng này, quý này, năm nay
     */
    INumberSurgeryPreviewDTO getPreviewCurrentNumberSurgery();

    /**
     * Lấy dữ liệu số ca phẫu thuật được thực hiện trong các tháng của một năm
     */
    List<MonthlySurgeryStatistics> getMonthSurgeryStatistics(Integer year);

    /**
     * Lấy danh sách thông tin các ca phẫu thuật theo mội khoảng thời gian nhất định ( phục vụ biểu đồ )
     */
    List<IGetListSurgery> getPreviewSurgeryList(Date startTime, Date endTime);
}
