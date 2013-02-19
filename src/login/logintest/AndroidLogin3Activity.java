package login.logintest;

import net.sourceforge.javajson.JsonException;
import net.sourceforge.javajson.JsonObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AndroidLogin3Activity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	
	Button login, adduser;
	TextView result;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Login button clicked
        login = (Button)findViewById(R.id.loginbutton);
        login.setOnClickListener(this);
        // Adduser button clicked
        adduser = (Button)findViewById(R.id.adduserbutton);
        adduser.setOnClickListener(this);
        
        result = (TextView)findViewById(R.id.resulttext);
    }
    
    public void postLoginData(String inp, String user, String pass) {
    	String baseURL = "http://shrouded-eyrie-7225.herokuapp.com/";
    	String resp = "Major error in AndroidPOSTtest.java!";
    	AndroidPOSTtest myposter = null;
    	if (inp == "login") {
    		myposter = new AndroidPOSTtest(baseURL+"/users/login", user, pass);
    		myposter.execute();
    	}
    	if (inp == "adduser") {
    		myposter = new AndroidPOSTtest(baseURL+"/users/add", user, pass);
    		myposter.execute();
    	}
    	while (!myposter.done) {
    	}
    	resp = myposter.output;
    	
    	int errCode = 0;
    	int count = 0;
    	JsonObject json;
		try {
			json = JsonObject.parse(resp);
			errCode = json.getInt("errCode");
			if (errCode == 1)
				count= json.getInt("count");
		} catch (JsonException e) {
			// TODO Auto-generated catch block\
			resp = "error formatting json response";
			e.printStackTrace();
		}
			
		resp = getOutput(errCode, count, inp);
    	result.setText(resp);
    }
    
    public String getOutput(int errCode, int count, String inp) {
    	if (inp == "adduser") {
    		if (errCode==1) {
    			return "Congrats! You were successfully added!";
    		}
    		else if (errCode==-2) {
    			return "Sorry, that username has been taken!";
    		}
    		else if (errCode==-3) {
    			return "Sorry, the username should be between 1 and 128 characters long!";
    		}
    		else if (errCode==-4) {
    			return "Sorry, the password must be between 0 and 128 characters long!";
    		}
    	}
    	
    	if (inp == "login") {
    		if (errCode==1) {
    			return "Congratulations!  You have logged in " + count + " times!";
    		}
    		else if (errCode==-2) {
    			return "Sorry, you have entered in invalid credentials!";
    		}
    	}
    	return "the server returned an invalid response";
    }
    
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		EditText uname = (EditText)findViewById(R.id.userinput);
    	String username = uname.getText().toString();
    	EditText pword = (EditText)findViewById(R.id.passinput);
    	String password = pword.getText().toString();
    	
		if(arg0 == login){
			postLoginData("login", username, password);
		}
		else if (arg0 == adduser) {
			postLoginData("adduser", username, password);
		}
	}
	
}