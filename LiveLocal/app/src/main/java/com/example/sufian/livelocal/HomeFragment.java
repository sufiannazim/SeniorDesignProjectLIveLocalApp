package com.example.sufian.livelocal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;


public class HomeFragment extends Fragment{

    protected String tokenstr;
    CarouselView carouselView;
    private ImageView imageOne, imageTwo, imageThree, imageFour, imageFive;
    //int[] sampleImages = {R.drawable.ct10cow, R.drawable.ct10tee, R.drawable.ct10squash, R.drawable.ct10tee, R.drawable.ct10cow};
    String[] imageArray = {"http://susfood.uconn.edu/wp-content/uploads/sites/781/2016/03/picture1.jpg",
            "http://susfood.uconn.edu/wp-content/uploads/sites/781/2016/03/picture2.jpg",
            "http://susfood.uconn.edu/wp-content/uploads/sites/781/2016/03/picture3.jpg",
            "http://susfood.uconn.edu/wp-content/uploads/sites/781/2016/03/picture4.jpg",
            "http://susfood.uconn.edu/wp-content/uploads/sites/781/2016/03/picture5.jpg"};
    int numberOfImages = 5;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(numberOfImages);
        carouselView.setImageListener(imageListener);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        HashMap map = WebAPICommunication.getCtStatsHashMap();
        double peoplePD = Double.parseDouble(map.get("people").toString());
        DecimalFormat peopleformatter = new DecimalFormat("#,###");
        String peopleF = peopleformatter.format(peoplePD);

        double locallyPD = Double.parseDouble(map.get("locally").toString());
        DecimalFormat locallyformatter = new DecimalFormat("#,###,###");
        String locallyF = locallyformatter.format(locallyPD);
        textView.setText(Html.fromHtml("<font color='#e64a19'>" + peopleF + "</font>"));
        textView.append(" PEOPLE and ");
        textView.append(Html.fromHtml("<font color='#e64a19'>" + map.get("businesses").toString() + "</font>"));
        textView.append("\n");
        textView.append("BUSINESSES SPENT ");
        textView.append(Html.fromHtml("<font color='#e64a19'>" + "$" + locallyF + "</font>"));
        textView.append(Html.fromHtml("<font size='6'> LOCALLY since August 2013</font>"));

        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            new DownloadImageTask(imageView).execute(imageArray[position]);
        }
    };

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
