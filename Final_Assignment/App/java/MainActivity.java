
package resistance.avalon;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.Locale;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseACL;
import com.parse.ParseUser;


public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener{
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position)
            {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++){
            actionBar.addTab(actionBar.newTab()
                    .setText(mSectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }
        Parse.initialize(this, "wuHDDbOwKqCzzkbxyqB93JaaAs36l0Aupn9C8Elz", "F7VLDIKNH5gFeYiO2GmPn1XQWzSGRr3ZlVPWjJGn");

    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction){
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction){}

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction){}
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
        SparseArray<Fragment> fragmentArray = new SparseArray<Fragment>();

        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        public Fragment getFragment(int position)
        {
            return fragmentArray.get(position);
        }

        @Override
        public Fragment getItem(int position){
            Fragment fragment;
            if (position == 0){
                fragment = GameFragment.newInstance();
            }
            else if (position == 1){
                fragment = RulesFragment.newInstance();
            }
            else {
                fragment = HistoryFragment.newInstance();
            }

            fragmentArray.put(position, fragment); // store for use later
            return fragment;
        }

        @Override
        public int getCount(){
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position){
            Locale l = Locale.getDefault();
            switch (position)
            {
                case 0:
                    return getString(R.string.game).toUpperCase(l);
                case 1:
                    return getString(R.string.rules).toUpperCase(l);
                case 2:
                    return getString(R.string.history).toUpperCase(l);
            }
            return null;
        }

        public void destroyItem(ViewGroup container, int position, Object object){
            super.destroyItem(container, position, object);
            fragmentArray.remove(position);
        }
    }

}