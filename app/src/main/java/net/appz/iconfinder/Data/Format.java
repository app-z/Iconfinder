package net.appz.iconfinder.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by App-z.net on 29.03.15.
 */
public class Format implements Parcelable {

    @SerializedName("preview_url")
    @Expose
    private String previewUrl;
    @SerializedName("download_url")
    @Expose
    private String downloadUrl;
    @Expose
    private String format;

    /**
     *
     * @return
     * The previewUrl
     */
    public String getPreviewUrl() {
        return previewUrl;
    }

    /**
     *
     * @param previewUrl
     * The preview_url
     */
    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

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


    protected Format(Parcel in) {
        previewUrl = in.readString();
        downloadUrl = in.readString();
        format = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(previewUrl);
        dest.writeString(downloadUrl);
        dest.writeString(format);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Format> CREATOR = new Parcelable.Creator<Format>() {
        @Override
        public Format createFromParcel(Parcel in) {
            return new Format(in);
        }

        @Override
        public Format[] newArray(int size) {
            return new Format[size];
        }
    };
}