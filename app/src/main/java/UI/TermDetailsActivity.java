package UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.c196_abm2_charity_yohn.R;

import Database.RepositoryForStudentOrganizer;
import Entities.Term;

public class TermDetailsActivity extends AppCompatActivity {

    EditText termTitleEditTxt;
    EditText termStartDateEditTxt;
    EditText termEndDateEditTxt;
    String termName;
    String termStartDate;
    String termEndDate;
    int termId;
    RepositoryForStudentOrganizer.Repository repo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        termId = getIntent().getIntExtra("id", -1);
        termTitleEditTxt=findViewById(R.id.termTitleEditTxt);
        termStartDateEditTxt=findViewById(R.id.termStartDateEditTxt);
        termEndDateEditTxt=findViewById(R.id.termEndDateEditTxt);

        termName =getIntent().getStringExtra("termTitleValue");
        termStartDate =getIntent().getStringExtra("termStartDateValue");
        termEndDate =getIntent().getStringExtra("termEndDateValue");

        termTitleEditTxt.setText(termName);
        termStartDateEditTxt.setText(termStartDate);
        termEndDateEditTxt.setText(termEndDate);

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

    public void termSaveAndAddCourseBtn(View view){
        Term term;
        if (termId == -1) {
            int newID = repo.getmAllTerms().get(repo.getmAllTerms().size() - 1).getTermId() + 1;
            term = new Term(newID, termTitleEditTxt.getText().toString(), termStartDateEditTxt.getText().toString(), termEndDateEditTxt.getText().toString());
            repo.insert(term);
        } else {
            term = new Term(termId, termTitleEditTxt.getText().toString(), termStartDateEditTxt.getText().toString(), termEndDateEditTxt.getText().toString());
            repo.update(term);
        }
    }
}