package UI;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

public class AssessmentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);
    }

    public void pressedListAllAssessmentsBtn(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.assessmentFragmentContainerView, new AllAssessmentsListFragment());
        fragmentTransaction.addToBackStack("AllAssessmentsListFragment");
        fragmentTransaction.commit();
    }


    public void pressedOpenAddAssessmentFragBtn(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.assessmentFragmentContainerView, new AddAssessmentFragment());
        fragmentTransaction.addToBackStack("AddAssessmentFragmentView");
        fragmentTransaction.commit();
    }
}