package com.epam.movie_warehouse.entity;

import java.time.LocalDate;

public class User {
 private long id;
 private String login;
 private String password;
 private String mail;
 private LocalDate birthDate;
 private LocalDate registrationDate;
 private int roleId;
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

 public int getRoleId() {
  return roleId;
 }

 public void setRoleId(int roleId) {
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
}
