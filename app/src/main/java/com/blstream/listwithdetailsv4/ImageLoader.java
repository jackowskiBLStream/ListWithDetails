package com.blstream.listwithdetailsv4;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import static com.blstream.listwithdetailsv4.MainListFragment.decodeSampledBitmapFromStream;

/**
 *
 */
class ImageLoader extends AsyncTask<InputStream, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private int data = 0;

    public ImageLoader(ImageView imageView) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = new WeakReference<>(imageView);
    }

    // Decode image in background.
    @Override
    protected Bitmap doInBackground(InputStream... params) {
        //data = params[0];
        return decodeSampledBitmapFromStream(params[0], 100, 100);
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
