package com.cnkaptan.memorygame.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by cnkaptan on 06/09/17.
 */
@Entity
public class FlickrPhoto implements Parcelable {

    /**
     * photoId : 36665518600
     * owner : 152040970@N02
     * secret : 2aa502b48e
     * server : 4344
     * farm : 5
     * title : Follow me
     * ispublic : 1
     * isfriend : 0
     * isfamily : 0
     */

    @Id
    private long boxId;

    @SerializedName("id")
    private String photoId;

    private String owner;
    private String secret;
    private String server;
    private int farm;
    private String title;
    private int ispublic;
    private int isfriend;
    private int isfamily;

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIspublic() {
        return ispublic;
    }

    public void setIspublic(int ispublic) {
        this.ispublic = ispublic;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public int getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(int isfamily) {
        this.isfamily = isfamily;
    }

    public String getPhotoUrl() {
        // http://farm{farm-photoId}.staticflickr.com/{server-photoId}/{photoId}_{secret}.jpg
        return "http://farm" + farm + ".staticflickr.com/" + server +
                "/" + photoId + "_" + secret + ".jpg";
    }

    public long getBoxId() {
        return boxId;
    }

    public void setBoxId(long boxId) {
        this.boxId = boxId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.boxId);
        dest.writeString(this.photoId);
        dest.writeString(this.owner);
        dest.writeString(this.secret);
        dest.writeString(this.server);
        dest.writeInt(this.farm);
        dest.writeString(this.title);
        dest.writeInt(this.ispublic);
        dest.writeInt(this.isfriend);
        dest.writeInt(this.isfamily);
    }

    public FlickrPhoto() {
    }

    protected FlickrPhoto(Parcel in) {
        this.boxId = in.readLong();
        this.photoId = in.readString();
        this.owner = in.readString();
        this.secret = in.readString();
        this.server = in.readString();
        this.farm = in.readInt();
        this.title = in.readString();
        this.ispublic = in.readInt();
        this.isfriend = in.readInt();
        this.isfamily = in.readInt();
    }

    public static final Creator<FlickrPhoto> CREATOR = new Creator<FlickrPhoto>() {
        @Override
        public FlickrPhoto createFromParcel(Parcel source) {
            return new FlickrPhoto(source);
        }

        @Override
        public FlickrPhoto[] newArray(int size) {
            return new FlickrPhoto[size];
        }
    };
}
