package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentInfo {
    private String name;
    private Integer group;
    private Integer byIllness;
    private Integer anotherReason;
    private Integer withoutReason;
    private Integer summary;

    public StudentInfo(String name,
                           Integer group,
                           Integer byIllness,
                           Integer anotherReason,
                           Integer withoutReason) {
        this.name = name;
        this.group = group;
        this.byIllness = byIllness;
        this.anotherReason = anotherReason;
        this.withoutReason = withoutReason;
        this.summary = byIllness + anotherReason + withoutReason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAnotherReason() {
        return anotherReason;
    }

    public Integer getByIllness() {
        return byIllness;
    }

    public Integer getGroup() {
        return group;
    }

    public Integer getWithoutReason() {
        return withoutReason;
    }

    public void setAnotherReason(int anotherReason) {
        this.anotherReason = anotherReason;
    }

    public void setByIllness(int byIllness) {
        this.byIllness = byIllness;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public void setWithoutReason(int withoutReason) {
        this.withoutReason = withoutReason;
    }

    public void setSummary(int summary) {
        this.summary = summary;
    }

    public Integer getSummary() {
        return getByIllness() + getWithoutReason() + getAnotherReason();
    }
}
