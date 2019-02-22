package com.ammar.saifiyahschool.Gallery;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Upload implements Parcelable {

    public String name;
    public String url;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, String url) {
        this.name = name;
        this.url= url;
    }
    protected  Upload(Parcel in){
        name = in.readString();
        url = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }

    public static final Creator<Upload> CREATOR =new Creator<Upload>() {
        @Override
        public Upload createFromParcel(Parcel source) {
            return new Upload(source);
        }

        @Override
        public Upload[] newArray(int size) {
            return new Upload[size];
        }
    };
}
