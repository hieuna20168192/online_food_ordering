package com.example.onlinefoodordering.model;

public class User {

    private String userId;
    private String userName;
    private String userThumb;
    private String userEmail;
    private String userRole;

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserThumb() {
        return userThumb;
    }

    public void setUserThumb(String userThumb) {
        this.userThumb = userThumb;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public User(String userId, String userName, String userThumb, String userEmail, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userThumb = userThumb;
        this.userEmail = userEmail;
        this.userRole = userRole;
    }
}
