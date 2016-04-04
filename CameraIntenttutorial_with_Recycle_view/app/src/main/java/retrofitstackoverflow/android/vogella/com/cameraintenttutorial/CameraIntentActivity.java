package retrofitstackoverflow.android.vogella.com.cameraintenttutorial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraIntentActivity extends AppCompatActivity {

    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private ImageView mPhotoCaptureImageView;
    private String mImageFileLocation = "";
    private String GALLERY_LOCATION = "image gallery";
    private File mGalleryFolder;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);

        createImageGallery();

        mRecyclerView = (RecyclerView) findViewById(R.id.galleryRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter imageAdapter = new ImageAdapter(mGalleryFolder);
        mRecyclerView.setAdapter(imageAdapter);

    }

    public void takePhoto(View view) {
        //Toast.makeText(this,"proverka",Toast.LENGTH_LONG).show();
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
        callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

        startActivityForResult(callCameraApplicationIntent, ACTIVITY_START_CAMERA_APP);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            // Toast.makeText(this,"just a try ", Toast.LENGTH_LONG).show();
            // Bundle extras = data.getExtras();
            // Bitmap photoCaptureBitmap = (Bitmap) extras.get("data");
            // mPhotoCaptureImageView.setImageBitmap(photoCaptureBitmap);
            // Bitmap photoCaptureBitmap = BitmapFactory.decodeFile(mImageFileLocation);
            //  mPhotoCaptureImageView.setImageBitmap(photoCaptureBitmap);
            // setReducedImageSize();

            RecyclerView.Adapter newImageAdapter = new ImageAdapter(mGalleryFolder);

            mRecyclerView.swapAdapter(newImageAdapter, false);
        }

    }

    private void createImageGallery() { // pocetok na recycleView  odbiranje na file location
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mGalleryFolder = new File(storageDirectory, GALLERY_LOCATION);
        if (!mGalleryFolder.exists()) {
            mGalleryFolder.mkdirs(); // ako nema folder kreiraj go ova e funkcija ja kreiranje mkdirs();
        }
    }

    File createImageFile() throws IOException {
        String timeStamap = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE " + timeStamap + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName, ".jpg", mGalleryFolder);

        mImageFileLocation = image.getAbsolutePath();
        return image;
    }

    void setReducedImageSize() {
        int targetImageViewWidth = mPhotoCaptureImageView.getWidth();
        int targetImageViewHeight = mPhotoCaptureImageView.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        int cameraImageWidth = bmOptions.outWidth;
        int cameraImageHeight = bmOptions.outHeight;

        int scaleFactor = Math.min(cameraImageWidth / targetImageViewWidth, cameraImageHeight / targetImageViewHeight);
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;

        Bitmap photoReducedSizeBitmap = BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        mPhotoCaptureImageView.setImageBitmap(photoReducedSizeBitmap);
    }
}


