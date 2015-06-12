package com.urbangalore.urbangalore;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity implements NewsList.OnFragmentInteractionListener, View.OnClickListener
{

    private Toolbar myToolbar;
    private NewsList myNewsList;

    private FloatingActionButton myFAB;
    private FloatingActionMenu myFABMenu;
    private FragmentDrawer myDrawerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        VolleySingleton.getInstance(this);  //  Just initialize the singleton

        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setLogo(R.drawable.uglogotinyw);
        setupDrawer();

        if(Build.VERSION.SDK_INT >= 15)
        {
            setupFAB();
        }

   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri)
    {

    }

    @Override
    public void onFragmentCreation(Fragment aFragment_in)
    {
        myNewsList = (NewsList)aFragment_in;
    }

    private void setupDrawer() {

        //setup the NavigationDrawer
        myDrawerFragment = (FragmentDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer_id);
        myDrawerFragment.setUp(R.id.fragment_navigation_drawer_id, (DrawerLayout) findViewById(R.id.drawer_layout), myToolbar);
    }

    public void onDrawerSlide(float slideOffset) {
        toggleTranslateFAB(slideOffset);
    }

    private void toggleTranslateFAB(float slideOffset) {
        if (myFABMenu != null)
        {
            if (myFABMenu.isOpen()) {
                myFABMenu.close(true);
            }
            if(myFAB != null)
            {
                myFAB.setTranslationX(slideOffset * 200);
            }
        }
    }

    private void setupFAB() {
        //define the icon for the main floating action button
        ImageView iconFAB = new ImageView(this);
        iconFAB.setImageResource(R.drawable.button_action_fg_filter);


        //set the appropriate background for the main floating action button along with its icon
        myFAB = new FloatingActionButton.Builder(this)
                .setContentView(iconFAB)
                .setBackgroundDrawable(R.drawable.button_action_bg_yellow)
                .build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_bg_yellow));

        FloatingActionMenu.Builder aBuilder = new FloatingActionMenu.Builder(this);

        List<FilterItem> aFilterList = Constants.getFilterList();
        for (FilterItem aFilter : aFilterList)
        {
            ImageView iconView = new ImageView(this);
            iconView.setImageResource(aFilter.myResId);

            SubActionButton buttonFilter = itemBuilder.setContentView(iconView).build();
            buttonFilter.setTag(aFilter.myName);
            buttonFilter.setOnClickListener(this);

            aBuilder.addSubActionView(buttonFilter);
        }
        aBuilder.attachTo(myFAB);
        aBuilder.setRadius(getResources().getDimensionPixelSize(R.dimen.action_menu_radius_more));
        myFABMenu = aBuilder.build();

    }

    @Override
    public void onClick(View v)
    {
        onClick(v.getTag().toString());
    }

    public void onClick(String aTag_in)
    {
        if (!aTag_in.equals(""))
        {
            myNewsList.filterNewsList(aTag_in);
        }
    }
}
