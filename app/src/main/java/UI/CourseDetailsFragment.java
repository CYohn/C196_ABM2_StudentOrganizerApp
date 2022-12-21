package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import Database.RepositoryForStudentOrganizer;
import Entities.Instructor;
import Entities.Term;


public class CourseDetailsFragment extends Fragment {

int termId;
int courseId;
String courseTitle;
String courseStart;
String courseEnd;
String courseInstructor;

EditText editCourseTitle;
Button setCourseStartBtn;
Button setSetCourseEndBtn;
Spinner instructorSpinner;
ImageButton addInstructorBtn;
Spinner termSpinner;
ImageButton addTermBtn;
Button addAssessmentbtn;
Button addNoteBtn;
ImageButton saveBtn;

RepositoryForStudentOrganizer.Repository repo;


    public CourseDetailsFragment() {
        // Required empty public constructor
    }


    public static CourseDetailsFragment newInstance(String param1, String param2) {
        CourseDetailsFragment fragment = new CourseDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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

            repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());
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

        //Set the instructor spinner
        ArrayList<Instructor> instructorArrayList = (ArrayList<Instructor>) repo.getmAllInstructors(); //Get instructors from repo, add them to the list
        Spinner instructorSelectionSpinner = (Spinner) view.findViewById(R.id.chooseInstructorSpinner);
        ArrayAdapter<Instructor> instructorArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, instructorArrayList);
        instructorSelectionSpinner.setAdapter(instructorArrayAdapter);

    return view;
    }

    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState){

        EditText editCourseTitle = (EditText) view.findViewById(R.id.courseNameTxtInput);
        Button setCourseStartBtn= (Button) view.findViewById(R.id.courseStartDateBtn);
        Button setSetCourseEndBtn= (Button) view.findViewById(R.id.courseEndDateBtn);
        Spinner instructorSpinner = (Spinner) view.findViewById(R.id.chooseInstructorSpinner);
        ImageButton addInstructorBtn = (ImageButton) view.findViewById(R.id.addInstructorBtn);
        Spinner termSpinner = (Spinner) view.findViewById(R.id.associatedTermSpinner);
        ImageButton addTermBtn = (ImageButton) view.findViewById(R.id.addAssociatedTermBtn);
        Button addAssessmentBtn = (Button) view.findViewById(R.id.addAssessmentBtn);
        Button addNoteBtn = (Button) view.findViewById(R.id.addNoteBtn);
        ImageButton saveBtn = (ImageButton) view.findViewById(R.id.saveCourseBtn);

        //Set course details to the details of the selected course
        Bundle bundle = getArguments();
        if (bundle != null){
            int associatetTerm = termId;
            int selectedCourseId = courseId;
            editCourseTitle.setText(courseTitle);
            setCourseStartBtn.setText(courseStart);
            setSetCourseEndBtn.setText(courseEnd);

            //instructorSpinner.setSelection(courseInstructor);
            //termSpinner.setSelection(termId);
            //Spinners require array position*******************

        }




        //TODO: SET THE COURSE INFO BUNDLE TO PASS
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
            }
        });


        //TODO: SET THE COURSE INFO BUNDLE TO PASS
        //ImageButton addInstructorBtn = (ImageButton) view.findViewById(R.id.addInstructorBtn);
        addInstructorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewCourses, new InstructorFragment());
                fragmentTransaction.addToBackStack("addInstructorView");
                fragmentTransaction.commit();
            }
        });


        //TODO: SET THE COURSE INFO BUNDLE TO PASS
        //Button addNoteBtn = (Button) view.findViewById(R.id.addNoteBtn);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerViewCourses, new AllNotesFragment());
                fragmentTransaction.addToBackStack("addNoteView");
                fragmentTransaction.commit();
            }
        });

    }


}