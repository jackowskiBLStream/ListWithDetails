package com.blstream.listwithdetailsv4;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DetailsListFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.details_list_fragment_layout, container, false);
        int position = getArguments().getInt("position") + 1;

        TextView textView = (TextView) view.findViewById(R.id.detailTextView);
        textView.setText("Number " + position);

        ImageView imageView = (ImageView) view.findViewById(R.id.detailImageView);
        try {
            imageView.setImageBitmap(getImageFromPosition(position));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*RecyclerView recyclerDetailsView = (RecyclerView) view.findViewById(R.id.detailsList);
        recyclerDetailsView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerDetailsView.setItemAnimator(new DefaultItemAnimator());

        List<String> dataList = new ArrayList<>();
        int position = getArguments().getInt("position");
        for (int i = 0; i < 5; i++) {
            dataList.add("Position " + (position + 1) + " Detail position " + (i + 1));
        }
        recyclerDetailsView.setAdapter(new MainListAdapter(getActivity(), dataList, recyclerDetailsView));*/
        return view;
    }
    public Bitmap getImageFromPosition(int position) throws IOException {
        AssetManager assetManager = getContext().getAssets();

        String[] imgPath = assetManager.list("img");
        Bitmap bitmap = null;
        for (int i = 0; i < position % imgPath.length; i++) {
            InputStream is = assetManager.open("img/" + imgPath[i]);
            bitmap = BitmapFactory.decodeStream(is);
        }
        return bitmap;

    }
}
