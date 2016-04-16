package com.example.sufian.livelocal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.squareup.otto.Subscribe;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

public class CommunicateFragment extends Fragment {

    public static final int CAMERA_REQUEST = 10;

    private Button camerabtn, instagrambtn;
    private View view;
    private Bitmap photo;
    private ShareButton shareButton;
    private Bitmap image;
    private int counter = 0;

    public CommunicateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_communicate, container, false);
        camerabtn = (Button) view.findViewById(R.id.camerabth);
        camerabtn.setOnClickListener(mButtonClickListener);

        int logo = R.drawable.largelogo;
        ((ImageView) view.findViewById(R.id.cameraImageView)).setImageResource(logo);
        TextView textview = (TextView) view.findViewById(R.id.text);
        textview.setText("Show people where you buy local! Chose or take the photo you want to share with the CT10% campaign group on Facebook.");

        instagrambtn = (Button) view.findViewById((R.id.instagrambtn));
        instagrambtn.setOnClickListener(InstgramButtonClickListener);

        return view;
    }

    private OnClickListener InstgramButtonClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            
            Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.instagram.android");
            if (intent != null)
            {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setPackage("com.instagram.android");
                Uri tempUri = getImageUri(getActivity().getApplicationContext(), photo);
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), photo, "image", "share")));
                shareIntent.setType("image/jpeg");
                getActivity().startActivity(shareIntent);
            }
            else
            {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("market://details?id="+"com.instagram.android"));
                startActivity(intent);
            }
        }
    };

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shareButton = (ShareButton) view.findViewById(R.id.share_btn);
        shareButton.setFragment(this);
        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                postPicture();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(mActivityResultSubscriber);
    }

    @Override
    public void onStop() {
        super.onStop();
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };


    private OnClickListener mButtonClickListener = new OnClickListener() {
        
        @Override
        public void onClick(View v) {

            final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo"))
                    {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        getActivity().startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                    else if (options[item].equals("Choose from Gallery"))
                    {
                        Intent picintent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        getActivity().startActivityForResult(picintent, 2);
                    }
                    else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == CAMERA_REQUEST && data != null) {
                photo = (Bitmap) data.getExtras().get("data");
                ((ImageView) view.findViewById(R.id.cameraImageView)).setImageBitmap(photo);
            } else if (requestCode == 2 && data != null) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                photo = thumbnail;
                ((ImageView) view.findViewById(R.id.cameraImageView)).setImageBitmap(thumbnail);
            }

    }

    public void postPicture() {
        if(counter == 0) {
            image = photo;
            AlertDialog.Builder shareDialog = new AlertDialog.Builder(getActivity());
            shareDialog.setTitle("Share");
            shareDialog.setMessage("Share image to Facebook?");
            shareDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SharePhoto photo = new SharePhoto.Builder()
                            .setBitmap(image)
                            .build();
                    SharePhotoContent content = new SharePhotoContent.Builder().addPhoto(photo).build();
                    shareButton.setShareContent(content);
                    counter = 1;
                    shareButton.performClick();
                }
            });
            shareDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            final AlertDialog alert = shareDialog.create();
            alert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
                }
            });
            alert.show();
        }
        else {
            counter = 0;
            shareButton.setShareContent(null);
        }
    }
}

