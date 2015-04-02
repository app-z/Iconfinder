package net.appz.iconfinder.Data;

/**
 * Created by App-z.net on 02.04.15.
 */
public class DataHolder {
    private Styles styles;
    private Icons icons;
    private Iconsets iconsets;

    public DataHolder() {
    }

    public Styles getStyles(){
        return styles;
    }

    public void setStyles(Styles styles){
        this.styles = styles;
    }

    public Iconsets getIconSets(){
        return iconsets;
    }

    public void setIconsets(Iconsets iconsets){
        this.iconsets = iconsets;
    }

    public Icons getIcons(){
        return icons;
    }

    public void setIcons(Icons icons){
        this.icons = icons;
    }



}
