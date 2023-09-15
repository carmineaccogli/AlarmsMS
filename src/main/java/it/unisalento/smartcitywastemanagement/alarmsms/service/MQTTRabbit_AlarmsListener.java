package it.unisalento.smartcitywastemanagement.alarmsms.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisalento.smartcitywastemanagement.alarmsms.message.AlarmMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MQTTRabbit_AlarmsListener {

    @Autowired
    private Validator validator;

    @Autowired
    private CapacityAlarmsService capacityAlarmsService;

    @Autowired
    private CleaningAlarmsService cleaningAlarmsService;


    /** FUNZIONE PER GESTIRE GLI ALLARMI MQTT
     *
     * 1 Conversione messaggio da stringa nella classe AlarmMessage
     * 2 Validazione dei campi del messaggio ricevuto
     * 3 Gestione del messaggio nell'apposita service class
     *
     * @param message
     */

    @ServiceActivator(inputChannel = "mqttInputChannelCapacityTopic")
    public void consumeCapacityAlarms(@Payload String message) throws Exception {

        System.out.println(message);

        AlarmMessage capacityMessage = doValidation(message);

        capacityAlarmsService.manageCapacityAlarms(capacityMessage);
    }



    @ServiceActivator(inputChannel = "mqttInputChannelCleaningTopic")
    public void consumeCleaningAlarms(@Payload String message) throws Exception {

        AlarmMessage cleaningMessage = doValidation(message);

        cleaningAlarmsService.manageCleaningAlarms(cleaningMessage);
    }





    private AlarmMessage doValidation(String message) throws Exception{

        // 1
        ObjectMapper objectMapper = new ObjectMapper();
        AlarmMessage alarmMessage = objectMapper.readValue(message, AlarmMessage.class);

        System.out.println(alarmMessage.getSmartBinID());
        System.out.println(alarmMessage.getDescription());
        System.out.println(alarmMessage.getTimestamp());

        // 2
        String validationError = isMessageValid(alarmMessage);
        if(validationError != null) {
            throw new AmqpRejectAndDontRequeueException(validationError);
        }

        return alarmMessage;
    }





    private String isMessageValid(AlarmMessage alarmMessage) {

        Set<ConstraintViolation<AlarmMessage>> violations = validator.validate(alarmMessage);

        StringBuilder errorMessage = new StringBuilder("Errori di validazione: ");
        for (ConstraintViolation<AlarmMessage> violation : violations) {
            errorMessage.append(violation.getMessage()).append(", ");
        }

        errorMessage.setLength(errorMessage.length() - 2);
        String errors = errorMessage.toString();

        if (!violations.isEmpty()) {
            return errors;
        }

        return null;
    }
}
