# Iconfinder

Iconfinder Android client demo App

See description on github http://habrahabr.ru/post/254801/

Api iconfinder.com https://developer.iconfinder.com/api/2.0/index.html

This just demo how to parse feed with Volley and use Fragments

```
private String urlStyles = "https://api.iconfinder.com" + "/v2/styles";

...

final GsonRequest gsonRequest = new GsonRequest(urlStyles, Styles.class, null, new Response.Listener<Styles>() {
            @Override
            public void onResponse(Styles styles) {
                fillStyles(styles);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(volleyError != null)
                    Log.e(TAG, volleyError.getMessage());
            }
        });
        gsonRequest.setTag("Styles");
        requestQueue.add(gsonRequest);

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

