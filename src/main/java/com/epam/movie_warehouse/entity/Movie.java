package com.epam.movie_warehouse.entity;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Movie {
    private long id;
    private String imdbID;
    private long budget;
    private long countOfLike;
    private long dues;
    private int ageLimit;
    private double rating;
    private String name;
    private String description;
    private String country;
    private List<Genre> genres = new ArrayList<>();
    private List<Human> movieCrew = new ArrayList<>();
    private String imageURL;
    private LocalDate releaseDate;
    private LocalDate uploadDate;
    private LocalTime duration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }


    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public LocalTime getDuration() {
        return duration;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getCountOfLike() {
        return countOfLike;
    }

    public void setCountOfLike(long countOfLike) {
        this.countOfLike = countOfLike;
    }

    public long getDues() {
        return dues;
    }

    public void setDues(long dues) {
        this.dues = dues;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Human> getMovieCrew() {
        return movieCrew;
    }

    public void setMovieCrew(List<Human> movieCrew) {
        this.movieCrew = movieCrew;
    }

    @Override
    public String toString() {
        return "\nMovie{" +
                "id=" + id +
                ", imdbID='" + imdbID +
                ", budget=" + budget +
                ", countOfLike=" + countOfLike +
                ", dues=" + dues +
                ", ageLimit=" + ageLimit +
                ", rating=" + rating +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", genres=" + genres +
                ", \nmovieCrew=" + movieCrew +
                ", \nimageURL='" + imageURL + '\'' +
                ", releaseDate=" + releaseDate +
                ", uploadDate=" + uploadDate +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                budget == movie.budget &&
                countOfLike == movie.countOfLike &&
                dues == movie.dues &&
                ageLimit == movie.ageLimit &&
                Double.compare(movie.rating, rating) == 0 &&
                Objects.equals(imdbID, movie.imdbID) &&
                Objects.equals(name, movie.name) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(country, movie.country) &&
                Objects.equals(genres, movie.genres) &&
                Objects.equals(movieCrew, movie.movieCrew) &&
                Objects.equals(imageURL, movie.imageURL) &&
                Objects.equals(releaseDate, movie.releaseDate) &&
                Objects.equals(uploadDate, movie.uploadDate) &&
                Objects.equals(duration, movie.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imdbID, budget, countOfLike, dues, ageLimit, rating, name, description,
                country, genres, movieCrew, imageURL, releaseDate, uploadDate, duration);
    }
}
