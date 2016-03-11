package com.blstream.listwithdetailsv4;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements MainListAdapter.OnAdapterClickListener {
    private static final String DETAILS_FRAGMENT_TAG = "detailsFragment";
    private DetailsListFragment detailsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        detailsListFragment = new DetailsListFragment();
        if (savedInstanceState != null) {
            return;
        }


        manageFragments();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();


    }

    public boolean isTablet(Context context) { //TODO if width > height ...
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    public int getLayout() {
        boolean isTablet = isTablet(this);
        return isTablet ? R.layout.tablet_layout : R.layout.phone_layout;
    }

    public void manageFragments() {
        // Create an instance of ExampleFragment
        MainListFragment firstFragment = new MainListFragment();

        // In case this activity was started with special instructions from an Intent,
        // pass the Intent's extras to the fragment as arguments
        firstFragment.setArguments(getIntent().getExtras());


        // Add the fragment to the 'fragment_container' FrameLayout
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (isTablet(this)) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentMainTabletContainer, firstFragment).commit();
        } else {
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentPhoneContainer, firstFragment).commit();
        }

    }

    @Override
    public void onItemSelected(int position) {

        Bundle args = new Bundle();
        args.putInt("position", position);
        detailsListFragment = new DetailsListFragment();
        detailsListFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back

        if (isTablet(this)) {
            transaction.replace(R.id.fragmentDetailsTabletContainer, detailsListFragment, DETAILS_FRAGMENT_TAG );
        } else {
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
            transaction.replace(R.id.fragmentPhoneContainer, detailsListFragment);

        }
        transaction.addToBackStack(null);


        // Commit the transaction
        transaction.commit();
    }

    private Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
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
