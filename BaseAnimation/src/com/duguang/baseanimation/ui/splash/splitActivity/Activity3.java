package com.duguang.baseanimation.ui.splash.splitActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.utils.ActivitySplitAnimationUtil;

public class Activity3 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Preparing the 2 images to be split
        ActivitySplitAnimationUtil.prepareAnimation(this);
        
        setContentView(R.layout.activity_splash_split_three);

        findViewById(R.id.three).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivitySplitAnimationUtil.startActivity(Activity3.this, new Intent(Activity3.this, Activity4.class));
            }
        });
        
        // Animating the items to be open, revealing the new activity
        ActivitySplitAnimationUtil.animate(this, 1000);
    }

}
