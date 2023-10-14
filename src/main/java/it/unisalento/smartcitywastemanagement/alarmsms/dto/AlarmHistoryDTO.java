package it.unisalento.smartcitywastemanagement.alarmsms.dto;

import it.unisalento.smartcitywastemanagement.alarmsms.domain.AlarmHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AlarmHistoryDTO {

    private String Id;

    private String type;

    private String description;

    private LocalDateTime timestamp;

    private AlarmHistory.Severity severity;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public AlarmHistory.Severity getSeverity() {
        return severity;
    }

    public void setSeverity(AlarmHistory.Severity severity) {
        this.severity = severity;
    }
}
