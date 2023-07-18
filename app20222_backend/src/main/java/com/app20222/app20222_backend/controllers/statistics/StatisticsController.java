package com.app20222.app20222_backend.controllers.statistics;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app20222.app20222_backend.dtos.statistics.IMonthSurgeryStatistics;
import com.app20222.app20222_backend.dtos.statistics.INumberSurgeryPreviewDTO;
import com.app20222.app20222_backend.dtos.statistics.MonthlySurgeryStatistics;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.services.statistics.StatisticsService;
import com.app20222.app20222_backend.utils.DateUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/statistics")
@Slf4j
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/preview-number-surgery")
    @Operation(description = "Lấy số ca phẫu thuật được thực hiện theo các mốc : tháng này, quý này, năm nay")
    public INumberSurgeryPreviewDTO getPreviewCurrentNumberSurgery() {
        return null;
    }


    @GetMapping("/preview-statistics-monthly")
    @Operation(description = "Lấy dữ liệu số ca phẫu thuật được thực hiện trong các tháng của một năm")
    public List<MonthlySurgeryStatistics> getMonthSurgeryStatistics(@RequestParam(name = "year") Integer year) {
        return statisticsService.getMonthSurgeryStatistics(year);
    }


    @GetMapping("/preview-list-surgery")
    @Operation(description = "Lấy danh sách thông tin các ca phẫu thuật theo mội khoảng thời gian nhất định ( phục vụ biểu đồ )")
    public List<IGetListSurgery> getPreviewSurgeryList(
        @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH)
        @RequestParam(name = "startTime") Date startTime,
        @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH)
        @RequestParam(name = "endTime") Date endTime
    ) {
        return null;
    }


}
