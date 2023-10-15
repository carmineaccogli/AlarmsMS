package it.unisalento.smartcitywastemanagement.alarmsms.service;


import it.unisalento.smartcitywastemanagement.alarmsms.domain.AlarmHistory;
import it.unisalento.smartcitywastemanagement.alarmsms.message.AlarmNotificationMessage;
import it.unisalento.smartcitywastemanagement.alarmsms.message.AlarmMessage;
import it.unisalento.smartcitywastemanagement.alarmsms.repositories.AlarmHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class CapacityAlarmsServiceImpl implements CapacityAlarmsService {

    @Autowired
    private AlarmHistoryRepository alarmHistoryRepository;

    @Autowired
    private WSRabbit_NotificationSender notificationSender;



    /** FUNZIONE PER GESTIRE GLI ALLARMI DI CAPACITA'
     *
     * 1 Controllo che nel db non esista già un allarme con stesso smartBinID e type che sia ancora da gestire
     *      1.1 Se Si, annullo la procedura
     * 2 Creo una nuova istanza di AlarmHistory con type = capacità e stato "da gestire"
     * 3 Salvo nel db
     * 4 Ottengo il numero di allarmi con stato "da gestire"
     * 5 Costruisco la notifica di capacità da inviare
     * 6 Comunico al frontend la notifica appena creata
     */

    public void manageCapacityAlarms(AlarmMessage message) {

        // 1
        boolean alarmAlreadyExists = alarmHistoryRepository.existsByTypeAndSmartBinIDAndHandled("Capacity", message.getSmartBinID(), false);
        if(alarmAlreadyExists)
            return;

        // 2
        AlarmHistory newAlarm = new AlarmHistory();
        newAlarm.setSmartBinID(message.getSmartBinID());
        newAlarm.setTimestamp(message.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        newAlarm.setType("Capacity");
        newAlarm.setSeverity(AlarmHistory.Severity.DANGER);
        newAlarm.setDescription(message.getDescription());
        newAlarm.setHandled(false);

        // 3
        alarmHistoryRepository.save(newAlarm);

        // 4
        int smartBinsToClean = alarmHistoryRepository.findByHandledAndType(false, "Capacity").size();

        // 5
        AlarmNotificationMessage capacityNotification = createCapacityNotification(smartBinsToClean);

        System.out.println(capacityNotification.getTitle());
        System.out.println(capacityNotification.getMessageType());
        System.out.println(capacityNotification.getDescription());
        System.out.println(capacityNotification.getTimestamp());

        // 6
        notificationSender.sendCapacityNotification(capacityNotification);
    }



    private AlarmNotificationMessage createCapacityNotification(int smartBinsToClean) {
        AlarmNotificationMessage capacityNotification = new AlarmNotificationMessage();

        capacityNotification.setTitle("Avviso saturazione capienza SmartBins");
        capacityNotification.setTimestamp(LocalDateTime.now());
        capacityNotification.setMessageType(AlarmHistory.Severity.DANGER.toString());

        String infoPart;
        if(smartBinsToClean > 1)
            infoPart = " SmartBins eccedono il limite di capienza.";
        else
            infoPart = " SmartBin eccede il limite di capienza.";

        capacityNotification.setDescription(smartBinsToClean + infoPart + " Si consiglia di istanziare un percorso di pulizia.");

        return capacityNotification;
    }


}
