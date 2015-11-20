package team.ideart.shiguang_app.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import team.ideart.shiguang_app.app.R;
import team.ideart.shiguang_app.app.entity.TimeLine;
import team.ideart.shiguang_app.app.utils.Host;

import java.util.List;

/**
 * TimeLineAdapter
 *
 * @author Allen Jin
 * @date 2015/11/21
 */
public class TimeLineAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater mInflater;
    private List<TimeLine> items;
    private ImageLoader imageLoader;

    public TimeLineAdapter(Context context, List<TimeLine> items) {
        this.context = context;
        this.items = items;
        mInflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return items.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        TimeLine timeline = items.get(i);
        if(view == null){
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.timeline_item, null);
            viewHolder.flag = (ImageView)view.findViewById(R.id.timeline_flag);
            viewHolder.dateTime = (TextView)view.findViewById(R.id.timeline_date_txt);
            viewHolder.weather = (TextView)view.findViewById(R.id.timeline_weather_txt);
            viewHolder.content = (ImageView) view.findViewById(R.id.timeline_img);
            viewHolder.overlay = (TextView) view.findViewById(R.id.timeline_overlay);
            viewHolder.title = (TextView) view.findViewById(R.id.timeline_title);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.dateTime.setText(timeline.getDate());
        viewHolder.weather.setText(timeline.getWeather());
        viewHolder.overlay.setBackgroundColor(timeline.getColor());
        viewHolder.title.setText(timeline.getContent());
        loadImage(viewHolder.content, Host.SERVER + timeline.getResUrl());
        return view;
    }

    private class ViewHolder{
        public ImageView flag;
        public TextView dateTime;
        public TextView weather;
        public ImageView content;
        public TextView overlay;
        public TextView title;
    }

    private void loadImage(ImageView imageView, String imgUrl){
            imageLoader.displayImage(imgUrl, imageView);
    }


}
