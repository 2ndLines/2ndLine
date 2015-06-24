package com.shiplus.model.picselector.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shiplus.model.R;
import com.shiplus.model.photoview.PhotoActivity;
import com.shiplus.model.picselector.LocalPictureLoader;
import com.shiplus.model.picselector.Logger;
import com.shiplus.model.picselector.PictureSelector;
import com.shiplus.model.picselector.adapter.OnItemClickListener;
import com.shiplus.model.picselector.adapter.PictureAdapter;
import com.shiplus.model.picselector.domain.ImageFolder;
import com.shiplus.model.picselector.domain.ImageItem;
import com.shiplus.model.picselector.view.CheckableFrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/5.
 */
public class PictureFragment extends Fragment implements OnItemClickListener<ImageItem>, View.OnClickListener,
        LoaderManager.LoaderCallbacks<List<ImageFolder>> {
    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_SELECTED_PICTURES = "selected_picture_index";
    private static final String TAG = "PictureFragment";
    private static final int LOADER_ID = 0x01;
    private static final int REQUEST_CODE_PHOTO_VIEWER = 0x100;
    private List<ImageFolder> folderList;
    private ArrayList<ImageItem> mSelects;
    private List<ImageItem> mData;
    private String mTitle = "";
    /**
     * ImageFolder position in its array list.
     */
    private int mPosition = 0;
    private RecyclerView list;
    private PictureAdapter adapter;
    private ImageButton btnPreview, btnConfirm;
    private TextView selectedSize;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "onCreate()");
        setHasOptionsMenu(true);

        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picture, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Logger.d(TAG, "onViewCreated");
        if (savedInstanceState != null) {
            Logger.d(TAG,"Restore from saved instance state");
            mSelects = savedInstanceState.getParcelableArrayList(EXTRA_SELECTED_PICTURES);
        }

        if (getArguments() != null) {
            Logger.d(TAG,"Restore from arguments");
            mPosition = getArguments().getInt(EXTRA_POSITION);
            mData = getArguments().getParcelableArrayList(EXTRA_DATA);
            mTitle = getArguments().getString(EXTRA_TITLE);
            mSelects = getArguments().getParcelableArrayList(EXTRA_SELECTED_PICTURES);
        }

        if(mSelects == null){
            mSelects = new ArrayList<>();
        }

        findViews(view, mData);
    }

    private void findViews(View view, List<ImageItem> data) {
        list = (RecyclerView) view.findViewById(R.id.reusable_recycler_view);
        list.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
        adapter = new PictureAdapter(data, mSelects);
        adapter.setOnItemClickListener(this);
        list.setAdapter(adapter);

        btnPreview = (ImageButton) view.findViewById(R.id.button_preview);
        btnConfirm = (ImageButton) view.findViewById(R.id.button_confirm);
        btnConfirm.setOnClickListener(this);
        btnPreview.setOnClickListener(this);
        selectedSize = (TextView) view.findViewById(R.id.selected_size_indicator);
        updateViews(mSelects == null ? 0 : mSelects.size());
    }

    private void updateViews(int size){
        boolean enable = size != 0;
        btnPreview.setEnabled(enable);
        btnConfirm.setEnabled(enable);

        selectedSize.setText(String.valueOf(size));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            ((PictureSelector)getActivity()).showFolderFragment(folderList);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_SELECTED_PICTURES, mSelects);
    }

    @Override
    public void onItemClick(View view, ImageItem item, int position) {
        try {
            CheckableFrameLayout layout = (CheckableFrameLayout) view;
            selectImage(layout.isChecked(), item);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    private void selectImage(boolean checked, ImageItem item) {
        if (checked) {
            if (!mSelects.contains(item)) {
                mSelects.add(item);
            }
        } else {
            mSelects.remove(item);
        }
        updateViews(mSelects.size());
        Logger.d(TAG, "Selected Image size = " + mSelects.size());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.d(TAG, "onActivityResult, resultCode = " + requestCode);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CODE_PHOTO_VIEWER && data != null){
                ArrayList<ImageItem> items = data.getParcelableArrayListExtra(PhotoActivity.EXTRA_IMAGE_LIST);
                Logger.d(TAG,"items size = " + items.size());
                mSelects = items;
                adapter.updateSelected(mSelects);
                updateViews(mSelects.size());
            }
        }
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.button_confirm) {
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(EXTRA_SELECTED_PICTURES, mSelects);
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        } else if (id == R.id.button_preview) {
            Intent preview = new Intent(getActivity(), PhotoActivity.class);
            preview.putParcelableArrayListExtra(PhotoActivity.EXTRA_IMAGE_LIST, mSelects);
            preview.putExtra(PhotoActivity.EXTRA_ACTION_TYPE, PhotoActivity.ACTION_PICK);
            startActivityForResult(preview, REQUEST_CODE_PHOTO_VIEWER);
        }
    }

    @Override
    public Loader<List<ImageFolder>> onCreateLoader(int id, Bundle args) {
        Logger.d(TAG,"onCreateLoader()");
        return new LocalPictureLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<ImageFolder>> loader, List<ImageFolder> data) {
        if(loader.getId() != LOADER_ID) return;
        folderList = data;
        ImageFolder allFolder = data.get(mPosition);
        mTitle = allFolder.getName();
        getActivity().setTitle(mTitle);
        if (adapter != null) {
            adapter.swapData(allFolder.getImages());
        }
    }

    @Override
    public void onLoaderReset(Loader<List<ImageFolder>> loader) {
        // Do nothing
    }

    @Override
    public void onResume() {
        super.onResume();
        showHomeAsUp(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        showHomeAsUp(false);
    }

    private void showHomeAsUp(boolean show) {
        PictureSelector selector = (PictureSelector) getActivity();
        selector.showHomeAsUp(show);
        getActivity().setTitle(show? mTitle:"");
    }
}
