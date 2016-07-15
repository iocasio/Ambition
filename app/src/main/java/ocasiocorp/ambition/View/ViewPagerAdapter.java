package ocasiocorp.ambition.View;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

import ocasiocorp.ambition.View.View_SupportingClasses.List_Fragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<List_Fragment> mFragmentList;
    private static final String ARG_TITLE = "title";

    public ViewPagerAdapter(FragmentManager manager, ArrayList<List_Fragment>fragments){
        super(manager);
        mFragmentList = new ArrayList<>();
        mFragmentList.addAll(fragments);
    }

    @Override
    public List_Fragment getItem(int position){
        List_Fragment fragment = mFragmentList.get(position);
        return fragment;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public int getCount(){
        return mFragmentList.size();
    }




}