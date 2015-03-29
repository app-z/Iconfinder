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
public class VectorSize implements Parcelable {

    @SerializedName("size_width")
    @Expose
    private Integer sizeWidth;
    @SerializedName("size_height")
    @Expose
    private Integer sizeHeight;
    @Expose
    private List<Format> formats; // = new ArrayList<Format_>();
    @SerializedName("target_sizes")
    @Expose
    private List<List<Integer>> targetSizes; // = new ArrayList<List<Integer>>();
    @Expose
    private Integer size;

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
    public void setSizeWidth(Integer sizeWidth) {
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
    public void setSizeHeight(Integer sizeHeight) {
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
     * The targetSizes
     */
    public List<List<Integer>> getTargetSizes() {
        return targetSizes;
    }

    /**
     *
     * @param targetSizes
     * The target_sizes
     */
    public void setTargetSizes(List<List<Integer>> targetSizes) {
        this.targetSizes = targetSizes;
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


    protected VectorSize(Parcel in) {
        sizeWidth = in.readByte() == 0x00 ? null : in.readInt();
        sizeHeight = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            formats = new ArrayList<Format>();
            in.readList(formats, Format.class.getClassLoader());
        } else {
            formats = null;
        }
        size = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (sizeWidth == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(sizeWidth);
        }
        if (sizeHeight == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(sizeHeight);
        }
        if (formats == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(formats);
        }
        if (size == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(size);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<VectorSize> CREATOR = new Parcelable.Creator<VectorSize>() {
        @Override
        public VectorSize createFromParcel(Parcel in) {
            return new VectorSize(in);
        }

        @Override
        public VectorSize[] newArray(int size) {
            return new VectorSize[size];
        }
    };
}