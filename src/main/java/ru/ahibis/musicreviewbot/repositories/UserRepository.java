package ru.ahibis.musicreviewbot.repositories;

import ru.ahibis.musicreviewbot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}