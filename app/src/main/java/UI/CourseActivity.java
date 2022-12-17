package UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

import java.util.ArrayList;

import Entities.Term;

public class CourseActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

    }

    public void pressedListAllCoursesBtn(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerViewCourses, new AllCoursesListFragment());
        fragmentTransaction.addToBackStack("allCoursesListFragmentView");
        fragmentTransaction.commit();
    }

    public void pressedAddCourseBtn(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerViewCourses, new AddNewCourseFragment());
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

            default:return super.onOptionsItemSelected(item);
        }
    }
}