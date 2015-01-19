package com.example.sam.Ass_04_T01;

import android.os.Parcel;
import android.os.Parcelable;

public class imageData implements Parcelable {
    // Initialize variables
    private String imageName;
    private String location;
    private String keyword;
    private int dayPicker;
    private int monthPicker;
    private int yearPicker;
    private String shareToggle;
    private String email;
    private String rating;

    // Create new imageData
    public imageData(String imageName, String location, String keyword, int dayPicker, int monthPicker, int yearPicker, String shareToggle, String email, String rating) {
        update(imageName, location, keyword, dayPicker, monthPicker, yearPicker, shareToggle, email, rating);
    }

    // Update the imageData with new content
    public void update(String imageName, String location, String keyword, int dayPicker, int monthPicker, int yearPicker, String shareToggle, String email, String rating) {
        this.imageName = imageName;
        this.location = location;
        this.keyword = keyword;
        this.dayPicker = dayPicker;
        this.monthPicker = monthPicker;
        this.yearPicker = yearPicker;
        this.shareToggle = shareToggle;
        this.email = email;
        this.rating = rating;
    }

    // Parse new data into a string and return that value
    public String toString() {
        String str = "Name: "+imageName;
        //str += "\nLocation: "+location;
        //str += "\nKeyword: "+keyword;
        str += "\nDate: "+dayPicker+ " / " +monthPicker+ " / " +yearPicker;
        //str += "\nShare? "+shareToggle;
        //str += "\nEmail: "+email;
        //str += "\nRating: "+rating;
        return str;
    }

    public int describeContents()
    {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags){
        out.writeString(imageName);
        out.writeString(location);
        out.writeString(keyword);
        out.writeInt(dayPicker);
        out.writeInt(monthPicker);
        out.writeInt(yearPicker);
        out.writeString(shareToggle);
        out.writeString(email);
        out.writeString(rating);
    }

    public static final Parcelable.Creator<imageData> CREATOR =
            new Parcelable.Creator<imageData>() {
                public imageData createFromParcel(Parcel in) {
                    return new imageData(in);
                }
                public imageData[] newArray(int size) {
                    return new imageData[size];
                }
            };

    private imageData(Parcel in) {
        imageName = in.readString();
        location = in.readString();
        keyword = in.readString();
        dayPicker = in.readInt();
        monthPicker = in.readInt();
        yearPicker  = in.readInt();
        shareToggle = in.readString();
        email = in.readString();
        rating = in.readString();
    }
}