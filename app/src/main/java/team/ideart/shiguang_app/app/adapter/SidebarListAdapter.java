package team.ideart.shiguang_app.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import team.ideart.shiguang_app.app.R;
import team.ideart.shiguang_app.app.entity.SidebarItem;

import java.util.List;

/**
 * SidebarListAdapter
 *
 * @author Allen Jin
 * @date 2015/11/20
 */
public class SidebarListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflater;
    private List<SidebarItem> items;

    public SidebarListAdapter(Context context, List<SidebarItem> items) {
        this.context = context;
        this.items = items;
        mInflater = LayoutInflater.from(context);
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
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.sidebar_list_item, null);
            viewHolder.itemTxt = (TextView)view.findViewById(R.id.item_title);
            viewHolder.itemIcon = (ImageView)view.findViewById(R.id.item_icon);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.itemTxt.setText(context.getResources().getText(items.get(i).getTxtRes()));
        viewHolder.itemIcon.setImageDrawable(context.getResources().getDrawable(items.get(i).getLogoRes()));
        return view;
    }

    private class ViewHolder{
        public TextView itemTxt;
        public ImageView itemIcon;
    }

}
