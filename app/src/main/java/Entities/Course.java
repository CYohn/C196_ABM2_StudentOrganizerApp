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
    private int instructorId;
    private String notifyStartDate;
    private String notifyEndDate;


    @Override
    public String toString() {
        return courseTitle +
                " | Start " + courseStartDate +
                " | Id " + courseId;
    }

    public Course(int courseId,  String courseTitle, String courseStartDate, String courseEndDate,
                  String courseStatus, String courseInstructor, int instructorId, int associatedTermId,
                    String notifyStartDate, String notifyEndDate) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
        this.courseInstructor = courseInstructor;
        this.instructorId = instructorId;
        this.associatedTermId = associatedTermId;
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

    public int getInstructorId() {return instructorId;}

    public void setInstructorId(int instructorId) {this.instructorId = instructorId;}

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

