package uk.co.ipodling.newsblurb;
/*
 * Technically unneeded as shared prefs go between all activities but makes it look a bit nicer
 * and act a bit more elegantly.
 * */
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class NewsblurbPreferences {
	private boolean firstTime;
    private SharedPreferences newsblurbPreferences;
    private static final String prefsPlace = NewsblurbPreferences.class.getSimpleName();;
	private Editor newsblurbEditor;

	public NewsblurbPreferences(Context context) {
		this.newsblurbPreferences = context.getSharedPreferences(prefsPlace, Activity.MODE_PRIVATE);
        this.newsblurbEditor = newsblurbPreferences.edit();
        if(newsblurbPreferences.contains("firstTime") == false){
            newsblurbEditor.putBoolean("firstTime", true);
            newsblurbEditor.commit();
        }
	}
	
	public boolean getFirstTimePref(){
		return newsblurbPreferences.getBoolean("firstTime", false);
	}
	
	public void setFirstTimePref(boolean set){
		newsblurbEditor.putBoolean("firstTime", set);
        newsblurbEditor.commit();
	}

}
