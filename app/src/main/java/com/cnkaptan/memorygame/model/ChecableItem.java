package com.cnkaptan.memorygame.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Created by cnkaptan on 04/09/17.
 */

public class ChecableItem implements Parcelable {
    private String name;
    private boolean checked;
    private boolean match;
    UUID id = UUID.randomUUID();

    public ChecableItem(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public UUID getId() {
        return id;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChecableItem that = (ChecableItem) o;

        if (checked != that.checked) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (checked ? 1 : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.match ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.id);
    }

    protected ChecableItem(Parcel in) {
        this.name = in.readString();
        this.checked = in.readByte() != 0;
        this.match = in.readByte() != 0;
        this.id = (UUID) in.readSerializable();
    }

    public static final Creator<ChecableItem> CREATOR = new Creator<ChecableItem>() {
        @Override
        public ChecableItem createFromParcel(Parcel source) {
            return new ChecableItem(source);
        }

        @Override
        public ChecableItem[] newArray(int size) {
            return new ChecableItem[size];
        }
    };
}
