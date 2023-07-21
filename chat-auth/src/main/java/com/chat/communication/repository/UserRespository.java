package com.chat.communication.repository;

import com.chat.communication.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRespository extends JpaRepository<User,Integer> {


    Optional<User> findUserByEmail(String email);

}
