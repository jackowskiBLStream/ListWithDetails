package com.blstream.listwithdetailsv4;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainListAdapter extends RecyclerView.Adapter {

    public interface OnAdapterClickListener {
        void onItemSelected(int position);
    }
    // źródło danych
    private List<String> mArticles = new ArrayList<>();
    private List<Integer> imageViews = new ArrayList<>();
    // obiekt listy artykułów
    private RecyclerView mRecyclerView;
    // context of the fragment
    private FragmentActivity context;
    private OnAdapterClickListener listener;



    // konstruktor adaptera
    public MainListAdapter(FragmentActivity context, List<String> pArticles,List<Integer> pImages, RecyclerView pRecyclerView) {
        mArticles = pArticles;
        imageViews = pImages;
        mRecyclerView = pRecyclerView;
        this.context = context;
    }
    public MainListAdapter(FragmentActivity context, List<String> pArticles, RecyclerView pRecyclerView) {
        mArticles = pArticles;

        mRecyclerView = pRecyclerView;
        this.context = context;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        listener = (OnAdapterClickListener) recyclerView.getContext();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        // tworzymy layout artykułu oraz obiekt ViewHoldera
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.main_list_single_element_layout, viewGroup, false);

        // dla elementu listy ustawiamy obiekt OnClickListener,
        // który usunie element z listy po kliknięciu na niego
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // odnajdujemy indeks klikniętego elementu
                int position = mRecyclerView.getChildAdapterPosition(v);
                // usuwamy element ze źródła danych
                //mArticles.remove(positionToDelete);
                // poniższa metoda w animowany sposób usunie element z listy
                // notifyItemRemoved(positionToDelete);

                Log.d("Open: ", String.valueOf(position + 1));
                listener.onItemSelected(position);


            }
        });

        // tworzymy i zwracamy obiekt ViewHolder
        return new MyViewHolder(view);
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
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        // uzupełniamy layout artykułu
        String article = mArticles.get(i);
      /*  if(i != 0){
            Integer image = imageViews.get(i);
            ((MyViewHolder) viewHolder).mImage.setImageResource(image);
        }*/

        ((MyViewHolder) viewHolder).mTitle.setText(article);
       // ((MyViewHolder) viewHolder).mImage.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), image, 100, 100));

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


    // implementacja wzorca ViewHolder
    // każdy obiekt tej klasy przechowuje odniesienie do layoutu elementu listy
    // dzięki temu wywołujemy findViewById() tylko raz dla każdego elementu
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public ImageView mImage;


        public MyViewHolder(View pItem) {
            super(pItem);
            mTitle = (TextView) pItem.findViewById(R.id.mainListTextView);
            mImage = (ImageView) pItem.findViewById(R.id.mainListImageView);
        }
    }
}
