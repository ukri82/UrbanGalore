package com.urbangalore.json;

/**
 * Created by Windows on 02-03-2015.
 */
public class Endpoints {
    public static String getRequestUrlNextNewsChunk(int aStart_in, int limit) {

        //return "http://80.240.142.76/get_news_feed_chunk_data?Limit=" + limit + "&NextChunk=" + aFirst_in;
        return "http://80.240.142.76/get_news_feed?" + "Start=" + aStart_in + "&Count=" + limit;
    }
}
