package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

import Database.RepositoryForStudentOrganizer;
import Entities.Instructor;


public class InstructorDetailsFragment extends Fragment {


    String instructorName;
    String insrtuctorEmail;
    String instructorPhone;
    int instructorId;

    EditText editName;
    EditText editEmail;
    EditText editPhone;
    ImageButton saveInstructorBtn;
    ImageButton closeInstructorBtn;

    RepositoryForStudentOrganizer.Repository repo;

    public InstructorDetailsFragment() {
        // Required empty public constructor
    }



    public static InstructorDetailsFragment newInstance() {
        InstructorDetailsFragment fragment = new InstructorDetailsFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            instructorName = bundle.getString("instructorNameValue");
            insrtuctorEmail = bundle.getString("instructorEmailvalue");
            instructorPhone = bundle.getString("instructorPhoneValue");
            instructorId = bundle.getInt("instructorIdValue", -1);

            repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_instructor_details, container, false);
    }

    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState){
        editName = (EditText) getView().findViewById(R.id.instructorNameTxtInput);
        editEmail = (EditText) getView().findViewById(R.id.instructorEmailTxtInput);
        editPhone = (EditText) getView().findViewById(R.id.instructorPhoneTxtinput);
        saveInstructorBtn = (ImageButton) getView().findViewById(R.id.saveInstructorBtn);
        closeInstructorBtn = (ImageButton) getView().findViewById(R.id.closeAddInstructorScreen);

        Bundle bundle = getArguments();
        if(bundle != null){
            editName.setText(bundle.getString("instructorNameValue"), TextView.BufferType.EDITABLE);
            editEmail.setText(bundle.getString("instructorEmailValue"), TextView.BufferType.EDITABLE);
            editPhone.setText(bundle.getString("instructorPhoneValue"), TextView.BufferType.EDITABLE);
        }
        else{
            editName.setText("Instructor Name", TextView.BufferType.EDITABLE);
            editEmail.setText("Email", TextView.BufferType.EDITABLE);
            editPhone.setText("Phone", TextView.BufferType.EDITABLE);
        }

        saveInstructorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Instructor instructor;
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String phone = editPhone.getText().toString();
                int instructorId = bundle.getInt("instructorIdvalue", -1);
                if(instructorId == -1){
                    int newId = repo.getmAllInstructors()
                            .get(repo.getmAllInstructors().size() - 1).getInstructorId() + 1;
                    instructor = new Instructor(newId, name, email, phone );
                    repo.insert(instructor);
                } else {
                    instructor = new Instructor(bundle.getInt("instructorIdValue"),
                            name, email, phone);
                    repo.update(instructor);
                }
                Fragment instructorList = new AllInstructorsFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.instructorActivityFragmentViewer, instructorList);
                fragmentTransaction.addToBackStack("NotesListFragment");
                fragmentTransaction.commit();
            }
        });

        closeInstructorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment instructorList = new AllInstructorsFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.instructorActivityFragmentViewer, instructorList);
                fragmentTransaction.addToBackStack("NotesListFragment");
                fragmentTransaction.commit();
            }
        });
    }
}