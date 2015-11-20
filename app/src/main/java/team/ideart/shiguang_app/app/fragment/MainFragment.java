package team.ideart.shiguang_app.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import team.ideart.shiguang_app.app.R;
import team.ideart.shiguang_app.app.StaticHolder;
import team.ideart.shiguang_app.app.adapter.TimeLineAdapter;
import team.ideart.shiguang_app.app.entity.TimeLine;
import team.ideart.shiguang_app.app.utils.Host;

import java.util.LinkedList;
import java.util.List;

/**
 * MainFragment
 *
 * @author Allen Jin
 * @date 2015/11/20
 */
public class MainFragment extends Fragment{

    private ListView timeLineView;
    private List<TimeLine> items;
    private AsyncHttpClient client = new AsyncHttpClient();
    private String requestUrl = Host.SERVER + "/list";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        timeLineView = (ListView)rootView.findViewById(R.id.timeline_view);

        requestTimeLine();

        timeLineView.setOnItemClickListener(new TimeLineItemClickListener());
        //getActivity().setTitle(getResources().getText(R.string.sidebar_item_home));
        return rootView;
    }

    private class TimeLineItemClickListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    }

    private void requestTimeLine(){
        items = new LinkedList<>();
        RequestParams params = new RequestParams();
        params.put("token", StaticHolder.token);

        client.get(getActivity(), requestUrl, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("LIST TIMELINE",response.toString());
                try{
                    JSONArray array = response.getJSONArray("postList");
                    for(int i = 0 ; i < array.length(); i++){
                        JSONObject tl = array.getJSONObject(i);
                        TimeLine timeline = new TimeLine();
                        timeline.setContent(tl.getString("content"));
                        timeline.setResUrl(tl.getString("path"));
                        timeline.setWeather(tl.getString("weather"));
                        timeline.setDate(tl.getString("date"));
                        timeline.setId(tl.getInt("id"));
                        timeline.setColor(tl.getInt("color"));
                        items.add(timeline);
                    }
                    timeLineView.setAdapter(new TimeLineAdapter(getActivity(), items));
                }catch (Exception e){
                    e.printStackTrace();
                }

               // super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("LIST TIMELINE", errorResponse.toString());
            }
        });
    }


}
