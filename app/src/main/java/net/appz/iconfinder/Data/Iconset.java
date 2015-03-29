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
public class Iconset implements Parcelable {

    @Expose
    private List<Style> styles; // = new ArrayList<Style>();
    @SerializedName("icons_count")
    @Expose
    private Integer iconsCount;
    @Expose
    private Author author;
    @SerializedName("is_premium")
    @Expose
    private Boolean isPremium;
    @SerializedName("published_at")
    @Expose
    private String publishedAt;
    @Expose
    private List<Price> prices; // = new ArrayList<Price>();
    @Expose
    private String identifier;
    @Expose
    private String type;
    @SerializedName("iconset_id")
    @Expose
    private Integer iconsetId;
    @Expose
    private List<Category> categories; // = new ArrayList<Category>();
    @Expose
    private String name;

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
     * The iconsCount
     */
    public Integer getIconsCount() {
        return iconsCount;
    }

    /**
     *
     * @param iconsCount
     * The icons_count
     */
    public void setIconsCount(Integer iconsCount) {
        this.iconsCount = iconsCount;
    }

    /**
     *
     * @return
     * The author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(Author author) {
        this.author = author;
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
     * The prices
     */
    public List<Price> getPrices() {
        return prices;
    }

    /**
     *
     * @param prices
     * The prices
     */
    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

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
     * The iconsetId
     */
    public Integer getIconsetId() {
        return iconsetId;
    }

    /**
     *
     * @param iconsetId
     * The iconset_id
     */
    public void setIconsetId(Integer iconsetId) {
        this.iconsetId = iconsetId;
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


    protected Iconset(Parcel in) {
        if (in.readByte() == 0x01) {
            styles = new ArrayList<Style>();
            in.readList(styles, Style.class.getClassLoader());
        } else {
            styles = null;
        }
        iconsCount = in.readByte() == 0x00 ? null : in.readInt();
        author = (Author) in.readValue(Author.class.getClassLoader());
        byte isPremiumVal = in.readByte();
        isPremium = isPremiumVal == 0x02 ? null : isPremiumVal != 0x00;
        publishedAt = in.readString();
        if (in.readByte() == 0x01) {
            prices = new ArrayList<Price>();
            in.readList(prices, Price.class.getClassLoader());
        } else {
            prices = null;
        }
        identifier = in.readString();
        type = in.readString();
        iconsetId = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            categories = new ArrayList<Category>();
            in.readList(categories, Category.class.getClassLoader());
        } else {
            categories = null;
        }
        name = in.readString();
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
        if (iconsCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(iconsCount);
        }
        dest.writeValue(author);
        if (isPremium == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isPremium ? 0x01 : 0x00));
        }
        dest.writeString(publishedAt);
        if (prices == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(prices);
        }
        dest.writeString(identifier);
        dest.writeString(type);
        if (iconsetId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(iconsetId);
        }
        if (categories == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(categories);
        }
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Iconset> CREATOR = new Parcelable.Creator<Iconset>() {
        @Override
        public Iconset createFromParcel(Parcel in) {
            return new Iconset(in);
        }

        @Override
        public Iconset[] newArray(int size) {
            return new Iconset[size];
        }
    };
}