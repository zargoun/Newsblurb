package uk.co.ipodling.newsblurb;
/*
 * No, not the salt
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieStore;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;

public class ParseTheJSON {
	 	static InputStream input = null;
	    static JSONObject theObject = null;
	    static String stringey = "";
	    static BasicCookieStore mCookieStore   = new BasicCookieStore();   
	    BasicHttpContext mHttpContext = new BasicHttpContext();
	    

	public ParseTheJSON() {
		mHttpContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore); //i never thought i would hate cookies
	
		// Nothing to see here, move along
	}
	
	public JSONObject gettheJSONLogin(String user, String pass) {
		 //make post request
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
	        String postURL = "http://www.newsblur.com/api/login";
            HttpPost post = new HttpPost(postURL);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", user));
            params.add(new BasicNameValuePair("password", pass));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse resEntity = httpClient.execute(post);
            HttpEntity httpEntity = resEntity.getEntity();
     
           
            input = httpEntity.getContent(); 
      //      Log.i("RESPONSE",input.toString()); //content can only be consumed once apparently
    		//mHttpContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore); //i never thought i would hate cookies
    		List<Cookie> cookies = httpClient.getCookieStore().getCookies();
            if (cookies.isEmpty()) {
            	 //Log.e("RESPONSEHEADER","nothing here brah");
            } else {
            	mCookieStore.addCookie(cookies.get(0));
            	Log.i("cookieCount",Integer.toString(cookies.size()));
                for (int i = 0; i < cookies.size(); i++) {
                   Log.e("responseHeader",cookies.get(i).toString());
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    input, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            input.close();
            stringey = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting: " + e.toString());
        }
 
        try {// makeJSON Object from input string (hopefully)
            theObject = new JSONObject(stringey);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing: " + e.toString());
        }
		mHttpContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore); //i never thought i would hate cookies
        return theObject; // return JSON object
 
    }
	
	public String[] getSidebarFeed(){
		String[] returnString = null;

		JSONObject forSidebar = null;
		mHttpContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore); //i never thought i would hate cookies
		  try {
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            httpClient.setCookieStore(mCookieStore);
		        String getURL = "http://www.newsblur.com/reader/feeds";
	            HttpGet get = new HttpGet(getURL);
	            HttpResponse resEntity = httpClient.execute(get);
	            //HttpEntity httpEntity = resEntity.getEntity();
	            InputStream is = resEntity.getEntity().getContent();
	            Header[] s = resEntity.getHeaders("newsblur_sessionid"); //searching for the evasive cookie
	            //Header t = resEntity.getHeaders("newsblur_sessionid");
	           // mCookieStore.add();
	            Log.d("Header:", is.toString());
	            input = is; 
	            Log.i("RESPONSE",input.toString()); //content can only be consumed once apparently
	            mHttpContext.setAttribute(ClientContext.COOKIE_STORE, mCookieStore); //yes this is copy pasted all over, no idea what it does, kind of hoped it would result in a cookie
	    		List<Cookie> cookies = httpClient.getCookieStore().getCookies();
	            if (cookies.isEmpty()) {
	                System.out.println("None");
	            } else {
	                for (int i = 0; i < cookies.size(); i++) {
	                    System.out.println("- " + cookies.get(i).toString());
	                }
	            }
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	        try {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    input, "iso-8859-1"), 8);
	            StringBuilder sb = new StringBuilder();
	            String line = null;
	            while ((line = reader.readLine()) != null) {
	                sb.append(line + "\n");
	            }
	            input.close();
	            stringey = sb.toString();
	        } catch (Exception e) {
	            Log.e("Buffer Error", "Error converting: " + e.toString());
	        }
	 
	        try {// makeJSON Object from input string (hopefully)
	        	Log.d("String = ", stringey);
	            forSidebar = new JSONObject(stringey);
	            JSONArray thing = null;
	    		try {
	    			
	    			thing = forSidebar.getJSONArray("folders");
	    			String[] stringList = new String[thing.length()];
	    	for(int i = 0; i < thing.length(); i++){//
	    			
	    			thing.get(i);
	    			String thing3 = forSidebar.getJSONObject("feeds").getJSONObject(Integer.toString(thing.getInt(i))).getString("feed_title");
	    			stringList[i]=thing3;
	    			Log.d("TESTING",thing3);
	    	}
	    	returnString = stringList;
	    	
	    		} catch (JSONException e) {
	    			// TODO Auto-generated catch block
	    			Log.d("thingError",e.toString());
	    		}
	        } catch (JSONException e) {
	            Log.e("JSON Parser", "Error parsing: " + e.toString());
	        }
	        
	 
	        return returnString; // return JSON object
	}

}
