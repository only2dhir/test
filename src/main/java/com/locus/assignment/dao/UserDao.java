package com.locus.assignment.dao;

import com.locus.assignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Modifying
    @Query(value = "DELETE FROM USER_ROLES WHERE USER_ID =:userId and ROLE_ID  =:roleId", nativeQuery = true)
    void removeUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Query(value = "INSERT INTO USER_ROLES (USER_ID, ROLE_ID) VALUES (?0, ?1)", nativeQuery = true)
    void addUserRole(Integer userId, Integer roleId);
}
