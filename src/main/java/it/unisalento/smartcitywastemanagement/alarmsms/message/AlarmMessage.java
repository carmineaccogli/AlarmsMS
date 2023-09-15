package it.unisalento.smartcitywastemanagement.alarmsms.message;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlarmMessage {

    @NotBlank(message = "smartBinID required")
    private String smartBinID;

    @NotNull(message = "timestamp required")
    private Date timestamp;

    private String description;


    public String getSmartBinID() {
        return smartBinID;
    }

    public void setSmartBinID(String smartBinID) {
        this.smartBinID = smartBinID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
