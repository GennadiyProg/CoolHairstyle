package ru.snapgot.coolhairstyle.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.snapgot.coolhairstyle.model.Comment;
import ru.snapgot.coolhairstyle.model.dto.CommentDto;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT " +
            "c.id AS id, " +
            "c.description AS description, " +
            "c.barber.name AS barberName, " +
            "c.client.name AS clientName " +
            "FROM Comment c where c.barber.id = :barberId")
    List<CommentDto> findAllByBarber_id(@Param("barberId") Long id);

    @Query("SELECT " +
            "c.id AS id, " +
            "c.description AS description, " +
            "c.barber.name AS barberName, " +
            "c.client.name AS clientName " +
            "FROM Comment c where c.barber.username = :username")
    List<CommentDto> findAllByBarberUsername(@Param("username") String username);
}
