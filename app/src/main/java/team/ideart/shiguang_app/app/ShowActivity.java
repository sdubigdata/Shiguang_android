package team.ideart.shiguang_app.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import cz.msebera.android.httpclient.Header;
import team.ideart.shiguang_app.app.component.BackgroundLayout;
import team.ideart.shiguang_app.app.component.ColorPickerLayout;
import team.ideart.shiguang_app.app.entity.TimeLine;
import team.ideart.shiguang_app.app.utils.Host;

/**
 * Created by yestin on 2015/11/20.
 */
public class ShowActivity extends Activity implements View.OnClickListener{

    public RelativeLayout bgLayout;

    public BackgroundLayout backgroudLayout;

    public ImageView colorPickBtn,rightBtn,leftBtn;

    public TextView editText;

    public ImageView picOpView;

    private int currentColor =  0x2a1ABC9C;

    private File filePath;

    private TimeLine timeLine;

    AsyncHttpClient client = new AsyncHttpClient();
    Handler mHandler;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        timeLine = (TimeLine)getIntent().getExtras().get("timeLine");
        findViews();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        backgroudLayout.setImageView(bitmap);
                        break;
                }
            }
        };
    }

    public void findViews(){
        backgroudLayout = (BackgroundLayout) findViewById(R.id.backgroud_layout);
        Log.v("tag",""+Host.SERVER + timeLine.getResUrl());
        new Thread(){
            public void run(){
                bitmap = returnBitMap(Host.SERVER + timeLine.getResUrl());
                Message message = new Message();
                message.what = 1;
                mHandler.sendMessage(message);
            }
        }.start();
        bgLayout = (RelativeLayout) findViewById(R.id.bgLayout);
        bgLayout.setBackgroundColor(timeLine.getColor());

        leftBtn = (ImageView) findViewById(R.id.left_btn);

        leftBtn.setOnClickListener(this);

        editText = (TextView) findViewById(R.id.edit_text);
        editText.setText(timeLine.getContent());
    }

    @Override
    public void onClick(View v) {
        if (v == leftBtn){
            this.finish();
        }
    }
    public Bitmap returnBitMap(String url){
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
