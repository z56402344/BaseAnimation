package com.duguang.baseanimation.ui.splash.splitActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.utils.ActivitySplitAnimationUtil;

public class Activity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Preparing the 2 images to be split
        ActivitySplitAnimationUtil.prepareAnimation(this);
        
        setContentView(R.layout.activity_splash_split_two);

        findViewById(R.id.two).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivitySplitAnimationUtil.startActivity(Activity2.this, new Intent(Activity2.this, Activity3.class));
            }
        });
        
        // Animating the items to be open, revealing the new activity
        ActivitySplitAnimationUtil.animate(this, 1000);
    }

}
