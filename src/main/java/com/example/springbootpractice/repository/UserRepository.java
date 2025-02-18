package com.example.springbootpractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootpractice.domain.Users;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Users save(Users user);
    List<Users> findByEmailAndPhone(String email, String phone);
    void deleteById(long id);
    
}
