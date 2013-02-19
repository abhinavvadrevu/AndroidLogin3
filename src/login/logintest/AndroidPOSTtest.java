package login.logintest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class AndroidPOSTtest  extends AsyncTask<String, Void, String>{
	
	private String url;
	private String user;
	private String pass;
	public boolean done;
	public String output;
	
	
	public AndroidPOSTtest(String myurl, String myuser, String mypass) {
		this.url = myurl;
		this.user = myuser;
		this.pass = mypass;
		this.done = false;
		this.output = "";
	}
	
	private static StringBuilder inputStreamToString(InputStream is) {
    	String line = "";
    	StringBuilder total = new StringBuilder();
    	// Wrap a BufferedReader around the InputStream
    	BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    	// Read response until the end
    	try {
			while ((line = rd.readLine()) != null) { 
				total.append(line); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	// Return full string
    	return total;
    }
	
	@Override
	protected String doInBackground(String... arg0) {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost request = new HttpPost(this.url);
			StringEntity params =new StringEntity("{\"user\":\"" + this.user + 
					"\",\"password\":\"" + this.pass + "\"} ");
			request.addHeader("content-type", "application/x-www-form-urlencoded");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			String str = inputStreamToString(response.getEntity().getContent()).toString();
			this.output = str;
			this.done = true;
			return str;
		// handle response here...
		}catch (Exception ex) {
			return ex.toString();
		// handle exception here
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	
}
