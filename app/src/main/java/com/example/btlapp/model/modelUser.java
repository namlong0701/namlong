package com.example.btlapp.model;

import androidx.annotation.NonNull;

public class modelUser {
    int userId;
    String  firstName,lastName,addRess,phoneNumber,userName,userPassword;

    public modelUser(int userId, String firstName, String lastName, String addRess, String phoneNumber, String userName, String userPassword) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addRess = addRess;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    @NonNull
    @Override
    public String toString() {
        return "Model_User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addRess='" + addRess + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
