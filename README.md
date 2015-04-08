# Iconfounder

Iconfounder Android client demo App

See description on habrhabr http://habrahabr.ru/post/254801/

Api iconfinder.com https://developer.iconfinder.com/api/2.0/index.html

This just demo how to parse feed with Volley Loader and use Fragments

```
public class DataLoader extends Loader<DataHolder> {

...

    @Override
    public void onForceLoad() {
        super.onForceLoad();
        if ( DEBUG ) Log.d(TAG, "Loader onForceLoad() : feedUrl = " + urlFeed);
        doRequest(DataHolder.getClazz(getId()));
    }

    /**
     *
     * Get Data
     */
    private void doRequest(Class<?> clazz) {
        final GsonRequest gsonRequest = new GsonRequest(urlFeed,
                clazz,
                null,
                new Response.Listener<DataHolder.DataHolderItem>() {
            @Override
            public void onResponse(DataHolder.DataHolderItem data) {
                dataHolder.setData(getId(), data);
                deliverResult(dataHolder);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null)
                    if (DEBUG) Log.e(TAG, "volleyError: " + volleyError.getMessage());
                deliverResult(null);
            }
        });
        requestQueue.add(gsonRequest);
    }

```

For pass argument to Fragment all custom nested classes should be Parceble (or Serilize)
```
public class Iconset implements Parcelable {
...

```
Then simple replace your Fragment through FragmentManager
```
    private void fillIconSets(Iconsets iconsets, final int position) {
        for(Iconset iconset : iconsets.getIconsets()){
            Log.d(TAG, iconset.getName());
        }
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position, iconsets))
                .commit();
    }

    private void fillIcons(Icons icons) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, IconsGridFragment.newInstance(icons))
                .commit();
    }
```

![](https://github.com/app-z/Iconfinder/blob/master/screen1.png)
![](https://github.com/app-z/Iconfinder/blob/master/screen2.png)
![](https://github.com/app-z/Iconfinder/blob/master/screen3.png)
![](https://github.com/app-z/Iconfinder/blob/master/screen4.png)

