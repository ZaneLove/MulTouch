package com.zanelove.multouch;

import com.zanelove.multouch.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ImageView iv;
	private FrameLayout root ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		root = (FrameLayout) findViewById(R.id.container);
		iv = (ImageView) findViewById(R.id.iv);

		root.setOnTouchListener(new View.OnTouchListener() {

			float currentDistance;
			float lastDistance = -1;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_MOVE:

					if (event.getPointerCount()>=2) {

						float offsetX = event.getX(0)-event.getX(1);
						float offsetY = event.getY(0)-event.getY(1);

						currentDistance = (float) Math.sqrt(offsetX*offsetX+offsetY*offsetY);
						
						if (lastDistance<0) {
							lastDistance = currentDistance;
						}else{
							if (currentDistance-lastDistance>5) {
								System.out.println("放大");
								
								FrameLayout.LayoutParams lp = (LayoutParams) iv.getLayoutParams();
								lp.width= (int) (1.1f*iv.getWidth());
								lp.height = (int) (1.1f * iv.getHeight());
								
								iv.setLayoutParams(lp);
								
								lastDistance = currentDistance;
							}else if (lastDistance-currentDistance>5) {
								System.out.println("缩小");
								
								FrameLayout.LayoutParams lp = (LayoutParams) iv.getLayoutParams();
								lp.width=(int) (0.9f*iv.getWidth());
								lp.height=(int) (0.9f*iv.getHeight());
								
								iv.setLayoutParams(lp);
								
								lastDistance = currentDistance;
							}
						}
					}else if(event.getPointerCount()==1){
						FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) iv.getLayoutParams();
						lp.leftMargin = (int) event.getX();
						lp.topMargin = (int) event.getY();
						iv.setLayoutParams(lp);
					}

					break;
				case MotionEvent.ACTION_UP:
					break;
				}
				return true;
			}
		});
	}
}
