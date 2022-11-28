package UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.c196_abm2_charity_yohn.R;

import Database.RepositoryForStudentOrganizer;
import Entities.Assessment;
import Entities.Course;
import Entities.Instructor;
import Entities.Note;
import Entities.Term;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RepositoryForStudentOrganizer.Repository repo=new RepositoryForStudentOrganizer.Repository(getApplication());

        //Testing data
        Assessment assessment =new Assessment(1,"Performance","Testing Assessments DB", "11/21/22", "11/20/22", 25 );
        Course course =new Course(2,"Testing Course DB","11/19/22", "11/19/22", "Testing", "Testing Instructor", 1);
        Instructor instructor =new Instructor(3,"Testing Instructor DB","email@test.com", "555-555-5555", 25);
        Note note =new Note(4,"11/17/22","testing note DB",25);
        Term term =new Term(5,"Testing Term DB","11/16/22", "11/15/22");

        repo.insert(assessment);
        repo.insert(course);
        repo.insert(instructor);
        repo.insert(note);
        repo.insert(term);
    }

    public void allTermsBtnPressed(View view){
        Intent intent = new Intent(MainActivity.this, TermsActivity.class);
        startActivity(intent);
    }

    public void allCoursesBtn(View view) {
        Intent intent = new Intent(MainActivity.this, CourseActivity.class);
        startActivity(intent);
    }

    public void allAssessmentsBtn(View view) {
        Intent intent = new Intent(MainActivity.this, AssessmentsActivity.class);
        startActivity(intent);
    }
}