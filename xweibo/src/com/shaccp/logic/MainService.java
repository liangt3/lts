package com.shaccp.logic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class MainService extends Service implements Runnable {

	public static List<Activity> allActivity = new ArrayList<Activity>();
	public static List<Task> allTask = new ArrayList<Task>();


	public boolean isrun = true;
	
	public static Activity getActivityByName(String name){
		
		for(Activity ac:allActivity){
			if(ac.getClass().getName().indexOf(name)>=0){
				return ac;
			}
		}
		
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public static void newTask(Task task) {
		allTask.add(task);
	}

	
	//执行任务，业务逻辑
	public void doTask(Task task) {
		
		Message mess = handler.obtainMessage();
		mess.what = task.getTaskId();
		System.out.println("#####----doTask-----");
		switch (task.getTaskId()) {
		
		case Task.TASK_LOGIN:
			
			break;

		case Task.TASK_GET_TIMELINE:
			
			break;
			
		case Task.TASK_NEW_WEIBO:
			
			break;
		}
		
		handler.sendMessage(mess); //发送消息
		allTask.remove(task);      //执行任务结束，移出任务
		

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isrun) {
			System.out.println("------RUN-----------");
			Task lastTask = null;
			if (allTask.size() > 0) {
				lastTask = allTask.get(0);
				doTask(lastTask);

			}
			
			try{Thread.sleep(1000);}catch(Exception e){};
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//更新UI
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			System.out.println("~~~~Handler~~~~~~~~~");
			switch (msg.what) {
			case Task.TASK_LOGIN:
				IWeiboActivity ia = (IWeiboActivity)getActivityByName("Login");
				ia.refresh(msg.what,msg.obj);
				break;

			default:
				break;
			}
		}
		
	};

}
