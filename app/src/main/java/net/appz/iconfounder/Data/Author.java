package net.appz.iconfounder.Data;

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
    private int userId;
    @Expose
    private String name;
    @SerializedName("iconsets_count")
    @Expose
    private int iconsetsCount;
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
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(int userId) {
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
    public int getIconsetsCount() {
        return iconsetsCount;
    }

    /**
     *
     * @param iconsetsCount
     * The iconsets_count
     */
    public void setIconsetsCount(int iconsetsCount) {
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
        userId = in.readInt();
        name = in.readString();
        iconsetsCount = in.readInt();
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
        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeInt(iconsetsCount);
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