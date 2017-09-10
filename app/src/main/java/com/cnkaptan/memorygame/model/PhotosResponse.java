package com.cnkaptan.memorygame.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cnkaptan on 06/09/17.
 */

public class PhotosResponse implements Parcelable {

    private String stat;
    private PhotosObject photos;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public PhotosObject getPhotos() {
        return photos;
    }

    public void setPhotos(PhotosObject photos) {
        this.photos = photos;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stat);
        dest.writeParcelable(this.photos, flags);
    }

    public PhotosResponse() {
    }

    protected PhotosResponse(Parcel in) {
        this.stat = in.readString();
        this.photos = in.readParcelable(PhotosObject.class.getClassLoader());
    }

    public static final Parcelable.Creator<PhotosResponse> CREATOR = new Parcelable.Creator<PhotosResponse>() {
        @Override
        public PhotosResponse createFromParcel(Parcel source) {
            return new PhotosResponse(source);
        }

        @Override
        public PhotosResponse[] newArray(int size) {
            return new PhotosResponse[size];
        }
    };
}
