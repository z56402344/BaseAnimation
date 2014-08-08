package com.duguang.baseanimation.ui.customview.foldablelayout.activity;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.azcltd.fluffycommons.texts.SpannableBuilder;
import com.azcltd.fluffycommons.utils.Views;
import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.customview.foldablelayout.item.Painting;
import com.duguang.baseanimation.ui.customview.foldablelayout.item.PaintingsAdapter;
import com.duguang.baseanimation.ui.customview.foldablelayout.library.UnfoldableView;
import com.duguang.baseanimation.ui.customview.foldablelayout.library.shading.GlanceFoldShading;
import com.squareup.picasso.Picasso;

public class UnfoldableDetailsMainActivity extends FoldableBaseActivity {

    private ListView mListView;
    private View mListTouchInterceptor;
    private View mDetailsLayout;
    private UnfoldableView mUnfoldableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_foldable_details);

        mListView = Views.find(this, R.id.list_view);
        mListView.setAdapter(new PaintingsAdapter(this));

        mListTouchInterceptor = Views.find(this, R.id.touch_interceptor_view);
        mListTouchInterceptor.setClickable(false);

        mDetailsLayout = Views.find(this, R.id.details_layout);
        mDetailsLayout.setVisibility(View.INVISIBLE);

        mUnfoldableView = Views.find(this, R.id.unfoldable_view);

        Bitmap glance = ((BitmapDrawable) getResources().getDrawable(R.drawable.custom_foldable_unfold_glance)).getBitmap();
        mUnfoldableView.setFoldShading(new GlanceFoldShading(this, glance));

        mUnfoldableView.setOnFoldingListener(new UnfoldableView.SimpleFoldingListener() {
            @Override
            public void onUnfolding(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
                mDetailsLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onUnfolded(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
            }

            @Override
            public void onFoldingBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(true);
            }

            @Override
            public void onFoldedBack(UnfoldableView unfoldableView) {
                mListTouchInterceptor.setClickable(false);
                mDetailsLayout.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mUnfoldableView != null && (mUnfoldableView.isUnfolded() || mUnfoldableView.isUnfolding())) {
            mUnfoldableView.foldBack();
        } else {
            super.onBackPressed();
        }
    }

    public void openDetails(View coverView, Painting painting) {
        ImageView image = Views.find(mDetailsLayout, R.id.details_image);
        TextView title = Views.find(mDetailsLayout, R.id.details_title);
        TextView description = Views.find(mDetailsLayout, R.id.details_text);

        Picasso.with(this).load(painting.getImageId()).into(image);
        title.setText(painting.getTitle());

        SpannableBuilder builder = new SpannableBuilder(this);
        builder
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append(R.string.year).append(": ")
                .clearStyle()
                .append(painting.getYear()).append("\n")
                .createStyle().setFont(Typeface.DEFAULT_BOLD).apply()
                .append(R.string.location).append(": ")
                .clearStyle()
                .append(painting.getLocation());
        description.setText(builder.build());

        mUnfoldableView.unfold(coverView, mDetailsLayout);
    }

}
