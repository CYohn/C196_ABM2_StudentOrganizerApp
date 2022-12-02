package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

public class AddTermFragment extends Fragment {

    public AddTermFragment() {
        // Required empty public constructor
    }


    public static AddTermFragment newInstance(String param1, String param2) {
        AddTermFragment fragment = new AddTermFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_term, container, false);

        ImageButton addCoursesFromTermAddBtn = (ImageButton) view.findViewById(R.id.addCoursesFromTermAddBtn);
        addCoursesFromTermAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.termsFragmentHolder, new AddNewCourseFragment());
                fragmentTransaction.addToBackStack("addInstructorView");
                fragmentTransaction.commit();
            }
        });

        ImageButton closeAddTermsBtn = (ImageButton) view.findViewById(R.id.closeAddTermsBtn);
        closeAddTermsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.termsFragmentHolder, new AllTermsListFragment());
                fragmentTransaction.addToBackStack("addNoteView");
                fragmentTransaction.commit();
            }
        });

        return view;
    }
    
    //TODO: Make this button save the new Term, add it to the list of terms, and return to the All Terms fragment
    public void pressedSaveNewTermBtn(View view) {
    }
}