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
public class Icon implements Parcelable {

    @SerializedName("raster_sizes")
    @Expose
    private List<RasterSize> rasterSizes; // = new ArrayList<RasterSize>();
    @Expose
    private List<Style> styles; // = new ArrayList<Style>();
    @Expose
    private List<String> tags; // = new ArrayList<String>();
    @SerializedName("icon_id")
    @Expose
    private Integer iconId;
    @SerializedName("vector_sizes")
    @Expose
    private List<VectorSize> vectorSizes; // = new ArrayList<VectorSize>();
    @SerializedName("is_premium")
    @Expose
    private Boolean isPremium;
    @SerializedName("published_at")
    @Expose
    private String publishedAt;
    @Expose
    private List<Container> containers; // = new ArrayList<Container>();
    @Expose
    private String type;
    @Expose
    private List<Category> categories; // = new ArrayList<Category>();

    /**
     *
     * @return
     * The rasterSizes
     */
    public List<RasterSize> getRasterSizes() {
        return rasterSizes;
    }

    /**
     *
     * @param rasterSizes
     * The raster_sizes
     */
    public void setRasterSizes(List<RasterSize> rasterSizes) {
        this.rasterSizes = rasterSizes;
    }

    /**
     *
     * @return
     * The styles
     */
    public List<Style> getStyles() {
        return styles;
    }

    /**
     *
     * @param styles
     * The styles
     */
    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }

    /**
     *
     * @return
     * The tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     * The tags
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     *
     * @return
     * The iconId
     */
    public Integer getIconId() {
        return iconId;
    }

    /**
     *
     * @param iconId
     * The icon_id
     */
    public void setIconId(Integer iconId) {
        this.iconId = iconId;
    }

    /**
     *
     * @return
     * The vectorSizes
     */
    public List<VectorSize> getVectorSizes() {
        return vectorSizes;
    }

    /**
     *
     * @param vectorSizes
     * The vector_sizes
     */
    public void setVectorSizes(List<VectorSize> vectorSizes) {
        this.vectorSizes = vectorSizes;
    }

    /**
     *
     * @return
     * The isPremium
     */
    public Boolean getIsPremium() {
        return isPremium;
    }

    /**
     *
     * @param isPremium
     * The is_premium
     */
    public void setIsPremium(Boolean isPremium) {
        this.isPremium = isPremium;
    }

    /**
     *
     * @return
     * The publishedAt
     */
    public String getPublishedAt() {
        return publishedAt;
    }

    /**
     *
     * @param publishedAt
     * The published_at
     */
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     *
     * @return
     * The containers
     */
    public List<Container> getContainers() {
        return containers;
    }

    /**
     *
     * @param containers
     * The containers
     */
    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     *
     * @param categories
     * The categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }


    protected Icon(Parcel in) {
        if (in.readByte() == 0x01) {
            rasterSizes = new ArrayList<RasterSize>();
            in.readList(rasterSizes, RasterSize.class.getClassLoader());
        } else {
            rasterSizes = null;
        }
        if (in.readByte() == 0x01) {
            styles = new ArrayList<Style>();
            in.readList(styles, Style.class.getClassLoader());
        } else {
            styles = null;
        }
        if (in.readByte() == 0x01) {
            tags = new ArrayList<String>();
            in.readList(tags, String.class.getClassLoader());
        } else {
            tags = null;
        }
        iconId = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            vectorSizes = new ArrayList<VectorSize>();
            in.readList(vectorSizes, VectorSize.class.getClassLoader());
        } else {
            vectorSizes = null;
        }
        byte isPremiumVal = in.readByte();
        isPremium = isPremiumVal == 0x02 ? null : isPremiumVal != 0x00;
        publishedAt = in.readString();
        if (in.readByte() == 0x01) {
            containers = new ArrayList<Container>();
            in.readList(containers, Container.class.getClassLoader());
        } else {
            containers = null;
        }
        type = in.readString();
        if (in.readByte() == 0x01) {
            categories = new ArrayList<Category>();
            in.readList(categories, Category.class.getClassLoader());
        } else {
            categories = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (rasterSizes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(rasterSizes);
        }
        if (styles == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(styles);
        }
        if (tags == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(tags);
        }
        if (iconId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(iconId);
        }
        if (vectorSizes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(vectorSizes);
        }
        if (isPremium == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isPremium ? 0x01 : 0x00));
        }
        dest.writeString(publishedAt);
        if (containers == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(containers);
        }
        dest.writeString(type);
        if (categories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(categories);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Icon> CREATOR = new Parcelable.Creator<Icon>() {
        @Override
        public Icon createFromParcel(Parcel in) {
            return new Icon(in);
        }

        @Override
        public Icon[] newArray(int size) {
            return new Icon[size];
        }
    };
}