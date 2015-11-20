package team.ideart.shiguang_app.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import team.ideart.shiguang_app.app.R;
import team.ideart.shiguang_app.app.adapter.TimeLineAdapter;
import team.ideart.shiguang_app.app.entity.TimeLineItem;

import java.util.List;

/**
 * MainFragment
 *
 * @author Allen Jin
 * @date 2015/11/20
 */
public class MainFragment extends Fragment{

    private ListView timeLineView;
    private List<TimeLineItem> items;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        timeLineView = (ListView)rootView.findViewById(R.id.timeline_view);

        timeLineView.setAdapter(new TimeLineAdapter(getActivity(), items));
        getActivity().setTitle(getResources().getText(R.string.sidebar_item_home));
        return rootView;
    }
}
