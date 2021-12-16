package ru.snapgot.coolhairstyle.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.snapgot.coolhairstyle.model.Record;
import ru.snapgot.coolhairstyle.model.RecordStatus;
import ru.snapgot.coolhairstyle.model.dto.RecordDto;

import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public interface RecordRepository extends JpaRepository<Record, Long> {
    @Query("SELECT " +
            "r.id AS id," +
            "r.barber.name AS barberName," +
            "r.client.name AS clientName," +
            "r.service.id AS serviceId," +
            "r.service.name AS serviceName," +
            "r.service.description AS serviceDescription," +
            "r.service.price AS servicePrice," +
            "r.recordStatus AS recordStatus FROM Record r where r.client.username = :username")
    List<RecordDto> findAllByClientUsername(@Param("username") String username);

    @Query("SELECT " +
            "r.id AS id," +
            "r.barber.name AS barberName," +
            "r.client.name AS clientName," +
            "r.service.id AS serviceId," +
            "r.service.name AS serviceName," +
            "r.service.description AS serviceDescription," +
            "r.service.price AS servicePrice," +
            "r.recordStatus AS recordStatus FROM Record r where r.barber.username = :username")
    List<RecordDto> findAllByBarberUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE Record r SET r.recordStatus = :status where r.id = :id")
    void updateAccept(@Param("id") long id,@Param("status") RecordStatus recordStatus);
}
