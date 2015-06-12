package com.urbangalore.urbangalore;

import java.util.List;
import java.util.Map;

/**
 * Created by u on 11.06.2015.
 */
public class Constants
{
    public static final String NO_FILTER = "NoFilter";
    public static final String FILTER_FOOD = "Food";
    public static final String FILTER_AUTO = "Auto";
    public static final String FILTER_MOVIE = "Movie";
    public static final String FILTER_SPORTS = "Sports";
    public static final String FILTER_LITEERATURE = "Literature";
    public static final String FILTER_MUSIC = "Music";
    public static final String FILTER_FASHION = "Fashion";

    public static List<FilterItem> getFilterList()
    {
        List<FilterItem> aFilterList = new java.util.ArrayList<>();
        aFilterList.add(new FilterItem(FILTER_FOOD, R.drawable.button_action_fg_food));
        aFilterList.add(new FilterItem(FILTER_AUTO, R.drawable.button_action_fg_auto));
        aFilterList.add(new FilterItem(FILTER_MOVIE, R.drawable.button_action_fg_movie));
        aFilterList.add(new FilterItem(FILTER_SPORTS, R.drawable.button_action_fg_sports));
        aFilterList.add(new FilterItem(FILTER_LITEERATURE, R.drawable.button_action_fg_literature));
        aFilterList.add(new FilterItem(FILTER_MUSIC, R.drawable.button_action_fg_music));
        aFilterList.add(new FilterItem(FILTER_FASHION, R.drawable.button_action_fg_fashion));
        aFilterList.add(new FilterItem(NO_FILTER, R.drawable.button_action_fg_filter_cross));

        return aFilterList;
    }

}
