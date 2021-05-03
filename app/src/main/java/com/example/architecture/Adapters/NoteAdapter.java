package com.example.architecture.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.architecture.Model.Note;
import com.example.architecture.R;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> mList = new ArrayList<>();
    private OnItemclickListener mListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note currentNote = mList.get(position);
        holder.mTitle.setText(currentNote.getTitle());
        holder.mDescribe.setText(currentNote.getDescription());
        holder.mPreority.setText(String.valueOf(currentNote.getPriority()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setNote(List<Note> note) {
        this.mList = note;
        notifyDataSetChanged();
    }

    public Note getNoteAt(int position) {
        return mList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mDescribe, mPreority, mTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDescribe = itemView.findViewById(R.id.description);
            mTitle = itemView.findViewById(R.id.title);
            mPreority = itemView.findViewById(R.id.priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (mListener != null && position != RecyclerView.NO_POSITION)
                        mListener.onItemClick(mList.get(position));
                }
            });
        }
    }

    public interface OnItemclickListener {
       void onItemClick(Note note);
    }

    public void setonitemclickListener(OnItemclickListener listener) {
        this.mListener = listener;
    }
}
