package UI;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

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
}