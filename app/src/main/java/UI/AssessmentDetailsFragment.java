package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.zybooks.c196_abm2_charity_yohn.R;

import org.jetbrains.annotations.Nullable;

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
EditText titleText;
Button startDateBtn;
Button endDateBtn;
Spinner associatedCourseSpinner;
Switch assessmentTypeSwitch;
Button closeBtn;
Button alertsBtn;
Button saveBtn;



    public AssessmentDetailsFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AssessmentDetailsFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AssessmentDetailsFragment newInstance(String param1, String param2) {
//        AssessmentDetailsFragment fragment = new AssessmentDetailsFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            assessmentTitle = bundle.getString("assessmentTitleTxtView");
            assessmentStartDate = bundle.getString("assessmentStartTxtView");
            assessmentEndDate = bundle.getString("assessmentEndTxtView");
            assessmentType = bundle.getString("assessmentTypeTxtView");


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assessment_details, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        Bundle bundle = getArguments();
        titleText = (EditText) getView().findViewById(R.id.assessmentNameTxtinput);
        startDateBtn = (Button) getView().findViewById(R.id.assessmentStartDateBtn);
        endDateBtn = (Button) getView().findViewById(R.id.assessmentEndDateBtn);
        associatedCourseSpinner = (Spinner) getView().findViewById(R.id.assessmentAssociatedCourseSpinner);
        //assessmentTypeSwitch = (Switch) getView().findViewById(assessmentTypeSwitch);

        if(bundle != null) {

            titleText.setText(assessmentTitle, TextView.BufferType.EDITABLE);
            startDateBtn.setText("Start: " + assessmentStartDate);
            endDateBtn.setText("End: " + assessmentEndDate);
            Spinner associatedCourseSpinner;
            Chip assessmentTypeChip;
        }

        else{
            titleText.setText("Please Enter Assessment Title");
        }
    }
}