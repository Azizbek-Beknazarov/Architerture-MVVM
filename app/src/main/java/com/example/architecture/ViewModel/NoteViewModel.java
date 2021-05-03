package com.example.architecture.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.architecture.Model.Note;
import com.example.architecture.Repository.NoteRepo;

import java.util.List;


public class NoteViewModel extends AndroidViewModel {
    private NoteRepo mRepo;
    private LiveData<List<Note>> mData;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mRepo = new NoteRepo(application);
        mData = mRepo.getAllNotes();
    }

    public void insert(Note note) {
        mRepo.insert(note);
    }

    public void update(Note note) {
        mRepo.update(note);
    }

    public void delete(Note note) {
        mRepo.delete(note);
    }

    public void deleteAll() {
        mRepo.deleteAll();
    }
    public LiveData<List<Note>> getAllData(){
        return mData;
    }
}
