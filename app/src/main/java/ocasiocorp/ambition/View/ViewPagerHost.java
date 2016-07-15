package ocasiocorp.ambition.View;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import ocasiocorp.ambition.Controller.Database_Controller;
import ocasiocorp.ambition.Model.Goal;
import ocasiocorp.ambition.Model.GoalList;
import ocasiocorp.ambition.R;
import ocasiocorp.ambition.View.View_SupportingClasses.List_Fragment;

public class ViewPagerHost extends Activity_Logger implements List_Fragment.viewPagerCallBack{
    private static final String ARG_TITLE = "title";
    private ViewPager mViewPager;
    private ArrayList<List_Fragment> mFragmentList;
    private ArrayList<GoalList> goalList;
    private ArrayList<Goal> goalArrayList;
    Database_Controller controller;
    ViewPagerAdapter adapter;
    FragmentManager fragmentManager;
    CirclePageIndicator indicator2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_host);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //deleteDatabase(Database_Schema.DATABASE_NAME);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6942193585541819~7894858282");
        AdView adView = (AdView) findViewById(R.id.ad);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Ambition");
        fragmentManager = getSupportFragmentManager();
        controller = Database_Controller.get(getApplicationContext());
        goalList = controller.getAllLists();
        goalArrayList = controller.getAllGoals();
        mViewPager = (ViewPager)findViewById(R.id.view_fragments);
        if(goalList.isEmpty()){
            Log.d("TAG", "THIS PART IS WORKING");
            GoalList listOne = new GoalList();
            listOne.setTitle("DEFAULT");
            controller.insertList(listOne);
            for(int i =0; i< goalArrayList.size(); i++){
                goalArrayList.get(i).setWhich_List(listOne.getTitle());
                controller.updateGoal(goalArrayList.get(i));
            }
            goalList = controller.getAllLists();
        }
        mFragmentList = getFragmentList(goalList);
        for(int i = 0; i < mFragmentList.size(); i++){
            Log.d("Fragment OutputTHREE", mFragmentList.get(i).getArguments().getString(ARG_TITLE));
        }
        adapter = new ViewPagerAdapter(fragmentManager, mFragmentList);
        mViewPager.setAdapter(adapter);
        indicator2 = (CirclePageIndicator)findViewById(R.id.indicator);
        indicator2.setViewPager(mViewPager);
    }

    public ArrayList<List_Fragment> getFragmentList(ArrayList<GoalList> list){
        ArrayList<List_Fragment> fragments = new ArrayList<>();
        for(int i = 0; i< list.size(); i++){
            Bundle bundle = new Bundle();
            bundle.putString(ARG_TITLE, list.get(i).getTitle());
            fragments.add(new List_Fragment());
            fragments.get(i).setArguments(bundle);
            Log.d("Fragment Output", fragments.get(i).getArguments().getString(ARG_TITLE));
        }

        for(int i = 0; i< list.size(); i++){
            Log.d("Fragment OutputTWO", fragments.get(i).getArguments().getString(ARG_TITLE));
        }
        return fragments;
    }

    @Override
    public void updateLists(final int resultCode) {
        goalList.clear();
        goalList = controller.getAllLists();
        ArrayList<List_Fragment> newFragmentList = getFragmentList(goalList);
        final int position = mViewPager.getCurrentItem();
        mFragmentList.clear();
        adapter.mFragmentList = mFragmentList;
        mViewPager.setAdapter(adapter);
        adapter.mFragmentList.addAll(newFragmentList);
        mViewPager.setAdapter(adapter);
        if(resultCode ==1){
            mViewPager.setCurrentItem(position);
            mViewPager.postDelayed(new Runnable(){
                @Override
                public void run() {
                    mViewPager.setCurrentItem(adapter.getCount());
                }
            },50);
        }else{
            mViewPager.setCurrentItem(position-2%adapter.getCount());
            mViewPager.postDelayed(new Runnable(){
                @Override
                public void run() {
                    mViewPager.setCurrentItem(position-1%adapter.getCount());
                }
            },50);
            }
    }
}
