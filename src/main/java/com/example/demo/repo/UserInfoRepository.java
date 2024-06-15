package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This is an interface that extends the JpaRepository interface
 * Spring will implement the methods for us
 * JpaRepository is a generic interface that takes two parameters
 * 1. the entity class
 * 2. the type of the primary key
 * JpaRepository provides CRUD operations
 * the bean scope is singleton
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    /** SOME EXAMPLES:
     *  defining some queries using the method names
     *  Spring will implement the method for us based on the method name
     *  https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
     */
    List<UserInfo> findByUserName(String userName);
    List<UserInfo> findUserByUserName(String userName);
    List<UserInfo> findByEmail(String email);
    List<UserInfo> findByUserNameAndEmail(String userName, String email);
    List<UserInfo> findFirst10ByOrderByUserNameDesc(); // find first 10 users ordered by userName desc

}
