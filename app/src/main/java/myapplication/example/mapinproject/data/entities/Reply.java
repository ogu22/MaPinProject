package myapplication.example.mapinproject.data.entities;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Objects;

@IgnoreExtraProperties
public class Reply {
    private String replayId;
    private String content;
    private String imagePath;

    public Reply(String replayId, String content, String imagePath) {
        this.replayId = replayId;
        this.content = content;
        this.imagePath = imagePath;
    }

    public Reply() {

    }

    public String getReplayId() {
        return replayId;
    }

    public String getContent() {
        return content;
    }

    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "replayId='" + replayId + '\'' +
                ", content='" + content + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply reply = (Reply) o;
        return replayId.equals(reply.replayId) &&
                content.equals(reply.content) &&
                imagePath.equals(reply.imagePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(replayId, content, imagePath);
    }
}