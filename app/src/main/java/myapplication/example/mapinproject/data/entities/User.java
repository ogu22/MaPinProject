package myapplication.example.mapinproject.data.entities;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Objects;

@IgnoreExtraProperties
public class User {
    private String userId;
    private String password;
    private String userName;
    private String email;
    private String imagePath;
    private String comment;
    private boolean flg;

    public User() {}

    public User(String userId, String password, String userName, String email, String imagePath, String comment, boolean flg) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.imagePath = imagePath;
        this.comment = comment;
        this.flg = flg;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getComment() {
        return comment;
    }

    public boolean isFlg() {
        return flg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                flg == user.flg &&
                password.equals(user.password) &&
                userName.equals(user.userName) &&
                email.equals(user.email) &&
                imagePath.equals(user.imagePath) &&
                comment.equals(user.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, userName, email, imagePath, comment, flg);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", comment='" + comment + '\'' +
                ", flg=" + flg +
                '}';
    }
}