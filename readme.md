安卓本地消息通知。原生实现

这是一个测试项工程，包含了完整的Android Studio工程代码。 


GET STARTED

1. 消息代码文件在 ` /PushAppAlarm/app/src/main/java/com/game/push ` 路径下。
2. 修改 自己工程下的 AndroidManifest.xml 文件，增加下面配置并按要求修改
	 >         
	 	 <!--本地消息配置 BEGIN-->
        <service android:name="com.game.push.ScheduleService"  />
        <service android:name="com.game.push.NotifyService" android:process=":service"/>
        		<!--通知栏界面图标-->
        <meta-data android:name="com.game.push.gameicon" android:resource="@mipmap/ic_launcher" />
        		<!--通知栏小图标-->
        <meta-data android:name="com.game.push.notifyicon" android:resource="@mipmap/ic_launcher" />
        		<!--App名称-->
        <meta-data android:name="com.game.push.title" android:value="@string/app_name" />
        <!--本地消息配置 END-->3. 在Activity onCreate方法初始化
	
	>      public void onCreate(Bundle savedInstanceState) {
		        super.onCreate(savedInstanceState);
		        setContentView(R.layout.activity_main);
		        // Create a new service client and bind our activity to this service
		        ScheduleClient.getInstance().init(this);
   		  }
4. 增加、删除本地消息，key表示一种消息类型id，由业务层自己维护
	 > 
	 		ScheduleClient.getInstance().addLocalNotify( new NotifyObject( {key} , {time} , {title}, {msg}));
	      // 删除
	       ScheduleClient.getInstance().removeLocalNotify({key});
	       
收到消息以后，点击进入app，导航栏的通知将自动清除。
	      
