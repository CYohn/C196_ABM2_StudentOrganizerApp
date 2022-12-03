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

public class NotesDetailsActivity extends AppCompatActivity {

    EditText noteTitleTxt;
    EditText noteDateTxt;
    EditText noteTxt;
    //EditText associatedCourseSpinner;


    String noteTitleValue;
    String noteDateValue;
    String noteTxtValue;
   //String associatedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);

        noteTitleTxt =findViewById(R.id.noteTitleTxt);
        noteDateTxt =findViewById(R.id.noteDateTxtInput);
        noteTxt=findViewById(R.id.noteTxtInput);


        noteTitleValue=getIntent().getStringExtra("noteTitleValue");
        noteDateValue=getIntent().getStringExtra("noteDateValue");
        noteTxtValue=getIntent().getStringExtra("noteTextValue");
        //associatedCourse=getIntent().getStringExtra("associatedCourseValue"); ** Add to Adapter


        noteTitleTxt.setText(noteTitleValue);
        noteDateTxt.setText(noteDateValue);
        noteTxt.setText(noteTxtValue);
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