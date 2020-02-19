package myapplication.example.mapinproject.data.entities;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.Objects;

@IgnoreExtraProperties
public class User implements Serializable {
    private String userId;
    private String userName;
    private String imagePath;
    private String comment;

    public User() {
    }

    public User(String userId, String userName, String imagePath, String comment) {
        this.userId = userId;
        this.userName = userName;
        this.imagePath = imagePath;
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(imagePath, user.imagePath) &&
                Objects.equals(comment, user.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, imagePath, comment);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}