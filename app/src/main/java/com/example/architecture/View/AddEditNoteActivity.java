package com.example.architecture.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.architecture.R;

public class AddEditNoteActivity extends AppCompatActivity {
    private EditText mTitle, mDescription;
    private NumberPicker mPicker;

    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PRIORITY = "PRIORITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mTitle = findViewById(R.id.titleET);
        mDescription = findViewById(R.id.descET);
        mPicker = findViewById(R.id.numberP);

        mPicker.setMinValue(1);
        mPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24);


        Intent intent = getIntent();
        if (intent.hasExtra(ID)) {
            setTitle("Edit Note");

            mTitle.setText(intent.getStringExtra(TITLE));
            mDescription.setText(intent.getStringExtra(DESCRIPTION));
            mPicker.setValue(intent.getIntExtra(PRIORITY, 1));

        } else
            setTitle("Add Note");
    }

    private void saveNote() {
        String title = mTitle.getText().toString();
        String desc = mDescription.getText().toString();
        int priority = mPicker.getValue();

        if (title.trim().isEmpty() || desc.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a title or a description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(TITLE, title);
        intent.putExtra(DESCRIPTION, desc);
        intent.putExtra(PRIORITY, priority);

        int id = getIntent().getIntExtra(ID,  -1);
        if (id != -1)
            Log.i("@@@","-1");
            intent.putExtra(ID, id);

        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_item:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}