package com.app20222.app20222_backend.services.statistics.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.dtos.statistics.IMonthSurgeryStatistics;
import com.app20222.app20222_backend.dtos.statistics.INumberSurgeryPreviewDTO;
import com.app20222.app20222_backend.dtos.surgery.IGetListSurgery;
import com.app20222.app20222_backend.repositories.statistics.StatisticsRepository;
import com.app20222.app20222_backend.services.statistics.StatisticsService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatisticServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public INumberSurgeryPreviewDTO getPreviewNumberSurgery() {
        return null;
    }

    @Override
    public List<IMonthSurgeryStatistics> getMonthSurgeryStatistics(Integer year) {
        return null;
    }

    @Override
    public List<IGetListSurgery> getPreviewSurgeryList(Date startTime, Date endTime) {
        return null;
    }
}
