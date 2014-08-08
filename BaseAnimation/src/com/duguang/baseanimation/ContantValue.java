package com.duguang.baseanimation;

import com.duguang.baseanimation.R;
import com.duguang.baseanimation.R.anim;

/**
 * 常量页面
 * 
 * @author duguang 博客地址:http://blog.csdn.net/duguang77
 */
public interface ContantValue {

	// ---------------------------Main页面------------------------------------
	String[] mainItem = { "简单动画", "复杂动画", "Splash引导动画", "Flip效果合集(折叠)",
			"NineOld", "Property效果", "高仿动画效果", "Listview效果集合", "自定义控件效果集合",
			"页面滑动切换效果集合", "画笔绘制效果集合", "手势库效果集合", "Notification效果集合" };

	// ---------------------------简单动画页面------------------------------------

	int[] simple = { R.anim.a1, R.anim.a2, R.anim.alpha, R.anim.alpha_rotate,
			R.anim.alpha_scale, R.anim.alpha_scale_rotate,
			R.anim.alpha_scale_translate, R.anim.alpha_scale_translate_rotate,
			R.anim.alpha_translate, R.anim.alpha_translate_rotate,
			R.anim.drawroll_ani_in, R.anim.drawroll_ani_out, R.anim.fade,
			R.anim.gallery_in, R.anim.hold, R.anim.hyperspace_in,
			R.anim.hyperspace_out, R.anim.left_in, R.anim.left_out,
			R.anim.my_alpha_action, R.anim.my_scale_action,
			R.anim.myanimation_set, R.anim.myown_design, R.anim.push_left_in,
			R.anim.push_left_out, R.anim.push_up_in, R.anim.push_up_out,
			R.anim.right_in, R.anim.right_out, R.anim.rotate, R.anim.scale,
			R.anim.scale_rotate, R.anim.scale_translate,
			R.anim.scale_translate_rotate, R.anim.slide_down_out,
			R.anim.slide_left, R.anim.slide_right, R.anim.slide_up_in,
			R.anim.translate, R.anim.translate_rotate, R.anim.wave_scale,
			R.anim.zoom_enter, R.anim.zoom_exit };

	String[] simpleName = { "a1", "a2", "alpha", "alpha_rotate", "alpha_scale",
			"alpha_scale_rotate", "alpha_scale_translate",
			"alpha_scale_translate_rotate", "alpha_translate",
			"alpha_translate_rotate", "drawroll_ani_in", "drawroll_ani_out",
			"fade", "gallery_in", "hold", "hyperspace_in", "hyperspace_out",
			"left_in", "left_out", "my_alpha_action", "my_scale_action",
			"myanimation_set", "myown_design", "push_left_in", "push_left_out",
			"push_up_in", "push_up_out", "right_in", "right_out", "rotate",
			"scale", "scale_rotate", "scale_translate",
			"scale_translate_rotate", "slide_down_out", "slide_left",
			"slide_right", "slide_up_in", "translate", "translate_rotate",
			"wave_scale", "zoom_enter", "zoom_exit" };

	// ---------------------------复杂动画页面------------------------------------

	int[] complex = { R.anim.block_move_right, R.anim.grow_from_top,
			R.anim.in_scale_x, R.anim.in_translate_top,
			R.anim.out_translate_top, R.anim.refreshable_list_rotate,
			R.anim.shrink_from_bottom, R.anim.small_2_big,
			R.anim.appear_bottom_right_in, R.anim.appear_bottom_right_out,
			R.anim.appear_top_left_in, R.anim.appear_top_left_out,
			R.anim.disappear_bottom_right_in,
			R.anim.disappear_bottom_right_out, R.anim.disappear_top_left_in,
			R.anim.disappear_top_left_out, R.anim.fade_in, R.anim.fade_out,
			R.anim.flip_horizontal_in, R.anim.flip_horizontal_out,
			R.anim.flip_vertical_in, R.anim.flip_vertical_out,
			R.anim.unzoom_in, R.anim.unzoom_out };

