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

import android.util.Log;

public class Networking {

	public Networking() {
	}
	
	public boolean login (String user, String pass){
		boolean success = false;
		try {
	        HttpClient client = new DefaultHttpClient();  
	        String postURL = "http://www.newsblur.com/api/login";
	        HttpPost post = new HttpPost(postURL); 
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("username", user));
	            params.add(new BasicNameValuePair("password", pass));
	            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
	            post.setEntity(ent);
	            HttpResponse responsePOST = client.execute(post);  
	            HttpEntity resEntity = responsePOST.getEntity();  
	            if (resEntity != null) {    
	                Log.i("RESPONSE",EntityUtils.toString(resEntity));
	                success = true;
	            }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return success;
	}
}
