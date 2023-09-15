package it.unisalento.smartcitywastemanagement.alarmsms.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.smartcitywastemanagement.alarmsms.message.AlarmNotificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSRabbit_NotificationSenderImpl implements WSRabbit_NotificationSender{


    @Value("${websocket.topic.capacityAlarm}")
    private String capacityAlarmTopic;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void sendCapacityNotification(AlarmNotificationMessage alarmMessage) {

        simpMessagingTemplate.convertAndSend(capacityAlarmTopic, alarmMessage);

    }
}
