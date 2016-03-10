package com.blstream.listwithdetailsv4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainListFragment extends Fragment {


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
        List<String> dataList = new ArrayList<>();
        List<Integer> imagesReferences = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("Position " + (i + 1));
            switch(i % 5){
                case 0:
                    imagesReferences.add(R.drawable.photo1);
                    break;
                case 1:
                    imagesReferences.add(R.drawable.photo2);
                    break;
                case 2:
                    imagesReferences.add(R.drawable.photo3);
                    break;
                case 3:
                    imagesReferences.add(R.drawable.photo4);
                    break;
                case 4:
                    imagesReferences.add(R.drawable.photo5);
                    break;
            }
        }
        MainListAdapter adapter = new MainListAdapter(getActivity(), dataList, recyclerView);
        recyclerView.setAdapter(adapter);


        return view;
    }

}
