package UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.c196_abm2_charity_yohn.R;

import java.util.List;

import Entities.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {


    private List<Note> mNotes;
    private final Context context;
    private final LayoutInflater mInflator;
    NoteAdapter(Context context){
        mInflator= LayoutInflater.from(context);
        this.context=context;
    }


    class NoteViewHolder extends RecyclerView.ViewHolder{
        private final TextView DBNoteDate;
        private final TextView DBNoteText;
        private final TextView DBNoteTitle;


        private NoteViewHolder(View itemView){
            super(itemView);
            DBNoteDate = itemView.findViewById(R.id.noteDateCardView);
            DBNoteText = itemView.findViewById(R.id.noteTextCardView);
            DBNoteTitle = itemView.findViewById(R.id.noteTitleCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Note selectedNote= mNotes.get(position);

                    Fragment noteDetails = new NoteDetailsFragment();
                    FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();

                    Bundle bundle = new Bundle();

                    bundle.putString("noteDateValue", selectedNote.getNoteDate());
                    bundle.putString("noteTextValue", selectedNote.getNoteText());
                    bundle.putString("noteTitleValue", selectedNote.getNoteTitle());
                    bundle.putInt("associatedCourse",selectedNote.getAssociatedCourseId());
                    bundle.putInt("noteIdValue", selectedNote.getNoteId());

                    noteDetails.setArguments(bundle);
                    fragmentTransaction.replace(R.id.notesActivityFragmentViewer, noteDetails);
                    fragmentTransaction.addToBackStack("NoteDetailFragment");
                    fragmentTransaction.commit();
                }
            });
        }
    }


    //Inflate the list item
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflator.inflate(R.layout.note_list_item_layout,parent,false);
        return new NoteViewHolder(itemView);
    }

    //Put things on the text view
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        if(mNotes !=null){
            Note selectedNote = mNotes.get(position);
            String name = selectedNote.getNoteTitle();
            String date = selectedNote.getNoteDate();
            String text = selectedNote.getNoteText();

            holder.DBNoteTitle.setText(name);
            holder.DBNoteDate.setText(date);
            holder.DBNoteText.setText(text);

        }
        else{
            holder.DBNoteTitle.setText("No Note Found");
            holder.DBNoteDate.setText("N/A");
            holder.DBNoteText.setText("N/A");
        }
    }
    public void setmNotes(List<Note> notes){
        mNotes =notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mNotes !=null){
            return mNotes.size();
        }
        else return 0;
    }



}
