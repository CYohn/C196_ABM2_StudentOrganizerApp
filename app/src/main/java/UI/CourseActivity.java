package UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

import Database.RepositoryForStudentOrganizer;

public class CourseActivity extends AppCompatActivity {
    RepositoryForStudentOrganizer.Repository repo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RepositoryForStudentOrganizer.Repository repo = new RepositoryForStudentOrganizer.Repository(getApplication());
        int associatedTerm = getIntent().getIntExtra("associatedTerm", -1);
        setContentView(R.layout.activity_courses);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            Bundle bundle1 = new Bundle();

            bundle1.putInt("associatedTerm", getIntent().getIntExtra("associatedTerm", -1));
            bundle1.putInt("courseId", -1);
            bundle1.putString("courseTitle", "Course Title");
            bundle1.putString("courseStart", "Start");
            bundle1.putString("courseEnd", "End");
            bundle1.putString("courseInstructor", "None");
            bundle1.putInt("insructorId", -1);
            bundle1.putString("notifyStart", "Start");
            bundle1.putString("notifyEnd", "End");
            bundle1.putString("courseStatus", "Unchecked");


            Fragment addCourse = new CourseDetailsFragment();
            addCourse.setArguments(bundle1);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerViewCourses, addCourse);
            fragmentTransaction.addToBackStack("addCourseFragmentView");
            fragmentTransaction.commit();
        }


        else{
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerViewCourses, new AllCoursesListFragment());
            fragmentTransaction.addToBackStack("allCoursesListFragmentView");
            fragmentTransaction.commit();
        }

    }

    public void pressedListAllCoursesBtn(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerViewCourses, new AllCoursesListFragment());
        fragmentTransaction.addToBackStack("allCoursesListFragmentView");
        fragmentTransaction.commit();
    }

    public void pressedAddCourseBtn(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerViewCourses, new CourseDetailsFragment());
        fragmentTransaction.addToBackStack("addCourseFragmentView");
        fragmentTransaction.commit();
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
                Intent termsIntent = new Intent(this, TermsActivity.class);
                startActivity(termsIntent);
                return true;

            case R.id.coursesMenuOption:
                Intent courseIntent = new Intent(this, CourseActivity.class);
                startActivity(courseIntent);
                return true;


            case R.id.assessmentsMenuOption:
                Intent assessmentIntent = new Intent(this, AssessmentsActivity.class);
                startActivity(assessmentIntent);
                return true;

            case R.id.notesMenuOption:
                Intent notesIntent = new Intent(this, NotesActivity.class);
                startActivity(notesIntent);
                return true;
            case R.id.instructorMenuOption:
                Intent instructorIntent = new Intent(this, InstructorActivity.class);
                startActivity(instructorIntent);
                return true;

            default:return super.onOptionsItemSelected(item);
        }
    }
}