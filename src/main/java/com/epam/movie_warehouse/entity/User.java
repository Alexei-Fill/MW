package com.epam.movie_warehouse.entity;

import com.epam.movie_warehouse.enumiration.UserRole;

import java.time.LocalDate;
import java.util.Objects;

public class User {
    private long id;
    private String login;
    private String password;
    private String mail;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    private UserRole roleId;
    private String imageURL;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public UserRole getRoleId() {
        return roleId;
    }

    public void setRoleId(UserRole roleId) {
        this.roleId = roleId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "\n\nUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", \nbirthDate=" + birthDate +
                ", registrationDate=" + registrationDate +
                ", roleId=" + roleId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                roleId == user.roleId &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(mail, user.mail) &&
                Objects.equals(birthDate, user.birthDate) &&
                Objects.equals(registrationDate, user.registrationDate) &&
                Objects.equals(imageURL, user.imageURL);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, login, password, mail, birthDate, registrationDate, roleId, imageURL);
    }
}
