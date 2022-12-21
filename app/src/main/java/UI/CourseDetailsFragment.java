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
import java.util.Collections;

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
int insructorId;

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
ArrayList<Term>termArrayList;
ArrayList<Instructor>instructorArrayList;



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
        RepositoryForStudentOrganizer.Repository repo = new RepositoryForStudentOrganizer.Repository(requireActivity().getApplication());

        //Set the terms spinner
        ArrayList<Term> termArrayList = (ArrayList<Term>) repo.getmAllTerms(); //Get terms from repo, add them to the list
        ArrayList<Instructor> instructorArrayList = (ArrayList<Instructor>) repo.getmAllInstructors(); //Get instructors from repo, add them to the list
        Spinner termsSelectionSpinner = (Spinner) view.findViewById(R.id.associatedTermSpinner);
        ArrayAdapter<Term> termArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, termArrayList);
        termsSelectionSpinner.setAdapter(termArrayAdapter);
        int termPosition = getItemPosition(termId, termArrayList);
        termsSelectionSpinner.setSelection(termPosition);

        //Set the instructor spinner

        Spinner instructorSelectionSpinner = (Spinner) view.findViewById(R.id.chooseInstructorSpinner);
        ArrayAdapter<Instructor> instructorArrayAdapter = new ArrayAdapter<>(this.getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, instructorArrayList);
        instructorSelectionSpinner.setAdapter(instructorArrayAdapter);

    return view;
    }

    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState){
        ArrayList<Term> termArrayList = (ArrayList<Term>) repo.getmAllTerms(); //Get terms from repo, add them to the list
        ArrayList<Instructor> instructorArrayList = (ArrayList<Instructor>) repo.getmAllInstructors(); //Get instructors from repo, add them to the list

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
            int associatedTerm = termId;
            int selectedCourseId = courseId;
            int instructorId = insructorId;
            editCourseTitle.setText(courseTitle);
            setCourseStartBtn.setText(courseStart);
            setSetCourseEndBtn.setText(courseEnd);
//            instructorSpinner.setSelection();
//            termSpinner.setSelection(termId-1);
            //Spinners require array position*******************

            int instructorPosition = getItemPosition(bundle.getInt("insructorId"), instructorArrayList);
            instructorSpinner.setSelection(instructorPosition);

            int termPosition = getItemPosition(bundle.getInt("termId"), termArrayList);
            termSpinner.setSelection(termPosition);



        }





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

    }

    public int getItemPosition(int id, ArrayList arrayList)
    {
        for (int i=0; i<arrayList.size(); i++)
            if (arrayList.get(i).equals(id))
                return i;
        return 0;
    }


}