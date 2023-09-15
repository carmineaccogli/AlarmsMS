package it.unisalento.smartcitywastemanagement.alarmsms.service;

import it.unisalento.smartcitywastemanagement.alarmsms.message.AlarmNotificationMessage;

public interface WSRabbit_NotificationSender {


    void sendCapacityNotification(AlarmNotificationMessage alarmMessage);
}
