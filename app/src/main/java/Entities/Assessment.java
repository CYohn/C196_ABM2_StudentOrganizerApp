package Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "assessments")
public class Assessment implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private String assessmentType;
    private String assessmentTitle;
    private String assessmentEndDate;
    private String assessmentStartDate;
    private int associatedCourseId;
    private String notifyStartDate;
    private String notifyEndDate;

    @Override
    public String toString() {
        return assessmentTitle +
                " | Start " + assessmentStartDate +
                " Id " + assessmentId;
    }

    public Assessment(int assessmentId, String assessmentType, String assessmentTitle, String assessmentEndDate, String assessmentStartDate, int associatedCourseId, String notifyStartDate, String notifyEndDate) {
        this.assessmentId = assessmentId;
        this.assessmentType = assessmentType;
        this.assessmentTitle = assessmentTitle;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentStartDate = assessmentStartDate;
        this.associatedCourseId = associatedCourseId;
        this.notifyStartDate = notifyStartDate;
        this.notifyEndDate = notifyEndDate;
    }

    public String getNotifyStartDate() {
        return notifyStartDate;
    }

    public void setNotifyStartDate(String notifyStartDate) {
        this.notifyStartDate = notifyStartDate;
    }

    public String getNotifyEndDate() {
        return notifyEndDate;
    }

    public void setNotifyEndDate(String notifyEndDate) {
        this.notifyEndDate = notifyEndDate;
    }

    public int getAssociatedCourseId() {
        return associatedCourseId;
    }

    public void setAssociatedCourseId(int associatedCourseId) {
        this.associatedCourseId = associatedCourseId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }
}
