package Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructors")
public class Instructor {

    @PrimaryKey(autoGenerate = true)

    private int instructorId;
    private String instructorName;
    private String instructorEmail;
    private String instructorPhone;



    @Override
    public String toString() {
        return "Instructor{" +
                ", instructorId=" + instructorId +
                ", instructorName=" + instructorName +
                ", instructorPhone=" + instructorPhone +
                ", instructorEmail='" + instructorEmail + '\'' +
                '}';
    }

    public Instructor(int instructorId, String instructorName, String instructorEmail, String instructorPhone) {
        this.instructorId = instructorId;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhone = instructorPhone;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getInstructorPhone() {
        return instructorPhone;
    }

    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }
}
