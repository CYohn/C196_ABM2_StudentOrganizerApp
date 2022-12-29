package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.zybooks.c196_abm2_charity_yohn.R;


public class InstructorDetailsFragment extends Fragment {


    String instructorName;
    String insrtuctorEmail;
    String instructorPhone;

    EditText editName;
    EditText editEmail;
    EditText editPhone;
    ImageButton saveInstructorBtn;
    ImageButton closeInstructorBtn;

    public InstructorDetailsFragment() {
        // Required empty public constructor
    }



    public static InstructorDetailsFragment newInstance(String param1, String param2) {
        InstructorDetailsFragment fragment = new InstructorDetailsFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor_details, container, false);
    }
}