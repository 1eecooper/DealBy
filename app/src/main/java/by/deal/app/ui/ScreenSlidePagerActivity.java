package by.deal.app.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import by.deal.app.R;
import by.deal.app.sql.Contract;
import by.deal.app.sql.SQLHelper;

public class ScreenSlidePagerActivity extends FragmentActivity {

    public static final String KEY_POSITION = "key";

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        Intent intent = getIntent();
        int id = intent.getIntExtra(Contract.OrderEntry.ID, -1);
        mCursor = SQLHelper.getInstance(this.getApplicationContext()).getAllOrders();
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(id - 1);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (mCursor == null) {
                return null;
            }
            Fragment fragment = new ScreenSlidePageFragment();
            Bundle args = new Bundle();
            args.putInt(KEY_POSITION, position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            if (mCursor == null) {
                return 0;
            } else {
                return mCursor.getCount();
            }
        }
    }

}
