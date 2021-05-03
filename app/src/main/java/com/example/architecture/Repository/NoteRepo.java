package com.example.architecture.Repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.example.architecture.Model.Note;
import com.example.architecture.Model.NoteDao;
import com.example.architecture.Model.NoteDatabase;

import java.util.List;

public class NoteRepo {
    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes;

    public NoteRepo(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        mNoteDao = database.noteDao();
        mAllNotes = mNoteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(mNoteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(mNoteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(mNoteDao).execute(note);
    }

    public void deleteAll() {
        new DeleteAllNoteAsyncTask(mNoteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        public DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
