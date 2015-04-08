package net.appz.iconfounder.Data;

import com.android.volley.VolleyError;

/**
 * Created by App-z.net on 02.04.15.
 */
public class DataHolder {
    public static final int LOADER_ICONS_ID = 1;
    public static final int LOADER_STYLES_ID = 2;
    public static final int LOADER_ICONSETS_ID = 3;

    private Styles styles;
    private Icons icons;
    private Iconsets iconsets;
    private VolleyError error;

    public Styles getStyles(){
        return styles;
    }

    private void setStyles(Styles styles){
        this.styles = styles;
    }

    public Iconsets getIconsets() {
        return iconsets;
    }

    private void setIconsets(Iconsets iconsets){
        this.iconsets = iconsets;
    }

    public Icons getIcons(){
        return icons;
    }

    private void setIcons(Icons icons){
        this.icons = icons;
    }

    public void setData(int dataId, DataHolderItem item){
        switch (dataId){
            case LOADER_ICONS_ID:
                setIcons((Icons)item);
                break;
            case LOADER_STYLES_ID:
                setStyles((Styles)item);
                break;
            case LOADER_ICONSETS_ID:
                setIconsets((Iconsets)item);
                break;
            default:
                assert false : "Error LOADER ID";
        }
    }

    public DataHolderItem getData(int dataId){
        switch (dataId){
            case LOADER_ICONS_ID:
                return getIcons();
            case LOADER_STYLES_ID:
                return getStyles();
            case LOADER_ICONSETS_ID:
                return getIconsets();
            default:
                assert false : "Error LOADER ID";
        }
        return null;
    }

    public static Class<?> getClazz(int dataId){
            switch (dataId){
                case LOADER_ICONS_ID:
                    return Icons.class;
                case LOADER_STYLES_ID:
                    return Styles.class;
                case LOADER_ICONSETS_ID:
                    return Iconsets.class;
                default:
                    assert false : "Error LOADER ID";
            }
            return null;
        }

    public void setError(VolleyError error) {
        this.error = error;
    }

    public VolleyError getError() {
        return error;
    }

    public interface DataHolderItem{
    }
}
