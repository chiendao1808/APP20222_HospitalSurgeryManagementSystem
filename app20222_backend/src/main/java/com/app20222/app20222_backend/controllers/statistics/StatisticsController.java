package com.app20222.app20222_backend.controllers.statistics;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app20222.app20222_backend.dtos.statistics.INumberOfSurgeryByDiseaseGroup;
import com.app20222.app20222_backend.dtos.statistics.INumberSurgeryPreviewDTO;
import com.app20222.app20222_backend.dtos.statistics.MonthlySurgeryStatistics;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.enums.commons.TimeIntervalEnum;
import com.app20222.app20222_backend.services.statistics.StatisticsService;
import com.app20222.app20222_backend.utils.DateUtils;
import com.app20222.app20222_backend.utils.excelFile.ExcelFileUtils;
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
        return statisticsService.getPreviewCurrentNumberSurgery();
    }

    @GetMapping("/get-no-surgery-by-disease-group")
    @Operation(description = "Thống kê dữ liệu ca phẫu thuật đã thực hiện theo nhóm bệnh (phục vụ PieChart)")
    public List<INumberOfSurgeryByDiseaseGroup> getNumOfSurgeryByDiseaseGroup(@RequestParam(name = "interval")TimeIntervalEnum timeInterval){
        return statisticsService.getNumOfSurgeryByDiseaseGroup(timeInterval);
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
        @RequestParam(name = "startTime", required = false, defaultValue = "01/01/1970") Date startTime,
        @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH)
        @RequestParam(name = "endTime", required = false, defaultValue = "01/01/1970") Date endTime
    ) {
        return statisticsService.getPreviewSurgeryList(startTime, endTime);
    }


    @GetMapping("/export-preview-surgery")
    @Operation(description = "Xuất dữ liệu danh sách ca phẫu thuật")
    public ResponseEntity<InputStreamResource> exportPreviewSurgery(
        @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH)
        @RequestParam(name = "startTime", required = false, defaultValue = "01/01/1970") Date startTime,
        @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH)
        @RequestParam(name = "endTime", required = false, defaultValue = "01/01/1970") Date endTime
    ) throws SQLException {
        return ExcelFileUtils.getResponseCSVStream(statisticsService.exportPreviewSurgery(startTime, endTime));
    }


}
