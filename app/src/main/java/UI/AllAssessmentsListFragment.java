package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zybooks.c196_abm2_charity_yohn.R;

import java.util.List;

import Database.RepositoryForStudentOrganizer;
import Entities.Assessment;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AllAssessmentsListFragment#newInstance} factory method to
// * create an instance of this fragment.
 //*/
public class AllAssessmentsListFragment extends Fragment {

    FloatingActionButton floatingActionButton;


    public AllAssessmentsListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_assessments_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = getView().findViewById(R.id.assessmentsRecyclerView);
        RepositoryForStudentOrganizer.Repository repo = new RepositoryForStudentOrganizer.Repository(requireActivity().getApplication());
        List<Assessment> assessments = repo.getAllAssessments();
        final AssessmentAdapter assessmentAdapter= new AssessmentAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(assessmentAdapter);
        assessmentAdapter.setmAssessments(assessments);

        floatingActionButton = getView().findViewById(R.id.addAssessmentFloatingAction);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment assessmentDetails = new AssessmentDetailsFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.assessmentFragmentContainerView, assessmentDetails);
                fragmentTransaction.addToBackStack("AssessmentDetailsView");
                fragmentTransaction.commit();
            }
        });
    }

    public void addAssessmentPressed(View view) {
    }


}