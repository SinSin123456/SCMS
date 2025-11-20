package com.SCMS.SCMS.repository.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SCMS.SCMS.entities.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

        boolean existsByUsername(String username);

        @Query("SELECT u FROM Users u WHERE " +
                        "(:search IS NULL OR u.fullname LIKE %:search% OR u.username LIKE %:search% OR u.email LIKE %:search%) "
                        +
                        "AND u.status = true")
        List<Users> findAllUsers(@Param("search") String search, Pageable pageable);

        @Query(value = "SELECT COUNT(*) FROM users u " +
                        "WHERE (:search IS NULL OR u.fullname LIKE CONCAT('%', :search, '%') " +
                        "OR u.username LIKE CONCAT('%', :search, '%') " +
                        "OR u.email LIKE CONCAT('%', :search, '%')) " +
                        "AND u.status = true", nativeQuery = true)
        int countAllUsers(@Param("search") String search);

        static final String querygetusersId = "SELECT * FROM users WHERE id = :id AND status = true";

        @Query(value = querygetusersId, nativeQuery = true)
        Users findUsersById(@Param("id") int id);

        @Query("SELECT u FROM Users u WHERE u.status = true")
        List<Users> findAllActiveUsers();

        Optional<Users> findByIdAndRole(Long id, String role);

}
