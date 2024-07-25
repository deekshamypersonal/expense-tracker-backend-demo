package com.expensetracker.expensetracker.io.repository;


import com.expensetracker.expensetracker.io.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    @Query("select user from User user where user.email=:email")
    User findByEmail(@Param("email") String email);



}
