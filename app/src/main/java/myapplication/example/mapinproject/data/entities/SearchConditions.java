package myapplication.example.mapinproject.data.entities;

import java.util.Objects;

public class SearchConditions {
    private String searchWords;
    private DateRange range;
    private Tag tag;
    private int radiusRange;

    public SearchConditions() {
        this.searchWords =  "";
        this.range = new DateRange();
        this.tag = new Tag();
        this.radiusRange = 100;
    }

    public SearchConditions(String searchWords, DateRange range, Tag tag, int radiusRange) {
        this.searchWords = searchWords;
        this.range = range;
        this.tag = tag;
        this.radiusRange = radiusRange;
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
        return radiusRange == that.radiusRange &&
                Objects.equals(searchWords, that.searchWords) &&
                Objects.equals(range, that.range) &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchWords, range, tag, radiusRange);
    }

    @Override
    public String toString() {
        return "SearchConditions{" +
                "searchWords='" + searchWords + '\'' +
                ", range=" + range +
                ", tag=" + tag +
                ", radiusRange=" + radiusRange +
                '}';
    }
}
