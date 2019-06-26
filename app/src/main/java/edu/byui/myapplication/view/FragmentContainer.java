package edu.byui.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import edu.byui.myapplication.R;

public class FragmentContainer extends AppCompatActivity {

    private FragmentMenuAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        pagerAdapter = new FragmentMenuAdapter(getSupportFragmentManager(),0);
        viewPager = (ViewPager) findViewById(R.id.container);

        viewPager.setCurrentItem(0);
    }

    private void serupViewPager(ViewPager nViewPager) {
        FragmentMenuAdapter adapter = new FragmentMenuAdapter(getSupportFragmentManager(),0);
        adapter.addFragment(new MenuFragment(),"");

        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber) {
        viewPager.setCurrentItem(fragmentNumber);
    }

}
