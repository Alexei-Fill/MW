package com.epam.movie_warehouse.entity;

import java.util.Objects;

public class Language {
    private int id;
    private String name;
    private String local;
    private String dateFormat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", local='" + local + '\'' +
                ", dateFormat='" + dateFormat + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return id == language.id &&
                Objects.equals(name, language.name) &&
                Objects.equals(local, language.local) &&
                Objects.equals(dateFormat, language.dateFormat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, local, dateFormat);
    }
}
