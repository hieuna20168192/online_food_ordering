package com.example.onlinefoodordering.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("uid")
    private String userId;
    @SerializedName("userName")
    private String userName;
    @SerializedName("profileThumb")
    private String userThumb;
    @SerializedName("email")
    private String userEmail;
    @SerializedName("role")
    private String userRole;

    public User() {
    }

    public String getUid() {
        return userId;
    }

    public void setUid(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileThumb() {
        return userThumb;
    }

    public void setProfileThumb(String userThumb) {
        this.userThumb = userThumb;
    }


    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getRole() {
        return userRole;
    }

    public void setRole(String userRole) {
        this.userRole = userRole;
    }

    public User(String userId, String userName, String userThumb, String userEmail, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userThumb = userThumb;
        this.userEmail = userEmail;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{"+
                "userId='"+userId+'\'' +
                ", name='"+userName +'\'' +
                ", email='"+userEmail+'\''+
                ", profilePicture='" + userThumb + '\''+
                ", role='"+userRole+'\''+
                '}';
    }
}
