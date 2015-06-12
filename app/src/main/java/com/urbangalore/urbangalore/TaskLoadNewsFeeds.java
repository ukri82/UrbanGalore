package com.urbangalore.urbangalore;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

/**
 * Created by u on 10.06.2015.
 */
public class TaskLoadNewsFeeds  extends AsyncTask<Void, Void, ArrayList<NewsItem>>
{
    public interface NewsItemsLoadedListener {
        public void onNewsItemsLoaded(ArrayList<NewsItem> listNews);
    }


    private NewsItemsLoadedListener myListener;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private int myStart = 0;
    private int myCount = 10;


    public TaskLoadNewsFeeds(NewsItemsLoadedListener aListener_in, int aStart_in, int limit) {

        myStart = aStart_in;
        myCount = limit;
        this.myListener = aListener_in;
        volleySingleton = VolleySingleton.getInstance(null);
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<NewsItem> doInBackground(Void... params) {

        ArrayList<NewsItem> listNews = NewsUtils.loadNewsItems(requestQueue, myStart, myCount);
        return listNews;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsItem> listNews) {
        if (myListener != null) {
            myListener.onNewsItemsLoaded(listNews);
        }
    }


}