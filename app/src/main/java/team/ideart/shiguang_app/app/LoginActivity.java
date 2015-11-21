package team.ideart.shiguang_app.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;
import team.ideart.shiguang_app.app.R;
import team.ideart.shiguang_app.app.utils.Host;

/**
 * Created by yestin on 2015/11/21.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView loginBtn;
    private EditText userNameT,passwordT;
    private LinearLayout containerLayout;

    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        if(StaticHolder.getToken(this) != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            LoginActivity.this.finish();
        }
    }

    private void findViews(){

        userNameT = (EditText) findViewById(R.id.tv_username);
        passwordT = (EditText) findViewById(R.id.tv_password);

        loginBtn = (TextView) findViewById(R.id.tv_login);
        loginBtn.setOnClickListener(this);

        containerLayout = (LinearLayout) findViewById(R.id.content_layout);
    }


    @Override
    public void onClick(View v) {
        if(v ==  loginBtn){
            RequestParams params = new RequestParams();
            params.put("username", userNameT.getText());
            params.put("password", passwordT.getText());
            client.post(this, Host.SERVER + "/login", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArray
                    try {
                        if (response.getInt("code") == 0) {
                            String path = response.getString("token");
                            StaticHolder.setToken(LoginActivity.this,path);
                            Log.i("token", path);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();
                        } else {
                            Log.i("error", "code:"+response.getInt("code"));
                            YoYo.with(Techniques.Shake).duration(500).playOn(containerLayout);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
