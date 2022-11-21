package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Entities.Note;

@Dao
public interface NoteDAO {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM NOTES ORDER BY noteDate DESC")
    List<Note> getAllNotes();
}
