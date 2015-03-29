# Iconfinder

Iconfinder Android client demo App

Feed see on iconfinder.com https://developer.iconfinder.com/api/2.0/index.html

This just demo how to parse feed with Volley and use Fragmets

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


![](https://github.com/app-z/Iconfinder/blob/master/screen1.png)
![](https://github.com/app-z/Iconfinder/blob/master/screen2.png)
![](https://github.com/app-z/Iconfinder/blob/master/screen3.png)

