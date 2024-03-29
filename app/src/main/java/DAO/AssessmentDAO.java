package DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Entities.Assessment;

@Dao
public interface AssessmentDAO {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentId ASC")
    //Do not change the order of the query to Descending order, it messes up the Id numbering function to save.

    List<Assessment> getAllAssessments();

}
