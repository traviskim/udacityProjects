package com.yesplz.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkUtil {
//    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
//    private static final String PARAM_API_KEY = "api_key";
//    private static final String KEY = "aad932bfde91fe41370340fd96073fea";
//
//    OkHttpClient client = new OkHttpClient();
//
//    public ResponseBody run(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            return response.body();
//        }
//    }
//
//    public URL builtUrl(String option){
//        Uri builtUri = Uri.parse(BASE_URL + option).buildUpon()
//                .appendQueryParameter(PARAM_API_KEY, KEY)
//                .build();
//
//        URL url = null;
//        try{
//            url = new URL(builtUri.toString());
//        }catch (MalformedURLException e){
//            e.printStackTrace();
//        }
//        return url;
//    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("isNetworkAvailable", "Network Availableß");
            return true;
        }
        Log.d("isNetworkAvailable", "No Network Availableß");
        return false;
    }
}
