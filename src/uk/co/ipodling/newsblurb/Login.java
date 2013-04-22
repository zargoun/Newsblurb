package uk.co.ipodling.newsblurb;
/*
 * Not perfect, throws some errors, but seems to work itself out at the end of the day, one handler error is concerning but executes as expected on my phone
 * */
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Looper;
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
// must authenticate before you can make calls to endpoints
public class Login extends Activity {
    CheckBox checkBox;
    EditText userText;
    EditText passText;
    private NewsblurbPreferences newsblurbPreferences;
    ParseTheJSON parser;
    boolean loggedin = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
		userText = (EditText)findViewById(R.id.editText1);
		passText = (EditText)findViewById(R.id.editText2);
        newsblurbPreferences = new NewsblurbPreferences(getApplicationContext());
        parser = new ParseTheJSON();
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	final CountDownLatch latch = new CountDownLatch(1); // wait for thread
            	final String user = userText.getText().toString();
        		final String pass = passText.getText().toString();
        		if(user.matches("")){
    				Toast.makeText(Login.this, "Please enter username and password!", Toast.LENGTH_LONG).show();
        		} else {
        			if (checkBox.isChecked()){
        				newsblurbPreferences.setUser(userText.getText().toString());
        				newsblurbPreferences.setPass(passText.getText().toString());
        				new Thread(new Runnable(){     //for message sending
                			@Override
                			public void run(){
                				JSONObject response = parser.gettheJSONLogin(user,  pass);
            	                Log.i("object contains: ",response.toString());
                	        		 try {
 										if(response.getBoolean("authenticated") == true){
 											Log.d("got it!", "i got it!");
 											loggedin = true;
 											latch.countDown(); //indicates thread has ended
 										}else if (response.getBoolean("authenticated") == false){
 											loggedin = false;
 		                    				latch.countDown();
 		                				}
 									} catch (JSONException e) {
 										e.printStackTrace();
 										latch.countDown();
 									}
                				}
                			}).start(); //

            			} else {
            				new Thread(new Runnable(){     //for message sending
                    			@Override
                    			public void run(){
                    				JSONObject response = parser.gettheJSONLogin(user,  pass);
                	                Log.i("object contains: ",response.toString());
                    	        		 try {
     										if(response.getBoolean("authenticated") == true){
     											Log.d("got it!", "i got it!");
     											loggedin = true;
     											latch.countDown();
     										}else if (response.getBoolean("authenticated") == false){
     											loggedin = false;
     		                    				latch.countDown();
     		                				}
     									} catch (JSONException e) {
    		                    				Toast.makeText(Login.this, "Error, please try again!", Toast.LENGTH_LONG).show();
     										e.printStackTrace();
     										latch.countDown();
     									}
                    				}
                    			}).start();
        			}
        		}//
        		try {
					latch.await(); //waits for http post response to finish, should probably put a timeout on it really
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		if (loggedin == true){
        	        Intent returnIntent = new Intent();
        	        returnIntent.putExtra("result", true);
        	        setResult(RESULT_OK,returnIntent);     
 				finish(); //herp derp, finish not intent because i don't want a new instance *headdesk*
        		}else{
     				Toast.makeText(Login.this, "Error, please try again!", Toast.LENGTH_LONG).show();
        		}
        		//do stuff for finish
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
