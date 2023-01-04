package Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int noteId;
    private String noteDate;
    private String noteText;
    private String noteTitle;
    private int associatedCourseId;

    @Override
    public String toString() {
        return noteTitle +
                " | Date " + noteDate +
                " | Id: " + noteId;
    }

    public Note(int noteId, String noteDate, String noteText, String noteTitle, int associatedCourseId) {
        this.noteId = noteId;
        this.noteDate = noteDate;
        this.noteText = noteText;
        this.noteTitle = noteTitle;
        this.associatedCourseId=associatedCourseId;
    }

    public String getNoteTitle() {return noteTitle;}

    public void setNoteTitle(String noteTitle) {this.noteTitle = noteTitle;}

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
