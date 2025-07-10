package com.SCMS.SCMS.repository.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SCMS.SCMS.entities.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

       
        static String queryAllUsers = "SELECT * FROM users " +
                        "WHERE is_active = 1 " +
                        "AND fullname LIKE CONCAT('%', :search, '%') " +
                        "AND (:joinDate IS NULL OR join_date = :joinDate) " +
                        "ORDER BY fullname " +
                        "LIMIT :start, :length";

        static String queryCountAllUsers = "SELECT COUNT(id) FROM users " +
                        "WHERE is_active = 1 " +
                        "AND fullname LIKE CONCAT('%', :search, '%') " +
                        "AND (:joinDate IS NULL OR join_date = :joinDate)";

        static String queryUsersByEmail = "SELECT * FROM users " +
                        "WHERE TRIM(LOWER(email)) = LOWER(:email) " +
                        "AND is_active = 1 " +
                        "LIMIT 1";

        static String queryUsersByFullname = "SELECT * FROM users " +
                        "WHERE TRIM(LOWER(fullname)) = LOWER(:fullname) " +
                        "AND is_active = 1 " +
                        "LIMIT 1";

        static String queryUsers = "SELECT * FROM users " +
                        "WHERE DAY(join_date) = :day AND MONTH(join_date) = :month " +
                        "AND is_active = 1";

        static String querygetUsersId = "SELECT * FROM users " +
                        "WHERE id = :id " +
                        "AND is_active = 1";

        @Query(value = querygetUsersId, nativeQuery = true)
        Users findByUsersId(@Param("id") int id);

        @Query(value = queryUsers, nativeQuery = true)
        List<Users> findUsers(@Param("day") int day, @Param("month") int month);

        @Query(value = queryUsersByEmail, nativeQuery = true)
        Users findByEmail(@Param("email") String email);

        @Query(value = queryUsersByFullname, nativeQuery = true)
        Users findUsersByFullname(@Param("fullname") String fullname);

        @Query(value = queryAllUsers, nativeQuery = true)
        List<Users> findAllUsers(
                        @Param("start") int start,
                        @Param("length") int length,
                        @Param("search") String search,
                        @Param("joinDate") String joinDate);

        @Query(value = queryCountAllUsers, nativeQuery = true)
        int countAllUsers(@Param("search") String search, @Param("joinDate") String joinDate);

        Users findByIdAndIsActive(int id, int isActive);
}
