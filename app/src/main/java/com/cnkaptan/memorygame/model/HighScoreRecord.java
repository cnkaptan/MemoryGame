package com.cnkaptan.memorygame.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by cnkaptan on 10/09/2017.
 */

public class HighScoreRecord implements Parcelable,Comparable<HighScoreRecord> {
    private int points;
    private String name;

    public HighScoreRecord(int points, String name) {
        this.points = points;
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HighScoreRecord that = (HighScoreRecord) o;

        if (points != that.points) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = points;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.points);
        dest.writeString(this.name);
    }

    protected HighScoreRecord(Parcel in) {
        this.points = in.readInt();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<HighScoreRecord> CREATOR = new Parcelable.Creator<HighScoreRecord>() {
        @Override
        public HighScoreRecord createFromParcel(Parcel source) {
            return new HighScoreRecord(source);
        }

        @Override
        public HighScoreRecord[] newArray(int size) {
            return new HighScoreRecord[size];
        }
    };

    @Override
    public int compareTo(@NonNull HighScoreRecord highScoreRecord) {
        if (this.getPoints() > highScoreRecord.getPoints()){
            return -1;
        }else if (this.getPoints() < highScoreRecord.getPoints()){
            return 1;
        }else {
            return this.getName().compareTo(highScoreRecord.getName());
        }
    }

    @Override
    public String toString() {
        return "HighScoreRecord{" +
                "points=" + points +
                ", name='" + name + '\'' +
                '}';
    }
}
