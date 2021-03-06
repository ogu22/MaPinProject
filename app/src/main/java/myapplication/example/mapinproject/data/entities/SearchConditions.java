package myapplication.example.mapinproject.data.entities;

import java.io.Serializable;
import java.util.Objects;

public class SearchConditions implements Serializable {
    private String searchWords;
    private DateRange range;
    private Tag tag;
    private int rating;
    private int radiusRange;

    public SearchConditions() {
        this.searchWords =  "";
        this.range = new DateRange();
        this.tag = new Tag();
        this.rating = 0;
        this.radiusRange = 1000;
    }

    public SearchConditions(String searchWords, DateRange range, Tag tag,int rating, int radiusRange) {
        this.searchWords = searchWords;
        this.range = range;
        this.tag = tag;
        this.rating = rating;
        this.radiusRange = radiusRange;
    }

    public int getRating() {
        return rating;
    }

    public String getSearchWords() {
        return searchWords;
    }

    public DateRange getRange() {
        return range;
    }

    public Tag getTag() {
        return tag;
    }

    public int getRadiusRange() {
        return radiusRange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchConditions that = (SearchConditions) o;
        return rating == that.rating &&
                radiusRange == that.radiusRange &&
                Objects.equals(searchWords, that.searchWords) &&
                Objects.equals(range, that.range) &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchWords, range, tag, rating, radiusRange);
    }

    @Override
    public String toString() {
        return "SearchConditions{" +
                "searchWords='" + searchWords + '\'' +
                ", range=" + range +
                ", tag=" + tag +
                ", rating=" + rating +
                ", radiusRange=" + radiusRange +
                '}';
    }
}
