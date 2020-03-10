package com.example.jason.mxlake.email.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.jason.mxlake.R;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class SendMail extends Activity {
	private ImageView btnClick;
	private ImageView btnBackClick;
	private EditText txtToAddress;
	private EditText txtSubject;
	private EditText txtContent;
	private static final String SAVE_INFORMATION = "save_information";
	String username;
	String password;

	public void SendMail() throws MessagingException, IOException {
		// 用sharedpreference来获取数值
		SharedPreferences pre = getSharedPreferences(SAVE_INFORMATION,
				MODE_PRIVATE);
		String content = pre.getString("save", "");
		String[] Information = content.split(";");
		username = Information[0];
		password = Information[1];

		// 该部分有待完善！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.sina.com");// 存储发送邮件服务器的信息
		props.put("mail.smtp.auth", "true");// 同时通过验证
		// 基本的邮件会话
		final Session session = Session.getInstance(props);
		session.setDebug(true);// 设置调试标志
		// 构造信息体
		final MimeMessage message = new MimeMessage(session);

		// 发件地址
		InternetAddress fromAddress = null;
		// fromAddress = new InternetAddress("sarah_susan@sina.com");
		fromAddress = new InternetAddress(username);
		message.setFrom(fromAddress);

		// 收件地址
		InternetAddress toAddress = null;
		toAddress = new InternetAddress(txtToAddress.getText().toString());
		message.addRecipient(Message.RecipientType.TO, toAddress);



		new Thread(){
			public void run(){

				try {

					message.setSubject(txtSubject.getText().toString());// 设置信件的标题
					message.setText(txtContent.getText().toString());// 设置信件内容
					message.saveChanges(); // implicit with send()//存储有信息

					// send e-mail message

					Transport transport = null;
					transport = session.getTransport("smtp");
					transport.connect("smtp-mail.outlook.com", username, password);

					transport.sendMessage(message, message.getAllRecipients());
					transport.close();
					System.out.println("邮件发送成功！");
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		}.start();
		// 解析邮件内容


	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.email_send);

		txtToAddress = (EditText) findViewById(R.id.txtToAddress);
		txtSubject = (EditText) findViewById(R.id.txtSubject);
		txtContent = (EditText) findViewById(R.id.txtContent);

		txtToAddress.setText("jason0wzj@gmail.com");
		txtSubject.setText("Hello~");
		txtContent.setText("\n\n\n\n来自梦溪小筑～");

		btnClick = (ImageView) findViewById(R.id.btnSEND);

		btnClick.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					SendMail();
				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		btnBackClick = (ImageView) findViewById(R.id.btnSENDback);

		btnBackClick.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}

		});

	}

}