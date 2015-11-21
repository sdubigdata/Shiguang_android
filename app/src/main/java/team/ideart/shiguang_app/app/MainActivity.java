package team.ideart.shiguang_app.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import team.ideart.shiguang_app.app.adapter.SidebarListAdapter;
import team.ideart.shiguang_app.app.entity.SidebarItem;
import team.ideart.shiguang_app.app.fragment.MainFragment;
import team.ideart.shiguang_app.app.fragment.SettingsFragment;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private List<SidebarItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        initSideBar();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
//        return super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_add).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch(item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        Fragment fragment = null;
        switch (items.get(position).getId()) {
            case "SETTINGS":
                fragment = new SettingsFragment();
                break;
            case "EXIT":
                exitApp();
                break;
            default:
                fragment = new MainFragment();
        }
        switchFragment(fragment);
        mDrawerList.setItemChecked(position, true);
        setTitle(getResources().getText(items.get(position).getTxtRes()));
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        //getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void initSideBar() {
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        items = new LinkedList<>();
        items.add(new SidebarItem("HOME", R.drawable.ic_grid, R.drawable.ic_grid_active, R.string.sidebar_item_home));
        items.add(new SidebarItem("SETTINGS", R.drawable.ic_settings, R.drawable.ic_settings_active, R.string.sidebar_item_settings));
        items.add(new SidebarItem("EXIT", R.drawable.ic_exit1, R.drawable.ic_exit1_active,R.string.sidebar_item_exit));
        mDrawerList.setAdapter(new SidebarListAdapter(MainActivity.this, items));

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    }

    private void exitApp(){
        new AlertDialog
                .Builder(this)
                .setTitle("确定注销?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StaticHolder.setToken(MainActivity.this, null);
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

}
