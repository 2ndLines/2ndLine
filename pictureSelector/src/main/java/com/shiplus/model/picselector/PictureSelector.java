package com.shiplus.model.picselector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.shiplus.model.R;
import com.shiplus.model.picselector.domain.ImageFolder;
import com.shiplus.model.picselector.domain.ImageItem;
import com.shiplus.model.picselector.fragment.FolderFragment;
import com.shiplus.model.picselector.fragment.PictureFragment;

import java.util.ArrayList;
import java.util.List;


public class PictureSelector extends AppCompatActivity implements FolderFragment.OnFolderClickListener{
    private static final String TAG = "PictureSelector";

    /**
     * Fragment tag indicator
     */
    enum FRAGMENT{
        FRAGMENT_FOLDER,
        FRAGMENT_PICTURE
    }

    @Override
    public void onFolderClick(int position,ImageFolder imageFolder) {
        Logger.d(TAG, ">>> Clicked image folder. Name : " + imageFolder.getName());
        String title = imageFolder.getName();
        List<ImageItem> images = imageFolder.getImages();
        showPictureFragment(position, title, images);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_selector);
        setToolbar();
        showPictureFragment();
    }

    private void showPictureFragment(){
        showPictureFragment(0,null, null);
    }

    public void showPictureFragment(int position,String title, List<ImageItem> data){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT.FRAGMENT_PICTURE.name());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_left);

        if(fragment == null){
            fragment = new PictureFragment();
        }

        if(title != null && data != null){
            Bundle bundle = new Bundle();
            bundle.putInt(PictureFragment.EXTRA_POSITION, position);
            bundle.putString(PictureFragment.EXTRA_TITLE, title);
            bundle.putParcelableArrayList(PictureFragment.EXTRA_DATA, new ArrayList<ImageItem>(data));
            fragment.setArguments(bundle);
        }
        ft.replace(R.id.container,fragment,FRAGMENT.FRAGMENT_PICTURE.name()).commit();
    }

    public void showFolderFragment(List<ImageFolder> data){
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT.FRAGMENT_FOLDER.name());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        if(fragment == null){
            fragment = new FolderFragment();
        }

        ((FolderFragment)fragment).setData(data);

        ft.replace(R.id.container,fragment,FRAGMENT.FRAGMENT_FOLDER.name()).commit();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_normal);

    }

    public void showHomeAsUp(boolean enable){
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(enable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picture_selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
