package com.blstream.listwithdetailsv4;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
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

    // źródło danych
    private List<String> mArticles = new ArrayList<>();
    private List<Bitmap> imageViews = new ArrayList<>();
    // obiekt listy artykułów
    private RecyclerView mRecyclerView;
    // context of the fragment
    private FragmentActivity context;
    private OnAdapterClickListener listener;
    // konstruktor adaptera
    public MainListAdapter(FragmentActivity context, List<String> pArticles, List<Bitmap> pImages, RecyclerView pRecyclerView) {
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

    public void setListener(OnAdapterClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        // tworzymy layout artykułu oraz obiekt ViewHoldera
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.main_list_single_element_layout, viewGroup, false);

        // dla elementu listy ustawiamy obiekt OnClickListener,
        // który usunie element z listy po kliknięciu na niego
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // odnajdujemy indeks klikniętego elementu
//                int position = mRecyclerView.getChildAdapterPosition(v);
//                // usuwamy element ze źródła danych
//                //mArticles.remove(positionToDelete);
//                // poniższa metoda w animowany sposób usunie element z listy
//                // notifyItemRemoved(positionToDelete);
//
//
//                Log.d("Open: ", String.valueOf(position + 1));
//                listener.onItemSelected(position);


//            }
//        });

        // tworzymy i zwracamy obiekt ViewHolder
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        // uzupełniamy layout artykułu
        String article = mArticles.get(i);

        Bitmap image = imageViews.get(i % 5);
        ((MyViewHolder) viewHolder).mImage.setImageBitmap(image);

//ASYNC TASK TUTORIAL BY FILIP
       /* AsyncTask oldTask = imgViw.getTag();
        if (oldTag == null) {
            AsyncTask task = new AsyncTask(imgNAme, imgView).execute();
            imgView.setTag(task);
        } else {
            oldTask.cancel();
            AsyncTask task = new AsyncTask(imgNAme, imgView).execute();
            imgView.setTag(task);
        }*/


        ((MyViewHolder) viewHolder).mTitle.setText(article);
        // ((MyViewHolder) viewHolder).mImage.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), image, 100, 100));

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public interface OnAdapterClickListener {
        void onItemSelected(int position);
    }

    // implementacja wzorca ViewHolder
    // każdy obiekt tej klasy przechowuje odniesienie do layoutu elementu listy
    // dzięki temu wywołujemy findViewById() tylko raz dla każdego elementu
    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public ImageView mImage;

        public MyViewHolder(View pItem, final OnAdapterClickListener listener) {
            super(pItem);
            mTitle = (TextView) pItem.findViewById(R.id.mainListTextView);
            mImage = (ImageView) pItem.findViewById(R.id.mainListImageView);
            pItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemSelected(getAdapterPosition());
                    }
                }
            });
        }


    }
}
