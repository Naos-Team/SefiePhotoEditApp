package com.sweet.selfiecameraphotoeditor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdView;
import com.sweet.selfiecameraphotoeditor.Ad_class;
import com.sweet.selfiecameraphotoeditor.R;
import com.google.android.material.tabs.TabLayout;

public class MYCreationActivity extends AppCompatActivity {
    ImageView imgBack;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.photocreation_activity);
        getWindow().setFlags(1024, 1024);


        AdView mAdView = findViewById(R.id.adView);
        Ad_class.Show_banner(mAdView);

        this.tabLayout = (TabLayout) findViewById(R.id.tabHost);
        this.tabLayout.setTabGravity(0);
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.viewPager.setAdapter(this.viewPagerAdapter);
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.imgBack = (ImageView) findViewById(R.id.img_back);
        this.imgBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MYCreationActivity.this.onBackPressed();
            }
        });
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int i) {
            if (i == 0) {
                return "Sweet Camera";
            }
            if (i == 1) {
                return "Editor & Collage";
            }
            return null;
        }

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new CameraCreationFragment();
                case 1:
                    return new PhotoCreationFragment();
                default:
                    return null;
            }
        }
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
