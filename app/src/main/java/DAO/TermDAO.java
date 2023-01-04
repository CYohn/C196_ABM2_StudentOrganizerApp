package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Entities.Term;

@Dao
public interface TermDAO {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);

    @Query("SELECT * FROM TERMS ORDER BY termId ASC")
        //Do not change the order of the query to Descending order, it messes up the Id numbering function to save.

    List<Term> getAllTerms();

}
