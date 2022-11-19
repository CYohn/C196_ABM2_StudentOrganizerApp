package UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTermFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTermFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddTermFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTermFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTermFragment newInstance(String param1, String param2) {
        AddTermFragment fragment = new AddTermFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                fragmentTransaction.replace(R.id.termsFragmentContainerView, new AddNewCourseFragment());
                fragmentTransaction.addToBackStack("addInstructorView");
                fragmentTransaction.commit();
            }
        });

        ImageButton closeAddTermsBtn = (ImageButton) view.findViewById(R.id.closeAddTermsBtn);
        closeAddTermsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.termsFragmentContainerView, new AllTermsListFragment());
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