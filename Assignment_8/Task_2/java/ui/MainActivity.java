package swindroid.suntime.ui;

        import android.os.Bundle;
        import android.support.v4.app.FragmentActivity;

        import swindroid.suntime.R;

        import java.util.Locale;

        import android.app.ActionBar;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.util.SparseArray;
        import android.view.ViewGroup;

public class MainActivity extends FragmentActivity implements
        ActionBar.TabListener{
    /**
     * This example show how to make a Master-Detail relationship using Fragments
     *
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If data is memory intensive
     * switch to using state pager adapter (and manage data internally)
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    private String favMovieName = "none selected";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position)
            {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++)
        {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(actionBar.newTab()
                    .setText(mSectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction)
    {
        // When tab is selected, switch to the corresponding fragment in
        // ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
        // TODO: Remember back stack here if needed
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction)
    {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction)
    {
    }
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
        public Fragment getItem(int position)
        {
            Fragment fragment;
            if (position == 0)
            {
                fragment = MapFragment.newInstance();
            }
            else {
                fragment = SunriseFragment.newInstance();
            }

            fragmentArray.put(position, fragment); // store for use later
            return fragment;
        }

        @Override
        public int getCount()
        {
            return 2; // 2 fragments
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            Locale l = Locale.getDefault();
            switch (position)
            {
                case 0:
                    return getString(R.string.map).toUpperCase(l);
                case 1:
                    return getString(R.string.sunset).toUpperCase(l);
            }
            return null;
        }

        public void destroyItem(ViewGroup container, int position, Object object)
        {
            super.destroyItem(container, position, object);
            fragmentArray.remove(position);
        }
    }

}
