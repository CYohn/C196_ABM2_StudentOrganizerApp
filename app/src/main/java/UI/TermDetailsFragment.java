package UI;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Database.RepositoryForStudentOrganizer;
import Entities.Term;

public class TermDetailsFragment extends Fragment {

    //private final Context context;

    private String termTitle;
    private String termStart;
    private String termEnd;
    private int termId;

    EditText nameEditText;
    ImageButton closeBtn;
    ImageButton saveBtn;
    ImageButton nextBtn;

    Button startBtn;
    Button endBtn;
    final Calendar startDateCalendar = Calendar.getInstance();
    final Calendar endDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDateDialog;
    DatePickerDialog.OnDateSetListener endDateDialog;

    RepositoryForStudentOrganizer.Repository repo;

    public TermDetailsFragment() {
        // Required empty public constructor
    }


//    public static TermDetailsFragment newInstance(final String param1, final String param2) {
//        final TermDetailsFragment fragment = new TermDetailsFragment();
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            termTitle = bundle.getString("termTitleValue");
            termStart = bundle.getString("termStartDateValue");
            termEnd = bundle.getString("termEndDateValue");
            termId = bundle.getInt("termId", -1);

            repo=new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());

        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_term_details, container, false);

        return view;
    }


    public  void onViewCreated(final View view, @Nullable final Bundle savedInstanceState){

//        Term termTest = new Term(2,"Testing Update Without UI", "Start", "End");
//        repo.insert(termTest);

        Bundle bundle = getArguments();

        closeBtn = (ImageButton) getView().findViewById(R.id.closeAddTermsBtn);
        nameEditText = (EditText) getView().findViewById(R.id.termNameEditText);
        startBtn = (Button) getView().findViewById(R.id.termStartBtn);
        endBtn = (Button) getView().findViewById(R.id.termEndBtn);
        saveBtn = (ImageButton) getView().findViewById(R.id.termSaveBtn);
        nextBtn = (ImageButton) getView().findViewById(R.id.addTermNextBtn);



        if (bundle != null){
            termId = bundle.getInt("termId");
            nameEditText.setText(termTitle, TextView.BufferType.EDITABLE);
            startBtn.setText(termStart);
            endBtn.setText(termEnd);
        }
        else{
            termId = -1;
            nameEditText.setText("Please Enter the Term Name(ex: Spring 2022)");
            startBtn.setText("Start Date");
            endBtn.setText("End Date");
        }

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment termList = new AllTermsListFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity)getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.termsFragmentContainerView, termList);
                fragmentTransaction.addToBackStack("TermListFragmentView");
                fragmentTransaction.commit();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),CourseActivity.class);
                intent.putExtra("associatedTerm", termId);
                Context context = getContext();
                context.startActivity(intent);
            }

        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int termID = termId;
                String termName = ((EditText) getView().findViewById(R.id.termNameEditText)).getText().toString();
                //TODO: GET THE OTHER INFO FROM THE VIEW
                String startDate = startBtn.getText().toString();
                String endDate = endBtn.getText().toString();

                //Save info to DB
                Term term;
                if (termId == -1){
                    repo=new RepositoryForStudentOrganizer.Repository(getActivity().getApplication()); //Without this line, the program was throwing a null pointer exception for the repo
                    int newId = repo.getmAllTerms().get(repo.getmAllTerms().size() - 1).getTermId() + 1;//get the ID of the last term in the list
                    term = new Term(newId, termName, startDate, endDate);
                    repo.insert(term);
                }
                else{
                    term = new Term(termID, termName, startDate, endDate);
                    repo.update(term);
                }
                Fragment termList = new AllTermsListFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity)getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.termsFragmentContainerView, termList);
                fragmentTransaction.addToBackStack("TermListFragmentView");
                fragmentTransaction.commit();
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateFormat = "MM/dd/YY";
                String dateString = startBtn.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
                try {
                    startDateCalendar.setTime(simpleDateFormat.parse(dateString));
                } catch (ParseException startDateParseException){
                    startDateParseException.printStackTrace();
                    startDateParseException.getMessage();
                    startDateParseException.getCause();
                }
                new DatePickerDialog(getContext(), startDateDialog, startDateCalendar.get(YEAR),
                    startDateCalendar.get(MONTH), startDateCalendar.get(DAY_OF_MONTH)).show();
            }

        });
        startDateDialog = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth ){
                startDateCalendar.set(YEAR, year);
                startDateCalendar.set(MONTH, monthOfYear);
                startDateCalendar.set(DAY_OF_MONTH, dayOfMonth);
                updateStartDateLabel();
            }
        };

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateFormat = "MM/dd/YY";
                String dateString = endBtn.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
                try {
                    endDateCalendar.setTime(simpleDateFormat.parse(dateString));
                } catch (ParseException endDateParseException){
                    endDateParseException.printStackTrace();
                    endDateParseException.getMessage();
                    endDateParseException.getCause();
                }
                new DatePickerDialog(getContext(), endDateDialog, endDateCalendar.get(YEAR),
                        endDateCalendar.get(MONTH), endDateCalendar.get(DAY_OF_MONTH)).show();
            }

        });
        endDateDialog = new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth ){
                endDateCalendar.set(YEAR, year);
                endDateCalendar.set(MONTH, monthOfYear);
                endDateCalendar.set(DAY_OF_MONTH, dayOfMonth);
                updateEndDateLabel();
            }
        };
    }

    private void updateStartDateLabel(){
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        startBtn.setText(simpleDateFormat.format(startDateCalendar.getTime()));
    }

    private void updateEndDateLabel(){
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        endBtn.setText(simpleDateFormat.format(endDateCalendar.getTime()));
    }

}