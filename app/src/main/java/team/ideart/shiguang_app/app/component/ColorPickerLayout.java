package team.ideart.shiguang_app.app.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import team.ideart.shiguang_app.app.R;

/**
 * Created by yestin on 2015/11/20.
 */
public class ColorPickerLayout extends TableLayout implements View.OnClickListener {

    public TableLayout layout;

    public Context context;

    public OnPickListener listener;

    public ImageView imgViews[] = new ImageView[9];

    public ColorPickerLayout(Context context) {
        super(context);
        this.context = context;
        findViews();
    }

    public ColorPickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        findViews();
    }

    public void findViews(){
        LayoutInflater.from(context).inflate(R.layout.layout_pick_color, this, true);
        imgViews[0] = (ImageView) findViewById(R.id.img1);
        imgViews[1] = (ImageView) findViewById(R.id.img2);
        imgViews[2] = (ImageView) findViewById(R.id.img3);
        imgViews[3] = (ImageView) findViewById(R.id.img4);
        imgViews[4] = (ImageView) findViewById(R.id.img5);
        imgViews[5] = (ImageView) findViewById(R.id.img6);
        imgViews[6] = (ImageView) findViewById(R.id.img7);
        imgViews[7] = (ImageView) findViewById(R.id.img8);
        imgViews[8] = (ImageView) findViewById(R.id.img9);

        for(ImageView iv : imgViews){
            iv.setOnClickListener(this);
        }
    }

    public void setOnPickedListener(OnPickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        YoYo.with(Techniques.TakingOff)
                .duration(500)
                .playOn(v);
        String s = (String) v.getTag();
        listener.onPicked(s);
    }

    public static interface OnPickListener{
        public void onPicked(String colorStr);
    }

}
