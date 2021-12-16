package ru.snapgot.coolhairstyle.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.snapgot.coolhairstyle.model.Service;
import ru.snapgot.coolhairstyle.model.dto.ServiceDto;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Query("SELECT s.id AS id, s.name AS name, s.description AS description, s.price AS price FROM Service s where s.barber.username = :username")
    List<ServiceDto> findAllByBarber(@Param("username") String username);

    List<ServiceDto> findAllByBarber_id(Long barberId);
}
