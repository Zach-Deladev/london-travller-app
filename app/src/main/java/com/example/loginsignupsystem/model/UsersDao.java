package com.example.loginsignupsystem.model;

public interface UsersDao {

    String hashPassword(String password);

    boolean emailExists(String email);

    boolean phoneExists(String phone);

    long register(Users user);

    void logInUser(int userId);

    void logOutUser(int userId);

    Users getLoggedInUser();

    boolean isLoggedIn();

    Users getUserById(int userId);

    int checkUserCredentials(String email, String password);
}
