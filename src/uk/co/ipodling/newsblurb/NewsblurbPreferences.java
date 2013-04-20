package uk.co.ipodling.newsblurb;
/*
 * Technically unneeded as shared prefs go between all activities but makes it look a bit nicer
 * and act a bit more elegantly.
 * Is this going to work? Maybe...
 * */
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class NewsblurbPreferences {
    private SharedPreferences newsblurbPreferences;
    private static final String prefsPlace = NewsblurbPreferences.class.getSimpleName();;
	private Editor newsblurbEditor;

	public NewsblurbPreferences(Context context) {
		this.newsblurbPreferences = context.getSharedPreferences(prefsPlace, Activity.MODE_PRIVATE);
        this.newsblurbEditor = newsblurbPreferences.edit();
        newsblurbEditor.clear(); //clear so as if first time run
        newsblurbEditor.commit();
	}//
//
	public String getUser(){
		String user = null;
		if (newsblurbPreferences.contains("user")){
		user = newsblurbPreferences.getString("user", "");
		} //
		return user;
	}
	
	public String getPass(){ //obviously this needs encrypting down the road
		String pass = null;
		if (newsblurbPreferences.contains("pass")){
		pass = newsblurbPreferences.getString("pass", "");
		}
		return pass;	
	}
	
	public void setUser(String user){
		newsblurbEditor.putString("user", user);
        newsblurbEditor.commit();
	}
	
	public void setPass(String pass){
		newsblurbEditor.putString("pass", pass);
        newsblurbEditor.commit();
	}
	
	public boolean contains(String s){
		boolean isItThere = false;
		if(newsblurbPreferences.contains(s)){
			isItThere = true;
		}
		return isItThere;
	}

}
