package com.blundell.com.pushappalarm.ui.phone;

import android.app.Activity;
import android.os.Bundle;

import com.blundell.com.pushappalarm.R;

/**
 * This is the activity that is started when the user presses the notification in the status bar
 * @author paul.blundell
 */
public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
	}
	
}
