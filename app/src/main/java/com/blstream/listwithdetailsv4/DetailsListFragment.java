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
public class DetailsListFragment extends Fragment {

    private RecyclerView recyclerDetailsView;
    private List<String> dataList;


    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.main_list_fragment_layout, container, false);
        recyclerDetailsView = (RecyclerView) view.findViewById(R.id.mainList);
        recyclerDetailsView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerDetailsView.setItemAnimator(new DefaultItemAnimator());

        dataList = new ArrayList<>();
        int position = getArguments().getInt("position");
        for (int i = 0; i < 5; i++) {
            dataList.add("Position " + (position + 1) + " Detail position " + (i + 1));
        }
        recyclerDetailsView.setAdapter(new MainListAdapter(getActivity(), dataList, recyclerDetailsView));
        return view;
    }
}
