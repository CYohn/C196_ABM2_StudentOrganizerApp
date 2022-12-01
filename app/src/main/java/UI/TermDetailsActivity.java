package UI;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.zybooks.c196_abm2_charity_yohn.R;

public class TermDetailsActivity extends AppCompatActivity {

    EditText termTitleEditTxt;
    EditText termStartDateEditTxt;
    EditText termEndDateEditTxt;
    String name;
    String startDate;
    String endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        termTitleEditTxt=findViewById(R.id.termTitleEditTxt);
        termStartDateEditTxt=findViewById(R.id.termStartDateEditTxt);
        termEndDateEditTxt=findViewById(R.id.termEndDateEditTxt);

        name=getIntent().getStringExtra("termTitleValue");
        startDate=getIntent().getStringExtra("termStartDateValue");
        endDate=getIntent().getStringExtra("termEndDateValue");

        termTitleEditTxt.setText(name);
        termStartDateEditTxt.setText(startDate);
        termEndDateEditTxt.setText(endDate);

    }
}