package UI;

import android.os.Bundle;
import android.widget.EditText;

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
}