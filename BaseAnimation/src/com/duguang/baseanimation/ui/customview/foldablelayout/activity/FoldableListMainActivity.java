package com.duguang.baseanimation.ui.customview.foldablelayout.activity;

import android.os.Bundle;

import com.azcltd.fluffycommons.utils.Views;
import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.customview.foldablelayout.item.PaintingsAdapter;
import com.duguang.baseanimation.ui.customview.foldablelayout.library.FoldableListLayout;

public class FoldableListMainActivity extends FoldableBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_foldable_list);

        FoldableListLayout foldableListLayout = Views.find(this, R.id.foldable_list);
        foldableListLayout.setAdapter(new PaintingsAdapter(this));
    }

}
