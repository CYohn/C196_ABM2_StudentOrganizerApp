package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Entities.Course;


@Dao
public interface CourseDAO {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM COURSES ORDER BY courseId ASC")
        //Do not change the order of the query to Descending order, it messes up the Id numbering function to save.

    List<Course> getAllCourses();

    //@Query("SELECT * FROM COURSES WHERE TERMID = termId ORDER BY courseStartDate DESC")

}
