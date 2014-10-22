package com.example.meituandemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

import com.example.meituandemo.MyScrollView.OnScrollListener;
/**
 * ���͵�ַ:http://blog.csdn.net/xiaanming
 * 
 * @author xiaanming
 *
 */
public class MainActivity extends Activity implements OnScrollListener{
	private static String TAG = MainActivity.class.getSimpleName();
	private MyScrollView myScrollView;
	private LinearLayout mBuyLayout;
	private WindowManager mWindowManager;
	private int screenWidth;
	private static View suspendView;
	private static WindowManager.LayoutParams suspendLayoutParams;
	private int buyLayoutHeight;
	private int myScrollViewTop;

	private int buyLayoutTop;
	
	private ViewStub mViewStub;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myScrollView = (MyScrollView) findViewById(R.id.scrollView);
		mBuyLayout = (LinearLayout) findViewById(R.id.buy);
		
		myScrollView.setOnScrollListener(this);
		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		screenWidth = mWindowManager.getDefaultDisplay().getWidth();  
	}

	@Override  
	public void onWindowFocusChanged(boolean hasFocus) {  
	    super.onWindowFocusChanged(hasFocus);  
	    if(hasFocus){
	    	buyLayoutHeight = mBuyLayout.getHeight();
	    	buyLayoutTop = mBuyLayout.getTop();
	    	
	    	myScrollViewTop = myScrollView.getTop();
	    }
	} 

	@Override
	public void onScroll(int scrollY) {
		if(scrollY >= buyLayoutTop){
			// if(suspendView == null){
			// showSuspend();
			// }
			myShowSuspend();
		}else if(scrollY <= buyLayoutTop + buyLayoutHeight){
			// if(suspendView != null){
			// removeSuspend();
			// }
			myRemoveSuspend();
		}
	}


	private void myShowSuspend() {
		if (suspendView == null) {
			mViewStub = (ViewStub) findViewById(R.id.buy_title);
			suspendView = mViewStub.inflate();
		}
		// if (suspendView != null) {
		// Log.d(TAG, "myShowSuspend");
		// suspendView.setVisibility(View.VISIBLE);
		// }
		final int vFlag = suspendView.getVisibility();
		if (vFlag == View.GONE) {
			Log.d(TAG, "myShowSuspend");
			suspendView.setVisibility(View.VISIBLE);
		}
	}

	private void myRemoveSuspend() {
		if (suspendView != null) {
			// Log.d(TAG, "myRemoveSuspend");
			// suspendView.setVisibility(View.GONE);

			final int vFlag = suspendView.getVisibility();
			if (vFlag == View.VISIBLE) {
				Log.d(TAG, "myRemoveSuspend");
				suspendView.setVisibility(View.GONE);
			}
		}
	}
	private void showSuspend(){
		if(suspendView == null){
			suspendView = LayoutInflater.from(this).inflate(R.layout.buy_layout, null);
			if(suspendLayoutParams == null){
				suspendLayoutParams = new LayoutParams();
				suspendLayoutParams.type = LayoutParams.TYPE_PHONE;  
				suspendLayoutParams.format = PixelFormat.RGBA_8888;  
				suspendLayoutParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL  
	                     | LayoutParams.FLAG_NOT_FOCUSABLE;  
				suspendLayoutParams.gravity = Gravity.TOP;  
				suspendLayoutParams.width = screenWidth;
				suspendLayoutParams.height = buyLayoutHeight;  
				suspendLayoutParams.x = 0;  
				suspendLayoutParams.y = myScrollViewTop;  
			}
		}
		
		mWindowManager.addView(suspendView, suspendLayoutParams);
	}
	
	
	private void removeSuspend(){
		if(suspendView != null){
			mWindowManager.removeView(suspendView);
			suspendView = null;
		}
	}

}
