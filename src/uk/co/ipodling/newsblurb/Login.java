package uk.co.ipodling.newsblurb;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
            	if (checkBox.isChecked()){
        			if(userText.getText() != null && passText.getText() != null){
        			newsblurbPreferences.setUser(userText.getText().toString());
        			newsblurbPreferences.setPass(passText.getText().toString());
        			// log into newsblur
        			// if successful intent to move to main activity
        			// if not toast error
        			Intent i = new Intent(getApplicationContext(), MainActivity.class);
        			startActivity(i);
        			} else{
        				Toast.makeText(Login.this, "Please enter username and password!", Toast.LENGTH_LONG).show();
        			}
        		} else {
        			// log in without saving passing
        			// if successful intent to move to main activity
        			// if not toast error
        			Intent i = new Intent(getApplicationContext(), MainActivity.class);
        			startActivity(i);
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
