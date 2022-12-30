package UI;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.zybooks.c196_abm2_charity_yohn.R;

import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Database.RepositoryForStudentOrganizer;
import Entities.Course;
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
    ImageButton deleteBtn;

    Button startBtn;
    Button endBtn;
    final Calendar startDateCalendar = Calendar.getInstance();
    final Calendar endDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDateDialog;
    DatePickerDialog.OnDateSetListener endDateDialog;

    RepositoryForStudentOrganizer.Repository repo;
    ArrayList<Course> courseArrayList;

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

        Bundle bundle = getArguments();

        closeBtn = (ImageButton) getView().findViewById(R.id.closeAddTermsBtn);
        nameEditText = (EditText) getView().findViewById(R.id.termNameEditText);
        startBtn = (Button) getView().findViewById(R.id.termStartBtn);
        endBtn = (Button) getView().findViewById(R.id.termEndBtn);
        saveBtn = (ImageButton) getView().findViewById(R.id.termSaveBtn);
        nextBtn = (ImageButton) getView().findViewById(R.id.addTermNextBtn);
        deleteBtn = (ImageButton) getView().findViewById(R.id.deleteTermBtn);



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

        //Handling the delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Term term;
                Bundle bundle = getArguments();

                if (bundle != null) {
                    termId = bundle.getInt("termId", -1);
                    if (termId != -1) {
                        termId = bundle.getInt("termId");
                        termStart = bundle.getString("termStartDateValue");
                        termEnd = bundle.getString("termEndDateValue");
                        termTitle = bundle.getString("termTitleValue");

                        term = new Term(termId, termTitle, termStart,
                                termEnd);

                        //Set an alert to confirm the choice to delete
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Permanently Deleting Term");
                        builder.setMessage("Are you sure you wish to permanently delete this Term?");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        boolean anyCourses = checkForAssociatedCourses(termId, courseArrayList);
                                        if (anyCourses == false){
                                            repo.delete(term);
                                            Fragment allterms = new AllTermsListFragment();
                                            FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.termsFragmentContainerView, allterms);
                                            fragmentTransaction.addToBackStack("AllTermsView");
                                            fragmentTransaction.commit();
                                        }
                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        //Else if the assessmentId is the default -1, then no assessment exists to delete -
                        //alert the user.
                    } else {
                        Context context = getContext();
                        CharSequence text = "Assessment must be saved before deleting.";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }else{
                    Context context = getContext();
                    CharSequence text = "Assessment must be saved before deleting.";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
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

    private boolean checkForAssociatedCourses(int termId, ArrayList courseArrayList){
        courseArrayList = new ArrayList<Course>(repo.getmAllCourses());

        for (int i=0; i < courseArrayList.size(); i++ ) {
            Course course = (Course) courseArrayList.get(i);
            int associatedTerm = course.getAssociatedTermId();
            if(associatedTerm == termId){
            //Show user the dialog informing them to delete associated courses first.
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setCancelable(false);
            builder.setTitle("Associated Courses Found");
            builder.setMessage("Please delete the courses associated with this term before deleting the term. Thank you.");
            builder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        }
        return false;
    }
}