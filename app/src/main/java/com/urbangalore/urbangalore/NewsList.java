package com.urbangalore.urbangalore;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import jp.wasabeef.recyclerview.animators.LandingAnimator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsList extends Fragment implements TaskLoadNewsFeeds.NewsItemsLoadedListener, NewsItemSelectedListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView myNewsListView;
    private NewsListViewAdapter myNewsAdapter;

    private LandingAnimator myAnimator;




    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsList.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsList newInstance(String param1, String param2)
    {
        NewsList fragment = new NewsList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsList()
    {

    }

    public void filterNewsList(String aCategory_in)
    {
        myNewsAdapter.filterNewsList(aCategory_in);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {

        if (mListener != null)
        {
            mListener.onFragmentCreation(this);
        }

        myNewsListView = (RecyclerView)view.findViewById(R.id.news_list);

        List<NewsItem> aNewsList = new ArrayList<>();
        myNewsAdapter = new NewsListViewAdapter(getActivity(), aNewsList, this);
        myNewsListView.setAdapter(myNewsAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        myNewsListView.setLayoutManager(llm);



        final TaskLoadNewsFeeds.NewsItemsLoadedListener aListener = this;

        myNewsListView.setOnScrollListener(new EndlessRecyclerOnScrollListener(llm)
        {
            @Override
            public void onLoadMore(int current_page)
            {
                //myNewsAdapter.appendNewsList(getData());
                new TaskLoadNewsFeeds(aListener, myNewsAdapter.getItemCount(), 10).execute();
            }


        });



        new TaskLoadNewsFeeds(this, 0, 10).execute();

        //enableItemAnimation();
    }

    private void enableItemAnimation()
    {
        if(Build.VERSION.SDK_INT >= 11)
        {
            myAnimator = new LandingAnimator();
            myAnimator.setAddDuration(1000);
            myAnimator.setRemoveDuration(1000);
            myNewsListView.setItemAnimator(myAnimator);


        }
    }

    @Override
    public void onNewsItemSelected(View v)
    {
        int itemPosition = myNewsListView.getChildPosition(v);
        final String aURL = myNewsAdapter.getNewsURL(itemPosition);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("NEWS_ITEM_URL", aURL);

                startActivity(intent);
            }
        }, 500);


    }

    /*
    public List<NewsItem> getData(){
        List<NewsItem> aNewsList = new ArrayList<>();
        aNewsList.add(new NewsItem("Title1", "Short Descsdadadsadadadadadaddadaddaddasd1", "URL1", "The Hindu", "PhotoURL1"));
        aNewsList.add(new NewsItem("Title2", "Short Dedadasdasdadadadadadadadadadsadadasddadasdadadadadasdsc2", "URL2", "The Hindu", "PhotoURL2"));
        aNewsList.add(new NewsItem("Title3", "Short Descddadasdadaddadadadadadadadads3", "URL3", "The Hindu", "PhotoURL3"));
        aNewsList.add(new NewsItem("Title3", "Short Desc3", "URL3", "The Hindu", "PhotoURL3"));
        aNewsList.add(new NewsItem("Title3", "Short Desc3", "URL3", "The Hindu", "PhotoURL3"));
        aNewsList.add(new NewsItem("Title3", "Short Desc3", "URL3", "The Hindu", "PhotoURL3"));
        aNewsList.add(new NewsItem("Title3", "Short Desc3", "URL3", "The Hindu", "PhotoURL3"));
        aNewsList.add(new NewsItem("Title3", "Short Desc3", "URL3", "The Hindu", "PhotoURL3"));
        aNewsList.add(new NewsItem("Title3", "Short Desc3", "URL3", "The Hindu", "PhotoURL3"));
        aNewsList.add(new NewsItem("Title3", "Short Desc3", "URL3", "The Hindu", "PhotoURL3"));

        return aNewsList;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onNewsItemsLoaded(ArrayList<NewsItem> listNews)
    {
        myNewsAdapter.appendNewsList(listNews);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {
        public void onFragmentInteraction(Uri uri);
        public void onFragmentCreation(Fragment aFragment_in);
    }


}
