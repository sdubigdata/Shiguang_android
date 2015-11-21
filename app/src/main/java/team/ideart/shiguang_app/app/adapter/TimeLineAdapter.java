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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private SimpleDateFormat sdf;
    private String loaction = "山东大学计算机学院";

    public TimeLineAdapter(Context context, List<TimeLine> items) {
        this.context = context;
        this.items = items;
        mInflater = LayoutInflater.from(context);
        imageLoader = ImageLoader.getInstance();
        sdf = new SimpleDateFormat("yyyy年MM月dd日 ");
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
            viewHolder.location = (TextView) view.findViewById(R.id.timeline_location);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(timeline.getDate()));
        String pmam = getPMAM(calendar.get(Calendar.HOUR_OF_DAY));
        String date = sdf.format(timeline.getDate());

        viewHolder.dateTime.setText(date + "星期" + getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK) - 1) + " " + pmam);
        viewHolder.location.setText(loaction);
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
        public TextView location;
    }

    private void loadImage(ImageView imageView, String imgUrl){
            imageLoader.displayImage(imgUrl, imageView);
    }

    private String getPMAM(int hour){
        if(0 <= hour && hour <6){
            return "凌晨";
        }else if(hour < 12){
            return "上午";
        }else if(hour < 18){
            return "下午";
        }else{
            return "晚上";
        }
    }
    private static final String[] DAY_OF_WEEK_ARRAY = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private String getDayOfWeek(int numDay) {
        return DAY_OF_WEEK_ARRAY[numDay];
    }

}
