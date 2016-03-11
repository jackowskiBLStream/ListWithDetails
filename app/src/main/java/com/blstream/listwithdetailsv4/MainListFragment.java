package com.blstream.listwithdetailsv4;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
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
        //FIXME:
        final List<String> dataList = new ArrayList<>();
        List<Bitmap> bitmapList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            dataList.add("Position " + (i + 1));

        }
        bitmapList.add(decodeSampledBitmapFromResource( "picture1.jpg", 100, 100));
        bitmapList.add(decodeSampledBitmapFromResource( "picture2.jpg", 100, 100));
        bitmapList.add(decodeSampledBitmapFromResource( "picture3.jpg", 100, 100));
        bitmapList.add(decodeSampledBitmapFromResource( "picture4.jpg", 100, 100));
        bitmapList.add(decodeSampledBitmapFromResource("picture5.jpg", 100, 100));

        MainListAdapter adapter = new MainListAdapter(getActivity(), dataList, bitmapList,  recyclerView);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new MainListAdapter.OnAdapterClickListener() {
            @Override
            public void onItemSelected(int position) {
                //TODO: handle click here
                Bundle args = new Bundle();
                args.putString("data", dataList.get(position));

                DetailsListFragment fragment = new DetailsListFragment();
                fragment.setArguments(args);
                //todo: display framgnet
            }
        });

        //TODO: whaaat?
        Intent intent = new Intent(view.getContext(), DetailsListFragment.class);
        intent.putExtra("bitmap1",  bitmapList.get(0));
        intent.putExtra("bitmap2",  bitmapList.get(1));
        intent.putExtra("bitmap3",  bitmapList.get(2));
        intent.putExtra("bitmap4",  bitmapList.get(3));
        intent.putExtra("bitmap5",  bitmapList.get(4));


        return view;
    }

    private Bitmap decodeSampledBitmapFromResource(String path,
                                                   int reqWidth, int reqHeight) {
        AssetManager assetManager = getContext().getAssets();

        InputStream istr = null;
        Bitmap bitmap = null;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            istr = assetManager.open(path);

        } catch (IOException e) {
            // handle exception
        }


        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeStream(istr, null,  options);
        return bitmap;
    }

    private int calculateInSampleSize(
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

}
