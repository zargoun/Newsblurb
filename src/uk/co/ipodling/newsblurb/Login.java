package uk.co.ipodling.newsblurb;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
    final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);
    EditText userText = (EditText)findViewById(R.id.textView1);
    EditText passText = (EditText)findViewById(R.id.textView2);
    private NewsblurbPreferences newsblurbPreferences;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
        newsblurbPreferences = new NewsblurbPreferences(getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	public void onClickContentButton(View v) {
		if (checkBox.isChecked()){
			if(userText.getText() != null && passText.getText() != null){
			newsblurbPreferences.setUser(userText.getText().toString());
			newsblurbPreferences.setPass(passText.getText().toString());
			// log into newsblur
			// if successful intent to move to main activity
			// if not toast error
			} else{
				Toast.makeText(Login.this, "Please enter username and password!", Toast.LENGTH_LONG).show();
			}
		} else {
			// log in without saving passing
			// if successful intent to move to main activity
			// if not toast error
		}
    }

}
