package myapplication.example.mapinproject.data.entities;

import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Objects;

@IgnoreExtraProperties
public class Reply {
    private String replayId;
    private String replayUserName;
    private Uri replayUserImagePath;
    private String replayDate;
    private String content;
    private Uri imagePath;

    public Reply() {
    }

    public Reply(String replayId, String replayUserName, Uri replayUserImagePath, String replayDate, String content, Uri imagePath) {
        this.replayId = replayId;
        this.replayUserName = replayUserName;
        this.replayUserImagePath = replayUserImagePath;
        this.replayDate = replayDate;
        this.content = content;
        this.imagePath = imagePath;
    }

    public String getReplayId() {
        return replayId;
    }

    public String getReplayUserName() {
        return replayUserName;
    }

    public Uri getReplayUserImagePath() {
        return replayUserImagePath;
    }

    public String getReplayDate() {
        return replayDate;
    }

    public String getContent() {
        return content;
    }

    public Uri getImagePath() {
        return imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply reply = (Reply) o;
        return Objects.equals(replayId, reply.replayId) &&
                Objects.equals(replayUserName, reply.replayUserName) &&
                Objects.equals(replayUserImagePath, reply.replayUserImagePath) &&
                Objects.equals(replayDate, reply.replayDate) &&
                Objects.equals(content, reply.content) &&
                Objects.equals(imagePath, reply.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(replayId, replayUserName, replayUserImagePath, replayDate, content, imagePath);
    }

    @Override
    public String toString() {
        return "Reply{" +
                "replayId='" + replayId + '\'' +
                ", replayUserName='" + replayUserName + '\'' +
                ", replayUserImagePath=" + replayUserImagePath +
                ", replayDate='" + replayDate + '\'' +
                ", content='" + content + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}