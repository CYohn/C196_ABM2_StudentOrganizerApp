package Entities;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "terms")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int termId;
    private String termTitle;
    private String termEndDate;
    private String termStartDate;

    @Override
    public String toString() {
        return "Term{" +
                "termId=" + termId +
                ", termTitle=" + termTitle +
                ", termStartDate=" + termStartDate +
                ", termEndDate='" + termEndDate + '\'' +
                '}';
    }

    @Ignore
    public Term(int termId, String termTitle, String termStartDate, String termEndDate) {
        this.termId = termId;
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    public Term(String termTitle){
        this.termTitle = termTitle;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public String getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(String termEndDate) {
        this.termEndDate = termEndDate;
    }

    public String getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(String termStartDate) {
        this.termStartDate = termStartDate;
    }
}
