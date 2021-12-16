package ru.snapgot.coolhairstyle.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.snapgot.coolhairstyle.model.*;
import ru.snapgot.coolhairstyle.model.dto.UserDto;

import java.util.List;

@Repository
@Transactional(isolation = Isolation.READ_COMMITTED)
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u.id AS id, u.name AS name, u.photoUrl AS photoUrl, u.city AS city from User u where u.id = :id")
    UserDto getBarber(@Param("id") Long id);

    User findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.name = :name, u.photoUrl = :photoUrl, u.city = :city where u.username = :username")
    void updateDescription(@Param("username") String username,
                           @Param("name") String name,
                           @Param("photoUrl") String photoUrl,
                           @Param("city") String city);

    @Query("select u.id AS id, u.name AS name, u.photoUrl AS photoUrl, u.city AS city from User u where u.city = :city and u.role = :role")
    List<UserDto> getNearestBarber(@Param("city") String requiredCity,@Param("role") Role role);
}
