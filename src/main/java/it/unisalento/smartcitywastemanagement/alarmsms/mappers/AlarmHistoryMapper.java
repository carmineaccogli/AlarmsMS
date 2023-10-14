package it.unisalento.smartcitywastemanagement.alarmsms.mappers;

import it.unisalento.smartcitywastemanagement.alarmsms.domain.AlarmHistory;
import it.unisalento.smartcitywastemanagement.alarmsms.dto.AlarmHistoryDTO;
import org.springframework.stereotype.Component;

@Component
public class AlarmHistoryMapper {


    public AlarmHistoryDTO fromAlarmHistoryToDTO(AlarmHistory alarmHistory) {

        AlarmHistoryDTO alarmHistoryDTO = new AlarmHistoryDTO();

        alarmHistoryDTO.setId(alarmHistory.getId());
        alarmHistoryDTO.setType(alarmHistory.getType());
        alarmHistoryDTO.setDescription(alarmHistory.getDescription());
        alarmHistoryDTO.setTimestamp(alarmHistory.getTimestamp());
        alarmHistoryDTO.setSeverity(alarmHistory.getSeverity());

        return alarmHistoryDTO;
    }
}
