package com.simpleform.service;

import com.simpleform.model.AdminEnum;
import com.simpleform.model.GenderEnum;
import com.simpleform.model.UsersModel;
import com.simpleform.repository.UsersRepository;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {


    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public UsersModel registerUser(String login, String password, String Email, String mobile, GenderEnum Gender, AdminEnum selectedRole) {
        if (login == null || password == null) {
            return null;
        } else {
            UsersModel usersModel = new UsersModel();
            usersModel.setLogin(login);
            usersModel.setPassword(password);
            usersModel.setEmail(Email);
            usersModel.setMobile(mobile);
            usersModel.setGender(Gender);

            // Check if the registered user is you (admin)
            if ("Shivank".equals(login)) {
                usersModel.setRole(AdminEnum.ADMIN);
            } else {
                usersModel.setRole(AdminEnum.USER);
            }

            return usersRepository.save(usersModel);
        }
    }


    public UsersModel authenticate(String login,String password) {

        return usersRepository.findByLoginAndPassword(login, password).orElse(null);

    }

    public UsersModel findByLogin(String login) {
        return usersRepository.findByLogin(login).orElse(null);
    }

    public void updatePassword(UsersModel user, String newPassword) {
        user.setPassword(newPassword); // Set the new password
        usersRepository.save(user);    // Save the updated user entity
    }



    public void updateUser(UsersModel user) {
        usersRepository.save(user);
    }


    public void deleteUser(Integer userId) {
        usersRepository.deleteById(userId);
    }

    public List<UsersModel> getAllUsers() {
        return usersRepository.findAll();
    }




}