	String[] animName = { "block_move_right", "grow_from_top", "in_scale_x",
			"in_translate_top", "out_translate_top", "refreshable_list_rotate",
			"shrink_from_bottom", "small_2_big", "appear_bottom_right_in",
			"appear_bottom_right_out", "appear_top_left_in",
			"appear_top_left_out", "disappear_bottom_right_in",
			"disappear_bottom_right_out", "disappear_top_left_in",
			"disappear_top_left_out", "fade_in", "fade_out",
			"flip_horizontal_in", "flip_horizontal_out", "flip_vertical_in",
			"flip_vertical_out", "unzoom_in", "unzoom_out" };

	// ---------------------------SplashMain页面------------------------------------
	String[] splashName = { "Zaker风格", "镜头风格_由远至近", "硬币翻转风格", "3D翻转效果",
			"ViewPager引导风格", "中心打开式", "ShowTime2秒式", "ShowTime2秒式(第二种实现方法)",
			"ViewPage滑动背景渐变", "顶部带标记的切换页面" };

	// ---------------------------Imitate高仿效果页面------------------------------------
	String[] imitateName = { "火车票出票动画", "加入购物车动画(曲线轨迹)", "优酷导航菜单", "3D图片浏览",
			"下拉选择框", "腾讯,360安全雷达效果", "计算器", "温度表盘效果", "瀑布流效果", "高仿360首页",
			"滚轮效果_时间And地方", "浮动在首页的小插件效果", "仿微信页面", "模拟心跳", "上拉抽屉样式", "淘宝菜单2",
			"淘宝菜单", "支付宝声波动画(水波)","水波Loding效果" };

	// ---------------------------ListView页面------------------------------------
	String[] listViewName = { "上下拉刷新的ListView", "滑动删除条目的ListView",
			"通讯录效果一ListView", "通讯录效果二ListView", "左侧按钮删除条目ListView",
			"渐变后删除条目ListView", "GoogleCardsListView效果", "GridView效果",
			"AppearanceListView效果", "Drag And Drop效果", "Swipe to Dismiss效果",
			"Animate Dismiss效果", "Expand listItems效果", "ListView中的item随意拖动",
			"翻译效果的ListView", "八种横向切换+Listview效果" };

	// ---------------------------自定义控件合集Main页面------------------------------------
	String[] customName = { "自定义FramLayout文字飞入飞出效果", "在任意控件上增加带数字的气泡效果",
			"圆形旋转菜单(自定义Layout+ImageView)", "ListView循环更换标题效果",
			"自定义Gallery+ListView效果", "自定义LinearLayout侧边栏效果", "自定义横向ScrollView",
			"自定义ProgressBar(4种样式)", "自定义加载LodingDialog样式", "自定义PopWindow",
			"多种DiaLog大集合", "Y轴旋转切换图片(自定义Relayout)", "菜单式PopWindow",
			"自定义的ImageView效果", "ViewPager+ScrollView+ListView效果", "自定义滑动开关按钮",
			"仿小米启动效果", "文字随机消失效果", "很炫的折叠打开效果", "书翻页效果" };

	// ---------------------------SplashMain页面------------------------------------
	String[] tabName = { "ViewPager实现页面滑动切换", "横向ScrollView+Viewpager滑动切换",
			"ViewPager+GridView滑动切换", "横向拖动TabHost", "横向选项卡+菜单效果",
			"八种横向切换+Listview效果", "顶部带标记的切换页面", "顶部带标题的切换页面", "顶部带标题内容不同的切换页面",
			"顶部标题是日期的切换页面" };

	// ---------------------------SplashMain页面------------------------------------
	String[] CanvasName = { "用画笔绘制奥运五环", "绘制折线、柱状、圆饼图表" };

}
