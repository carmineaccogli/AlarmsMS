package it.unisalento.smartcitywastemanagement.alarmsms.repositories;

import it.unisalento.smartcitywastemanagement.alarmsms.domain.AlarmHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmHistoryRepository extends MongoRepository<AlarmHistory, String> {


    boolean existsByTypeAndSmartBinIDAndHandled(String type, String smartBinID, boolean handled);

    List<AlarmHistory> findByHandledAndType(boolean handled,String type);

    Optional<AlarmHistory> findBySmartBinIDAndTypeAndHandled(String smartBinID, String type, boolean handled);
}
