package UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.c196_abm2_charity_yohn.R;

import Database.RepositoryForStudentOrganizer;
import Entities.Assessment;
import Entities.Course;
import Entities.Instructor;
import Entities.Note;
import Entities.Term;

public class MainActivity extends AppCompatActivity {

    public static int alertId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RepositoryForStudentOrganizer.Repository repo=new RepositoryForStudentOrganizer.Repository(getApplication());

        //Testing data

//        Instructor instructor =new Instructor(0,"Testing Instructor DB","email@test.com", "555-555-5555");
//        Note note =new Note(0,"11/17/22","testing note DB","Note Title Test",0);
//        Assessment assessment =new Assessment(0,"Performance","Testing Assessments DB", "11/21/22", "11/20/22", 0 );
//        Term term =new Term(0,"Testing Term DB","11/16/22", "11/15/22");
//        Course course =new Course(0,"Testing Course DB","11/19/22", "11/19/22", "Testing", "Testing Instructor", 0, 0);
//
//
//        repo.insert(assessment);
//        repo.insert(instructor);
//        repo.insert(note);
//        repo.insert(term);
//        repo.insert(course);

//        Course course = new Course(3, "Save and Add Assessment",	"01/01/23",	"01/31/23",	"In Progress",	"Instructor From course",	2,	6);
//        Course course2 = new Course(5,	"Course Title",	"01/02/23",	"01/27/23",	"Completed",	"Testing Instructor DB",	4,	3);
//        repo.delete(course);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.termsMenuOption:
                Intent termsIntent = new Intent(MainActivity.this, TermsActivity.class);
                startActivity(termsIntent);
                return true;

            case R.id.coursesMenuOption:
                Intent courseIntent = new Intent(MainActivity.this, CourseActivity.class);
                startActivity(courseIntent);
                return true;

            case R.id.assessmentsMenuOption:
                Intent assessmentIntent = new Intent(MainActivity.this, AssessmentsActivity.class);
                startActivity(assessmentIntent);
                return true;

            case R.id.notesMenuOption:
                Intent notesIntent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(notesIntent);
                return true;
            case R.id.instructorMenuOption:
                Intent instructorIntent = new Intent(this, InstructorActivity.class);
                startActivity(instructorIntent);
                return true;

            default:return super.onOptionsItemSelected(item);
        }
    }

    public void allTermsBtnPressed(View view){
        Intent intent = new Intent(MainActivity.this, TermsActivity.class);
        startActivity(intent);
    }

}