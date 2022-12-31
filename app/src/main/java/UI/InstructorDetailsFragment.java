package UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

import Database.RepositoryForStudentOrganizer;
import Entities.Assessment;
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
    ImageButton deleteBtn;

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
        deleteBtn = (ImageButton) getView().findViewById(R.id.deleteInstructorBtn);

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
                repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());
                Bundle bundle = getArguments();
                Instructor instructor;
                editName = (EditText) getView().findViewById(R.id.instructorNameTxtInput);
                editEmail = (EditText) getView().findViewById(R.id.instructorEmailTxtInput);
                editPhone = (EditText) getView().findViewById(R.id.instructorPhoneTxtinput);
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                String phone = editPhone.getText().toString();
                int instructorId;
                if(bundle != null) {
                    instructorId = bundle.getInt("instructorIdvalue", -1);
                }else{instructorId = -1;}

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
                fragmentTransaction.addToBackStack("InstructorListFragment");
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

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Instructor instructor;
                Bundle bundle = getArguments();

                if (bundle != null) {
                    instructorId = bundle.getInt("instructorIdValue", -1);
                    if (instructorId != -1) {
                        instructorId = bundle.getInt("instructorIdValue");
                        instructorName = bundle.getString("instructorNameValue");
                        instructorPhone = bundle.getString("instructorPhoneValue");
                        insrtuctorEmail = bundle.getString("instructorEmailValue");

                        instructor = new Instructor(instructorId, instructorName,
                                insrtuctorEmail, instructorPhone);

                        //Set an alert to confirm the coice to delete
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Permanently Deleting Instructor");
                        builder.setMessage("Are you sure you wish to permanently delete this instructor?");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        repo.delete(instructor);
                                        //Advise the user the instructor was deleted
                                        Context context = getContext();
                                        CharSequence text = "Instructor successfully deleted.";
                                        int duration = Toast.LENGTH_LONG;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                        //Send the user back to the All Instructors List
                                        Fragment instructorsList = new AllInstructorsFragment();
                                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.instructorActivityFragmentViewer, instructorsList);
                                        fragmentTransaction.addToBackStack("InstructorListFragment");
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
                        CharSequence text = "instructor must be saved before deleting.";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }else{

                    Context context = getContext();
                    CharSequence text = "Instructor must be saved before deleting.";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }


}