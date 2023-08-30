package com.simpleform.repository;

import com.simpleform.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersModel, Integer> {  // for Default CRUD method for user



    Optional<UsersModel> findByLoginAndPassword(String login, String password);

    Optional<UsersModel> findByLogin(String login);
}
