package net.appz.iconfounder.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by App-z.net on 29.03.15.
 */
public class Container implements Parcelable {

    @SerializedName("download_url")
    @Expose
    private String downloadUrl;
    @Expose
    private String format;

    /**
     *
     * @return
     * The downloadUrl
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     *
     * @param downloadUrl
     * The download_url
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    /**
     *
     * @return
     * The format
     */
    public String getFormat() {
        return format;
    }

    /**
     *
     * @param format
     * The format
     */
    public void setFormat(String format) {
        this.format = format;
    }


    protected Container(Parcel in) {
        downloadUrl = in.readString();
        format = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(downloadUrl);
        dest.writeString(format);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Container> CREATOR = new Parcelable.Creator<Container>() {
        @Override
        public Container createFromParcel(Parcel in) {
            return new Container(in);
        }

        @Override
        public Container[] newArray(int size) {
            return new Container[size];
        }
    };
}