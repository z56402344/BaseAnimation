/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.duguang.baseanimation.ui.nineold.droidflakes;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.duguang.baseanimation.R;

public class Droidflakes extends Activity {

    private FlakeView flakeView;
    private static CheckBox accelerated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nineold_droidflakes);
        LinearLayout container = (LinearLayout) findViewById(R.id.container);

		accelerated = (CheckBox) findViewById(R.id.accelerated);
        flakeView = new FlakeView(this);
        container.addView(flakeView);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        Button more = (Button) findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flakeView.addFlakes(flakeView.getNumFlakes());
            }
        });
        Button less = (Button) findViewById(R.id.less);
        less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flakeView.subtractFlakes(flakeView.getNumFlakes() / 2);
            }
        });
        if (Integer.parseInt(Build.VERSION.SDK) >= Build.VERSION_CODES.HONEYCOMB) {
            HoneycombHelper.setup(this);
        }
    }

    private static final class HoneycombHelper {
        static void setup(final Droidflakes activity) {
            
        	accelerated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    activity.flakeView.setLayerType(
                            isChecked ? View.LAYER_TYPE_NONE : View.LAYER_TYPE_SOFTWARE, null);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        flakeView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        flakeView.resume();
    }


}
