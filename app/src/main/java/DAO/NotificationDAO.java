package DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Entities.Notification;
import Entities.Term;

@Dao
public interface NotificationDAO {


        @Insert(onConflict= OnConflictStrategy.IGNORE)
        void insert(Notification notification);

        @Update
        void update(Notification notification);

        @Delete
        void delete(Notification notification);

        @Query("SELECT * FROM NOTIFICATION ORDER BY notificationId ASC")
            //Do not change the order of the query to Descending order, it messes up the Id numbering function to save.

        List<Notification> getmAllNotifications();

}

