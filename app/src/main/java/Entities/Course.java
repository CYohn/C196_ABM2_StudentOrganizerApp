package Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int  courseId;
    private String courseTitle;
    private String courseStartDate;
    private String courseEndDate;
    private String courseStatus;
    private String courseInstructor;
    private int associatedTermId;


    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseTitle=" + courseTitle +
                ", courseEndDate=" + courseEndDate +
                ", courseStatus=" + courseStatus +
                ", courseInstructor=" + courseInstructor +
                ", courseStartDate='" + courseStartDate + '\'' +
                ", associatedTerm='" + associatedTermId + '\'' +
                '}';
    }

    public Course(int courseId,  String courseTitle, String courseStartDate, String courseEndDate, String courseStatus, String courseInstructor, int associatedTermId) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseInstructor = courseInstructor;
        this.associatedTermId = associatedTermId;
    }

    public int getAssociatedTermId() {return associatedTermId;}

    public void setAssociatedTermId(int associatedTermId) {this.associatedTermId = associatedTermId;}

    public int getCourseId() {return courseId;}

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {this.courseStartDate = courseStartDate;}

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseInstructor() {return courseInstructor;}

    public void setCourseInstructor(String courseInstructor) {this.courseInstructor = courseInstructor;}
}

