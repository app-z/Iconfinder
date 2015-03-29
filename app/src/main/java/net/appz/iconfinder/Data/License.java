package net.appz.iconfinder.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by App-z.net on 29.03.15.
 */
public class License implements Parcelable {

    @Expose
    private String url;
    @SerializedName("license_id")
    @Expose
    private Integer licenseId;
    @Expose
    private String name;
    @Expose
    private String scope;

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     * @return
     * The licenseId
     */
    public Integer getLicenseId() {
        return licenseId;
    }

    /**
     *
     * @param licenseId
     * The license_id
     */
    public void setLicenseId(Integer licenseId) {
        this.licenseId = licenseId;
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

    /**
     *
     * @return
     * The scope
     */
    public String getScope() {
        return scope;
    }

    /**
     *
     * @param scope
     * The scope
     */
    public void setScope(String scope) {
        this.scope = scope;
    }


    protected License(Parcel in) {
        url = in.readString();
        licenseId = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        scope = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        if (licenseId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(licenseId);
        }
        dest.writeString(name);
        dest.writeString(scope);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<License> CREATOR = new Parcelable.Creator<License>() {
        @Override
        public License createFromParcel(Parcel in) {
            return new License(in);
        }

        @Override
        public License[] newArray(int size) {
            return new License[size];
        }
    };
}