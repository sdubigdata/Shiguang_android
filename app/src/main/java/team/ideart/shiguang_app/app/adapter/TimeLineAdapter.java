package team.ideart.shiguang_app.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import team.ideart.shiguang_app.app.R;
import team.ideart.shiguang_app.app.entity.TimeLineItem;

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
    private List<TimeLineItem> items;

    public TimeLineAdapter(Context context, List<TimeLineItem> items) {
        this.context = context;
        this.items = items;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.timeline_item, null);
            viewHolder.dateTime = (TextView)view.findViewById(R.id.timeline_date_txt);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.dateTime.setText("9月11日");

        return view;
    }

    private class ViewHolder{
        public TextView dateTime;
        public TextView weather;
    }

}
