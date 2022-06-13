package com.sweet.selfiecameraphotoeditor.collageutils;

import android.os.Parcel;
import android.os.Parcelable;

public class Language implements Parcelable {
    public static final Creator<Language> CREATOR = new Creator<Language>() {
        public Language createFromParcel(Parcel parcel) {
            return new Language(parcel);
        }

        public Language[] newArray(int i) {
            return new Language[i];
        }
    };
    private String mName;
    private String mValue;

    public int describeContents() {
        return 0;
    }

    public Language() {
    }

    public void setName(String str) {
        this.mName = str;
    }

    public String getName() {
        return this.mName;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeString(this.mValue);
    }

    private Language(Parcel parcel) {
        this.mName = parcel.readString();
        this.mValue = parcel.readString();
    }
}
