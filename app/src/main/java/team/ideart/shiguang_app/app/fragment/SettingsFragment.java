package team.ideart.shiguang_app.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import team.ideart.shiguang_app.app.R;

/**
 * SettingsFragment
 *
 * @author Allen Jin
 * @date 2015/11/20
 */
public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().setTitle(getResources().getText(R.string.sidebar_item_settings));
        return rootView;
    }
}
