package myapplication.example.mapinproject.data.entities;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Objects;

@IgnoreExtraProperties
public class Tweeit {

    private String tweeitId;
    private String userId;
    private ArrayList<Tag> tags;
    private Location locations;
    private ArrayList<Reply> replies;
    private String tweeitTitle;
    private String tweeit;
    private String imagePath;
    private String timestamp;
    private int evaluation;

    public Tweeit(String tweeitId, String userId, ArrayList<Tag> tags, Location locations, ArrayList<Reply> replies, String tweeitTitle, String tweeit, String imagePath, String timestamp, int evaluation) {
        this.tweeitId = tweeitId;
        this.userId = userId;
        this.tags = tags;
        this.locations = locations;
        this.replies = replies;
        this.tweeitTitle = tweeitTitle;
        this.tweeit = tweeit;
        this.imagePath = imagePath;
        this.timestamp = timestamp;
        this.evaluation = evaluation;
    }

    public  Tweeit() {

    }

    public String getTweeitId() {
        return tweeitId;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public Location getLocations() {
        return locations;
    }

    public ArrayList<Reply> getReplies() {
        return replies;
    }

    public String getTweeitTitle() {
        return tweeitTitle;
    }

    public String getTweeit() {
        return tweeit;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getEvaluation() {
        return evaluation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweeit tweeit1 = (Tweeit) o;
        return evaluation == tweeit1.evaluation &&
                Objects.equals(tweeitId, tweeit1.tweeitId) &&
                Objects.equals(userId, tweeit1.userId) &&
                Objects.equals(tags, tweeit1.tags) &&
                Objects.equals(locations, tweeit1.locations) &&
                Objects.equals(replies, tweeit1.replies) &&
                Objects.equals(tweeitTitle, tweeit1.tweeitTitle) &&
                Objects.equals(tweeit, tweeit1.tweeit) &&
                Objects.equals(imagePath, tweeit1.imagePath) &&
                Objects.equals(timestamp, tweeit1.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tweeitId, userId, tags, locations, replies, tweeitTitle, tweeit, imagePath, timestamp, evaluation);
    }

    @Override
    public String toString() {
        return "Tweeit{" +
                "tweeitId='" + tweeitId + '\'' +
                ", userId='" + userId + '\'' +
                ", tags=" + tags +
                ", locations=" + locations +
                ", replies=" + replies +
                ", tweeitTitle='" + tweeitTitle + '\'' +
                ", tweeit='" + tweeit + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", evaluation=" + evaluation +
                '}';
    }
}