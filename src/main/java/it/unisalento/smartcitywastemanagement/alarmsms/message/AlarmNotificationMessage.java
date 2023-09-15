package it.unisalento.smartcitywastemanagement.alarmsms.message;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AlarmNotificationMessage {


    private String title;

    private String description;

    private LocalDateTime timestamp;

    private String messageType;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
