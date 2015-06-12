package com.urbangalore.urbangalore;

import java.util.Date;

/**
 * Created by u on 09.06.2015.
 */
public class NewsItem
{

    String myTitle;
    String myShortDesc;
    String myURL;

    String mySource;
    String myPhotoURL;
    String myCategory;

    private Date myDate;

    public NewsItem(String aTitle_in, String aShortDesc_in, String aURL_in, String aSource_in, String aPhotoURL_in, Date aDate_in, String aCategory_in)
    {
        myTitle = aTitle_in;
        myShortDesc = aShortDesc_in;
        myURL = aURL_in;
        mySource = aSource_in;
        myPhotoURL = aPhotoURL_in;
        myDate = aDate_in;
        myCategory = aCategory_in;
    }

    public String getTitle()
    {
        return myTitle;
    }

    public String getShortDesc()
    {
        return myShortDesc;
    }

    public String getURL()
    {
        return myURL;
    }

    public String getSource()
    {
        return mySource;
    }

    public String getPhotoURL()
    {
        return myPhotoURL;
    }

    public String getCategory()
    {
        return myCategory;
    }

    public Date getDate()
    {
        return myDate;
    }
}
