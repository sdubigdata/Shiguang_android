package team.ideart.shiguang_app.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        timeLine = (TimeLine)getIntent().getExtras().get("timeLine");
        findViews();
    }

    public void findViews(){
        backgroudLayout = (BackgroundLayout) findViewById(R.id.backgroud_layout);
        backgroudLayout.setImageView(getBitmap(Uri.parse(Host.SERVER + timeLine.getResUrl())));
        bgLayout = (RelativeLayout) findViewById(R.id.bgLayout);
        bgLayout.setBackgroundColor(timeLine.getColor());

        leftBtn = (ImageView) findViewById(R.id.left_btn);

        leftBtn.setOnClickListener(this);

        picOpView = (ImageView) findViewById(R.id.iv_add_img);
        picOpView.setOnClickListener(this);

        editText = (TextView) findViewById(R.id.edit_text);
        editText.setText(timeLine.getContent());
    }

    @Override
    public void onClick(View v) {
        if (v == leftBtn){
            this.finish();
        }
    }

    private Bitmap getBitmap(Uri uri){
        try{
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
            return bitmap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
