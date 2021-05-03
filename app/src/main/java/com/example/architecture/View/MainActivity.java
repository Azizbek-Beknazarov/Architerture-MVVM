package com.example.architecture.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.architecture.Adapters.NoteAdapter;
import com.example.architecture.Model.Note;
import com.example.architecture.R;
import com.example.architecture.ViewModel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private NoteViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton actionButton = findViewById(R.id.fbtn);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        mModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        mModel.getAllData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNote(notes);
           }
        });

// swipe for delete one item
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        adapter.setonitemclickListener(new NoteAdapter.OnItemclickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this,
                        AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.ID, note.getID());
                intent.putExtra(AddEditNoteActivity.TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.PRIORITY, note.getPriority());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.TITLE);
            String desc = data.getStringExtra(AddEditNoteActivity.DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.PRIORITY, 1);

            Note note = new Note(title, desc, priority);
            mModel.insert(note);
            Toast.makeText(this, "Note saved...", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id =data.getIntExtra(AddEditNoteActivity.ID,-1);

            if (id == -1) {
                Toast.makeText(this, "Note can not updated", Toast.LENGTH_SHORT).show();
                return;
            }
                Log.i("update","work");
            String title = data.getStringExtra(AddEditNoteActivity.TITLE);
            String desc = data.getStringExtra(AddEditNoteActivity.DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.PRIORITY, 1);

            Note note=new Note(title,desc,priority);
            note.setID(id);

            mModel.update(note);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(this, "Note not saved???", Toast.LENGTH_SHORT).show();
    }

// Item option menu for delete all
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_all, menu);
        return true;
    }

// Item select for delete all;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                mModel.deleteAll();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}