package com.app20222.app20222_backend.services.statistics;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.dtos.statistics.INumberOfSurgeryByDiseaseGroup;
import com.app20222.app20222_backend.dtos.statistics.INumberSurgeryPreviewDTO;
import com.app20222.app20222_backend.dtos.statistics.MonthlySurgeryStatistics;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.enums.commons.TimeIntervalEnum;

@Service
public interface StatisticsService {

    /**
     * Lấy số ca phẫu thuật được thực hiện theo các mốc : tháng này, quý này, năm nay
     */
    INumberSurgeryPreviewDTO getPreviewCurrentNumberSurgery();

    /**
     * Thống kê dữ liệu ca phẫu thuật đã thực hiện theo nhóm bệnh (phục vụ PieChart)
     */
    List<INumberOfSurgeryByDiseaseGroup> getNumOfSurgeryByDiseaseGroup(TimeIntervalEnum timeInterval);

    /**
     * Lấy dữ liệu số ca phẫu thuật được thực hiện trong các tháng của một năm
     */
    List<MonthlySurgeryStatistics> getMonthSurgeryStatistics(Integer year);

    /**
     * Lấy danh sách thông tin các ca phẫu thuật theo mội khoảng thời gian nhất định ( phục vụ biểu đồ )
     */
    List<IGetListSurgery> getPreviewSurgeryList(Date startTime, Date endTime);

    /**
     * Xuất dữ liệu các ca phẫu thuật đã được thực hiện theo một bộ lọc
     */
    InputStreamResource exportPreviewSurgery(Date startTime, Date endTime) throws SQLException;
}
