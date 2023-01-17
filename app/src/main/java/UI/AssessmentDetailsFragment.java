package UI;

import static androidx.core.app.ShareCompat.getCallingActivity;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getAvailableLocales;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.text.BreakIterator;
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
    private String assessmentNotifyStart;
    private String assessmentNotifyEnd;

    EditText titleText;
    Button startDateBtn;
    Button endDateBtn;
    Spinner associatedCourseSpinner;
    ToggleButton assessmentTypeToggle;
    ImageButton saveBtn;
    ImageButton deleteBtn;
    Button endNotification;
    Button startNotification;

    RepositoryForStudentOrganizer.Repository repo;
    ArrayList<Course> courseArrayList;
    ArrayList<Assessment> assessmentArrayList;
    final Calendar startDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDateDialog;
    final Calendar endDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDateDialog;
    final Calendar notifyStartDateCalendar = Calendar.getInstance();
    final Calendar notifyEndDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener notifyStartDateDialog;
    DatePickerDialog.OnDateSetListener notifyEndDateDialog;

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
            assessmentNotifyStart = bundle.getString("assessmentNotifyStart");
            assessmentNotifyEnd = bundle.getString("assessmentNotifyEnd");
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
        assessmentArrayList = (ArrayList<Assessment>) repo.getmAllAssessments();

        associatedCourseSpinner = (Spinner) getView().findViewById(R.id.assessmentAssociatedCourseSpinner);
        ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<>(this.getActivity(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, courseArrayList);
        associatedCourseSpinner.setAdapter(courseArrayAdapter);


        titleText = (EditText) getView().findViewById(R.id.assessmentNameTxtInput);
        startDateBtn = (Button) getView().findViewById(R.id.assessmentStartDateBtn);
        endDateBtn = (Button) getView().findViewById(R.id.assessmentEndDateBtn);
        deleteBtn = (ImageButton) getView().findViewById(R.id.deleteAssessmentBtn);
        saveBtn = (ImageButton) getView().findViewById(R.id.assessmentSaveButton);
        startNotification = (Button) getView().findViewById(R.id.startNotificationBtn);
        endNotification = (Button) getView().findViewById(R.id.endNotificationBtn);
        assessmentTypeToggle = (ToggleButton) getView().findViewById(R.id.assessmentTypeToggle);
        startNotification.setText("Start");
        endNotification.setText("End");

        if (bundle != null) {

            assessmentTitle = bundle.getString("assessmentTitleTxtView");
            assessmentStartDate = bundle.getString("assessmentStartTxtView");
            assessmentEndDate = bundle.getString("assessmentEndTxtView");
            assessmentType = bundle.getString("assessmentTypeTxtView");
            courseIdNumber = bundle.getInt("associatedCourseId", -1);
            assessmentId = bundle.getInt("assessmentId", -1);

            assessmentNotifyStart = bundle.getString("assessmentNotifyStart");
            assessmentNotifyEnd = bundle.getString("assessmentNotifyEnd");

            String formattedNotifyStart;
            if(assessmentNotifyStart.equals(null)){
                formattedNotifyStart = "Start";
            } else {
                //formattedNotifyStart = formatDate(assessmentNotifyStart);
                formattedNotifyStart = assessmentNotifyStart.toString();
            }
            String formattedNotifyEnd;
            if(assessmentNotifyEnd.equals(null)){
                formattedNotifyEnd = "End";
            } else {
                //formattedNotifyEnd = formatDate(assessmentNotifyEnd);
                formattedNotifyEnd = assessmentNotifyEnd.toString();
            }

            int coursePosition = getItemPosition(courseIdNumber, courseArrayList);
            associatedCourseSpinner.setSelection(coursePosition);

            titleText.setText(assessmentTitle, TextView.BufferType.EDITABLE);
            startDateBtn.setText(assessmentStartDate);
            endDateBtn.setText(assessmentEndDate);
            startNotification.setText(formattedNotifyStart);
            endNotification.setText(formattedNotifyEnd);

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
            startNotification.setText("Start");
            endNotification.setText("End");
        }



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
                        assessmentNotifyStart = bundle.getString("assessmentNotifyStart");
                        assessmentNotifyEnd = bundle.getString("assessmentNotifyEnd");

                        assessment = new Assessment(assessmentId, assessmentType,
                                assessmentTitle, assessmentEndDate,
                                assessmentStartDate, courseIdNumber, assessmentNotifyStart,assessmentNotifyEnd);

                        //Set an alert to confirm the choice to delete
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

                int courseId;
                if(selectedCourse != null){
                    courseId = selectedCourse.getCourseId();
                }else{courseId = -1;}

                String assessmentTitle = ((EditText) getView().findViewById(R.id.assessmentNameTxtInput)).getText().toString();
                String endDate = ((Button) getView().findViewById(R.id.assessmentStartDateBtn)).getText().toString();
                String startDate = ((Button) getView().findViewById(R.id.assessmentEndDateBtn)).getText().toString();
                String assessmentType = assessmentTypeToggle.getText().toString();
                int assessmentId;
                int assessmentRepoSize = repo.getmAllAssessments().size();
                String notifyStartDate = startNotification.getText().toString();
                String notifyEndDate = endNotification.getText().toString();


                if (bundle != null){
                    assessmentId = bundle.getInt("assessmentId", -1);
                } else{assessmentId = -1;}

                //Save info to DB
                Assessment assessment;
                if(assessmentId == -1){
                    if(assessmentRepoSize == 0){
                        assessmentId = 1;
                        assessment = new Assessment(assessmentId, assessmentType,
                                assessmentTitle, endDate, startDate,
                                courseId, notifyStartDate, notifyEndDate);
                        repo.insert(assessment);


                        //Get the date format of the notification start then pass it to the boradcastreceiver
                        //to set the notification
                        if(!notifyStartDate.equals("Notify Start")&&!notifyStartDate.equals("Start")){
                            Date dateNotifyStart = getStartNotificationDate();
                            triggerAlertBroadcastReciever(dateNotifyStart);
                        }
                        //Same with the end notification date
                        if(!notifyEndDate.equals("Notify End")&&!notifyEndDate.equals("End")){
                            Date dateNotifyEnd = getEndNotificationDate();
                            triggerAlertBroadcastReciever(dateNotifyEnd);
                        }

                        //Inform the user the assessment was saved
                        Context context = getContext();
                        CharSequence text = "Assessment saved successfully";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }else{
                        //Without this line, the program was throwing a null pointer exception for the repo
                        repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());
                        int newId = repo.getmAllAssessments().get(repo.getmAllAssessments().size()-1).getAssessmentId() + 1;
                        assessment = new Assessment(newId, assessmentType,
                                assessmentTitle, endDate, startDate, courseId, notifyStartDate, notifyEndDate);
                        repo.insert(assessment);


                        //Get the date format of the notification start then pass it to the boradcastreceiver
                        //to set the notification
                        if(!notifyStartDate.equals("Notify Start")&& !notifyStartDate.equals("Start")){
                            Date dateNotifyStart = getStartNotificationDate();
                            triggerAlertBroadcastReciever(dateNotifyStart);
                        }
                        //Same with the end notification date
                        if(!notifyEndDate.equals("Notify End")&& !notifyEndDate.equals("End")){
                            Date dateNotifyEnd = getEndNotificationDate();
                            triggerAlertBroadcastReciever(dateNotifyEnd);
                        }


                        //Inform the user the assessment was saved
                        Context context = getContext();
                        CharSequence text = "Assessment saved successfully";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    }
                } else {
                    //Update Existing Assessment
                    if (assessmentId != -1) {
                        assessmentId = bundle.getInt("assessmentId", -1);
                        assessment = new Assessment(assessmentId, assessmentType,
                                assessmentTitle, endDate, startDate, courseId, notifyStartDate, notifyEndDate);
                        repo.update(assessment);

                        //Get the date format of the notification start then pass it to the boradcastreceiver
                        //to set the notification
                        if(!notifyStartDate.equals("Notify Start") && !notifyStartDate.equals("Start")){
                            Date dateNotifyStart = getStartNotificationDate();
                            triggerAlertBroadcastReciever(dateNotifyStart);
                        }else{
                            //Do nothing
                        }
                        //Same with the end notification date
                        if(!notifyEndDate.equals("Notify End")&& !notifyEndDate.equals("End")){
                            Date dateNotifyEnd = getEndNotificationDate();
                            triggerAlertBroadcastReciever(dateNotifyEnd);
                        }else{
                            //Do nothing
                        }

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
//                    ArrayList<Course> courseList = (ArrayList<Course>)repo.getmAllCourses();
//                    courseId = bundle.getInt("associatedCourse");
//                    int termId;
//                    String courseTitle;
//                    String courseStart;
//                    String courseEnd;
//                    String courseInstructor;
//                    String courseProgress;
//                    int insructorId;
//                    String notifyStart;
//                    String notifyEnd;

//                    for (int i = 0; i < courseList.size(); i++){
//                        Course course = (Course) courseList.get(i);
//                        if (course.getCourseId() == courseId){
//                            termId = course.getAssociatedTermId();
//                            courseId = course.getCourseId();
//                            courseTitle = course.getCourseTitle();
//                            courseStart = course.getCourseStartDate();
//                            courseEnd = course.getCourseEndDate();
//                            courseInstructor = course.getCourseInstructor();
//                            courseProgress = course.getCourseStatus();
//                            insructorId = course.getInstructorId();
//                            notifyStart = course.getNotifyStartDate();
//                            notifyEnd = course.getNotifyEndDate();
//                        }
//                    }
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("associatedTerm", selectedCourse.getAssociatedTermId());
                    bundle1.putInt("courseId", selectedCourse.getCourseId());
                    bundle1.putString("courseTitle", selectedCourse.getCourseTitle());
                    bundle1.putString("courseStart", selectedCourse.getCourseStartDate());
                    bundle1.putString("courseEnd", (selectedCourse.getCourseEndDate()));
                    bundle1.putString("courseInstructor", selectedCourse.getCourseInstructor());
                    bundle1.putInt("insructorId", selectedCourse.getInstructorId());
                    bundle1.putString("courseStatus", selectedCourse.getCourseStatus());
                    bundle1.putString("notifyStart", selectedCourse.getNotifyStartDate());
                    bundle1.putString("notifyEnd", selectedCourse.getNotifyEndDate());

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

        startNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button startNotification = (Button) getView().findViewById(R.id.startNotificationBtn);
                String courseTitle = titleText.getText().toString();
                String dateFromScreen = startNotification.getText().toString();
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
                Date date = null;
                try {
                    date = simpleDateFormat.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                    e.getCause();
                    e.getMessage();
                }

                new DatePickerDialog(getContext(), notifyStartDateDialog, notifyStartDateCalendar.get(YEAR),
                        notifyStartDateCalendar.get(MONTH), notifyStartDateCalendar.get(DAY_OF_MONTH)).show();

                updateNotifyStartLabel();


            }


        });

        notifyStartDateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                notifyStartDateCalendar.set(YEAR, year);
                notifyStartDateCalendar.set(MONTH, monthOfYear);
                notifyStartDateCalendar.set(DAY_OF_MONTH, dayOfMonth);
                updateNotifyStartLabel();
            }
        };

        endNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateFromScreen = endNotification.getText().toString();
                String dateFormat = "MM/dd/yy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
                Date date = null;
                try {
                    date = simpleDateFormat.parse(dateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                    e.getCause();
                    e.getMessage();
                }

                new DatePickerDialog(getContext(), notifyEndDateDialog, notifyEndDateCalendar.get(YEAR),
                        notifyEndDateCalendar.get(MONTH), notifyEndDateCalendar.get(DAY_OF_MONTH)).show();
            }
        });

        notifyEndDateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                notifyEndDateCalendar.set(YEAR, year);
                notifyEndDateCalendar.set(MONTH, monthOfYear);
                notifyEndDateCalendar.set(DAY_OF_MONTH, dayOfMonth);
                updateNotifyEndLabel();
            }
        };




    }


    public int getItemPosition ( int id, ArrayList arrayList){
        if(arrayList.size() != 0) {
            if (arrayList.get(0) instanceof Course) {
                for (int i = 0; i < arrayList.size(); i++) {
                    Course course = (Course) arrayList.get(i);
                    if (course.getCourseId() == id) {
                        int index = arrayList.indexOf(course);
                        return index;
                    }
                }
            }
        }
        return -1;
    };


    private void updateEndDateLabel() {
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        //String currentDate = simpleDateFormat.format(new Date());
        endDateBtn = (Button) getView().findViewById(R.id.assessmentEndDateBtn);
        endDateBtn.setText(simpleDateFormat.format(endDateCalendar.getTime()));


    }

    private void updateStartDateLabel() {
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        //String currentDate = simpleDateFormat.format(new Date());
        startDateBtn = (Button) getView().findViewById(R.id.assessmentStartDateBtn);
        startDateBtn.setText(simpleDateFormat.format(startDateCalendar.getTime()));
    }

    private Date getEndNotificationDate() {
        String dateFromScreen = endNotification.getText().toString();
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(MONTH, 0);
        calendar.set(DAY_OF_MONTH, 01);
        calendar.set(YEAR, 1920);
        date = calendar.getTime();
        if (!dateFromScreen.equals("End")&&!dateFromScreen.equals("Notify End")){
            try {
                date = simpleDateFormat.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
                e.getCause();
                e.getMessage();
            }
        }
        return date;
    }

    private Date getStartNotificationDate() {
        String dateFromScreen = startNotification.getText().toString();
        String dateFormat = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(MONTH, 0);
        calendar.set(DAY_OF_MONTH, 01);
        calendar.set(YEAR, 1920);
        date = calendar.getTime();
        if(!dateFromScreen.equals("Start")&&!dateFromScreen.equals("Notify Start"))
            try {
                date = simpleDateFormat.parse(dateFromScreen);
            } catch (ParseException e) {
                e.printStackTrace();
                e.getCause();
                e.getMessage();
            }
        return date;
    }


    private void triggerAlertBroadcastReciever (Date dateFromScreen){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY");
        Date compareDate = new Date();
        try {
            compareDate = simpleDateFormat.parse("01/01/1920");
        }catch (Exception broadcastTriggerException){
            broadcastTriggerException.printStackTrace();
            broadcastTriggerException.getMessage();
            broadcastTriggerException.getCause();
        }
        if(dateFromScreen.compareTo(compareDate) > 0){
            //Set the trigger to the Broadcast receiver
            Long trigger = dateFromScreen.getTime();
            Intent intent = new Intent(getActivity().getApplicationContext(), AlertBroadcastReceiver.class);
            EditText assessmentTitleField = (EditText) getView().findViewById(R.id.assessmentNameTxtInput);
            String assessmentTitleString = assessmentTitleField.getText().toString();
            Button startDateButton = getView().findViewById(R.id.assessmentStartDateBtn);
            String startDate = startDateButton.getText().toString();
            System.out.println("Assessment title: " + assessmentTitleString);
            System.out.println("Date for Assessment Start: " + dateFromScreen);
            //PendingIntent intent = PendingIntent.getActivity(getActivity().getApplicationContext(), 0, new Intent(), 0);
            intent.putExtra("alertMessage", "Course: " + assessmentTitleString + " Starting On " + startDate);
            intent.putExtra("alertCreatedToast", "Alert Number: " + ++MainActivity.alertId + " Saved");
            PendingIntent sender = PendingIntent.getBroadcast(getActivity().getApplicationContext(), ++MainActivity.alertId, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getActivity().getApplication().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        }
    }

    private void updateNotifyEndLabel() {
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        endNotification = (Button) getView().findViewById(R.id.endNotificationBtn);
        endNotification.setText(simpleDateFormat.format(notifyEndDateCalendar.getTime()));
        TextView notifyEndLabel = (TextView) getView().findViewById(R.id.endNotifyLabel);
        notifyEndLabel.setText("Notify End");
    }

    private void updateNotifyStartLabel() {
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        startNotification = (Button) getView().findViewById(R.id.startNotificationBtn);
        startNotification.setText(simpleDateFormat.format(notifyStartDateCalendar.getTime()));
        TextView notifyStartLabel = (TextView) getView().findViewById(R.id.startNotifyLabel);
        notifyStartLabel.setText("Notify Start");
    }
//
//    private String formatDate(String dateFromDB) {
//        if (!dateFromDB.equals("Notify Start") && !dateFromDB.equals("Start") &&
//                !dateFromDB.equals("End") && !dateFromDB.equals("Notify End")) {
//            String dateFormat = "MM/dd/YY";
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
//            String formattedDate = simpleDateFormat.format(dateFromDB);
//            System.out.println("formattedDate: " + formattedDate);
//            return formattedDate;
//        } else {
//            String formattedDate = "Date";
//            return formattedDate;
//        }
    }




