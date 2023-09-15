package it.unisalento.smartcitywastemanagement.alarmsms.service;

import it.unisalento.smartcitywastemanagement.alarmsms.message.AlarmMessage;

public interface CleaningAlarmsService {

    void manageCleaningAlarms(AlarmMessage message);
}
