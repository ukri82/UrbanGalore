package com.urbangalore.urbangalore;

import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

import com.urbangalore.json.Requestor;
import com.urbangalore.json.Endpoints;
import com.urbangalore.json.Parser;

/**
 * Created by u on 10.06.2015.
 */
public class NewsUtils
{
    public static ArrayList<NewsItem> loadNewsItems(RequestQueue requestQueue, int aStart_in, int limit) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlNextNewsChunk(aStart_in, limit));
        return Parser.parseMoviesJSON(response);
    }
}
