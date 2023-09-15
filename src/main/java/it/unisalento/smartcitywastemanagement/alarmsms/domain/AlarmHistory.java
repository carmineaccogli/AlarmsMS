package it.unisalento.smartcitywastemanagement.alarmsms.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document("alarmHistory")
public class AlarmHistory {


    @Id
    private String id;

    private String type;

    private String description;

    private LocalDateTime timestamp;

    private Severity severity;

    private boolean handled;

    private String smartBinID;


    public enum Severity {
        WARNING, DANGER, SUCCESS
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public String getSmartBinID() {
        return smartBinID;
    }

    public void setSmartBinID(String smartBinID) {
        this.smartBinID = smartBinID;
    }
}
