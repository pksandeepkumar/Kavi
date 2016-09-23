package texus.kavi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import texus.kavi.datamodel.GalleryData;
import texus.kavi.fragments.FragmentGalleryGrid;

/**
 * Created by sandeep on 21/09/16.
 */
public class HomeActivity extends  BaseActivity{

    public static final String PARAM_IMAGES = "paramImages";

    ViewPager viewPager;
    TabLayout tabLayout;

    ArrayList<GalleryData> galleryDatas = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setActionBar();
//
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        if( bundle != null) {
            galleryDatas = bundle.getParcelableArrayList(PARAM_IMAGES);
        }
//
        init();

    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar =getSupportActionBar();
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);

            actionbar.setTitle(getResources().getString(R.string.app_name));

        }
        toolbar.setNavigationOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    public void init() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setUpFragmentPager();
    }

    private void setUpFragmentPager() {


        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Liked"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public class PagerAdapter extends FragmentPagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

//            return null;

            switch (position) {
                case 0:
                    //Grid All
                    FragmentGalleryGrid gridAllFragment = new FragmentGalleryGrid();
                    return gridAllFragment;
                case 1:
                    //Grid Liked
                    FragmentGalleryGrid gridLikedFragment = new FragmentGalleryGrid();
                    return gridLikedFragment;

                default:
                    FragmentGalleryGrid gridAllFragment2 = new FragmentGalleryGrid();
                    return gridAllFragment2;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
