package com.sururiana.favorite.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;
import android.util.Log;

import com.sururiana.favorite.model.Tv;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static android.support.constraint.Constraints.TAG;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteTvShow.COLUMN_MOVIEID;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteTvShow.COLUMN_OVERVIEW;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteTvShow.COLUMN_POSTER_PATH;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteTvShow.COLUMN_RELEASE;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteTvShow.COLUMN_TITLE;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteTvShow.COLUMN_USERRATING;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteTvShow.TABLE_NAME_TV;

public class TvHelper {
    private static final String DATABASE_TABLE = TABLE_NAME_TV;
    private static FavoriteDBHelper dataBaseHelper;
    private static TvHelper INSTANCE;
    private static SQLiteDatabase database;

    TvHelper(Context context) {
        dataBaseHelper = new FavoriteDBHelper(context);
    }

    public static TvHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvHelper(context);
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

    public ArrayList<Tv> getAllTv() {
        ArrayList<Tv> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Tv tv;
        if (cursor.getCount() > 0) {
            do {
                tv = new Tv();
                tv.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIEID))));
                tv.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                tv.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_USERRATING))));
                tv.setPosterPath(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH)));
                tv.setOverview(cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW)));
                tv.setFirstAirDate(cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE)));

                arrayList.add(tv);

                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(Tv tv) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_MOVIEID, tv.getId());
        args.put(COLUMN_TITLE, tv.getName());
        args.put(COLUMN_USERRATING, tv.getVoteAverage());
        args.put(COLUMN_POSTER_PATH, tv.getPosterPath());
        args.put(COLUMN_OVERVIEW, tv.getOverview());
        args.put(COLUMN_RELEASE, tv.getFirstAirDate());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public void deleteTv(int id) {
        database = dataBaseHelper.getWritableDatabase();
        database.delete(TABLE_NAME_TV, FavoriteContract.FavoriteTvShow.COLUMN_MOVIEID + "=" + id, null);
    }
    public boolean checkMovie(String id) {
        database = dataBaseHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME_TV + " WHERE " + FavoriteContract.FavoriteTvShow.COLUMN_MOVIEID + " =?";
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
