package retrofitstackoverflow.android.vogella.com.cameraintenttutorial;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);

        mPhotoCaptureImageView = (ImageView) findViewById(R.id.captureImageView);


    }

    public void takePhoto(View view) {
        //Toast.makeText(this,"proverka",Toast.LENGTH_LONG).show();
        Intent callCameraApplicationIntent = new Intent();
        callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();

        }catch (IOException e){
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
            Bitmap photoCaptureBitmap = BitmapFactory.decodeFile(mImageFileLocation);
            mPhotoCaptureImageView.setImageBitmap(photoCaptureBitmap);
        }

    }

    File createImageFile() throws IOException {
        String timeStamap = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE " + timeStamap + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        mImageFileLocation = image.getAbsolutePath();
        return image;
    }

}
