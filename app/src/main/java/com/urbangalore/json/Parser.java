package com.urbangalore.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.urbangalore.urbangalore.NewsItem;
import com.urbangalore.logging.L;

/**
 * Created by Windows on 02-03-2015.
 */
public class Parser {
    
    private static final String NA = "N.A";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESC = "description";
    private static final String KEY_URL = "links";
    private static final String KEY_SOURCE = "feed_source";
    private static final String KEY_PHOTO_URL = "photo_url";
    private static final String KEY_DATE_TIME = "pub_date";
    private static final String KEY_CATEGORY = "category";

    public static ArrayList<NewsItem> parseMoviesJSON(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ArrayList<NewsItem> listNews = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                JSONArray arrayNewsItems = response.getJSONArray("FeedData");
                for (int i = 0; i < arrayNewsItems.length(); i++) {
                    //long id = -1;
                    String title = NA;
                    String desc = NA;
                    String source = NA;
                    String aURL = NA;
                    String aPhotoURL = NA;
                    String aDateTime = NA;
                    String aCategory = NA;


                    JSONObject currentNewsItem = arrayNewsItems.getJSONObject(i);


                    //get the title of the current news item
                    if (Utils.contains(currentNewsItem, KEY_TITLE)) {
                        title = currentNewsItem.getString(KEY_TITLE);
                    }

                    //get the description of the current news item
                    if (Utils.contains(currentNewsItem, KEY_DESC)) {
                        desc = currentNewsItem.getString(KEY_DESC);
                    }

                    //get the source of the current news item
                    if (Utils.contains(currentNewsItem, KEY_SOURCE)) {
                        source = currentNewsItem.getString(KEY_SOURCE);
                    }

                    //get the news URL
                    if (Utils.contains(currentNewsItem, KEY_URL)) {
                        JSONArray aLinks = currentNewsItem.getJSONArray(KEY_URL);
                        if(aLinks.length() > 0)
                        {
                            aURL = aLinks.getString(0);
                        }
                    }

                    //get the news photo URL
                    if (Utils.contains(currentNewsItem, KEY_PHOTO_URL)) {
                        aPhotoURL = currentNewsItem.getString(KEY_PHOTO_URL);
                    }

                    //get the news photo URL
                    if (Utils.contains(currentNewsItem, KEY_CATEGORY)) {
                        aCategory = currentNewsItem.getString(KEY_CATEGORY);
                    }



                    //get the datetime of the current news item
                    if (Utils.contains(currentNewsItem, KEY_DATE_TIME)) {
                        aDateTime = currentNewsItem.getString(KEY_DATE_TIME);
                    }

                    Date date = null;
                    try {
                        date = dateFormat.parse(aDateTime);
                    } catch (ParseException e) {
                        //a parse exception generated here will store null in the release date, be sure to handle it
                    }

                    NewsItem aNewsItem = new NewsItem(title, desc, aURL, source, aPhotoURL, date, aCategory);

                    //L.t(getActivity(), aNewsItem + "");
                    //if (id != -1 && !title.equals(NA)) {
                        listNews.add(aNewsItem);
                    //}
                }

            } catch (JSONException e) {

            }
//                L.t(getActivity(), listMovies.size() + " rows fetched");
        }
        return listNews;
    }


}
