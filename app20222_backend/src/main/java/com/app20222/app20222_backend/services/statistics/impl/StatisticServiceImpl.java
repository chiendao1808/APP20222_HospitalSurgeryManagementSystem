package com.app20222.app20222_backend.services.statistics.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.dtos.statistics.IMonthSurgeryStatistics;
import com.app20222.app20222_backend.dtos.statistics.INumberSurgeryPreviewDTO;
import com.app20222.app20222_backend.dtos.statistics.MonthlySurgeryStatistics;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.repositories.statistics.StatisticsRepository;
import com.app20222.app20222_backend.services.permission.PermissionService;
import com.app20222.app20222_backend.services.statistics.StatisticsService;
import com.app20222.app20222_backend.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatisticServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public INumberSurgeryPreviewDTO getPreviewCurrentNumberSurgery() {
        return statisticsRepository.getPreviewCurrentNumberSurgery();
    }

    @Override
    public List<MonthlySurgeryStatistics> getMonthSurgeryStatistics(Integer year) {
        SimpleDateFormat formatter = new SimpleDateFormat(DateUtils.FORMAT_DATE_DD_MM_YY_HH_MM_SS);
        String firstDateOfYear = DateUtils.SEARCH_FIRST_YEAR_DAY_SLASH_PREFIX + year + DateUtils.SEARCH_START_DATE_SUFFIX;
        String lastDateOfYear = DateUtils.SEARCH_LAST_YEAR_DAY_SLASH_PREFIX + year + DateUtils.SEARCH_END_DATE_SUFFIX;
        try {
            // Lấy dữ liệu ca phẫu thuật trong db cùng với tháng
            List<IMonthSurgeryStatistics> lstMonthlySurgeryData = statisticsRepository.getMonthSurgeryStatistics(
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
}
