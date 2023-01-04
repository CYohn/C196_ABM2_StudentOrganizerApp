package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Entities.Instructor;

@Dao
public interface InstructorDAO {
    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Instructor instructor);

    @Update
    void update(Instructor instructor);

    @Delete
    void delete(Instructor instructor);

    @Query("SELECT * FROM INSTRUCTORS ORDER BY instructorId ASC")
        //Do not change the order of the query to Descending order, it messes up the Id numbering function to save.

    List<Instructor> getAllInstructors();

}
