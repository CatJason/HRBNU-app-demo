package com.example.jason.mxlake.email.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.jason.mxlake.R;
import com.example.jason.mxlake.email.core.ResolveMail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;


public class ReceiveList extends Activity {

	private static final String SAVE_INFORMATION = "save_information";

	private ListView listview;

	String Title;
	String Date;
	String username;
	String password;
	private ImageView btnCreatEmail;
	private ImageView btnCreatBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);

		setContentView(R.layout.email_listmenu);
		listview = (ListView) findViewById(R.id.my_list);

		btnCreatEmail = (ImageView) findViewById(R.id.btnCreatEmail);
		btnCreatBack = (ImageView) findViewById(R.id.btnCreatBack);

		btnCreatEmail.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent_third=new Intent();
				intent_third.setClass(ReceiveList.this, SendMail.class);
				startActivity(intent_third);
			}
		});

		btnCreatBack.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});


		try {
			MenuList();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void MenuList() throws MessagingException, IOException {

		// sharedpreference读取数据，用split（）方法，分开字符串。
		SharedPreferences pre = getSharedPreferences(SAVE_INFORMATION,
				MODE_PRIVATE);
		String content = pre.getString("save", "");
		String[] Information = content.split(";");
		username = Information[0];
		password = Information[1];

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props); // 取得pop3协议的邮件服务器
		final Store store = session.getStore("pop3");

		// 连接pop.sina.com邮件服务器 //

		new Thread(){
			public void run(){

				try {
					store.connect("pop-mail.outlook.com", username, password); // 返回文件夹对象
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		}.start();
		Folder folder = store.getFolder("INBOX"); // 设置仅读
		folder.open(Folder.READ_ONLY); // 获取信息
		Message message[] = folder.getMessages();

		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();//定义一个List并且将其实例化
		for (int i = 0; i < message.length; i++) {//通过for语句将读取到的邮件内容一个一个的在list中显示出来
			ResolveMail receivemail = new ResolveMail((MimeMessage) message[i]);

			Title = receivemail.getSubject();//得到邮件的标题
			Date = receivemail.getSentDate();//得到邮件的发送时间

			HashMap<String, String> map = new HashMap<String, String>();//定义一个Map.将获取的内容以键值的方式将内容展现
			map.put("title", Title);//显示邮件的标题
			map.put("info", Date);//显示邮件的信息
			list.add(map);

			SimpleAdapter listAdapter = new SimpleAdapter(this, list,R.layout.email_item, new String[] { "title", "info" }, new int[] {
					R.id.title, R.id.info });
			listview.setAdapter(listAdapter);
		}

		folder.close(true);//用好之后记得将floder和store进行关闭
		store.close();

		// Item长按事件。得到Item的值，然后传递给MailDetail的值
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("ID", position);
				intent.setClass(ReceiveList.this, MailDetails.class);
				startActivity(intent);
				return true;
			}

		});
	}
}