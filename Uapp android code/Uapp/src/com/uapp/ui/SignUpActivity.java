package com.uapp.ui;

import com.uapp.util.HttpConnection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	public EditText name_et;
	public EditText username_et;
	public EditText password_et;
	public EditText retry_password_et;
	public EditText email_et;
	public EditText occupation_et;
	public EditText phonenumber_et;

	public Button sign_up_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		name_et = (EditText)findViewById(R.id.name);
		username_et = (EditText)findViewById(R.id.username);
		password_et = (EditText)findViewById(R.id.password1);
		retry_password_et = (EditText)findViewById(R.id.password2);
		email_et = (EditText)findViewById(R.id.email);
		
		phonenumber_et = (EditText)findViewById(R.id.phonenumber);

		sign_up_btn = (Button)findViewById(R.id.sign_up);
		sign_up_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(password_et.getText().toString().equals(retry_password_et.getText().toString())){
					String response = "";
					String url = "http://10.0.2.2/uapp/signup.php";
					String data = "name=" + name_et.getText().toString() + "&username=" + username_et.getText().toString() + "&password=" + password_et.getText().toString() + "&email=" +email_et.getText().toString() +  "&phonenumber=" + phonenumber_et.getText().toString() + "&isadmin=0";
					System.out.println(data);
					try{
						HttpConnection con = new HttpConnection();
						response = con.httpPostString(url, data);
						System.out.println("rESPPPPPPPPP " + response);

					}catch(Exception ex){
						Log.i("error sigup", ex.toString());

					}
					if(response.equals("1")){
						Toast.makeText(getApplicationContext(), "signup Successful, Please login",Toast.LENGTH_LONG).show();
						Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
						startActivity(i);
						finish();
					}else{
						Toast.makeText(getApplicationContext(), "An error Occured",Toast.LENGTH_LONG).show();
					}
				}else{	
					Toast.makeText(getApplicationContext(), "Password Mismatch",Toast.LENGTH_LONG).show();

				}
			}
		});


	}
}
