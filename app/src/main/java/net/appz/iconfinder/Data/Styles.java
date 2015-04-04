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
public class Styles implements Parcelable, DataHolder.DataHolderItem {

    @Expose
    private List<Style> styles; // = new ArrayList<Style>();
    @SerializedName("total_count")
    @Expose
    private Integer totalCount;

    /**
     * @return The styles
     */
    public List<Style> getStyles() {
        return styles;
    }

    /**
     * @param styles The styles
     */
    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }

    /**
     * @return The totalCount
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount The total_count
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    protected Styles(Parcel in) {
        if (in.readByte() == 0x01) {
            styles = new ArrayList<Style>();
            in.readList(styles, Style.class.getClassLoader());
        } else {
            styles = null;
        }
        totalCount = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (styles == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(styles);
        }
        if (totalCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(totalCount);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Styles> CREATOR = new Parcelable.Creator<Styles>() {
        @Override
        public Styles createFromParcel(Parcel in) {
            return new Styles(in);
        }

        @Override
        public Styles[] newArray(int size) {
            return new Styles[size];
        }
    };
}