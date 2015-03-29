package net.appz.iconfinder.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by App-z.net on 29.03.15.
 */
public class Author implements Parcelable {

    @Expose
    private String username;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @Expose
    private String name;
    @SerializedName("iconsets_count")
    @Expose
    private Integer iconsetsCount;
    @SerializedName("is_designer")
    @Expose
    private Boolean isDesigner;

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
     * The iconsetsCount
     */
    public Integer getIconsetsCount() {
        return iconsetsCount;
    }

    /**
     *
     * @param iconsetsCount
     * The iconsets_count
     */
    public void setIconsetsCount(Integer iconsetsCount) {
        this.iconsetsCount = iconsetsCount;
    }

    /**
     *
     * @return
     * The isDesigner
     */
    public Boolean getIsDesigner() {
        return isDesigner;
    }

    /**
     *
     * @param isDesigner
     * The is_designer
     */
    public void setIsDesigner(Boolean isDesigner) {
        this.isDesigner = isDesigner;
    }


    protected Author(Parcel in) {
        username = in.readString();
        userId = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        iconsetsCount = in.readByte() == 0x00 ? null : in.readInt();
        byte isDesignerVal = in.readByte();
        isDesigner = isDesignerVal == 0x02 ? null : isDesignerVal != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        if (userId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(userId);
        }
        dest.writeString(name);
        if (iconsetsCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(iconsetsCount);
        }
        if (isDesigner == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (isDesigner ? 0x01 : 0x00));
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Author> CREATOR = new Parcelable.Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
}