package Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int  courseId;
    private String courseTitle;
    private Date courseStartDate;
    private Date courseEndDate;
    private String courseStatus;


    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseTitle=" + courseTitle +
                ", courseEndDate=" + courseEndDate +
                ", courseStatus=" + courseStatus +
                ", courseStartDate='" + courseStartDate + '\'' +
                '}';
    }

    public Course(int courseId,  String courseTitle, Date courseStartDate, Date courseEndDate, String courseStatus) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.courseStatus = courseStatus;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Date getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Date courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }
}
