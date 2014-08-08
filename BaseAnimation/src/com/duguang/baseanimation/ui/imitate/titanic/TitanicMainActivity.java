package com.duguang.baseanimation.ui.imitate.titanic;

import android.app.Activity;
import android.os.Bundle;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.imitate.titanic.library.Titanic;
import com.duguang.baseanimation.ui.imitate.titanic.library.TitanicTextView;


public class TitanicMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imitate_titanic_main);

        TitanicTextView tv = (TitanicTextView) findViewById(R.id.my_text_view);

        // set fancy typeface
        tv.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));

        // start animation
        new Titanic().start(tv);
    }

}
