package com.sururiana.apimoviecatalogue.data;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.sururiana.apimoviecatalogue.data.FavoriteContract.FavoriteMovie.TABLE_NAME;
import static com.sururiana.apimoviecatalogue.data.FavoriteContract.FavoriteTvShow.TABLE_NAME_TV;

public class FavoriteContract {
    static final String AUTHORITY ="com.sururiana.apimoviecatalogue";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME_TV)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static final class FavoriteMovie implements BaseColumns {

        public static final String TABLE_NAME = "favorite_movie";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_USERRATING = "userrating";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE = "realise";

    }

    public static final class FavoriteTvShow implements BaseColumns {

        public static final String TABLE_NAME_TV= "favorite_tv";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE = "release";
        public static final String COLUMN_USERRATING = "userating";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_OVERVIEW = "overview";
    }
}
