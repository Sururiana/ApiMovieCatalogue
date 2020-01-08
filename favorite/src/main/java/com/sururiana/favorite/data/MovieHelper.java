package com.sururiana.favorite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;
import android.util.Log;

import com.sururiana.favorite.model.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static android.support.constraint.Constraints.TAG;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteMovie.COLUMN_MOVIEID;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteMovie.COLUMN_OVERVIEW;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteMovie.COLUMN_POSTER_PATH;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteMovie.COLUMN_RELEASE;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteMovie.COLUMN_TITLE;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteMovie.COLUMN_USERRATING;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteMovie.TABLE_NAME;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static FavoriteDBHelper dataBaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    MovieHelper(Context context) {
        dataBaseHelper = new FavoriteDBHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getAllMovie() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIEID))));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_USERRATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE)));

                arrayList.add(movie);

                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_MOVIEID, movie.getId());
        args.put(COLUMN_TITLE, movie.getTitle());
        args.put(COLUMN_USERRATING, movie.getVoteAverage());
        args.put(COLUMN_POSTER_PATH, movie.getPosterPath());
        args.put(COLUMN_OVERVIEW, movie.getOverview());
        args.put(COLUMN_RELEASE, movie.getReleaseDate());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public void deleteMovie(int id) {
        database = dataBaseHelper.getWritableDatabase();
        database.delete(FavoriteContract.FavoriteMovie.TABLE_NAME, FavoriteContract.FavoriteMovie.COLUMN_MOVIEID + "=" + id, null);
    }

    public boolean checkMovie(String id) {
        database = dataBaseHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + FavoriteContract.FavoriteMovie.TABLE_NAME + " WHERE " + FavoriteContract.FavoriteMovie.COLUMN_MOVIEID + " =?";
        Cursor cursor = database.rawQuery(selectString, new String[]{id});
        boolean checkMovie = false;
        if (cursor.moveToFirst()) {
            checkMovie = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkMovie;
    }

    Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , MediaStore.Audio.Playlists.Members._ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , MediaStore.Audio.Playlists.Members._ID + " ASC");
    }

    long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, MediaStore.Audio.Playlists.Members._ID + " =?", new String[]{id});
    }

    int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, MediaStore.Audio.Playlists.Members._ID + " = ?", new String[]{id});
    }
}
