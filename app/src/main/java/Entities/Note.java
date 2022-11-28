package Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int noteId;
    private String noteDate;
    private String noteText;
    private int associatedCourseId;

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", noteDate=" + noteDate +
                ", noteText='" + noteText +
                ", associatedCourseId='" + associatedCourseId + '\'' +
                '}';
    }

    public Note(int noteId, String noteDate, String noteText, int associatedCourseId) {
        this.noteId = noteId;
        this.noteDate = noteDate;
        this.noteText = noteText;
        this.associatedCourseId=associatedCourseId;
    }


    public int getAssociatedCourseId() {return associatedCourseId;}

    public void setAssociatedCourseId(int associatedCourseId) {this.associatedCourseId = associatedCourseId;}

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
}
