package com.cnkaptan.memorygame.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cnkaptan on 06/09/17.
 */

public class PhotosObject implements Parcelable {

    private int page;
    private int pages;
    private int perpage;
    private String total;
    private List<FlickrPhoto> photo;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<FlickrPhoto> getPhotos() {
        return photo;
    }

    public void setPhotos(List<FlickrPhoto> photos) {
        this.photo = photos;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.pages);
        dest.writeInt(this.perpage);
        dest.writeString(this.total);
        dest.writeList(this.photo);
    }

    public PhotosObject() {
    }

    protected PhotosObject(Parcel in) {
        this.page = in.readInt();
        this.pages = in.readInt();
        this.perpage = in.readInt();
        this.total = in.readString();
        this.photo = new ArrayList<FlickrPhoto>();
        in.readList(this.photo, FlickrPhoto.class.getClassLoader());
    }

    public static final Parcelable.Creator<PhotosObject> CREATOR = new Parcelable.Creator<PhotosObject>() {
        @Override
        public PhotosObject createFromParcel(Parcel source) {
            return new PhotosObject(source);
        }

        @Override
        public PhotosObject[] newArray(int size) {
            return new PhotosObject[size];
        }
    };
}
