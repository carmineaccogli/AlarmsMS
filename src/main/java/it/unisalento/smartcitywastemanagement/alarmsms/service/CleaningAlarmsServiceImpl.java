package it.unisalento.smartcitywastemanagement.alarmsms.service;

import it.unisalento.smartcitywastemanagement.alarmsms.domain.AlarmHistory;
import it.unisalento.smartcitywastemanagement.alarmsms.message.AlarmMessage;
import it.unisalento.smartcitywastemanagement.alarmsms.message.AlarmNotificationMessage;
import it.unisalento.smartcitywastemanagement.alarmsms.repositories.AlarmHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;


@Service
public class CleaningAlarmsServiceImpl implements CleaningAlarmsService{


    @Autowired
    private AlarmHistoryRepository alarmHistoryRepository;

    @Autowired
    private WSRabbit_NotificationSender notificationSender;




    /** FUNZIONE PER GESTIRE GLI "ALLARMI" DI AVVENUTA PULIZIA DELLO SMARTBIN
     *
     * 1 Ricerco nel db un allarme con type=Capacity, con stato da gestire con lo stesso smartBinID
     *          1.1 Se non trovo, interrompo la procedura (nessuna eccezione. Uno smartBin può essere svuotato anche se non ha
     *              raggiunto la soglia di avvertimento)
     * 2 Aggiorno l'alarmHistory trovato settando lo stato su "gestito"
     * 3 Creo una nuova istanza di alarmHistory che fa riferimento all'allarme di cleaning
     * 4 Salvo nel db
     * 5 Ottengo il numero di allarmi con stato "da gestire"
     * 6 Controllo se rimangono smartBins da notificare (quindi allarmi non gestiti di capacità)
     *      6.1 Se rimangono, creo l'oggetto notifica
     *      6.2 Comunico al frontend la notifica
     *
     *
     * @param message
     */

    public void manageCleaningAlarms(AlarmMessage message) {

        // 1
        AlarmHistory capacityAlarm = searchRelatedCapacityAlarm(message);
        if(capacityAlarm == null)
            return;

        // 2
        capacityAlarm.setHandled(true);

        // 3
        AlarmHistory cleaningAlarm = new AlarmHistory();
        cleaningAlarm.setSmartBinID(message.getSmartBinID());
        cleaningAlarm.setType("Cleaning");
        cleaningAlarm.setTimestamp(message.getTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        cleaningAlarm.setDescription(message.getDescription());
        cleaningAlarm.setSeverity(AlarmHistory.Severity.WARNING);
        cleaningAlarm.setHandled(true);

        // 4
        alarmHistoryRepository.save(capacityAlarm);
        alarmHistoryRepository.save(cleaningAlarm);

        // 5
        int smartBinsToClean = alarmHistoryRepository.findByHandledAndType(false,"Capacity").size();


        // 6.1
        AlarmNotificationMessage remainingSmartBinsToCleanMessage = createCleaningNotification(smartBinsToClean);

        System.out.println(remainingSmartBinsToCleanMessage.getTitle());
        System.out.println(remainingSmartBinsToCleanMessage.getMessageType());
        System.out.println(remainingSmartBinsToCleanMessage.getDescription());

        // 6.2
        notificationSender.sendCapacityNotification(remainingSmartBinsToCleanMessage);

    }

    private AlarmNotificationMessage createCleaningNotification(int smartBinsToClean) {
        AlarmNotificationMessage cleaningNotification = new AlarmNotificationMessage();

        cleaningNotification.setTitle("SmartBin Cleanup Alert");
        cleaningNotification.setTimestamp(LocalDateTime.now());
        cleaningNotification.setMessageType(AlarmHistory.Severity.SUCCESS.toString());

        String description = null;
        if(smartBinsToClean > 1)
            description = "Cleaning in progress. "+smartBinsToClean+" SmartBins remaining to empty";
        else if(smartBinsToClean == 1)
            description = "Cleaning in progress. "+smartBinsToClean+ " SmartBin left to empty";
        else if(smartBinsToClean == 0)
            description = "All notified SmartBins have been emptied";

        cleaningNotification.setDescription(description);

        return cleaningNotification;
    }




    private AlarmHistory searchRelatedCapacityAlarm(AlarmMessage message) {

        Optional<AlarmHistory> optionalAlarm = alarmHistoryRepository.findBySmartBinIDAndTypeAndHandled(message.getSmartBinID(), "Capacity", false);

        if(!optionalAlarm.isPresent())
            return null;

        return optionalAlarm.get();
    }
}
