package com.pax.photoslideshow;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;
import java.util.Random;


public class MainActivity extends Activity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    private SliderLayout mDemoSlider;
    private SliderLayout.Transformer[] mTransformers;
    private Random mTransformerRandom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDemoSlider = findViewById(R.id.slider);
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDemoSlider != null) {
                    mDemoSlider.startAutoCycle();
                }
            }
        });

        mTransformers = SliderLayout.Transformer.values();
        mTransformerRandom = new Random();

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal",R.mipmap.hannibal);
        file_maps.put("Big Bang Theory",R.mipmap.bigbang);
        file_maps.put("House of Cards",R.mipmap.house);
        file_maps.put("Game of Thrones", R.mipmap.game_of_thrones);

        for(String name : file_maps.keySet()){
            ImageSliderView imageSliderView = new ImageSliderView(this);
            // initialize a SliderLayout
            imageSliderView
                    //.description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterInside)
                    .setOnSliderClickListener(this);

            //add your extra information
            imageSliderView.bundle(new Bundle());
            imageSliderView.getBundle()
                        .putString("extra",name);

           mDemoSlider.addSlider(imageSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(null);
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);

        //mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
    }

    @Override
    protected void onDestroy() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onDestroy();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        mDemoSlider.stopAutoCycle();
        //Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
        mDemoSlider.setPresetTransformer(mTransformerRandom.nextInt(mTransformers.length));
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}
