package com.simpleform.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Users_table")
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer Id;

    @Column
    String login;
    @Column
    String password;
    @Column
    String Email;

    @Column
    private String mobile;

    @Enumerated(EnumType.STRING) // Specify that Gender is an Enum and store it as a string
    GenderEnum Gender; //GenderEnum which acts like a set of predefined choices: "MALE" and "FEMALE".

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AdminEnum role = AdminEnum.ADMIN; // Assign the ADMIN role to your account



    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public GenderEnum getGender() {
        return Gender;
    }

    public void setGender(GenderEnum gender) {
        Gender = gender;
    }

    public AdminEnum getRole() {
        return role;
    }

    public void setRole(AdminEnum role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersModel that = (UsersModel) o;
        return Objects.equals(Id, that.Id) && Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(Email, that.Email) && Objects.equals(mobile, that.mobile) && Gender == that.Gender && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, login, password, Email, mobile, Gender, role);
    }

    @Override
    public String toString() {
        return "UsersModel{" +
                "Id=" + Id +
                ", login='" + login + '\'' +
                ", Email='" + Email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", Gender=" + Gender +
                ", role=" + role +
                '}';
    }
}




