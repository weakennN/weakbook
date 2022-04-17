package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE (first_name LIKE ?1 OR last_name LIKE ?1) AND id != ?3 LIMIT ?2", nativeQuery = true)
    List<User> getUserBySearch(String prefix, int limit, Long userId);
}
