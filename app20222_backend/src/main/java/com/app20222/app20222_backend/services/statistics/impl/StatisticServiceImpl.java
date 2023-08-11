package com.app20222.app20222_backend.services.statistics.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.constants.sql.statistics.SQLStatistics;
import com.app20222.app20222_backend.dtos.statistics.IMonthSurgeryStatistics;
import com.app20222.app20222_backend.dtos.statistics.INumberOfSurgeryByDiseaseGroup;
import com.app20222.app20222_backend.dtos.statistics.INumberSurgeryPreviewDTO;
import com.app20222.app20222_backend.dtos.statistics.MonthlySurgeryStatistics;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.enums.commons.TimeIntervalEnum;
import com.app20222.app20222_backend.repositories.statistics.StatisticsRepository;
import com.app20222.app20222_backend.services.permission.PermissionService;
import com.app20222.app20222_backend.services.statistics.StatisticsService;
import com.app20222.app20222_backend.utils.DateUtils;
import com.app20222.app20222_backend.utils.StringUtils;
import com.app20222.app20222_backend.utils.excelFile.ExcelFileUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatisticServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private PermissionService permissionService;

    @Value("${spring.datasource.hikari.schema}.")
    private String defaultSchema;

    @Override
    public INumberSurgeryPreviewDTO getPreviewCurrentNumberSurgery() {
        Set<Long> lstViewableSurgeryId = permissionService.getLstViewableSurgeryId();
        return statisticsRepository.getPreviewCurrentNumberSurgery(lstViewableSurgeryId);
    }

    @Override
    public List<INumberOfSurgeryByDiseaseGroup> getNumOfSurgeryByDiseaseGroup(TimeIntervalEnum timeInterval) {
        Set<Long> lstViewableSurgeryId = permissionService.getLstViewableSurgeryId();
        return statisticsRepository.getListNumberOfSurgeryByDiseaseGroupAndInterval(lstViewableSurgeryId, timeInterval.getName());
    }

    @Override
    public List<MonthlySurgeryStatistics> getMonthSurgeryStatistics(Integer year) {
        Set<Long> lstViewableSurgeryId = permissionService.getLstViewableSurgeryId();
        SimpleDateFormat formatter = new SimpleDateFormat(DateUtils.FORMAT_DATE_DD_MM_YY_HH_MM_SS);
        String firstDateOfYear = DateUtils.SEARCH_FIRST_YEAR_DAY_SLASH_PREFIX + year + DateUtils.SEARCH_START_DATE_SUFFIX;
        String lastDateOfYear = DateUtils.SEARCH_LAST_YEAR_DAY_SLASH_PREFIX + year + DateUtils.SEARCH_END_DATE_SUFFIX;
        try {
            // Lấy dữ liệu ca phẫu thuật trong db cùng với tháng
            List<IMonthSurgeryStatistics> lstMonthlySurgeryData = statisticsRepository.getMonthSurgeryStatistics(lstViewableSurgeryId,
                formatter.parse(firstDateOfYear), formatter.parse(lastDateOfYear));
            // Mapping dữ liệu ca phẫu thuật đã được thực hiện theo tháng theo tháng
            Map<Integer, Integer> mapStatisticsData = new LinkedHashMap<>();
            lstMonthlySurgeryData.forEach(data -> mapStatisticsData.put(data.getMonth(), data.getNumSurgery()));
            List<MonthlySurgeryStatistics> response = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                response.add(new MonthlySurgeryStatistics(i, mapStatisticsData.get(i) != null ? mapStatisticsData.get(i) : 0));
            }
            return response;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<IGetListSurgery> getPreviewSurgeryList(Date startTime, Date endTime) {
        // Lấy danh sách id các ca phẫu thuật có quyền xem
        Set<Long> lstViewableSurgeryId = permissionService.getLstViewableSurgeryId();
        return statisticsRepository.getPreviewSurgeryList(lstViewableSurgeryId, startTime, endTime);
    }

    @Override
    public InputStreamResource exportPreviewSurgery(Date startTime, Date endTime) throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.FORMAT_YYYY_MM_DD);
        String startTimeStr = dateFormat.format(startTime);
        String endTimeStr = dateFormat.format(endTime);

        // Lấy danh sách id các ca phẫu thuật có quyền xem
        Set<Long> lstViewableSurgeryId = permissionService.getLstViewableSurgeryId();
        String lstViewableSurgeryIdStr = lstViewableSurgeryId.stream().map(item -> String.valueOf(item)).collect(Collectors.joining(","));

        // Build header query
        String[] headers = {"Tên ca phẫu thuật", "Mã ca phẫu thuật", "Nhóm bệnh", "Loại ca phẫu thuật", "Tên bệnh nhân", "Mã bệnh nhân",
            "Phòng phẫu thuật", "Trạng thái ca phẫu thuật", "Thời gian bắt đầu", "Thời gian kết thúc dự kiến", "Thời gian kết thúc", "Kết quả"};
        String headerQuery = "SELECT " + Arrays.stream(headers).map(item -> StringUtils.formatSqlText(item)).collect(Collectors.joining(", "));

        String contentQuery = SQLStatistics.EXPORT_PREVIEW_SURGERY_QUERY
            .replace("{h-schema}", defaultSchema)
            .replace(":lstViewableSurgeryId", lstViewableSurgeryIdStr)
            .replace(":startTime", StringUtils.formatSqlText(startTimeStr))
            .replace(":endTime", StringUtils.formatSqlText(endTimeStr));
        return ExcelFileUtils.createCSVFile(headerQuery, contentQuery);
    }
}
