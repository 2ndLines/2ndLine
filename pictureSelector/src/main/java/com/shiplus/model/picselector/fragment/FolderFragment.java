package com.shiplus.model.picselector.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shiplus.model.R;
import com.shiplus.model.picselector.Logger;
import com.shiplus.model.picselector.adapter.FolderAdapter;
import com.shiplus.model.picselector.adapter.OnItemClickListener;
import com.shiplus.model.picselector.domain.ImageFolder;

import java.util.List;

/**
 * Created by Administrator on 2015/6/5.
 */
public class FolderFragment extends Fragment implements OnItemClickListener<ImageFolder> {
    private static final String TAG = "FolderFragment";
    private RecyclerView recyclerView;
    private FolderAdapter folderAdapter;
    private OnFolderClickListener onFolderClickListener;
    private List<ImageFolder> imageFolders;

    public interface OnFolderClickListener{
        void onFolderClick(int position,ImageFolder imageFolder);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Logger.d(TAG,"onAttach");
        try {
//            PictureSelector selector = (PictureSelector) getActivity();
//            selector.notifyObservers();
            onFolderClickListener = (OnFolderClickListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_folder, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Logger.d(TAG, "onViewCreated");
        initRecyclerView(view);
    }

    private void initRecyclerView(View parent){
        recyclerView = (RecyclerView) parent.findViewById(R.id.reusable_recycler_view);
        folderAdapter = new FolderAdapter(imageFolders);
        folderAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(folderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setData(final List<ImageFolder> folderList){
        imageFolders = folderList;
        if(getActivity() == null) return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(folderAdapter != null){
                    folderAdapter.swapData(folderList);
                }
            }
        });
    }

    @Override
    public void onItemClick(View view, ImageFolder item, int position) {
        onFolderClickListener.onFolderClick(position,item);
    }
}
