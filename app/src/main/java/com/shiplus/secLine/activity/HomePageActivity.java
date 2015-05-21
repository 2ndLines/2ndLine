package com.shiplus.secLine.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.shiplus.secLine.R;
import com.shiplus.secLine.fragment.BaseFragment;
import com.shiplus.secLine.view.navibar.IIconPagerAdapter;
import com.shiplus.secLine.view.navibar.IconPageIndicator;

import java.util.ArrayList;
import java.util.List;


public class HomePageActivity extends BaseActivity {
    private ViewPager mViewPager;
    private IconPageIndicator mTabWidget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        findViews();
    }

    private void findViews(){
        mViewPager = (ViewPager) findViewById(R.id.homepage_view_pager);
        mTabWidget = (IconPageIndicator) findViewById(R.id.homepage_tabs);
        initFragments();
    }

    private void initFragments(){
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        BaseFragment homeFragment = new BaseFragment();
        homeFragment.setTitle(R.string.tab_title_home);
        homeFragment.setIconResId(R.drawable.tab_selector_home);
        adapter.addFragment(homeFragment);

        BaseFragment channelFragment = new BaseFragment();
        channelFragment.setTitle(R.string.tab_title_channel);
        channelFragment.setIconResId(R.drawable.tab_selector_channel);
        adapter.addFragment(channelFragment);

        BaseFragment mineFragment = new BaseFragment();
        mineFragment.setTitle(R.string.tab_title_mine);
        mineFragment.setIconResId(R.drawable.tab_selector_mine);
        adapter.addFragment(mineFragment);

        mViewPager.setAdapter(adapter);
        mTabWidget.setViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class FragmentAdapter extends FragmentPagerAdapter implements IIconPagerAdapter{
        private List<BaseFragment> mmFragments;
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(BaseFragment fragment){
            if(mmFragments == null){
                mmFragments = new ArrayList<BaseFragment>();
            }
            if(!mmFragments.contains(fragment)){
                mmFragments.add(fragment);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return mmFragments == null ? null : mmFragments.get(position);
        }

        @Override
        public int getIconResId(int index) {
            return mmFragments == null ? 0 : mmFragments.get(index).getIconResId() ;
        }

        @Override
        public int getCount() {
            return mmFragments == null? 0 : mmFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mmFragments == null ? null : mmFragments.get(position).getTitle();
        }
    }

}
