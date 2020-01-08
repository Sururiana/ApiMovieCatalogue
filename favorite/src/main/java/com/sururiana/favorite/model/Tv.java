package com.sururiana.favorite.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import static android.provider.UserDictionary.Words._ID;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteTvShow.COLUMN_POSTER_PATH;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteTvShow.COLUMN_RELEASE;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteTvShow.COLUMN_TITLE;
import static com.sururiana.favorite.data.FavoriteContract.FavoriteTvShow.COLUMN_USERRATING;
import static com.sururiana.favorite.data.FavoriteContract.getColumnInt;
import static com.sururiana.favorite.data.FavoriteContract.getColumnString;

public class Tv implements Parcelable {
    private String originalName;
    private List<Integer> genreIds;
    private String name;
    private Double popularity;
    private List<String> originCountry;
    private Integer voteCount;
    private String firstAirDate;
    private String backdropPath;
    private String originalLanguage;
    private Integer id;
    private Double voteAverage;
    private String overview;
    private String posterPath;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate){
        this.firstAirDate = firstAirDate;
    }


    public String getBackdropPath() {
        return "https://image.tmdb.org/t/p/w500" + backdropPath;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage (Double voteAverage){
        this.voteAverage = voteAverage;
    }


    public String getOverview() {
        return overview;
    }
    public void  setOverview(String overview){
        this.overview = overview;
    }


    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }
    public void setPosterPath(String posterPath){
        this.posterPath = posterPath;
    }
    public String getPosterPathFav() {
        return  posterPath;
    }

    public Tv(){

    }
    public Tv(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.name = getColumnString(cursor, COLUMN_TITLE);
        this.firstAirDate = getColumnString(cursor, COLUMN_RELEASE);
        this.voteAverage = Double.parseDouble(getColumnString(cursor, COLUMN_USERRATING));
        this.posterPath = getColumnString(cursor, COLUMN_POSTER_PATH);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.originalName);
        dest.writeList(this.genreIds);
        dest.writeString(this.name);
        dest.writeValue(this.popularity);
        dest.writeStringList(this.originCountry);
        dest.writeValue(this.voteCount);
        dest.writeString(this.firstAirDate);
        dest.writeString(this.backdropPath);
        dest.writeString(this.originalLanguage);
        dest.writeValue(this.id);
        dest.writeValue(this.voteAverage);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
    }


    private Tv(Parcel in) {
        this.originalName = in.readString();
        this.genreIds = new ArrayList<>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.name = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.originCountry = in.createStringArrayList();
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.firstAirDate = in.readString();
        this.backdropPath = in.readString();
        this.originalLanguage = in.readString();
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.overview = in.readString();
        this.posterPath = in.readString();
    }

    public static final Creator<Tv> CREATOR = new Creator<Tv>() {
        @Override
        public Tv createFromParcel(Parcel source) {
            return new Tv(source);
        }

        @Override
        public Tv[] newArray(int size) {
            return new Tv[size];
        }
    };
}
