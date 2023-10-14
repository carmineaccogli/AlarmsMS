package it.unisalento.smartcitywastemanagement.alarmsms.service;

import it.unisalento.smartcitywastemanagement.alarmsms.domain.AlarmHistory;
import it.unisalento.smartcitywastemanagement.alarmsms.repositories.AlarmHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmHistoryServiceImpl implements AlarmHistoryService {

    @Autowired
    private AlarmHistoryRepository alarmHistoryRepository;

    public List<AlarmHistory> getAllAlarms() {
        return alarmHistoryRepository.findAll();
    }
}
