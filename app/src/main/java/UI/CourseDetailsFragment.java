package UI;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
import Entities.Instructor;
import Entities.Note;
import Entities.Term;


public class CourseDetailsFragment extends Fragment {

    int termId;
    int courseId;
    String courseTitle;
    String courseStart;
    String courseEnd;
    String courseInstructor;
    String courseProgress;
    int insructorId;
    String notifyStart;
    String notifyEnd;

    EditText editCourseTitle;
    Button setCourseStartBtn;
    Button setCourseEndBtn;
    Spinner instructorSpinner;
    ImageButton addInstructorBtn;
    Spinner termSpinner;
    ImageButton addTermBtn;
    Button addAssessmentbtn;
    Button addNoteBtn;
    ImageButton saveBtn;
    RadioGroup progressRadioGroup;
    RadioButton inProgressRadioBtn;
    RadioButton plannedRadioBtn;
    RadioButton droppedRadioBtn;
    RadioButton completedRadioBtn;
    Button startNotificationBtn;
    Button endNotificationBtn;

    RepositoryForStudentOrganizer.Repository repo;
    ArrayList<Term> termArrayList;
    ArrayList<Instructor> instructorArrayList;
    private ArrayList<Note> assessmentArrayList;
    private ArrayList<Assessment> assessmentsArrayList;

    final Calendar startDateCalendar = Calendar.getInstance();
    final Calendar endDateCalendar = Calendar.getInstance();
    final Calendar notifyStartDateCalendar = Calendar.getInstance();
    final Calendar notifyEndDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDateDialog;
    DatePickerDialog.OnDateSetListener startDateDialog;
    DatePickerDialog.OnDateSetListener notifyStartDateDialog;
    DatePickerDialog.OnDateSetListener notifyEndDateDialog;
    private ImageButton deleteBtn;


    public CourseDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            termId = bundle.getInt("associatedTerm", -1);
            courseId = bundle.getInt("courseId", -1);
            courseTitle = bundle.getString("courseTitle");
            courseStart = bundle.getString("courseStart");
            courseEnd = bundle.getString("courseEnd");
            courseInstructor = bundle.getString("courseInstructor");
            insructorId = bundle.getInt("insructorId");
            courseProgress = bundle.getString("courseProgress");
            notifyStart = bundle.getString("notifyStart", "Start");
            notifyEnd = bundle.getString("notifyEnd", "End");

            repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());
            ArrayList<Term> termArrayList = (ArrayList<Term>) repo.getmAllTerms(); //Get terms from repo, add them to the list
            ArrayList<Instructor> instructorArrayList = (ArrayList<Instructor>) repo.getmAllInstructors(); //Get instructors from repo, add them to the list
        }
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null){
            termId = extras.getInt("associatedTerm", -1);
            courseId = extras.getInt("courseId", -1);
            courseTitle = extras.getString("courseTitle");
            courseStart = extras.getString("courseStart");
            courseEnd = extras.getString("courseEnd");
            courseInstructor = extras.getString("courseInstructor");
            insructorId = extras.getInt("insructorId");
            courseProgress = extras.getString("courseProgress");
            notifyStart = extras.getString("notifyStart", "Start");
            notifyEnd = extras.getString("notifyEnd", "End");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_details, container, false);
        RepositoryForStudentOrganizer.Repository repo = new RepositoryForStudentOrganizer.Repository(requireActivity().getApplication());
        //Set the terms spinner
        ArrayList<Term> termArrayList = (ArrayList<Term>) repo.getmAllTerms(); //Get terms from repo, add them to the list
        Spinner termsSelectionSpinner = (Spinner) view.findViewById(R.id.associatedTermSpinner);
        ArrayAdapter<Term> termArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, termArrayList);
        termsSelectionSpinner.setAdapter(termArrayAdapter);
        int termPosition = getItemPosition(termId, termArrayList);
        System.out.println("Term Position: " + termPosition);

        if (termPosition != -1) {
            termsSelectionSpinner.setSelection(termPosition);
        } else {
            termsSelectionSpinner.setSelection(0);
        }

        //Set the instructor spinner
        ArrayList<Instructor> instructorArrayList = (ArrayList<Instructor>) repo.getmAllInstructors(); //Get instructors from repo, add them to the list
        Spinner instructorSelectionSpinner = (Spinner) view.findViewById(R.id.chooseInstructorSpinner);
        ArrayAdapter<Instructor> instructorArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, instructorArrayList);
        instructorSelectionSpinner.setAdapter(instructorArrayAdapter);
        int instructorPosition = getItemPosition(insructorId, instructorArrayList);
        instructorSelectionSpinner.setSelection(instructorPosition);
        return view;
    }


    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());

        //Get entities from repo, add them to the corresponding list
        ArrayList<Term> termArrayList = (ArrayList<Term>) repo.getmAllTerms();
        ArrayList<Instructor> instructorArrayList = (ArrayList<Instructor>) repo.getmAllInstructors();
        ArrayList<Note> noteArrayList = (ArrayList<Note>) repo.getAllNotes();
        ArrayList<Assessment> assessmentArrayList = (ArrayList<Assessment>) repo.getmAllAssessments();

        EditText editCourseTitle = (EditText) view.findViewById(R.id.courseNameTxtInput);
        Button setCourseStartBtn = (Button) view.findViewById(R.id.courseStartDateBtn);
        Button setCourseEndBtn = (Button) view.findViewById(R.id.courseEndDateBtn);
        Spinner instructorSpinner = (Spinner) view.findViewById(R.id.chooseInstructorSpinner);
        Spinner termSpinner = (Spinner) view.findViewById(R.id.associatedTermSpinner);
        RadioGroup progressRadioGroup = (RadioGroup) view.findViewById(R.id.courseStatusRadioGroup);
        RadioButton inProgressRadioBtn = (RadioButton) view.findViewById(R.id.inProgressRadioBtn);
        RadioButton plannedRadioBtn = (RadioButton) view.findViewById(R.id.plannedRadio);
        RadioButton droppedRadioBtn = (RadioButton) view.findViewById(R.id.droppedRadioBtn);
        RadioButton completedRadioBtn = (RadioButton) view.findViewById(R.id.completedRadioBtn);
        Button startNotificationBtn = (Button) view.findViewById(R.id.notifyStartBtn);
        Button endNotificationBtn = (Button) view.findViewById(R.id.notifyEndBtn);

        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.deleteCourseBtn);
        ImageButton addInstructorBtn = (ImageButton) view.findViewById(R.id.addInstructorBtn);
        ImageButton addTermBtn = (ImageButton) view.findViewById(R.id.addAssociatedTermBtn);
        Button addAssessmentBtn = (Button) view.findViewById(R.id.addAssessmentBtn);
        Button addNoteBtn = (Button) view.findViewById(R.id.addNoteBtn);
        ImageButton saveBtn = (ImageButton) view.findViewById(R.id.saveCourseBtn);




        //Set course details to the details of the selected course
        Bundle bundle = getArguments();
        if (bundle != null) {
            int instructorId = insructorId;
            int associatedTerm = termId;
            int selectedCourseId = courseId;
            editCourseTitle.setText(courseTitle);
            setCourseStartBtn.setText(courseStart);
            setCourseEndBtn.setText(courseEnd);
            String formattedNotifyStart = formatDate(notifyStart);
            String formattedNotifyEnd = formatDate(notifyEnd);
            startNotificationBtn.setText(formattedNotifyStart);
            endNotificationBtn.setText(formattedNotifyEnd);
            courseProgress = bundle.getString("courseStatus", "Unchecked");
            //Set the progress radio
            setCourseProgressBtn(courseProgress);
            //Set the spinners
            setInstructorSpinner(instructorId);
            setTermSpinner(associatedTerm);


        }
        // Get the extras (if there are any)
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            int associatedTerm = extras.getInt("associatedTerm");
            int selectedCourseId = extras.getInt("courseId");
            int instructorId = extras.getInt("instructorId");

            editCourseTitle.setText(extras.getString("courseTitle"));
            setCourseStartBtn.setText(extras.getString("courseStart"));
            setCourseEndBtn.setText(extras.getString("courseEnd"));
            String formattedNotifyStart = formatDate(extras.getString("notifyStart"));
            String formattedNotifyEnd = formatDate(extras.getString("notifyEnd"));
            startNotificationBtn.setText(formattedNotifyStart);
            endNotificationBtn.setText(formattedNotifyEnd);
            //Set the spinners
            setInstructorSpinner(instructorId);
            setTermSpinner(associatedTerm);
            //Set the term progress
            courseProgress = extras.getString("courseStatus", "Unchecked");
            setCourseProgressBtn(courseProgress);

        } else if((bundle == null) && (extras == null)) { //Bundle and extras are null
            int associatedTerm = -1;
            int selectedCourseId = -1;
            int instructorId = -1;
            editCourseTitle.setText("Course Title");
            setCourseStartBtn.setText("Start");
            setCourseEndBtn.setText("End");
            startNotificationBtn.setText("Start");
            endNotificationBtn.setText("End");
            //Set the spinners
            setTermSpinner(associatedTerm);
            setInstructorSpinner(instructorId);
            //Set the term progress
            courseProgress = "Unchecked";
            setCourseProgressBtn(courseProgress);
        }


        //Set up the calendar for the course Start and End buttons
        setCourseStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateFormat = "MM/dd/YY";
                String dateString = setCourseStartBtn.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
                try {
                    startDateCalendar.setTime(simpleDateFormat.parse(dateString));
                } catch (ParseException startDateParseException) {
                    startDateParseException.printStackTrace();
                    startDateParseException.getMessage();
                    startDateParseException.getCause();
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

        setCourseEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateFormat = "MM/dd/YY";
                String dateString = setCourseEndBtn.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
                try {
                    endDateCalendar.setTime(simpleDateFormat.parse(dateString));
                } catch (ParseException endDateParseException) {
                    endDateParseException.printStackTrace();
                    endDateParseException.getMessage();
                    endDateParseException.getCause();
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


        addAssessmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("associatedCourseId", bundle.getInt("courseId", -1));
                    bundle2.putString("assessmentTitleTxtView", "Please enter assessment title");
                    bundle2.putString("assessmentStartTxtView", "Start");
                    bundle2.putString("assessmentEndTxtView", "End");
                    bundle2.putString("assessmentTypeTxtView", "Type");
                    bundle2.putString("assessmentNotifyStart", "Start");
                    bundle2.putString("assessmentNotifyEnd", "End");
                    bundle2.putInt("assessmentId", -1);

                    saveCourse();

                    Fragment assessmentDetails = new AssessmentDetailsFragment();
                    assessmentDetails.setArguments(bundle2);
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, assessmentDetails);
                    fragmentTransaction.addToBackStack("addAssessmentView");
                    fragmentTransaction.commit();
                } else {
                    Bundle bundle2 = new Bundle();
                    int courseId = (repo.getmAllCourses().get(repo.getmAllCourses().size() - 1).getCourseId()) + 1;
                    bundle2.putInt("associatedCourseId", courseId);
                    bundle2.putString("assessmentTitleTxtView", "Please enter assessment title");
                    bundle2.putString("assessmentStartTxtView", "Start");
                    bundle2.putString("assessmentEndTxtView", "End");
                    bundle2.putString("assessmentTypeTxtView", "Type");
                    bundle2.putString("assessmentNotifyStart", "Start");
                    bundle2.putString("assessmentNotifyEnd", "End");
                    bundle2.putInt("assessmentId", -1);

                    saveCourse();

                    Fragment assessmentDetails = new AssessmentDetailsFragment();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, assessmentDetails);
                    fragmentTransaction.addToBackStack("addAssessmentView");
                    fragmentTransaction.commit();
                }
            }
        });


        addInstructorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bundle != null) {
                    Bundle bundle1 = getArguments();
                    bundle1.putInt("courseId", bundle.getInt("courseId", -1));
                    bundle1.putString("instructorNameValue", "Please Enter the Instructor Name");
                    bundle1.putString("instructorEmailValue", "email@email.com");
                    bundle1.putString("instructorPhoneValue", "555-555-5555");
                    bundle1.putInt("instructorIdValue", -1);

                    saveCourse();

                    Fragment instructorViewFragment = new InstructorDetailsFragment();
                    instructorViewFragment.setArguments(bundle1);
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, instructorViewFragment);
                    fragmentTransaction.addToBackStack("addInstructorView");
                    fragmentTransaction.commit();
                } else {
                    Bundle bundle1 = new Bundle();
                    int repoSize = repo.getmAllCourses().size();
                    if (repoSize > 0) {
                        courseId = (repo.getmAllCourses().get(repo.getmAllCourses().size() - 1).getCourseId()) + 1;
                    } else {
                        courseId = 0;
                    }

                    bundle1.putInt("courseId", courseId);
                    bundle1.putString("instructorNameValue", "Please Enter the Instructor Name");
                    bundle1.putString("instructorEmailValue", "email@email.com");
                    bundle1.putString("instructorPhoneValue", "555-555-5555");
                    bundle1.putInt("instructorIdValue", -1);

                    saveCourse();

                    Fragment instructorViewFragment = new InstructorDetailsFragment();
                    instructorViewFragment.setArguments(bundle1);
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, instructorViewFragment);
                    fragmentTransaction.addToBackStack("addInstructorView");
                    fragmentTransaction.commit();
                }
            }
        });


        //Button addNoteBtn = (Button) view.findViewById(R.id.addNoteBtn);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bundle != null) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("associatedCourse", bundle.getInt("courseId", -1));
                    bundle2.putString("noteDateValue", "Date");
                    bundle2.putString("noteTextValue", "Please Enter Note Text Here");
                    bundle2.putString("noteTitleValue", "Title");
                    bundle2.putInt("noteIdValue", -1);

                    saveCourse();

                    Fragment noteDetails = new NoteDetailsFragment();
                    noteDetails.setArguments(bundle2);
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, noteDetails);
                    fragmentTransaction.addToBackStack("noteDetailView");
                    fragmentTransaction.commit();
                } else {
                    Bundle bundle2 = new Bundle();

                    int courseId = (repo.getmAllCourses().get(repo.getmAllCourses().size() - 1).getCourseId()) + 1;
                    bundle2.putInt("associatedCourseId", courseId);
                    bundle2.putString("noteDateValue", "Date");
                    bundle2.putString("noteTextValue", "Please Enter Note Text Here");
                    bundle2.putString("noteTitleValue", "Title");
                    bundle2.putInt("noteIdValue", -1);

                    saveCourse();

                    Fragment noteDetails = new NoteDetailsFragment();
                    noteDetails.setArguments(bundle2);
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, noteDetails);
                    fragmentTransaction.addToBackStack("noteDetailView");
                    fragmentTransaction.commit();
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCourse();

                Fragment courseList = new AllCoursesListFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewCourses, courseList);
                fragmentTransaction.addToBackStack("CourseListFragmentView");
                fragmentTransaction.commit();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Course course;
                Bundle bundle = getArguments();

                if (bundle != null) {
                    courseId = bundle.getInt("courseId", -1);
                    if (courseId != -1) {

                        termId = bundle.getInt("associatedTerm", -1);
                        courseId = bundle.getInt("courseId");
                        courseTitle = bundle.getString("coursetitle");
                        courseStart = bundle.getString("courseStart");
                        courseEnd = bundle.getString("courseEnd");
                        insructorId = bundle.getInt("instructorId", -1);
                        courseProgress = bundle.getString("courseStatus");
                        String startNotification = getStartNotificationDate().toString();
                        String endNotification = getEndNotificationDate().toString();

                        course = new Course(courseId, courseTitle, courseStart,
                                courseEnd, courseProgress, courseInstructor,
                                insructorId, termId, startNotification, endNotification);

                        //Set an alert to confirm the choice to delete
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Permanently Deleting Course, Notes, and Assessments");
                        builder.setMessage("Are you sure you wish to permanently delete this Course? All associated notes and assessments will also be deleted.");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteAssociatedNotes(courseId, CourseDetailsFragment.this.assessmentArrayList);
                                        deleteAssociatedAssessments(courseId, assessmentsArrayList);
                                        repo.delete(course);
                                        CharSequence text = "Course successfully deleted";
                                        int duration = Toast.LENGTH_LONG;

                                        Toast toast = Toast.makeText(getContext(), text, duration);
                                        toast.show();

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

                    }
                } else { //Else if the assessmentId is the default -1, then no assessment exists to delete -
                    //alert the user.
                    Context context = getContext();
                    CharSequence text = "Course must be saved before deleting.";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                Fragment courseList = new AllCoursesListFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewCourses, courseList);
                fragmentTransaction.addToBackStack("CourseListFragmentView");
                fragmentTransaction.commit();
            }
        });


        startNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String courseTitle = editCourseTitle.getText().toString();
                String dateFromScreen = startNotificationBtn.getText().toString();
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

        endNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateFromScreen = endNotificationBtn.getText().toString();
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


    private void saveCourse() {
        Bundle bundle = getArguments();

        EditText editCourseTitle = (EditText) getView().findViewById(R.id.courseNameTxtInput);
        Button setCourseStartBtn = (Button) getView().findViewById(R.id.courseStartDateBtn);
        Button setCourseEndBtn = (Button) getView().findViewById(R.id.courseEndDateBtn);
        Spinner instructorSpinner = (Spinner) getView().findViewById(R.id.chooseInstructorSpinner);
        Spinner termSpinner = (Spinner) getView().findViewById(R.id.associatedTermSpinner);
        RadioGroup progressRadioGroup = (RadioGroup) getView().findViewById(R.id.courseStatusRadioGroup);
        RadioButton inProgressRadioBtn = (RadioButton) getView().findViewById(R.id.inProgressRadioBtn);
        RadioButton plannedRadioBtn = (RadioButton) getView().findViewById(R.id.plannedRadio);
        RadioButton droppedRadioBtn = (RadioButton) getView().findViewById(R.id.droppedRadioBtn);
        RadioButton completedRadioBtn = (RadioButton) getView().findViewById(R.id.completedRadioBtn);
        //Button
        String courseTitle = ((EditText) getView().findViewById(R.id.courseNameTxtInput)).getText().toString();
        String startDate = setCourseStartBtn.getText().toString();
        String endDate = setCourseEndBtn.getText().toString();
        String courseStatus = getCourseProgress();

        int instructorRepoSize = repo.getmAllInstructors().size();
        if (instructorRepoSize == 0) {
            insructorId = 0;
            courseInstructor = "None Assigned";
        } else {
            Instructor selectedInstructor = (Instructor) instructorSpinner.getSelectedItem();
            insructorId = selectedInstructor.getInstructorId();
            courseInstructor = selectedInstructor.getInstructorName();
        }

        Term selectedTerm = (Term) termSpinner.getSelectedItem();
        if (selectedTerm != null) {
            termId = selectedTerm.getTermId();
        } else {
            termId = -1;
        }

        if (bundle != null) {
            courseId = bundle.getInt("courseId");
        } else {
            courseId = -1;
        }


        //Save info to DB
        Course course;
        int courseRepoSize = repo.getmAllCourses().size();
        int newId;
        String startNotificationString = getStartNotificationDate().toString();
        String endNotificationString = getEndNotificationDate().toString();
        if (courseId == -1) {
            if (courseRepoSize == 0) {
                newId = 1;
                course = new Course(newId, courseTitle, startDate, endDate, courseStatus, courseInstructor, insructorId, termId, startNotificationString, endNotificationString);
                repo.insert(course);
            } else {
                repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());
                newId = repo.getmAllTerms().get(repo.getmAllTerms().size() - 1).getTermId() + 1;//Without this line, the program was throwing a null pointer exception for the reponewId = repo.getmAllTerms().get(repo.getmAllTerms().size() - 1).getTermId() + 1;
                System.out.println("Course Repo Size = " + courseRepoSize + ", " + "New Id No = " + newId);
                course = new Course(newId, courseTitle, startDate, endDate, courseStatus, courseInstructor, insructorId, termId, startNotificationString, endNotificationString);
                repo.insert(course);
            }
        } else {
            course = new Course(courseId, courseTitle, startDate, endDate, courseStatus, courseInstructor, insructorId, termId, startNotificationString, endNotificationString);
            repo.update(course);
        }

        //Get the date format of the notification start then pass it to the boradcastreceiver
        //to set the notification
        if(!startNotificationString.equals("Notify Start") && !startNotificationString.equals("Start")){
            Date dateNotifyStart = getStartNotificationDate();
            triggerAlertBroadcastReciever(dateNotifyStart);
        }
        //Same with the end notification date
        if(!endNotificationString.equals("Notify End") && !endNotificationString.equals("End")){
            Date dateNotifyEnd = getEndNotificationDate();
            triggerAlertBroadcastReciever(dateNotifyEnd);
        }
        else{
            String dateFormat = "MM/dd/YY";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
            String currentDate = simpleDateFormat.format(new Date());
        }
    }


    private void deleteAssociatedNotes(int courseId, ArrayList<Note> notesArrayList) {
        notesArrayList = new ArrayList<Note>(repo.getAllNotes());

        for (int i = 0; i < notesArrayList.size(); i++) {
            Note note = (Note) notesArrayList.get(i);
            int associatedCourseId = note.getAssociatedCourseId();
            if (associatedCourseId == courseId) {
                repo.delete(note);
            }
        }
    }


    private void deleteAssociatedAssessments(int courseId, ArrayList<Assessment> assessmentArrayList) {
        assessmentArrayList = new ArrayList<Assessment>(repo.getmAllAssessments());

        for (int i = 0; i < assessmentArrayList.size(); i++) {
            Assessment assessment = (Assessment) assessmentArrayList.get(i);
            int associatedCourseId = assessment.getAssociatedCourseId();
            if (associatedCourseId == courseId) {
                repo.delete(assessment);
            }
        }
    }

    public int getItemPosition(int id, ArrayList arrayList) {
        if (arrayList.isEmpty() != true) {
            if (arrayList.get(0) instanceof Term) {
                for (int i = 0; i < arrayList.size(); i++) {
                    Term term = (Term) arrayList.get(i);
                    if (term.getTermId() == id) {
                        int index = arrayList.indexOf(term);
                        return index;
                    }
                }
            }
            if (arrayList.get(0) instanceof Instructor) {
                for (int i = 0; i < arrayList.size(); i++) {
                    Instructor instructor = (Instructor) arrayList.get(i);
                    if (instructor.getInstructorId() == id) {
                        int index = arrayList.indexOf(instructor);
                        return index;
                    }

                }
            }
        }
        return 0;
    }


    //Update date buttons
    private void updateNotifyEndLabel() {
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        endNotificationBtn = (Button) getView().findViewById(R.id.notifyEndBtn);
        endNotificationBtn.setText(simpleDateFormat.format(notifyEndDateCalendar.getTime()));
    }

    private void updateStartDateLabel() {
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        setCourseStartBtn = (Button) getView().findViewById(R.id.courseStartDateBtn);
        setCourseStartBtn.setText(simpleDateFormat.format(startDateCalendar.getTime()));
    }

    private void updateEndDateLabel() {
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        setCourseEndBtn = (Button) getView().findViewById(R.id.courseEndDateBtn);
        setCourseEndBtn.setText(simpleDateFormat.format(endDateCalendar.getTime()));
    }

    private void updateNotifyStartLabel() {
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        startNotificationBtn = (Button) getView().findViewById(R.id.notifyStartBtn);
        startNotificationBtn.setText(simpleDateFormat.format(notifyStartDateCalendar.getTime()));
    }

    private String formatDate(String dateFromDB) {
        if (!dateFromDB.equals("Notify Start") && !dateFromDB.equals("Start") &&
                !dateFromDB.equals("End") && !dateFromDB.equals("Notify End")) {
            String dateFormat = "MM/dd/YY";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
            String formattedDate = simpleDateFormat.format(new Date());
            return formattedDate;
        } else {
            String formattedDate = "Date";
            return formattedDate;
        }
    }

    //Send information to and from radios
    private String setCourseProgressBtn(String courseProgress) {
        if (!courseProgress.equals(null)) {
            switch (courseProgress) {
                case "Planned":
                    plannedRadioBtn = getView().findViewById(R.id.plannedRadio);
                    plannedRadioBtn.setChecked(true);
                    return "Planned";

                case "In Progress":
                    inProgressRadioBtn = getView().findViewById(R.id.inProgressRadioBtn);
                    inProgressRadioBtn.setChecked(true);
                    return "In Progress";

                case "Completed":
                    completedRadioBtn = getView().findViewById(R.id.completedRadioBtn);
                    completedRadioBtn.setChecked(true);
                    return "Completed";

                case "Dropped":
                    droppedRadioBtn = getView().findViewById(R.id.droppedRadioBtn);
                    droppedRadioBtn.setChecked(true);
                    return "Dropped";

                default:
                    return "Unchecked";
            }
        }
        else{return "Unchecked";}
    }

    private String getCourseProgress() {
        RadioGroup radioBtnGroup = getView().findViewById(R.id.courseStatusRadioGroup);
        int selectedRadioBtnId = radioBtnGroup.getCheckedRadioButtonId();
        View selectedRadioBtn = radioBtnGroup.findViewById(selectedRadioBtnId);
        int selectedRadioBtnIndex = radioBtnGroup.indexOfChild(selectedRadioBtn);
        switch (selectedRadioBtnIndex) {
            case -1:
                return "Unchecked";
            case 0:
                return "Planned";

            case 1:
                return "In Progress";

            case 2:
                return "Completed";

            case 3:
                return "Dropped";

            default:
                return "Unchecked";
        }
    }


    //Get dates for the notification Broadcast reciever / and the trigger
    private Date getStartNotificationDate() {
        Button startNotificationBtn = getView().findViewById(R.id.notifyStartBtn);
        if(!startNotificationBtn.getText().toString().equals(null)) {
        String dateFromScreen = startNotificationBtn.getText().toString();
            if(dateFromScreen.equals("Start")){
                dateFromScreen = "01/01/3000";
            }
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
            return date;
        }
        return null;
    }

    private Date getEndNotificationDate() {
        Button endNotificationBtn = (Button) getView().findViewById(R.id.notifyEndBtn);
        if(endNotificationBtn != null){
            String dateFromScreen = endNotificationBtn.getText().toString();
            if(dateFromScreen.equals("End")){
                dateFromScreen = "01/01/3000";
            }
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
        return date;
        }
        return null;
    }

    private void triggerAlertBroadcastReciever(Date dateFromScreen) {

        //Set the trigger to the Broadcast receiver
        courseTitle = getView().findViewById(R.id.courseNameTxtInput).toString();
        Long alertId = Long.valueOf(++MainActivity.alertId);
        Long trigger = dateFromScreen.getTime();
        Intent intent = new Intent(getActivity().getApplicationContext(), AlertBroadcastReceiver.class);
        System.out.println("Course title: " + courseTitle.toString());
        System.out.println("Date for course Start: " + dateFromScreen);
        //PendingIntent intent = PendingIntent.getActivity(getActivity().getApplicationContext(), 0, new Intent(), 0);
        intent.putExtra("alertMessage ", "Course: " + courseTitle.toString() + " Starting On " + dateFromScreen);
        intent.putExtra("alertCreatedToast", "Alert Number: " + alertId + " Saved");
        PendingIntent sender = PendingIntent.getBroadcast(getActivity().getApplicationContext(), ++MainActivity.alertId, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getActivity().getApplication().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);

//            String startOrEnd = getStartOrEnd(isStartNotify,isEndNotify);
//            int notificationId = getNotificationId(courseId, startOrEnd);
//            
//        Notification newNotification = new Notification(notificationId, "Course", courseId, startOrEnd, dateFromScreen.toString(), alertId);

    }

    private void setTermSpinner(int associatedTerm){
        //Set the terms spinner
        ArrayList<Term> termArrayList = (ArrayList<Term>) repo.getmAllTerms(); //Get terms from repo, add them to the list
        Spinner termsSelectionSpinner = (Spinner) getView().findViewById(R.id.associatedTermSpinner);
        ArrayAdapter<Term> termArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, termArrayList);
        termsSelectionSpinner.setAdapter(termArrayAdapter);
        int termPosition = getItemPosition(associatedTerm, termArrayList);
        System.out.println("Term Position: " + termPosition);

        if (termPosition != -1) {
            termsSelectionSpinner.setSelection(termPosition);
        } else {
            termsSelectionSpinner.setSelection(0);
        }
    }

    private void setInstructorSpinner(int instructorId){
        //Set the instructor spinner
        ArrayList<Instructor> instructorArrayList = (ArrayList<Instructor>) repo.getmAllInstructors(); //Get instructors from repo, add them to the list
        Spinner instructorSelectionSpinner = (Spinner) getView().findViewById(R.id.chooseInstructorSpinner);
        ArrayAdapter<Instructor> instructorArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, instructorArrayList);
        instructorSelectionSpinner.setAdapter(instructorArrayAdapter);
        int instructorPosition = getItemPosition(instructorId, instructorArrayList);
        instructorSelectionSpinner.setSelection(instructorPosition);
    }



    }



