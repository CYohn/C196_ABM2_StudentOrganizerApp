package UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        //private final TextView DBNoteText;
        private final TextView DBNoteTitle;


        private NoteViewHolder(View itemView){
            super(itemView);
            DBNoteDate = itemView.findViewById(R.id.noteDateTxt);
            //DBNoteText = itemView.findViewById(R.id.noteTextInput);
            DBNoteTitle = itemView.findViewById(R.id.noteTitleInput);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Note selectedNote= mNotes.get(position);
                    Intent intent = new Intent(context,NotesDetailsActivity.class);

                    intent.putExtra("noteDateValue", selectedNote.getNoteDate());
                    intent.putExtra("noteTextValue", selectedNote.getNoteText());
                    intent.putExtra("notTitleValue", selectedNote.getNoteTitle());

                    context.startActivity(intent);
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
            //holder.DBNoteText.setText(text);

        }
        else{
            holder.DBNoteTitle.setText("No Note Found");
            holder.DBNoteDate.setText("N/A");
            //holder.DBNoteText.setText("N/A");

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
