package UI;

import static androidx.core.app.ShareCompat.getCallingActivity;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Database.RepositoryForStudentOrganizer;
import Entities.Assessment;
import Entities.Course;
import Entities.Term;

//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AssessmentDetailsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class AssessmentDetailsFragment extends Fragment {

private String assessmentTitle;
private String assessmentStartDate;
private String assessmentEndDate;
private String assessmentType;
private int courseIdNumber;
private int assessmentId;

EditText titleText;
Button startDateBtn;
Button endDateBtn;
Spinner associatedCourseSpinner;
ToggleButton assessmentTypeToggle;
ImageButton closeBtn;
ImageButton alertsBtn;
ImageButton saveBtn;
ImageButton deleteBtn;

RepositoryForStudentOrganizer.Repository repo;
ArrayList<Course> courseArrayList;
final Calendar startDateCalendar = Calendar.getInstance();
DatePickerDialog.OnDateSetListener startDateDialog;
final Calendar endDateCalendar = Calendar.getInstance();
DatePickerDialog.OnDateSetListener endDateDialog;

Bundle bundle;



    public AssessmentDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            assessmentTitle = bundle.getString("assessmentTitleTxtView");
            assessmentStartDate = bundle.getString("assessmentStartTxtView");
            assessmentEndDate = bundle.getString("assessmentEndTxtView");
            assessmentType = bundle.getString("assessmentTypeTxtView");
            courseIdNumber = bundle.getInt("associatedCourseId", -1);
            assessmentId = bundle.getInt("assessmentId", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assessment_details, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());
        courseArrayList = (ArrayList<Course>) repo.getmAllCourses();

        associatedCourseSpinner = (Spinner) getView().findViewById(R.id.assessmentAssociatedCourseSpinner);
        ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<>(this.getActivity(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, courseArrayList);
        associatedCourseSpinner.setAdapter(courseArrayAdapter);


        titleText = (EditText) getView().findViewById(R.id.assessmentNameTxtInput);
        startDateBtn = (Button) getView().findViewById(R.id.assessmentStartDateBtn);
        endDateBtn = (Button) getView().findViewById(R.id.assessmentEndDateBtn);
        deleteBtn = (ImageButton) getView().findViewById(R.id.deleteAssessmentBtn);
        alertsBtn = (ImageButton) getView().findViewById(R.id.assessmentAlarmBtn);
        saveBtn = (ImageButton) getView().findViewById(R.id.assessmentSaveButton);

        assessmentTypeToggle = (ToggleButton) getView().findViewById(R.id.assessmentTypeToggle);

        if (bundle != null) {

            assessmentTitle = bundle.getString("assessmentTitleTxtView");
            assessmentStartDate = bundle.getString("assessmentStartTxtView");
            assessmentEndDate = bundle.getString("assessmentEndTxtView");
            assessmentType = bundle.getString("assessmentTypeTxtView");
            courseIdNumber = bundle.getInt("associatedCourseId", -1);
            assessmentId = bundle.getInt("assessmentId", -1);

            int coursePosition = getItemPosition(courseIdNumber, courseArrayList);
            associatedCourseSpinner.setSelection(coursePosition);

            titleText.setText(assessmentTitle, TextView.BufferType.EDITABLE);
            startDateBtn.setText(assessmentStartDate);
            endDateBtn.setText(assessmentEndDate);
            //Spinner associatedCourseSpinner;
            String performance = "Performance";
            String objective = "Objective";
            if (assessmentType.equalsIgnoreCase(performance)) {
                assessmentTypeToggle.setText("Performance");
                assessmentTypeToggle.setChecked(true);
            }
            if (assessmentType.equalsIgnoreCase(objective)) {
                assessmentTypeToggle.setText("Objective");
                assessmentTypeToggle.setChecked(false);
            }else{assessmentTypeToggle.setText("Type");}
        } else {
            titleText.setText("Please Enter Assessment Title");
            startDateBtn.setText("Start Date");
            endDateBtn.setText("End Date");
            assessmentTypeToggle.setChecked(false);
            assessmentTypeToggle.setText("Type");
            associatedCourseSpinner.setSelection(0);
        }

        alertsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Handling the delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Assessment assessment;
                Bundle bundle = getArguments();

                if (bundle != null) {
                    assessmentId = bundle.getInt("assessmentId", -1);
                    if (assessmentId != -1) {
                        assessmentId = bundle.getInt("assessmentId");
                        assessmentType = bundle.getString("assessmentTypeTxtView");
                        assessmentStartDate = bundle.getString("assessmentStartTxtView");
                        assessmentEndDate = bundle.getString("assessmentEndTxtView");
                        assessmentTitle = bundle.getString("assessmentTitleTxtView");
                        courseIdNumber = bundle.getInt("associatedCourse");

                        assessment = new Assessment(assessmentId, assessmentType,
                                assessmentTitle, assessmentEndDate,
                                assessmentStartDate, courseIdNumber);

                        //Set an alert to confirm the coice to delete
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Permanently Deleting Assessment");
                        builder.setMessage("Are you sure you wish to permanently delete this assessment?");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        repo.delete(assessment);
                                        //Advise the user the assessment was deleted
                                        Context context = getContext();
                                        CharSequence text = "Assessment successfully deleted.";
                                        int duration = Toast.LENGTH_LONG;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                        //Send the user back to the All Assessments List
                                        Fragment assessmentList = new AllAssessmentsListFragment();
                                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.assessmentFragmentContainerView, assessmentList);
                                        fragmentTransaction.addToBackStack("AssessmentListFragment");
                                        fragmentTransaction.commit();
                                    }
                                });
                        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Do nothing
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

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getArguments();
                associatedCourseSpinner = view.findViewById(R.id.assessmentAssociatedCourseSpinner);
                Course selectedCourse = (Course) associatedCourseSpinner.getSelectedItem();
                int courseId = selectedCourse.getCourseId();

                String assessmentTitle = ((EditText) getView().findViewById(R.id.assessmentNameTxtInput)).getText().toString();
                String endDate = ((Button) getView().findViewById(R.id.assessmentStartDateBtn)).getText().toString();
                String startDate = ((Button) getView().findViewById(R.id.assessmentEndDateBtn)).getText().toString();
                String assessmentType = assessmentTypeToggle.getText().toString();
                int assessmentId;
                if (bundle != null){
                    assessmentId = bundle.getInt("assessmentId", -1);
                } else{assessmentId = -1;}


                //Save info to DB
                Assessment assessment;
                if(assessmentId == -1){
                    //Without this line, the program was throwing a null pointer exception for the repo
                    repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());
                    int newId = repo.getmAllCourses().get(repo.getmAllCourses().size()).getCourseId() + 1;;
                    System.out.println("newId no: " + newId);
                    try {
                        assessment = new Assessment(newId, assessmentType,
                                assessmentTitle, endDate, startDate, courseId);
                        repo.insert(assessment);
                        //Thread.sleep(1000);
                    } catch (Exception assessmentInsertException){
                        assessmentInsertException.printStackTrace();
                        assessmentInsertException.getCause();
                        assessmentInsertException.getMessage();
                    }


                    //Inform the user the assessment was saved
                    Context context = getContext();
                    CharSequence text = "Assessment saved successfully";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {

                    if (assessmentId != -1) {
                        assessmentId = bundle.getInt("assessmentId", -1);
                        assessment = new Assessment(assessmentId, assessmentType,
                                assessmentTitle, endDate, startDate, courseId);
                        repo.update(assessment);

                        //Inform the user the assessment updated successfully
                        Context context = getContext();
                        CharSequence text = "Assessment updated successfully";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }

                String activityName = getActivity().getClass().getCanonicalName();
                System.out.println("Activity name =  " + activityName);

                if (activityName.equals("UI.CourseActivity")) {
                    ArrayList<Course> courseList = (ArrayList<Course>)repo.getmAllCourses();
                    courseId = bundle.getInt("associatedCourse");
                    int termId;
                    String courseTitle;
                    String courseStart;
                    String courseEnd;
                    String courseInstructor;
                    String courseProgress;
                    int insructorId;
                    for (int i = 0; i < courseList.size(); i++){
                        Course course = (Course) courseList.get(i);
                        if (course.getCourseId() == courseId){
                            termId = course.getAssociatedTermId();
                            courseTitle = course.getCourseTitle();
                            courseStart = course.getCourseStartDate();
                            courseEnd = course.getCourseEndDate();
                            courseInstructor = course.getCourseInstructor();
                            courseProgress = course.getCourseStatus();
                            insructorId = course.getInstructorId();
                        }
                    }
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("associatedTerm", selectedCourse.getAssociatedTermId());
                    bundle1.putInt("courseId", selectedCourse.getCourseId());
                    bundle1.putString("courseTitle", selectedCourse.getCourseTitle());
                    bundle1.putString("courseStart", selectedCourse.getCourseStartDate());
                    bundle1.putString("courseEnd", (selectedCourse.getCourseEndDate()));
                    bundle1.putString("courseInstructor", selectedCourse.getCourseInstructor());
                    bundle1.putInt("insructorId", selectedCourse.getInstructorId());
                    bundle1.putString("courseStatus", selectedCourse.getCourseStatus());

                    Fragment courseDetails = new CourseDetailsFragment();
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    courseDetails.setArguments(bundle1);
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, courseDetails);
                    fragmentTransaction.addToBackStack("CourseDetailsView");
                    fragmentTransaction.commit();

                }else{ // The hosting activity is UI.AssessmentActivity
                    Fragment assessmentList = new AllAssessmentsListFragment();
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.assessmentFragmentContainerView, assessmentList);
                    fragmentTransaction.addToBackStack("AssessmentListFragment");
                    fragmentTransaction.commit();
                }


            }
        });

        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateFormat = "MM/dd/YY";
                String dateString = startDateBtn.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
                try {
                    startDateCalendar.setTime(simpleDateFormat.parse(dateString));
                } catch (ParseException dateParseException) {
                    dateParseException.printStackTrace();
                    dateParseException.getMessage();
                    dateParseException.getCause();
                }
                new DatePickerDialog(getContext(), startDateDialog, startDateCalendar.get(YEAR),
                        startDateCalendar.get(MONTH), startDateCalendar.get(DAY_OF_MONTH)).show();
            }
        });

        startDateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                startDateCalendar.set(YEAR, year);
                startDateCalendar.set(MONTH, monthOfYear);
                startDateCalendar.set(DAY_OF_MONTH, dayOfMonth);
                updateStartDateLabel();
            }
        };

        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateFormat = "MM/dd/YY";
                String dateString = endDateBtn.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
                try {
                    endDateCalendar.setTime(simpleDateFormat.parse(dateString));
                } catch (ParseException dateParseException) {
                    dateParseException.printStackTrace();
                    dateParseException.getMessage();
                    dateParseException.getCause();
                }
                new DatePickerDialog(getContext(), endDateDialog, endDateCalendar.get(YEAR),
                        endDateCalendar.get(MONTH), endDateCalendar.get(DAY_OF_MONTH)).show();
            }
        });

        endDateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                endDateCalendar.set(YEAR, year);
                endDateCalendar.set(MONTH, monthOfYear);
                endDateCalendar.set(DAY_OF_MONTH, dayOfMonth);
                updateEndDateLabel();
            }
        };
    }


    public int getItemPosition ( int id, ArrayList arrayList){
        if (arrayList.get(0) instanceof Course) {
            for (int i = 0; i < arrayList.size(); i++) {
                Course course = (Course) arrayList.get(i);
                if (course.getCourseId() == id) {
                    int index = arrayList.indexOf(course);
                    return index;
                }
            }
        }
        return -1;
    };

    private void updateStartDateLabel() {
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        startDateBtn = (Button) getView().findViewById(R.id.assessmentStartDateBtn);
        startDateBtn.setText(simpleDateFormat.format(startDateCalendar.getTime()));
    }

    private void updateEndDateLabel() {
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        endDateBtn = (Button) getView().findViewById(R.id.assessmentEndDateBtn);
        endDateBtn.setText(simpleDateFormat.format(endDateCalendar.getTime()));
    }
}
