package team.ideart.shiguang_app.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DialerFilter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import team.ideart.shiguang_app.app.component.BackgroundLayout;
import team.ideart.shiguang_app.app.component.ColorPickerLayout;
import team.ideart.shiguang_app.app.utils.Host;

/**
 * Created by yestin on 2015/11/20.
 */
public class AddActivity extends Activity implements View.OnClickListener{

    private static int INTENT_REQUEST_GET_IMAGES = 1,INTENT_REQUEST_SHOT_IMAGES=2,
            REQUEST_CODE_TAKE_VIDEO=3,INTENT_REQUEST_RECORD_SOUND=4;

    public RelativeLayout bgLayout;

    public BackgroundLayout backgroudLayout;

    public ImageView colorPickBtn,rightBtn,leftBtn;

    public EditText editText;

    public ImageView picOpView,videoOpView,soundOpView;

    private int currentColor =  0x2a1ABC9C;

    private File filePath;

    private String strVideoPath,strRecorderPath;

    AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViews();
    }

    public void findViews(){
        backgroudLayout = (BackgroundLayout) findViewById(R.id.backgroud_layout);

        bgLayout = (RelativeLayout) findViewById(R.id.bgLayout);
        bgLayout.setBackgroundColor(0x2aFF9800);

        leftBtn = (ImageView) findViewById(R.id.left_btn);
        rightBtn = (ImageView) findViewById(R.id.right_btn);

        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);

        colorPickBtn = (ImageView) findViewById(R.id.iv_pick_color);
        colorPickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUpColor(v);
            }
        });

        picOpView = (ImageView) findViewById(R.id.iv_add_img);
        picOpView.setOnClickListener(this);

        editText = (EditText) findViewById(R.id.edit_text);

        videoOpView = (ImageView) findViewById(R.id.iv_add_video);
        videoOpView.setOnClickListener(this);

        soundOpView = (ImageView) findViewById(R.id.iv_add_voice);
        soundOpView.setOnClickListener(this);
    }

    public void sendPost(String path){
        RequestParams params = new RequestParams();

        params.add("token",StaticHolder.getToken(this));
        params.add("path",path);
        params.add("content", editText.getText().toString());
        params.add("color", currentColor + "");
        params.add("weather", "sunny");

        final RequestHandle post = client.post(Host.SERVER+"/uploadPost", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    int code = response.getInt("code");
                    Log.i("code", "" + code);
                    if(code == 0){
                        Toast.makeText(AddActivity.this,"记录成功",Toast.LENGTH_SHORT).show();
                    } else if (code == 1){
                        startActivity(new Intent(AddActivity.this, LoginActivity.class));
                        AddActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void send(){
        RequestParams params = new RequestParams();
        try {
            if(strVideoPath == null){
                params.put("multipart_upload_file", filePath);
            } else {
                params.put("multipart_upload_file", new File(strVideoPath));
            }
            params.put("token",StaticHolder.getToken(this));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        final RequestHandle post = client.post(Host.SERVER+"/uploadMultipart", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    String path = response.getString("multipartPath");
                    Log.i("path",path);
                    //post to server
                    sendPost(path);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e("path","status:"+statusCode);
            }
        });
    }

    public void pickUpColor(View view){
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popup_window_pick_color, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
        ColorPickerLayout colorPicker = (ColorPickerLayout) contentView.findViewById(R.id.color_picker);
        colorPicker.setOnPickedListener(new ColorPickerLayout.OnPickListener() {
            @Override
            public void onPicked(String colorStr) {
                String hexStr = colorStr.replace("#","").replace("88","2a");
                int colorVal = Integer.valueOf(hexStr,16);
                bgLayout.setBackgroundColor(colorVal);
//                colorPickBtn.setBackgroundColor(colorVal);
                currentColor = colorVal;
                Log.v(this.getClass().getSimpleName(), "" + colorStr);
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(view);

        YoYo.with(Techniques.FlipInX).duration(500).playOn(contentView);


    }

    @Override
    public void onClick(View v) {
        if (v == rightBtn){
            //send to server.
            send();
        } else if (v == leftBtn){
            this.finish();
        } else if (v == picOpView){
            //choose file
            shotOrPick();
        } else if (v == videoOpView){
            recordVideo();
        } else if (v == soundOpView){
//            recordSound();
        }
    }

    private void recordSound(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/amr");
        startActivityForResult(intent, INTENT_REQUEST_RECORD_SOUND);
    }

    private void recordVideo(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivityForResult(intent, REQUEST_CODE_TAKE_VIDEO);
    }

    private void shotOrPick(){
        getImages();
    }

    private void shot(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, INTENT_REQUEST_SHOT_IMAGES);
    }

    private void getImages() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 3);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == INTENT_REQUEST_GET_IMAGES){
            if (resultCode == Activity.RESULT_OK) {
                strVideoPath = null;
                Uri cameraBitmapUri = (Uri) intent.getData();
                if(cameraBitmapUri != null){
                    Bitmap cameraBitmap = null;
                    try {
                        cameraBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), cameraBitmapUri);
                        backgroudLayout.setImageView(cameraBitmap);
                        saveFile(cameraBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else{
                    Bitmap cameraBitmap = null;
                    cameraBitmap = (Bitmap) intent.getExtras().get("data");
                    backgroudLayout.setImageView(cameraBitmap);
                    saveFile(cameraBitmap);
                }
            }
        } else if (requestCode == INTENT_REQUEST_SHOT_IMAGES){
            if(resultCode == Activity.RESULT_OK){
                strVideoPath = null;
                Bitmap cameraBitmap = null;
                cameraBitmap = (Bitmap) intent.getExtras().get("data");
                backgroudLayout.setImageView(cameraBitmap);
                saveFile(cameraBitmap);
            }
        } else if (requestCode == REQUEST_CODE_TAKE_VIDEO){
            if(resultCode == Activity.RESULT_OK){
                Uri uriVideo = intent.getData();
                Cursor cursor=this.getContentResolver().query(uriVideo, null, null, null, null);
                if (cursor.moveToNext()) {
                                         /* _data：文件的绝对路径 ，_display_name：文件名 */
                    strVideoPath = cursor.getString(cursor.getColumnIndex("_data"));
                    Toast.makeText(this, strVideoPath, Toast.LENGTH_SHORT).show();
                    try {
                        backgroudLayout.setVideoView(uriVideo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == INTENT_REQUEST_RECORD_SOUND){
            if (resultCode == RESULT_OK) {
                Uri uriRecorder = intent.getData();
                Cursor cursor=this.getContentResolver().query(uriRecorder, null, null, null, null);
                if (cursor.moveToNext()) {
                                         /* _data：文件的绝对路径 ，_display_name：文件名 */
                    strRecorderPath = cursor.getString(cursor.getColumnIndex("_data"));
                    Toast.makeText(this, strRecorderPath, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void saveFile(Bitmap cameraBitmap){
        File sdDire = Environment.getExternalStorageDirectory();
        try {
            File file = new File(sdDire.getCanonicalPath() + "/ideart/" + System.currentTimeMillis() + ".png");
            if(!file.getParentFile().isDirectory()) file.getParentFile().mkdirs();
            FileOutputStream outFileStream = new FileOutputStream(file);
            cameraBitmap.compress(Bitmap.CompressFormat.PNG, 90, outFileStream);
            outFileStream.flush();
            outFileStream.close();
            filePath = file;
           Log.v("saveFile","Done! path:"+file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
