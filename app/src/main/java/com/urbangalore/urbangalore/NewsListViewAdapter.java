package com.urbangalore.urbangalore;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by u on 09.06.2015.
 */
public class NewsListViewAdapter extends RecyclerView.Adapter<NewsListViewAdapter.NewsItemViewHolder>{


    private List<NewsItem> myNewsList;
    private List<NewsItem> myBackupNewsList;

    private LayoutInflater myInflator;
    private Context myContext;

    private NewsItemSelectedListener myNewsItemSelectionListener;

    private VolleySingleton myVolleySingleton;
    private ImageLoader myImageLoader;

    private int myPreviousPosition = 0;

    private String myCurrentFilter = Constants.NO_FILTER;


    public NewsListViewAdapter(Context context, List<NewsItem> data, NewsItemSelectedListener aNewsItemSelectedListener_in){
        this.myContext = context;
        this.myInflator = LayoutInflater.from(myContext);
        this.myNewsList = data;
        myBackupNewsList = new ArrayList<NewsItem>();

        myNewsItemSelectionListener = aNewsItemSelectedListener_in;

        myVolleySingleton = VolleySingleton.getInstance(null);
        myImageLoader = myVolleySingleton.getImageLoader();

    }



    public static class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        CardView myCard;
        TextView myTitleView;
        TextView myDescView;
        TextView mySourceView;
        TextView myDateView;
        ImageView myPhotoView;
        RippleView myRippleView;

        public NewsItemSelectedListener myListener;
        View myItemView;

        NewsItemViewHolder(View itemView, NewsItemSelectedListener aClickListener_in) {
            super(itemView);
            myItemView = itemView;
            myListener = aClickListener_in;
            myCard = (CardView)itemView.findViewById(R.id.news_card);
            myTitleView = (TextView)itemView.findViewById(R.id.news_header);
            myDescView = (TextView)itemView.findViewById(R.id.news_details);
            mySourceView = (TextView)itemView.findViewById(R.id.news_source);
            myDateView = (TextView)itemView.findViewById(R.id.news_date);
            myPhotoView = (ImageView)itemView.findViewById(R.id.news_photo);
            myRippleView = (RippleView)itemView.findViewById(R.id.ripple_view);

            myTitleView.setOnClickListener(this);
            myDescView.setOnClickListener(this);
            myPhotoView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(myListener != null)
            {
                myListener.onNewsItemSelected(myItemView);
            }
        }
    }


    private void loadImages(String urlThumbnail, final NewsItemViewHolder holder) {
        if (!urlThumbnail.equals("N.A") && !urlThumbnail.equals("")) {
            myImageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.myPhotoView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.myInflator.inflate(R.layout.news_card, parent, false);
        NewsItemViewHolder holder=new NewsItemViewHolder(view, myNewsItemSelectionListener);
        return holder;
    }

    private String getTimeText(Date aDate_in)
    {
        Time now = new Time();
        now.setToNow();
        long diffInSeconds = (now.toMillis(true) - aDate_in.getTime()) / 1000;
        String aTimeText = "";
        long aNumber = 0;
        if(diffInSeconds > 86400)
        {
            aNumber = diffInSeconds / 86400;
            aTimeText = aNumber > 1 ? " Days ago" : " Day ago";
        }
        else if(diffInSeconds > 3600)
        {
            aNumber = diffInSeconds / 3600;
            aTimeText = aNumber > 1 ? " Hours ago" : " Hour ago";
        }
        else if(diffInSeconds > 60)
        {
            aNumber = diffInSeconds / 60;
            aTimeText = aNumber > 1 ? " Minutes ago" : " Minute ago";
        }
        else
        {
            aNumber = diffInSeconds;
            aTimeText = aNumber > 1 ? " Seconds ago" : " Second ago";
        }
        return aNumber + aTimeText;
    }
    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {

        if(position >= myNewsList.size())
        {
            return;
        }

        NewsItem current = myNewsList.get(position);
        holder.myTitleView.setText(current.getTitle());
        holder.myDescView.setText(current.getShortDesc());
        holder.mySourceView.setText(current.getSource());
        holder.myDateView.setText(getTimeText(current.getDate()));
        holder.myPhotoView.setImageResource(R.drawable.uglogotinyw);

        String urlThumnail = current.getPhotoURL();
        //Log.d("onBindViewHolder", "urlThumnail = " + urlThumnail);
        loadImages(urlThumnail, holder);

        if(Build.VERSION.SDK_INT >= 11)
        {
            if (position > myPreviousPosition)
            {
                AnimationUtils.animateSunblind(holder, true);
            }
            else
            {
                AnimationUtils.animateSunblind(holder, false);
            }
        }
        myPreviousPosition = position;
     }

    @Override
    public int getItemCount() {
        return myNewsList.size();
    }


    public void appendNewsList(List<NewsItem> data)
    {
        int aNumItems = 0;

        if(myCurrentFilter.equals(Constants.NO_FILTER))
        {
            aNumItems = myNewsList.size();
            myNewsList.addAll(data);
        }
        else
        {
            for (NewsItem aNewsItem : data)
            {
                if (aNewsItem.getCategory().equals(myCurrentFilter))
                {
                    myNewsList.add(aNewsItem);
                    aNumItems++;
                }
            }
            myBackupNewsList.addAll(data);
        }
        notifyItemRangeInserted(aNumItems, data.size());
    }


    public String getNewsURL(int anItemIndex_in)
    {
        return myNewsList.get(anItemIndex_in).getURL();
    }

    public void filterNewsList(String aCategory_in)
    {
        myCurrentFilter = aCategory_in;
        if(aCategory_in.equals(Constants.NO_FILTER))
        {
            myNewsList.clear();
            myNewsList.addAll(myBackupNewsList);
            myBackupNewsList.clear();
        }
        else
        {
            if(myBackupNewsList.size() == 0)
            {
                myBackupNewsList.addAll(myNewsList);
            }
            myNewsList.clear();
            for (NewsItem aNewsItem : myBackupNewsList)
            {
                if (aNewsItem.getCategory().equals(aCategory_in))
                {
                    myNewsList.add(aNewsItem);
                }
            }
        }
        notifyDataSetChanged();

    }
}
