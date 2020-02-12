package myapplication.example.mapinproject.data.entities;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@IgnoreExtraProperties
public class Tag implements Serializable {
    private String tagId;
    private String tag;
    private List<String> tweeitId;

    public Tag() {

    }

    public Tag(String tagId, String tag, List<String> tweeitId) {
        this.tagId = tagId;
        this.tag = tag;
        this.tweeitId = tweeitId;
    }

    public List<String> getTweeitId() {
        return tweeitId;
    }

    public String getTagId() {
        return tagId;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag1 = (Tag) o;
        return Objects.equals(tagId, tag1.tagId) &&
                Objects.equals(tag, tag1.tag) &&
                Objects.equals(tweeitId, tag1.tweeitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, tag, tweeitId);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId='" + tagId + '\'' +
                ", tag='" + tag + '\'' +
                ", tweeitId=" + tweeitId +
                '}';
    }
}