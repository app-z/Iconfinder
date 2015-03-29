package net.appz.iconfinder.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by App-z.net on 29.03.15.
 */
public class Icons implements Parcelable {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @Expose
    private List<Icon> icons; // = new ArrayList<Icon>();

    /**
     *
     * @return
     * The totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     *
     * @param totalCount
     * The total_count
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     *
     * @return
     * The icons
     */
    public List<Icon> getIcons() {
        return icons;
    }

    /**
     *
     * @param icons
     * The icons
     */
    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }


    protected Icons(Parcel in) {
        totalCount = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            icons = new ArrayList<Icon>();
            in.readList(icons, Icon.class.getClassLoader());
        } else {
            icons = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (totalCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(totalCount);
        }
        if (icons == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(icons);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Icons> CREATOR = new Parcelable.Creator<Icons>() {
        @Override
        public Icons createFromParcel(Parcel in) {
            return new Icons(in);
        }

        @Override
        public Icons[] newArray(int size) {
            return new Icons[size];
        }
    };
}