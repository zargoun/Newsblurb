package uk.co.ipodling.newsblurb;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends Activity {
    CheckBox checkBox;
    EditText userText;
    EditText passText;
    private NewsblurbPreferences newsblurbPreferences;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
		userText = (EditText)findViewById(R.id.editText1);
		passText = (EditText)findViewById(R.id.editText2);
        newsblurbPreferences = new NewsblurbPreferences(getApplicationContext());
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	String user = userText.getText().toString();
        		String pass = passText.getText().toString();
        		if(user.matches("") || pass.matches("")){
    				Toast.makeText(Login.this, "Please enter username and password!", Toast.LENGTH_LONG).show();
        		} else {
        			if (checkBox.isChecked()){
        				newsblurbPreferences.setUser(userText.getText().toString());
        				newsblurbPreferences.setPass(passText.getText().toString());
        				try {
        			        HttpClient client = new DefaultHttpClient();  
        			        String postURL = "http://www.newsblur.com/api";
        			        HttpPost post = new HttpPost(postURL); 
        			            List<NameValuePair> params = new ArrayList<NameValuePair>();
        			            params.add(new BasicNameValuePair("user", userText.getText().toString()));
        			            params.add(new BasicNameValuePair("pass", passText.getText().toString()));
        			            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
        			            post.setEntity(ent);
        			            HttpResponse responsePOST = client.execute(post);  
        			            HttpEntity resEntity = responsePOST.getEntity();  
        			            if (resEntity != null) {    
        			                Log.i("RESPONSE",EntityUtils.toString(resEntity));
        	        				finish(); //herp derp, finish not intent because i don't want a new instance *headdesk*
        			            }
        			    } catch (Exception e) {
        			        e.printStackTrace();
        			    }
        				// log into newsblur
        				// if successful intent to move to main activity
        				// if not toast error
            			} else {
        				// log in without saving passing
        				// if successful intent to move to main activity
        				// if not toast error
            				try {
        			        HttpClient client = new DefaultHttpClient();  
        			        String postURL = "http://www.newsblur.com/api";
        			        HttpPost post = new HttpPost(postURL); 
        			            List<NameValuePair> params = new ArrayList<NameValuePair>();
        			            params.add(new BasicNameValuePair("user", userText.getText().toString()));
        			            params.add(new BasicNameValuePair("pass", passText.getText().toString()));
        			            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
        			            post.setEntity(ent);
        			            HttpResponse responsePOST = client.execute(post);  
        			            HttpEntity resEntity = responsePOST.getEntity();  
        			            if (resEntity != null) {    
        			                Log.i("RESPONSE",EntityUtils.toString(resEntity));
        	        				finish(); //herp derp, finish not intent because i don't want a new instance *headdesk*
        			            }
        			    } catch (Exception e) {
        			        e.printStackTrace();
        			    }        			
            		}
        		}
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}


}
