package com.blstream.listwithdetailsv4;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainListFragment extends Fragment {
    List<String> dataList;
    List<InputStream> inputStreamImagesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_list_fragment_layout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.mainList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        dataList = new ArrayList<>();
        inputStreamImagesList = new ArrayList<>();
        //FIXME:

        for (int i = 0; i < 1000; i++) {
            dataList.add("Position " + (i + 1));

        }
        //TODO: Do it with hashmap (Position, bitmap)
        try {
            getPathImagesFromAssetsAndAddThemToList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MainListAdapter adapter = new MainListAdapter(getActivity(), dataList, inputStreamImagesList, recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new MainListAdapter.OnAdapterClickListener() {
            @Override
            public void onItemSelected(int position) {
                //TODO: handle click here
               // String stringUri = null;
///                InputStream imageUrl = inputStreamImagesList.get(position);
                DetailsListFragment ldf = new DetailsListFragment ();
                Bundle args = new Bundle();
                args.putInt("position", position);
                ldf.setArguments(args);
                FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentManager.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentManager.replace(R.id.fragmentPhoneContainer, ldf);
                fragmentManager.addToBackStack(null).commit();

               // fragmentManager.beginTransaction().add(R.id.fragmentPhoneContainer, ldf).commit();
                //todo: display fragment
            }
        });


        return view;
    }

    public void getPathImagesFromAssetsAndAddThemToList() throws IOException {
        AssetManager assetManager = getContext().getAssets();

        String[] imgPath = assetManager.list("img");

        for (String anImgPath : imgPath) {
            InputStream is = assetManager.open("img/" + anImgPath);
            inputStreamImagesList.add(is);
        }
    }

}
