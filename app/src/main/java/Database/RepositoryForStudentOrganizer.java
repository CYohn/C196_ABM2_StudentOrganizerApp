package Database;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

public class RepositoryForStudentOrganizer {

    public class Repository {
        private AssessmentDAO mAssessmentDAO;
        private CourseDAO mCourseDAO;
        private InstructorDAO mInstructorDAO;
        private NoteDAO mNoteDAO;
        private TermDAO mTermDAO;

        private List<Assessment> mAllAssessments;
        private List<Course> mAllCourses;
        private List<Instructor> mAllInstructors;
        private List<Note> mAllNotes;
        private List<Term> mAllTerms;

        private final int NUMBER_OF_THREADS=6;
        final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        public Repository(Application application){
            StudentOrganizerDatabaseBuilder db= StudentOrganizerDatabaseBuilder.getDatabase(application);
            mAssessmentDAO=db.assessmentDAO();
            mCourseDAO=db.courseDAO();
            mInstructorDAO=db.instructorDAO();
            mNoteDAO=db.noteDAO();
            mTermDAO=db.termDAO();
        }
        public List<Assessment>getAllAssessments(){
            databaseExecutor.execute(()->{
                mAllAssessments=mAssessmentDAO.getAllAssessments();
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mAllAssessments;
        }
        public void insert(Assessment assessment){

            databaseExecutor.execute(()->{
                mAssessmentDAO.insert(assessment);
            });
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }
        public void update(Assessment assessment){
            databaseExecutor.execute(()->{
                mAssessmentDAO.update(assessment);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void delete(Assessment assessment){
            databaseExecutor.execute(()->{
                mAssessmentDAO.delete(assessment);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*************************************************************/
        public List<Course>getmAllCourses(){
            databaseExecutor.execute(()->{
                mAllCourses=mCourseDAO.getAllCourses();
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mAllCourses;
        }
        public void insert(Course course){
            databaseExecutor.execute(()->{
                mCourseDAO.insert(course);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void update(Course course){
            databaseExecutor.execute(()->{
                mCourseDAO.update(course);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void delete(Course course){
            databaseExecutor.execute(()->{
                mCourseDAO.delete(course);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        /*************************************************************/
        public List<Instructor>getmAllInstructors(){
            databaseExecutor.execute(()->{
                mAllInstructors=mInstructorDAO.getAllInstructors();
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mAllInstructors;
        }
        public void insert(Instructor instructor){
            databaseExecutor.execute(()->{
                mInstructorDAO.insert(instructor);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void update(Instructor instructor){
            databaseExecutor.execute(()->{
                mInstructorDAO.update(instructor);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void delete(Instructor instructor){
            databaseExecutor.execute(()->{
                mInstructorDAO.delete(instructor);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*************************************************************/
        public List<Note>getAllNotes(){
            databaseExecutor.execute(()->{
                mAllNotes=mNoteDAO.getAllNotes();
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mAllNotes;
        }
        public void insert(Note note){
            databaseExecutor.execute(()->{
                mNoteDAO.insert(note);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void update(Note note){
            databaseExecutor.execute(()->{
                mNoteDAO.update(note);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void delete(Note note){
            databaseExecutor.execute(()->{
                mNoteDAO.delete(note);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*************************************************************/
        public List<Term>getmAllTerms(){
            databaseExecutor.execute(()->{
                mAllTerms=mTermDAO.getAllTerms();
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return mAllTerms;
        }
        public void insert(Term term){
            databaseExecutor.execute(()->{
                mTermDAO.insert(term);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void update(Term term){
            databaseExecutor.execute(()->{
                mTermDAO.update(term);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public void delete(Term term){
            databaseExecutor.execute(()->{
                mTermDAO.delete(term);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
