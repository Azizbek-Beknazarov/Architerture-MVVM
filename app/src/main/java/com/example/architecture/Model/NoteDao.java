package com.example.architecture.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note")
    void deleteAllNotes();

    @Query("SELECT * FROM note ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();
}
