package UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.c196_abm2_charity_yohn.R;

public class CourseDetailsActivity extends AppCompatActivity {


    EditText courseTitleEditTxt;
    EditText courseStartDateEditTxt;
    EditText courseEndDateEditTxt;
    EditText courseStatusEditTxt;
    EditText courseInstructorEditTxt;

    String courseName;
    String courseStartDate;
    String courseEndDate;
    String courseInstructor;
    String courseStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);


        courseTitleEditTxt = findViewById(R.id.courseTitleEditTxt);
        courseStartDateEditTxt = findViewById(R.id.courseStartEditTxt);
        courseEndDateEditTxt = findViewById(R.id.courseEndEditTxt);
        courseStatusEditTxt = findViewById(R.id.courseStatusEditTxt);
        courseInstructorEditTxt = findViewById(R.id.courseInstructorEditTxt);

        courseName = getIntent().getStringExtra("courseTitleValue");
        courseStartDate = getIntent().getStringExtra("courseStartValue");
        courseEndDate = getIntent().getStringExtra("courseEndValue");
        courseStatus = getIntent().getStringExtra("courseStatusValue");
        courseInstructor = getIntent().getStringExtra("courseInstructorValue");

        courseTitleEditTxt.setText(courseName);
        courseStartDateEditTxt.setText(courseStartDate);
        courseEndDateEditTxt.setText(courseEndDate);
        courseStatusEditTxt.setText(courseStatus);
        courseInstructorEditTxt.setText(courseInstructor);

        Button notesBtn;
        notesBtn = (Button) findViewById(R.id.notesBtn);
        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetailsActivity.this, NotesActivity.class);
                CourseDetailsActivity.this.startActivity(intent);
            }
        });

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





    public void assessmentsBtnPressed(View view) {
    }

    public void notesBtnPressed(View view) {
        Intent intent = new Intent(CourseDetailsActivity.this, NotesActivity.class);
        startActivity(intent);
    }
}

