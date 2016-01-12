package com.example.fengge.shuttlebus;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;


public class BookingTicketActivity extends Activity {

	private GestureDetector mGestureDetector;
	private OnGestureListener myOnGestureListener;
	private AutoCompleteTextView routeAutoCompleteTextView;
	private AutoCompleteTextView stopAutoCompleteTextView;
	float x1 = 0;
	float x2 = 0;
	float y1 = 0;
	float y2 = 0;

	// TODO mock data
	private String[] routeAutoStrings = new String[] { "123", "321", "133" };
	private String[] stopAutoStrings = new String[] { "123", "321", "133" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.booking_ticket);
		initView();
//		initEvent();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 继承了Activity的onTouchEvent方法，直接监听点击事件
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// 当手指按下的时候
			x1 = event.getX();
			y1 = event.getY();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// 当手指离开的时候
			x2 = event.getX();
			y2 = event.getY();
			if (y1 - y2 > 50) {
				Toast.makeText(BookingTicketActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
			} else if (y2 - y1 > 50) {
				Toast.makeText(BookingTicketActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
			} else if (x1 - x2 > 50) {
				Toast.makeText(BookingTicketActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
			} else if (x2 - x1 > 50) {
				Toast.makeText(BookingTicketActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
			}
		}
		return super.onTouchEvent(event);
	}

	private void initView() {
		routeAutoCompleteTextView = (AutoCompleteTextView) this.findViewById(R.id.route_auto_text);
		ArrayAdapter<String> routeAdapter = new ArrayAdapter<String>(BookingTicketActivity.this,
				android.R.layout.simple_dropdown_item_1line, routeAutoStrings);
		routeAutoCompleteTextView.setAdapter(routeAdapter);

		stopAutoCompleteTextView = (AutoCompleteTextView) this.findViewById(R.id.stop_auto_text);
		ArrayAdapter<String> stopAdapter = new ArrayAdapter<String>(BookingTicketActivity.this,
				android.R.layout.simple_dropdown_item_1line, stopAutoStrings);
		stopAutoCompleteTextView.setAdapter(stopAdapter);
	}

}