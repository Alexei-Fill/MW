package com.epam.movie_warehouse.enumiration;

public enum UserRole {
    GUEST(0),
    USER(1),
    ADMIN(2);

    private int id;

    UserRole(int id) {
        this.id = id;
    }

    UserRole() {
    }

    public int getId() {
        return id;
    }

    public static UserRole getUserRole(int value) {
        for (UserRole v : values())
            if (v.getId() == value) return v;
        throw new IllegalArgumentException();
    }

}
