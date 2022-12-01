package UI;

import android.os.Bundle;
import android.widget.EditText;

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
}