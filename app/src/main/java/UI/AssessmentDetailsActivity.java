package UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.c196_abm2_charity_yohn.R;

public class AssessmentDetailsActivity extends AppCompatActivity {
    EditText assessmentTitleEditTxt;
    EditText assessmentStartEditTxt;
    EditText assessmentEndDateEditTxt;
    EditText assessmentTypeEditTxt;

    String assessmentTitle;
    String assessmentStartDate;
    String assessmentEndDate;
    String assessmentType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        assessmentTitleEditTxt=findViewById(R.id.assessmentTitleEditTxt);
        assessmentStartEditTxt =findViewById(R.id.assessmentStartEditTxt);
        assessmentEndDateEditTxt=findViewById(R.id.assessmentEndEditTxt);
        assessmentTypeEditTxt=findViewById(R.id.assessmentTypeEditTxt);

        assessmentTitle=getIntent().getStringExtra("assessmentTitleTxtView");
        assessmentStartDate=getIntent().getStringExtra("assessmentStartTxtView");
        assessmentEndDate=getIntent().getStringExtra("assessmentEndTxtView");
        assessmentType=getIntent().getStringExtra("assessmentTypeTxtView");


        assessmentTitleEditTxt.setText(assessmentTitle);
        assessmentStartEditTxt.setText(assessmentStartDate);
        assessmentEndDateEditTxt.setText(assessmentEndDate);
        assessmentTypeEditTxt.setText(assessmentType);
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