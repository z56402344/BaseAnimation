package com.duguang.baseanimation.ui.tab.viewflow;

import android.app.Activity;
import android.os.Bundle;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.ui.tab.viewflow.library.TitleFlowIndicator;
import com.duguang.baseanimation.ui.tab.viewflow.library.ViewFlow;

public class AsyncDataFlowActivity extends Activity {

	private ViewFlow viewFlow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.async_title);
		setContentView(R.layout.activity_tab_viewflow_title_layout);

		viewFlow = (ViewFlow) findViewById(R.id.viewflow);
		AsyncAdapter adapter = new AsyncAdapter(this);
		viewFlow.setAdapter(adapter, adapter.getTodayId());
		
		TitleFlowIndicator indicator = (TitleFlowIndicator) findViewById(R.id.viewflowindic);
		indicator.setTitleProvider(adapter);
		
		viewFlow.setFlowIndicator(indicator);
    }
    
}
