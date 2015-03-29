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
public class Iconsets implements Parcelable {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @Expose
    private List<Iconset> iconsets; // = new ArrayList<Iconset>();

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
     * The iconsets
     */
    public List<Iconset> getIconsets() {
        return iconsets;
    }

    /**
     *
     * @param iconsets
     * The iconsets
     */
    public void setIconsets(List<Iconset> iconsets) {
        this.iconsets = iconsets;
    }


    protected Iconsets(Parcel in) {
        totalCount = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            iconsets = new ArrayList<Iconset>();
            in.readList(iconsets, Iconset.class.getClassLoader());
        } else {
            iconsets = null;
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
        if (iconsets == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(iconsets);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Iconsets> CREATOR = new Parcelable.Creator<Iconsets>() {
        @Override
        public Iconsets createFromParcel(Parcel in) {
            return new Iconsets(in);
        }

        @Override
        public Iconsets[] newArray(int size) {
            return new Iconsets[size];
        }
    };
}