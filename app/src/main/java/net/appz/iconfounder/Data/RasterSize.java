package net.appz.iconfounder.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by App-z.net on 29.03.15.
 */
public class RasterSize implements Parcelable {

    @SerializedName("size_width")
    @Expose
    private int sizeWidth;
    @SerializedName("size_height")
    @Expose
    private int sizeHeight;
    @Expose
    private List<Format> formats; // = new ArrayList<Format>();
    @Expose
    private int size;

    /**
     *
     * @return
     * The sizeWidth
     */
    public Integer getSizeWidth() {
        return sizeWidth;
    }

    /**
     *
     * @param sizeWidth
     * The size_width
     */
    public void setSizeWidth(int sizeWidth) {
        this.sizeWidth = sizeWidth;
    }

    /**
     *
     * @return
     * The sizeHeight
     */
    public Integer getSizeHeight() {
        return sizeHeight;
    }

    /**
     *
     * @param sizeHeight
     * The size_height
     */
    public void setSizeHeight(int sizeHeight) {
        this.sizeHeight = sizeHeight;
    }

    /**
     *
     * @return
     * The formats
     */
    public List<Format> getFormats() {
        return formats;
    }

    /**
     *
     * @param formats
     * The formats
     */
    public void setFormats(List<Format> formats) {
        this.formats = formats;
    }

    /**
     *
     * @return
     * The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }



    protected RasterSize(Parcel in) {
        sizeWidth = in.readInt();
        sizeHeight = in.readInt();
        if (in.readByte() == 0x01) {
            formats = new ArrayList<Format>();
            in.readList(formats, Format.class.getClassLoader());
        } else {
            formats = null;
        }
        size = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sizeWidth);
        dest.writeInt(sizeHeight);
        if (formats == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(formats);
        }
        dest.writeInt(size);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RasterSize> CREATOR = new Parcelable.Creator<RasterSize>() {
        @Override
        public RasterSize createFromParcel(Parcel in) {
            return new RasterSize(in);
        }

        @Override
        public RasterSize[] newArray(int size) {
            return new RasterSize[size];
        }
    };
}