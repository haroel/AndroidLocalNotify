package com.blundell.com.pushappalarm.ui.phone;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;


import com.blundell.com.pushappalarm.R;
import com.game.push.NotifyObject;
import com.game.push.ScheduleClient;

//import com.instabug.library.Instabug;
//import com.instabug.library.InstabugColorTheme;
//import com.instabug.library.InstabugCustomTextPlaceHolder;
//import com.instabug.library.internal.module.InstabugLocale;
//import com.instabug.library.invocation.InstabugInvocationEvent;
//import com.instabug.library.model.BugCategory;
/**
 * This is the Main Activity of our app.
 * Here we allow the user to select a date,
 * we then set a notification for that date to appear in the status bar
 *  
 * @author paul.blundell
 */
public class MainActivity extends Activity  {
	// This is a handle so that we can call methods on our service
    // This is the date picker used to select the date for our notification
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create a new service client and bind our activity to this service
        ScheduleClient.getInstance().init(this);
//        new Instabug.Builder(this.getApplication(), "fb476023107122004ad9abd30ce55d55")
////                .setInvocationEvent(InstabugInvocationEvent.FLOATING_BUTTON)
//                .build();
    }
	
    /**
     * This is the onClick called from the XML to set a new notification 
     */
    public void onDateSelectedButtonClick(View v){

    	// Create a new calendar set to the date chosen
    	// we set the time to midnight (i.e. the first minute of that day)
    	Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND, 22);
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        ScheduleClient.getInstance().addLocalNotify( new NotifyObject(123,c.getTimeInMillis() , "Halo","测试消息"));
        ScheduleClient.getInstance().addLocalNotify( new NotifyObject(234,c.getTimeInMillis() , "Halo world","测试消息23231"));

        // Notify the user what they just did
    	Toast.makeText(this, "22秒以后将弹出两个通知消息 ", Toast.LENGTH_SHORT).show();
    }


    public void onDateCancelButtonClick(View v){
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        ScheduleClient.getInstance().removeLocalNotify(123);
        // Notify the user what they just did
//    	Toast.makeText(this, "Notification set for: "+ day +"/"+ (month+1) +"/"+ year, Toast.LENGTH_SHORT).show();
    }
    public void clearAll(View v){
//        Instabug.invoke();
        return;
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
//        ScheduleClient.getInstance().clear();
        // Notify the user what they just did
    }
}