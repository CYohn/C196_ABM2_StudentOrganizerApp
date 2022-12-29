package Database;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import DAO.AssessmentDAO;
import DAO.CourseDAO;
import DAO.InstructorDAO;
import DAO.NoteDAO;
import DAO.TermDAO;
import Entities.Assessment;
import Entities.Course;
import Entities.Instructor;
import Entities.Note;
import Entities.Term;


@Database(entities={Assessment.class, Course.class, Instructor.class, Note.class, Term.class}, version=8, exportSchema = false)

    public abstract class StudentOrganizerDatabaseBuilder extends RoomDatabase {
        public abstract AssessmentDAO assessmentDAO();
        public abstract CourseDAO courseDAO();
        public abstract InstructorDAO instructorDAO();
        public abstract NoteDAO noteDAO();
        public abstract TermDAO termDAO();

        private static volatile StudentOrganizerDatabaseBuilder INSTANCE;

        static StudentOrganizerDatabaseBuilder getDatabase(final Context context) {
            if(INSTANCE==null){
                synchronized (StudentOrganizerDatabaseBuilder.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StudentOrganizerDatabaseBuilder.class, "myStudentOrganizerDb")
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    }

