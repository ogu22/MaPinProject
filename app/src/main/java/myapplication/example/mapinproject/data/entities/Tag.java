package myapplication.example.mapinproject.data.entities;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Objects;

@IgnoreExtraProperties
public class Tag {
    private String tagId;
    private String tag;

    public Tag() {

    }

    public Tag(String tagId, String tag) {
        this.tagId = tagId;
        this.tag = tag;
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
        return tagId.equals(tag1.tagId) &&
                tag.equals(tag1.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, tag);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId='" + tagId + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}