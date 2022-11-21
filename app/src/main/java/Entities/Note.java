package Entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int noteId;
    private String noteDate;
    private String noteText;

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", noteDate=" + noteDate +
                ", noteText='" + noteText + '\'' +
                '}';
    }

    public Note(int noteId, String noteDate, String noteText) {
        this.noteId = noteId;
        this.noteDate = noteDate;
        this.noteText = noteText;
    }

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
