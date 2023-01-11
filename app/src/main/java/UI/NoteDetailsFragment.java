package UI;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

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
import Entities.Assessment;
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
    ImageButton deleteBtn;
    ImageButton shareNoteBtn;
    private int noteId;


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
        deleteBtn = (ImageButton) getView().findViewById(R.id.deleteNoteBtn);
        shareNoteBtn = (ImageButton) getView().findViewById(R.id.shareNoteBtn);

        repo = new RepositoryForStudentOrganizer.Repository(getActivity().getApplication());
        courseArrayList = (ArrayList<Course>) repo.getmAllCourses();

        Bundle bundle = getArguments();
        if (bundle != null) {
            associatedCourse = bundle.getInt("associatedCourse", -1);
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
            noteId = bundle.getInt("noteIdValue", -1);
        } else {
            String dateToday = Calendar.getInstance().getTime().toString();
            editNoteTitleField.setText("Note Title", TextView.BufferType.EDITABLE);
            editNoteDateBtn.setText("Date");
            editNoteTextField.setText("Note Text", TextView.BufferType.EDITABLE);
            noteId = -1;

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

        shareNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent=new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,editNoteTextField.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE,editNoteTitleField.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent=Intent.createChooser(sendIntent,null);
                startActivity(shareIntent);
            }
        });

        saveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                associatedCourseSpinner = view.findViewById(R.id.associatedCourse);
                Course selectedCourse = (Course) associatedCourseSpinner.getSelectedItem();
                int courseId;
                if(selectedCourse != null){
                courseId = selectedCourse.getCourseId();
                }else{courseId = -1;}
                String noteTitle = ((EditText) getView().findViewById(R.id.noteTitleTxt)).getText().toString();
                String noteDate = editNoteDateBtn.getText().toString();
                String noteText = editNoteTextField.getText().toString();
                int noteId;
                if (bundle != null){
                    noteId = bundle.getInt("noteIdValue", -1);
                } else{noteId = -1;}

                //Save info to DB
                Note note;
                int noteRepoSize = repo.getAllNotes().size();
                if (noteId == -1) {
                    int newId;

                    if(noteRepoSize == 0){
                        newId = 1;
                        note = new Note(newId, noteDate, noteText, noteTitle, courseId);
                        repo.insert(note);

                        //Inform the user the note was saved
                        Context context = getContext();
                        CharSequence text = "Note saved successfully";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                    } else{
                        newId = repo.getAllNotes().get(noteRepoSize - 1).getNoteId() + 1;

                        System.out.println("newId in Note Repo = " + newId);
                        //get the ID of the last note in the list
//                        Note lastNote = repo.getAllNotes().get(noteRepoSize);
//                        int lastNoteId = lastNote.getNoteId();
//                        int newId2 = lastNoteId + 1;

                        note = new Note(newId, noteDate, noteText, noteTitle, courseId);
                        repo.insert(note);

                        //Inform the user the note was saved
                        Context context = getContext();
                        CharSequence text = "Note saved successfully";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                } else {
                    if (noteId != -1) {
                        note = new Note(bundle.getInt("noteIdValue", -1), noteDate, noteText, noteTitle, courseId);
                        repo.update(note);
                        //Inform the user the note updated successfully
                        Context context = getContext();
                        CharSequence text = "Note updated successfully";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }

                String activityName = getActivity().getClass().getCanonicalName();
                System.out.println("Activity name =  " + activityName);

                if (activityName.equals("UI.CourseActivity")) {
//                    ArrayList<Course> courseList = (ArrayList<Course>)repo.getmAllCourses();
//                    courseId = bundle.getInt("associatedCourse");
//                    int termId;
//                    String courseTitle;
//                    String courseStart;
//                    String courseEnd;
//                    String courseInstructor;
//                    String courseProgress;
//                    int insructorId;
//                    for (int i = 0; i < courseList.size(); i++){
//                        Course course = (Course) courseList.get(i);
//                        if (course.getCourseId() == courseId){
//                            termId = course.getAssociatedTermId();
//                            courseTitle = course.getCourseTitle();
//                            courseStart = course.getCourseStartDate();
//                            courseEnd = course.getCourseEndDate();
//                            courseInstructor = course.getCourseInstructor();
//                            courseProgress = course.getCourseStatus();
//                            insructorId = course.getInstructorId();
//                        }
//                    }
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("associatedTerm", selectedCourse.getAssociatedTermId());
                    bundle1.putInt("courseId", selectedCourse.getCourseId());
                    bundle1.putString("courseTitle", selectedCourse.getCourseTitle());
                    bundle1.putString("courseStart", selectedCourse.getCourseStartDate());
                    bundle1.putString("courseEnd", (selectedCourse.getCourseEndDate()));
                    bundle1.putString("courseInstructor", selectedCourse.getCourseInstructor());
                    bundle1.putInt("insructorId", selectedCourse.getInstructorId());
                    bundle1.putString("courseStatus", selectedCourse.getCourseStatus());

                    Fragment courseDetails = new CourseDetailsFragment();
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    courseDetails.setArguments(bundle1);
                    fragmentTransaction.replace(R.id.fragmentContainerViewCourses, courseDetails);
                    fragmentTransaction.addToBackStack("CourseDetailsView");
                    fragmentTransaction.commit();

                }else{ // The hosting activity is UI.NoteActivity
                    Fragment noteList = new AllNotesFragment();
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.notesActivityFragmentViewer, noteList);
                    fragmentTransaction.addToBackStack("NotesListFragment");
                    fragmentTransaction.commit();
                }
            }
        });

        //Handling the delete button
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note;
                Bundle bundle = getArguments();

                if (bundle != null) {
                    noteId = bundle.getInt("noteIdValue", -1);
                    if (noteId != -1) {
                        noteId = bundle.getInt("noteIdValue");
                        noteDate = bundle.getString("noteDateValue");
                        noteTitle = bundle.getString("noteTitleValue");
                        noteText = bundle.getString("assessmentEndTxtView");
                        associatedCourse = bundle.getInt("associatedCourse");

                        note = new Note(noteId, noteDate, noteText,
                                noteTitle, associatedCourse);

                        //Set an alert to confirm the coice to delete
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setTitle("Permanently Deleting Note");
                        builder.setMessage("Are you sure you wish to permanently delete this note?");
                        builder.setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Delete note from DB
                                        repo.delete(note);
                                        //Advise user the note was deleted successfully
                                        Context context = getContext();
                                        CharSequence text = "Note was successfully deleted.";
                                        int duration = Toast.LENGTH_LONG;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                        //Send the user back to the all notes list
                                        Fragment noteList = new AllNotesFragment();
                                        FragmentTransaction fragmentTransaction = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.notesActivityFragmentViewer, noteList);
                                        fragmentTransaction.addToBackStack("NotesListFragment");
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
                        CharSequence text = "Note must be saved before deleting.";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }else{

                    Context context = getContext();
                    CharSequence text = "Note must be saved before deleting.";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });




    }






        public int getItemPosition ( int id, ArrayList arrayList){
        if(arrayList.size() != 0) {
            if (arrayList.get(0) instanceof Course) {
                for (int i = 0; i < arrayList.size(); i++) {
                    Course course = (Course) arrayList.get(i);
                    if (course.getCourseId() == id) {
                        int index = arrayList.indexOf(course);
                        return index;
                    }
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



