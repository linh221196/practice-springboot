package com.example.springbootpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootpractice.domain.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User save(User user);
    List<User> findByEmailAndPhone(String email, String phone);

}
