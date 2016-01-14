package com.androidTest;


import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.Suppress;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.MapFragment;
import com.hihgSpeet.MainActivity;
import com.hihgSpeet.MapDbFragment;
import com.hihgSpeet.R;

/**
 * Created by MS on 13-01-2016.
 */
public class MainActivityUnitTest extends ActivityUnitTestCase<MainActivity> {

    Intent mLaunchIntent;

    public MainActivityUnitTest() {
        super(MainActivity.class);
    }

    MainActivity activity;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.MyMaterialTheme);
        setActivityContext(context);

        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), MainActivity.class);

        activity = startActivity(mLaunchIntent, null, null);


        activity.getFragmentManager().beginTransaction().add(R.layout.fragment_map, new MapFragment(), "MapFrag").commit();

    }


    @MediumTest
    public void testMainActivity(){

        assertNotNull(activity);

    }

    @MediumTest
    public void testMapDbFragment(){

        ViewPager viewPager = (ViewPager) activity.findViewById(R.id.viewpager);

        MainActivity.ViewPagerAdapter adapter = (MainActivity.ViewPagerAdapter) viewPager.getAdapter();
        MapDbFragment frag = (MapDbFragment) adapter.getItem(2);

        TabLayout tabLayout = (TabLayout) activity.findViewById(R.id.tabs);
        TabLayout.Tab tab = tabLayout.getTabAt(2);
        tab.select();

        String title = tab.getText().toString();

        assertNotNull(tab);

        assertNotNull(frag);

        assertEquals(title, "Boat Position");

    }

   @Suppress
    public void testMapFragment(){

        ViewPager viewPager = (ViewPager) activity.findViewById(R.id.viewpager);

        MainActivity.ViewPagerAdapter adapter = (MainActivity.ViewPagerAdapter) viewPager.getAdapter();
        Fragment frag = adapter.getItem(1);

        TabLayout tabLayout = (TabLayout) activity.findViewById(R.id.tabs);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();

        //Button button = (Button) frag.getView().findViewById(R.id.Navigatebutton);
        ViewGroup view = (ViewGroup) activity.findViewById(android.R.id.content);


        LayoutInflater layoutInflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.fragment_map, view, false);

        System.out.println();

        Button button = (Button) getActivity().findViewById(R.id.Navigatebutton);

        button.performClick();

        Intent launcedIntent = getStartedActivityIntent();

        assertNotNull(launcedIntent);

    }

}
