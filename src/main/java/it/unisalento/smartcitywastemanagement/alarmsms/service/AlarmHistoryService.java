package it.unisalento.smartcitywastemanagement.alarmsms.service;

import it.unisalento.smartcitywastemanagement.alarmsms.domain.AlarmHistory;

import java.util.List;

public interface AlarmHistoryService {

    List<AlarmHistory> getAllAlarms();
}
