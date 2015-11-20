package team.ideart.shiguang_app.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.loopj.android.http.AsyncHttpClient;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import team.ideart.shiguang_app.app.component.BackgroundLayout;
import team.ideart.shiguang_app.app.component.ColorPickerLayout;

/**
 * Created by yestin on 2015/11/20.
 */
public class AddActivity extends Activity implements View.OnClickListener{

    private static int INTENT_REQUEST_GET_IMAGES = 1,INTENT_REQUEST_SHOT_IMAGES=2;

    public RelativeLayout bgLayout;

    public BackgroundLayout backgroudLayout;

    public ImageView colorPickBtn,rightBtn,leftBtn;

    public ImageView picOpView;

    private Uri imgUri ;

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
    }

    public void send(){

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
        } else if (v == leftBtn){
            this.finish();
        } else if (v == picOpView){
            //choose file
            shotOrPick();
        }
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
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == INTENT_REQUEST_GET_IMAGES){
            if (resultCode == Activity.RESULT_OK) {
                Uri cameraBitmapUri = (Uri) intent.getData();
                Bitmap cameraBitmap = null;
                try {
                    cameraBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), cameraBitmapUri);
                    backgroudLayout.setImageView(cameraBitmap);
                    saveFile(cameraBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == INTENT_REQUEST_SHOT_IMAGES){
            if(resultCode == Activity.RESULT_OK){
                Bitmap cameraBitmap = null;
                cameraBitmap = (Bitmap) intent.getExtras().get("data");
                backgroudLayout.setImageView(cameraBitmap);
                saveFile(cameraBitmap);
            }
        }
    }

    public void saveFile(Bitmap cameraBitmap){

    }
}
