package net.appz.iconfinder.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by App-z.net on 29.03.15.
 */
public class Category implements Parcelable {

    @Expose
    private String identifier;
    @Expose
    private String name;

    /**
     *
     * @return
     * The identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     *
     * @param identifier
     * The identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }


    protected Category(Parcel in) {
        identifier = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(identifier);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}