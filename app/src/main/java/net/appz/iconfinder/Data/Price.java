package net.appz.iconfinder.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import net.appz.iconfinder.Data.License;

/**
 * Created by App-z.net on 29.03.15.
 */
public class Price implements Parcelable {

    @Expose
    private String currency;
    @Expose
    private Integer price;
    @Expose
    private License license;

    /**
     *
     * @return
     * The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     * The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     * The price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The license
     */
    public License getLicense() {
        return license;
    }

    /**
     *
     * @param license
     * The license
     */
    public void setLicense(License license) {
        this.license = license;
    }


    protected Price(Parcel in) {
        currency = in.readString();
        price = in.readByte() == 0x00 ? null : in.readInt();
        license = (License) in.readValue(License.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currency);
        if (price == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(price);
        }
        dest.writeValue(license);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Price> CREATOR = new Parcelable.Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel in) {
            return new Price(in);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };
}