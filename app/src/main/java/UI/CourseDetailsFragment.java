package UI;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.app.DatePickerDialog;
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
import Entities.Course;
import Entities.Instructor;
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

    RepositoryForStudentOrganizer.Repository repo;
    ArrayList<Term>termArrayList;
    ArrayList<Instructor>instructorArrayList;

    final Calendar startDateCalendar = Calendar.getInstance();
    final Calendar endDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDateDialog;
    DatePickerDialog.OnDateSetListener startDateDialog;

    public CourseDetailsFragment() {
        // Required empty public constructor
    }


//    public static CourseDetailsFragment newInstance(String param1, String param2) {
//        CourseDetailsFragment fragment = new CourseDetailsFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }

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
            insructorId = bundle.getInt("instructorId");

            repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());
            ArrayList<Term> termArrayList = (ArrayList<Term>) repo.getmAllTerms(); //Get terms from repo, add them to the list
            ArrayList<Instructor> instructorArrayList = (ArrayList<Instructor>) repo.getmAllInstructors(); //Get instructors from repo, add them to the list
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_details, container, false);
        //RepositoryForStudentOrganizer.Repository repo = new RepositoryForStudentOrganizer.Repository(requireActivity().getApplication());

        //Set the terms spinner
        ArrayList<Term> termArrayList = (ArrayList<Term>) repo.getmAllTerms(); //Get terms from repo, add them to the list

        Spinner termsSelectionSpinner = (Spinner) view.findViewById(R.id.associatedTermSpinner);
        ArrayAdapter<Term> termArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, termArrayList);
        termsSelectionSpinner.setAdapter(termArrayAdapter);
        int termPosition = getItemPosition(termId, termArrayList);
        termsSelectionSpinner.setSelection(termPosition);

        //Set the instructor spinner
        ArrayList<Instructor> instructorArrayList = (ArrayList<Instructor>) repo.getmAllInstructors(); //Get instructors from repo, add them to the list
        Spinner instructorSelectionSpinner = (Spinner) view.findViewById(R.id.chooseInstructorSpinner);
        ArrayAdapter<Instructor> instructorArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, instructorArrayList);
        instructorSelectionSpinner.setAdapter(instructorArrayAdapter);
        int instructorPosition = getItemPosition(insructorId, instructorArrayList);
        termsSelectionSpinner.setSelection(instructorPosition);
        return view;
    }

    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState){
        repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());

        ArrayList<Term> termArrayList = (ArrayList<Term>) repo.getmAllTerms(); //Get terms from repo, add them to the list
        ArrayList<Instructor> instructorArrayList = (ArrayList<Instructor>) repo.getmAllInstructors(); //Get instructors from repo, add them to the list

        EditText editCourseTitle = (EditText) view.findViewById(R.id.courseNameTxtInput);
        Button setCourseStartBtn= (Button) view.findViewById(R.id.courseStartDateBtn);
        Button setCourseEndBtn= (Button) view.findViewById(R.id.courseEndDateBtn);
        Spinner instructorSpinner = (Spinner) view.findViewById(R.id.chooseInstructorSpinner);
        ImageButton addInstructorBtn = (ImageButton) view.findViewById(R.id.addInstructorBtn);
        Spinner termSpinner = (Spinner) view.findViewById(R.id.associatedTermSpinner);
        ImageButton addTermBtn = (ImageButton) view.findViewById(R.id.addAssociatedTermBtn);
        Button addAssessmentBtn = (Button) view.findViewById(R.id.addAssessmentBtn);
        Button addNoteBtn = (Button) view.findViewById(R.id.addNoteBtn);
        ImageButton saveBtn = (ImageButton) view.findViewById(R.id.saveCourseBtn);
        RadioGroup progressRadioGroup = (RadioGroup) view.findViewById(R.id.courseStatusRadioGroup);
        RadioButton inProgressRadioBtn = (RadioButton) view.findViewById(R.id.inProgressRadioBtn);
        RadioButton plannedRadioBtn = (RadioButton) view.findViewById(R.id.plannedRadio);
        RadioButton droppedRadioBtn = (RadioButton) view.findViewById(R.id.droppedRadioBtn);
        RadioButton completedRadioBtn = (RadioButton) view.findViewById(R.id.completedRadioBtn);




        //Set course details to the details of the selected course
        Bundle bundle = getArguments();
        if (bundle != null){
            int associatedTerm = termId;
            int selectedCourseId = courseId;
            int instructorId = insructorId;
            editCourseTitle.setText(courseTitle);
            setCourseStartBtn.setText(courseStart);
            setCourseEndBtn.setText(courseEnd);
            //Set the spinners
            int instructorPosition = getItemPosition(bundle.getInt("insructorId"), instructorArrayList);
            instructorSpinner.setSelection(instructorPosition);
            int termPosition = getItemPosition(bundle.getInt("termId"), termArrayList);
            termSpinner.setSelection(termPosition);

        }
        else{
            int associatedTerm = -1;
            int selectedCourseId = -1;
            int instructorId = -1;
            editCourseTitle.setText("Course Title");
            setCourseStartBtn.setText("Start");
            setCourseEndBtn.setText("End");
            //Set the spinners
            instructorSpinner.setSelection(1);
            termSpinner.setSelection(1);
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

        setCourseEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dateFormat = "MM/dd/YY";
                String dateString = setCourseEndBtn.getText().toString();
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


        addAssessmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("associatedCourse", bundle.getInt("courseId", -1));

                    Fragment assessmentDetails = new AssessmentDetailsFragment();
                    assessmentDetails.setArguments(bundle2);
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, assessmentDetails);
                    fragmentTransaction.addToBackStack("addAssessmentView");
                    fragmentTransaction.commit();
                }
                else{
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
                if(bundle != null) {
                    Bundle bundle1 = getArguments();
                    bundle1.putInt("courseId", bundle.getInt("courseId"));
                    Fragment instructorViewFragment = new InstructorFragment();
                    instructorViewFragment.setArguments(bundle1);
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, instructorViewFragment);
                    fragmentTransaction.addToBackStack("addInstructorView");
                    fragmentTransaction.commit();
                }
                else{
                    Fragment instructorViewFragment = new InstructorFragment();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, instructorViewFragment);
                    fragmentTransaction.addToBackStack("addInstructorView");
                    fragmentTransaction.commit();}
            }
        });



        //Button addNoteBtn = (Button) view.findViewById(R.id.addNoteBtn);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bundle != null) {
                    Bundle bundle1 = getArguments();
                    bundle1.putInt("courseId", bundle.getInt("courseId"));
                    Fragment notesFragment = new NoteDetailsFragment();
                    notesFragment.setArguments(bundle1);
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, notesFragment);
                    fragmentTransaction.addToBackStack("addNoteView");
                    fragmentTransaction.commit();
                }
                else{
                    Fragment notesFragment = new NoteDetailsFragment();
                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, notesFragment);
                    fragmentTransaction.addToBackStack("addNoteView");
                    fragmentTransaction.commit();
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int termID = bundle.getInt("associatedTerm");
                int courseId = bundle.getInt("courseId");
                String courseTitle = ((EditText) getView().findViewById(R.id.courseNameTxtInput)).getText().toString();
                String startDate = setCourseStartBtn.getText().toString();
                String endDate = setCourseEndBtn.getText().toString();
                String courseStatus = "In Progress";


                Instructor selectedInstructor = (Instructor) instructorSpinner.getSelectedItem();
                insructorId = selectedInstructor.getInstructorId();
                courseInstructor = selectedInstructor.getInstructorName();

                Term selectedTerm = (Term) termSpinner.getSelectedItem();
                termId = selectedTerm.getTermId();

                //Save info to DB
                Course course;
                if (courseId == -1){
                    repo=new RepositoryForStudentOrganizer.Repository(getActivity().getApplication()); //Without this line, the program was throwing a null pointer exception for the repo
                    int newId = repo.getmAllCourses().get(repo.getmAllCourses().size() - 1).getCourseId() + 1;//get the ID of the last term in the list
                    course = new Course(newId, courseTitle, startDate, endDate, courseStatus, courseInstructor,insructorId,termId );
                    repo.insert(course);
                }
                else{
                    course = new Course(courseId, courseTitle, startDate, endDate, courseStatus, courseInstructor, insructorId,termId);
                    repo.update(course);
                }
                Fragment courseList = new AllCoursesListFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity)getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewCourses, courseList);
                fragmentTransaction.addToBackStack("CourseListFragmentView");
                fragmentTransaction.commit();
            }
        });
    }

    public int getItemPosition(int id, ArrayList arrayList)
    {
        for (int i=0; i<arrayList.size(); i++)
            if (arrayList.get(i).equals(id))
                return i;
        return 0;
    }

    private void updateStartDateLabel(){
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        setCourseStartBtn = (Button) getView().findViewById(R.id.courseStartDateBtn);
        setCourseStartBtn.setText(simpleDateFormat.format(startDateCalendar.getTime()));
    }

    private void updateEndDateLabel(){
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        setCourseEndBtn = (Button) getView().findViewById(R.id.courseEndDateBtn);
        setCourseEndBtn.setText(simpleDateFormat.format(endDateCalendar.getTime()));
    }


}