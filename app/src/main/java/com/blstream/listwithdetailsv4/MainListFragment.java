package com.blstream.listwithdetailsv4;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
            decodeImagesFromAssets();
        } catch (IOException e) {
            e.printStackTrace();
        }



       /* inputStreamImagesList.add(decodeSampledBitmapFromStream("img/picture1.jpg", 100, 100));
        inputStreamImagesList.add(decodeSampledBitmapFromStream("img/picture2.jpg", 100, 100));
        inputStreamImagesList.add(decodeSampledBitmapFromStream("img/picture3.jpg", 100, 100));
        inputStreamImagesList.add(decodeSampledBitmapFromStream("img/picture4.jpg", 100, 100));
        inputStreamImagesList.add(decodeSampledBitmapFromStream("img/picture5.jpg", 100, 100));*/

        MainListAdapter adapter = new MainListAdapter(getActivity(), dataList, inputStreamImagesList, recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new MainListAdapter.OnAdapterClickListener() {
            @Override
            public void onItemSelected(int position) {
                //TODO: handle click here
                Bundle args = new Bundle();
                args.putString("data", dataList.get(position));

                DetailsListFragment fragment = new DetailsListFragment();
                fragment.setArguments(args);
                //todo: display fragment
            }
        });


        return view;
    }

    public void decodeImagesFromAssets() throws IOException {
        AssetManager assetManager = getContext().getAssets();

        String[] imgPath = assetManager.list("img");

        for (String anImgPath : imgPath) {
            InputStream is = assetManager.open("img/" + anImgPath);
            inputStreamImagesList.add(is);
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromStream(InputStream is,
                                                int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        //return BitmapFactory.decodeResource(res, resId, options);
        return BitmapFactory.decodeStream(is, null, options);
    }


}
