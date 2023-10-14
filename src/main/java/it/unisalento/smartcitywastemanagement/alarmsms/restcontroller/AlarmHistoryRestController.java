package it.unisalento.smartcitywastemanagement.alarmsms.restcontroller;


import it.unisalento.smartcitywastemanagement.alarmsms.domain.AlarmHistory;
import it.unisalento.smartcitywastemanagement.alarmsms.dto.AlarmHistoryDTO;
import it.unisalento.smartcitywastemanagement.alarmsms.mappers.AlarmHistoryMapper;
import it.unisalento.smartcitywastemanagement.alarmsms.service.AlarmHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value="/api/alarms")
public class AlarmHistoryRestController {

    @Autowired
    private AlarmHistoryMapper alarmHistoryMapper;

    @Autowired
    private AlarmHistoryService alarmHistoryService;


    @PreAuthorize("hasRole('ROLE_WasteManagementCompany')")
    @RequestMapping(value="/", method = RequestMethod.GET)
    public ResponseEntity<List<AlarmHistoryDTO>> getAllAlarms() {

        List<AlarmHistory> results = alarmHistoryService.getAllAlarms();

        List<AlarmHistoryDTO> allAlarms = new ArrayList<>();

        for (AlarmHistory alarm : results) {
            AlarmHistoryDTO alarmHistoryDTO = alarmHistoryMapper.fromAlarmHistoryToDTO(alarm);


            allAlarms.add(alarmHistoryDTO);
        }

        if (allAlarms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allAlarms);
    }

}
