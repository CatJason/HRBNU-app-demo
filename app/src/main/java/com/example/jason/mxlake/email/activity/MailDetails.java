package com.example.jason.mxlake.email.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jason.mxlake.R;
import com.example.jason.mxlake.email.core.ResolveMail;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

public class MailDetails extends Activity {
	private static final String SAVE_INFORMATION = "save_information";
	private TextView text1;
	private TextView text2;
	private TextView text3;
	private TextView text4;
	private ReceiveList ml;

	public void receive() throws Exception {

		// sharedpreference读取数据，用split（）方法，分开字符串。
		SharedPreferences pre = getSharedPreferences(SAVE_INFORMATION,MODE_WORLD_READABLE);
		String content = pre.getString("save", "");
		String[] Information = content.split(";");
		String username = Information[0];
		String password = Information[1];

		Intent intent = getIntent();//得到上一个文件传入的ID号
		Bundle i = intent.getExtras();

		int num = i.getInt("ID");//将得到的ID号传递给变量num

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props);
		// 取得pop3协议的邮件服务器
		Store store = session.getStore("pop3");
		// 连接pop.qq.com邮件服务器
		store.connect("pop-mail.outlook.com", username, password);
		// 返回文件夹对象
		Folder folder = store.getFolder("INBOX");
		// 设置仅读
		folder.open(Folder.READ_ONLY);

		// 获取信息
		Message message[] = folder.getMessages();
		ResolveMail receivemail = new ResolveMail((MimeMessage) message[num]);
		text1.setText(receivemail.getSubject());
		text2.setText(receivemail.getFrom());
		text3.setText(receivemail.getSentDate());
		text4.setText((CharSequence) message[num].getContent().toString());

		folder.close(true);
		store.close();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email_main);

		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);
		text4 = (TextView) findViewById(R.id.text4);

		try {
			receive();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}