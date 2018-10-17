package com.epam.movie_warehouse.entity;

import java.time.LocalDate;

public class Human {
    private long id;
    private String imageURL;
    private String name;
    private LocalDate birthDate;
    private String biography;
    private int roleId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Human{" +
                "id=" + id +
                ", imageURL='" + imageURL + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", biography='" + biography + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
