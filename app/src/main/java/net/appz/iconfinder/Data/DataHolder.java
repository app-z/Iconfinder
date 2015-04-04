package net.appz.iconfinder.Data;

/**
 * Created by App-z.net on 02.04.15.
 */
public class DataHolder {

    public static final int LOADER_ICONS_ID = 1;
    public static final int LOADER_STYLES_ID = 2;
    public static final int LOADER_ICONSETS_ID = 3;

    private DataHolderItem styles;
    private DataHolderItem icons;
    private DataHolderItem iconsets;

    private DataHolderItem getStyles(){
        return styles;
    }

    private void setStyles(DataHolderItem styles){
        this.styles = styles;
    }

    public DataHolderItem getIconsets() {
        return iconsets;
    }

    private void setIconsets(DataHolderItem iconsets){
        this.iconsets = iconsets;
    }



    private DataHolderItem getIcons(){
        return icons;
    }

    private void setIcons(DataHolderItem icons){
        this.icons = icons;
    }

    public void setData(int dataId, DataHolderItem item){
        switch (dataId){
            case LOADER_ICONS_ID:
                setIcons(item);
                break;
            case LOADER_STYLES_ID:
                setStyles(item);
                break;
            case LOADER_ICONSETS_ID:
                setIconsets(item);
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

    public interface DataHolderItem{
    }

}
