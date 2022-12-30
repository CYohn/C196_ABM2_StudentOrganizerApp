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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.zybooks.c196_abm2_charity_yohn.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Database.RepositoryForStudentOrganizer;
import Entities.Course;
import Entities.Note;


public class NoteDetailsFragment extends Fragment {

    String noteTitle;
    String noteDate;
    String noteText;
    int associatedCourse;

    RepositoryForStudentOrganizer.Repository repo;
    ArrayList<Course> courseArrayList;
    final Calendar noteDateCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener noteDateDialog;

    EditText editNoteTitleField;
    Button editNoteDateBtn;
    EditText editNoteTextField;
    Spinner associatedCourseSpinner;
    ImageButton saveNoteBtn;


    public NoteDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            noteTitle = bundle.getString("noteTitleValue");
            noteDate = bundle.getString("noteDateValue");
            noteText = bundle.getString("noteTextValue");
            associatedCourse = bundle.getInt("associatedCourse", -1);

            repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());
            courseArrayList = (ArrayList<Course>) repo.getmAllCourses(); //Get course from repo, add them to the list
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_details, container, false);


    }

    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {

        editNoteTitleField = (EditText) getView().findViewById(R.id.noteTitleTxt);
        editNoteDateBtn = (Button) getView().findViewById(R.id.noteDateBtn);
        editNoteTextField = (EditText) getView().findViewById(R.id.noteTxtInput);
        saveNoteBtn = (ImageButton) getView().findViewById(R.id.saveNoteBtn);

        repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());
        courseArrayList = (ArrayList<Course>) repo.getmAllCourses();

        Bundle bundle = getArguments();
        if (bundle != null) {
            Spinner associatedCourseSpinner = (Spinner) getView().findViewById(R.id.associatedCourse);
            ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<>(this.getActivity(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, courseArrayList);
            associatedCourseSpinner.setAdapter(courseArrayAdapter);
            int coursePosition = getItemPosition(associatedCourse, courseArrayList);
            associatedCourseSpinner.setSelection(coursePosition);

            //Set the note details to the details of the selected note
            editNoteTitleField.setText(bundle.getString("noteTitleValue"), TextView.BufferType.EDITABLE);
            editNoteDateBtn.setText(bundle.getString("noteDateValue"));
            editNoteTextField.setText(bundle.getString("noteTextValue"), TextView.BufferType.EDITABLE);
        } else {
            String dateToday = Calendar.getInstance().getTime().toString();
            editNoteTitleField.setText("Note Title", TextView.BufferType.EDITABLE);
            editNoteDateBtn.setText("Date");
            editNoteTextField.setText("Note Text", TextView.BufferType.EDITABLE);

            Spinner associatedCourseSpinner = (Spinner) getView().findViewById(R.id.associatedCourse);
            ArrayAdapter<Course> courseArrayAdapter = new ArrayAdapter<>(this.getActivity(),
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, courseArrayList);
            associatedCourseSpinner.setAdapter(courseArrayAdapter);
            associatedCourseSpinner.setSelection(0);
        }

        editNoteDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateFormat = "MM/dd/YY";
                String dateString = editNoteDateBtn.getText().toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
                try {
                    noteDateCalendar.setTime(simpleDateFormat.parse(dateString));
                } catch (ParseException dateParseException) {
                    dateParseException.printStackTrace();
                    dateParseException.getMessage();
                    dateParseException.getCause();
                }
                new DatePickerDialog(getContext(), noteDateDialog, noteDateCalendar.get(YEAR),
                        noteDateCalendar.get(MONTH), noteDateCalendar.get(DAY_OF_MONTH)).show();
            }
        });

        noteDateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                noteDateCalendar.set(YEAR, year);
                noteDateCalendar.set(MONTH, monthOfYear);
                noteDateCalendar.set(DAY_OF_MONTH, dayOfMonth);
                updateNoteDateLabel();
            }
        };

        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                associatedCourseSpinner = view.findViewById(R.id.associatedCourse);
                Course selectedCourse = (Course) associatedCourseSpinner.getSelectedItem();
                int courseId = selectedCourse.getCourseId();
                String noteTitle = ((EditText) getView().findViewById(R.id.noteTitleTxt)).getText().toString();
                String noteDate = editNoteDateBtn.getText().toString();
                String noteText = editNoteTextField.getText().toString();

                //Save info to DB
                Note note;
                if (courseId == -1) {
                    repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication()); //Without this line, the program was throwing a null pointer exception for the repo
                    int newId = repo.getmAllCourses().get(repo.getAllNotes().size() - 1).getCourseId() + 1;//get the ID of the last term in the list
                    note = new Note(newId, noteDate, noteText, noteTitle, courseId);
                    repo.insert(note);
                } else {
                    note = new Note(bundle.getInt("noteIdValue", -1), noteDate, noteText, noteTitle, courseId);
                    repo.update(note);
                }
                Fragment noteList = new AllNotesFragment();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.notesActivityFragmentViewer, noteList);
                fragmentTransaction.addToBackStack("NotesListFragment");
                fragmentTransaction.commit();
            }
        });
    }



        public int getItemPosition ( int id, ArrayList arrayList){
            if (arrayList.get(0) instanceof Course) {
                for (int i = 0; i < arrayList.size(); i++) {
                    Course course = (Course) arrayList.get(i);
                    if (course.getCourseId() == id) {
                        int index = arrayList.indexOf(course);
                        return index;
                    }
                }
            }
            return -1;
        };

    private void updateNoteDateLabel() {
        String dateFormat = "MM/dd/YY";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String currentDate = simpleDateFormat.format(new Date());
        editNoteDateBtn = (Button) getView().findViewById(R.id.noteDateBtn);
        editNoteDateBtn.setText(simpleDateFormat.format(noteDateCalendar.getTime()));
    }

    }



