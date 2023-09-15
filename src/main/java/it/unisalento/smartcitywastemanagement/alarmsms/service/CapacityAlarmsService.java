package it.unisalento.smartcitywastemanagement.alarmsms.service;

import it.unisalento.smartcitywastemanagement.alarmsms.domain.AlarmHistory;
import it.unisalento.smartcitywastemanagement.alarmsms.message.AlarmMessage;
import it.unisalento.smartcitywastemanagement.alarmsms.message.AlarmNotificationMessage;

public interface CapacityAlarmsService {

    void manageCapacityAlarms(AlarmMessage message);

    AlarmNotificationMessage createCapacityNotification(int smartBinsToClean);
}
