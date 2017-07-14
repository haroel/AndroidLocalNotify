package com.game.push;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.util.SparseArray;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * This is our service client, it is the 'middle-man' between the
 * service and any activity that wants to connect to the service
 *
 * Created by hehao on 2017/7/13.
 */
public class ScheduleClient {

	private final static String TAG = "ScheduleClient";

	// singleton
	private static ScheduleClient __instance;
	// The hook into our service
	private ScheduleService mBoundService;
	// The context to start the service in
	private Context mContext;
	// A flag if we are connected to the service or not
	private boolean mIsBound;

	private NotifyDataModel dataModel= null;

	private ScheduleClient() {}

	public static ScheduleClient getInstance()
	{
		if (__instance == null)
		{
			__instance = new ScheduleClient();
		}
		return __instance;
	}

	public void init(Context context)
	{
		mContext = context;

		this.doBindService();
		this.initLocalConfigData();

		final NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
		// close all onshow notifictions
		notificationManager.cancelAll();

		ActivityLifecycleHandler.init( ((Activity)context).getApplication() );
		ActivityLifecycleHandler.get().addListener(new ActivityLifecycleHandler.Listener() {
			@Override
			public void onBecameForeground() {
				notificationManager.cancelAll(); // 关闭所有正在显示的icon
			}
			@Override
			public void onBecameBackground() {
			}
			@Override
			public void onDestroyed() {
				Log.d(TAG,"onDestroyed");
				ScheduleClient.getInstance().doUnbindService();
			}
		});
	}

	/**
	 * Call this to connect your activity to your service
	 */
	private void doBindService() {
		// Establish a connection with our service
		mContext.bindService(new Intent(mContext, ScheduleService.class), mConnection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}
	private void initLocalConfigData()
	{
		dataModel = new NotifyDataModel(mContext);
	}
	
	/**
	 * When you attempt to connect to the service, this connection will be called with the result.
	 * If we have successfully connected we instantiate our service object so that we can call methods on it.
	 */
	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			// This is called when the connection with our service has been established, 
			// giving us the service object we can use to interact with our service.
			mBoundService = ((ScheduleService.ServiceBinder) service).getService();
		}

		public void onServiceDisconnected(ComponentName className) {
			mBoundService = null;
		}
	};
	
	/**
	 * When you have finished with the service call this method to stop it 
	 * releasing your connection and resources
	 */
	public void doUnbindService() {
		if (mIsBound) {
			// Detach our existing connection.
			mContext.unbindService(mConnection);
			mIsBound = false;
		}
	}
	/*
	* 增加一个本地 通知
	* */
	public void addLocalNotify(NotifyObject nObj)
	{
		mBoundService.addNotify( nObj);
		dataModel.putNotifyObject( nObj);
	}
	/*
	* 删除一个通知
	* key: 事件key类型
	* */
	public void removeLocalNotify(int key )
	{
		mBoundService.removeNotify( dataModel.getNotifyObject(key) );
		dataModel.removeNotifyObject(key);
	}
	/*
	* 清除所有通知
	* */
	public void clear()
	{
		SparseArray<NotifyObject> notifyMap = dataModel.getNotifyMap();
		for(int i = 0; i < notifyMap.size(); i++)
		{
			int key = notifyMap.keyAt(i);
			mBoundService.removeNotify( notifyMap.get(key) );
		}
		dataModel.clear();
	}
}