package com.epam.movie_warehouse;

import com.epam.movie_warehouse.dao.GenreDAO;
import com.epam.movie_warehouse.dao.MovieDAO;
import com.epam.movie_warehouse.entity.Genre;
import com.epam.movie_warehouse.exception.ValidationException;

import java.sql.SQLException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException, ValidationException, SQLException {
//        Password password = new Password();
//        String p1 = "alexalex";
//        String p2 = "alex21alex";
//        String p3 = "alex3alex";
//        String hp1 = password.hashingPassword(p1);
//        String hp11 ="$2a$12$08c/kQ/m6c58zJAi.gwfSeysMS3LlkuhEgNo6oroRkmS/pqtwvjAy";
//        String hp12 ="$2a$12$tPQSCTcuWcIfZTYjPr8ggeqhTDPuGxNdCmb6y1GEWnV9jmEf3ghmO";
//        String hp13 ="$2a$12$3Osvtaq4tBpoSXbiZ83yreHGjjyvzaTHQb4AnosuIfF17gGD56aM2";
//        String hp2 = password.hashingPassword(p2);
//        String hp3 = password.hashingPassword(p3);
//        System.out.println(p1 + "=========" + hp1);
//        System.out.println(p2 + "=========" + hp2);
//        System.out.println(p3 + "=========" + hp3);
//        System.out.println(password.checkPassword(p1,hp11));
//        System.out.println(password.checkPassword(p2,hp12));
//        System.out.println(password.checkPassword(p3,hp13));
//        UserValidator userValidator = new UserValidator();
//        String login = "alex_95_pvt@mail.ru";
//        String login1 = "alex.fill.77@gmail.com";
//        String login2 = "wronr@wrong";
//        String login3 = "@ru.ru";
//        String login4 = "hf.u";
//        String login5 = "aabbbzzzz11222";
//        System.out.println("login    ="  + userValidator.validateMail(login));
//        System.out.println("login 1  =" + userValidator.validateMail(login1) );
//        System.out.println("login 2  =" + userValidator.validateMail(login2));
//        System.out.println("login 3  =" +userValidator.validateMail(login3) );
//        System.out.println("login 4  =" + userValidator.validateMail(login4));


//        String date = "2017-11-30";
//        LocalDate releaseDate = LocalDate.parse(date);
//        String date2 = "22-11.2001";
//        DateTimeFormatter df =  DateTimeFormatter.ofPattern("d-MM-yyyy" , Locale.FRANCE);
//        LocalDate releaseDate2 = LocalDate.parse(date2, df);
//        System.out.println("1 date  ----" + releaseDate);
//        System.out.println("2 date  ----" + releaseDate2);

//        Movie m = new Movie();
//        m.setImdbID("146275856");
//        m.setName("Погребённый заживо 2");
//        m.setDescription("Герой фильма ловушки…");
//        m.setCountry("Великобританияб, США");
//        m.setImageURL(" ");
//        m.setTrailerURL(" ");
//        m.setBudget(3000000);
//        m.setRating(4.5);
//        m.setCountOfLikes(10);
//        m.setAgeLimit(18);
//        m.setReleaseDate(2015, 01, 23);
//        m.setUploadDate();
//        m.setDuration(5, 12, 01);
//        m.setDues(19152480);
////
//        Genre genre = new Genre();
////        genre.setId(2);
//        genre.setName("Аниме");
//        genre.setId(5);
//        System.out.println(genre);
////
//        GenreDAO genreDAO = new GenreDAO();
//        System.out.println(genreDAO.showAllAvailableGenres(1));
//        genreDAO.updateGenre(genre, 1);
////        genreDAO.deleteGenre(genre);
////        genreDAO.getMAXGenreId(genre);
////        System.out.println(genre);
////        genreDAO.addGenre(genre, 1);
////        System.out.println(genre);
////
//        genre.setName("Anime");
//        genreDAO.updateGenre(genre, 2);

//        genreDAO.addGenre(genre, 2);
//        System.out.println(genre);


//        Genre genre2 = new Genre();
//        genre2.setId(9);
//        genre2.setName("Документальный");
//        Genre genre3 = new Genre();
//        genre3.setId(1);
//        genre3.setName("Фантастика");
//        Genre genre4 = new Genre();
//        genre4.setId(1);
//        genre4.setName("Фантастика");
//
//        m.setGenres(genre);
//        m.setGenres(genre2);
//        m.setGenres(genre3);
//        m.setGenres(genre4);
//
//        Human human = new Human();
//        human.setId(1);
//        human.setFirstName("Дениел");
//        Human human2 = new Human();
//        human2.setId(4);
//        human2.setFirstName("Актер");
//        Human human3 = new Human();
//        human3.setId(7);
//        human3.setFirstName("Супер");
//
//        m.setActors(human);
//        m.setActors(human);
//        m.setDirector(human2);
//        m.setScreenwriter(human3);
//        System.out.println(m);


        MovieDAO moviesDAO = new MovieDAO();
        System.out.println(moviesDAO.listMovie(1));
//        System.out.println(moviesDAO.listMovieByName("ли", 1));
//        System.out.println(moviesDAO.showMovieById(11,1));
//        System.out.println(moviesDAO.listMovie(1));
//        moviesDAO.addMovie(m);
//        Movie m2 = moviesDAO.showMovieById(11, 1);
//        m2.setId(0);
//        m2.setImdbID("7777777");
//        m2.setName("Великолепная семерка 77");
//        m2.setBudget(10);
//        Genre f = new Genre();
//        f.setId(2);
//        m2.setGenres(f);
//        moviesDAO.addMovie(m2);
//        moviesDAO.updateMovie(m2, 1);
//        System.out.println(moviesDAO.showMovieById(11,1));
//        System.out.println(m2);
//        moviesDAO.deleteMovie(m2);
//        System.out.println(moviesDAO.listMovie("а"));
//        System.out.println(moviesDAO.showMovieById(4));
//        System.out.println(moviesDAO.listMovie());
//        Genre genre = new Genre();
//        genre.setName("Комедия");
//
//        genreDAO.updateGenre(genre);
//        genreDAO.addLinksGenresOnMovie("Комедия +");
//        genreDAO.deleteGenre(genre);
//        System.out.println(genreDAO.showGenresOfTheMovie());
//        System.out.println(moviesDAO.listMovie());

//        Human human = new Human();
//        human.setFirstName("Супер");
//        human.setName("Актеров");
//        human.setImageURL("skjfsjks");
//        human.setBirthDate(2012, 8, 11);
//        HumanDAO humanDAO = new HumanDAO();
//        System.out.println(humanDAO.showHumanById(1l, 1));
//        System.out.println(humanDAO.showMovieCrew(1l,1));
//        System.out.println(humanDAO.listGenre(1));
//        human = humanDAO.showHumanById(6l);
//        humanDAO.deleteHuman(human);
//        humanDAO.setHuman(human);
//        human.setFirstName("Coool");
//        human.setName("Dooood");
//        humanDAO.updateHuman(human);
//        System.out.println(humanDAO.getHumanFromDBFN("i"));
//        System.out.println(humanDAO.getHumanFromDBLN("i"));
//        System.out.println(humanDAO.listGenre());


//        UserDAO userDAO = new UserDAO();
//        System.out.println(userDAO.showUserByLogin("Admin"));
//        System.out.println(userDAO.listUser());
////        User us = new User();
//        us = userDAO.showUserByLogin(3);
//        us.setLogin("UserTh44");
//        userDAO.addUser(us);
//        System.out.println(userDAO.listUser());
    }
}
