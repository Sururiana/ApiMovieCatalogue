package com.sururiana.apimoviecatalogue.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "favorite.db";

    private static final int DATABASE_VERSION = 1;
    FavoriteDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String SQL_CREATE_TABLE_MOVIE =
            String.format("CREATE TABLE %s" +
                            "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s INTEGER," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL)",
                    FavoriteContract.FavoriteMovie.TABLE_NAME,
                    FavoriteContract.FavoriteMovie._ID,
                    FavoriteContract.FavoriteMovie.COLUMN_MOVIEID,
                    FavoriteContract.FavoriteMovie.COLUMN_TITLE,
                    FavoriteContract.FavoriteMovie.COLUMN_RELEASE,
                    FavoriteContract.FavoriteMovie.COLUMN_POSTER_PATH,
                    FavoriteContract.FavoriteMovie.COLUMN_USERRATING,
                    FavoriteContract.FavoriteMovie.COLUMN_OVERVIEW
            );
    private static final String SQL_CREATE_TABLE_TV =
            String.format("CREATE TABLE %s" +
                            "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s INTEGER," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL)",
                    FavoriteContract.FavoriteTvShow.TABLE_NAME_TV,
                    FavoriteContract.FavoriteTvShow._ID,
                    FavoriteContract.FavoriteTvShow.COLUMN_MOVIEID,
                    FavoriteContract.FavoriteTvShow.COLUMN_TITLE,
                    FavoriteContract.FavoriteTvShow.COLUMN_RELEASE,
                    FavoriteContract.FavoriteTvShow.COLUMN_POSTER_PATH,
                    FavoriteContract.FavoriteTvShow.COLUMN_USERRATING,
                    FavoriteContract.FavoriteTvShow.COLUMN_OVERVIEW
            );
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteMovie.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteTvShow.TABLE_NAME_TV);
        onCreate(db);
    }
}
