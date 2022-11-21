package Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "assessments")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private String assessmentType;
    private String assessmentTitle;
    private String assessmentEndDate;
    private String assessmentStartDate;

    public Assessment(String assessmentType, String assessmentTitle, String assessmentEndDate, String assessmentStartDate) {
        this.assessmentType = assessmentType;
        this.assessmentTitle = assessmentTitle;
        this.assessmentEndDate = assessmentEndDate;
        this.assessmentStartDate = assessmentStartDate;
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
