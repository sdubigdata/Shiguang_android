package team.ideart.shiguang_app.app.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.IOException;

import team.ideart.shiguang_app.app.R;

/**
 * Created by yestin on 2015/11/20.
 */
public class BackgroundLayout extends RelativeLayout{

    private AutoScrollImageView imageView;
    private ScalableVideoView videoView;

    private Context context;

    public BackgroundLayout(Context context) {
        super(context);
        this.context = context;
        findViews();
    }

    public BackgroundLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        findViews();
    }

    public BackgroundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        findViews();
    }

    public void findViews(){

        LayoutInflater.from(context).inflate(R.layout.layout_add_backgroud, this, true);

        imageView = (AutoScrollImageView) findViewById(R.id.img);
        videoView = (ScalableVideoView) findViewById(R.id.video);
        videoView.setVisibility(INVISIBLE);
//        try {
//            videoView.setRawData(R.raw.video1);
//            videoView.setVolume(0, 0);
//            videoView.setLooping(true);
//            videoView.prepareAsync(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    videoView.start();
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void setImageView(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

    public void clearImageView(){

    }

    public void setVideoView(){

    }

    public void clearVideoView(){

    }


}
