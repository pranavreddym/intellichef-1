package com.example.ankita.intellichef;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    File outputFile;
    int images[] = {R.drawable.dosa, R.drawable.pizza, R.drawable.salad, R.drawable.soup};
    MyCustomPagerAdapter myCustomPagerAdapter;
    private static  Button cameraButton, chatButtton;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final String IMAGE_PATH = Environment.getExternalStorageDirectory() + File.separator + "img1.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();


        viewPager = (ViewPager)findViewById(R.id.viewPager);

        myCustomPagerAdapter = new MyCustomPagerAdapter(MainActivity.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);
        // More onCreate code ...
        cameraButton = findViewById(R.id.camera);
        chatButtton =findViewById(R.id.chat);

        cameraButton.setOnClickListener(this);
        chatButtton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        File file = new File(IMAGE_PATH);
        Uri outputFileUri = Uri.fromFile(file);

        if (view == cameraButton) {
//            Toast.makeText(this, "camera button clicked", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent();

//            intent.setType("image/*");
//
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//
//            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

//            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");


//            startActivityForResult(intent, CAMERA_REQUEST);

            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_CAMERA_PERMISSION_CODE);
            } else {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
        if (view == chatButtton) {
            Toast.makeText(this, "chat button clicked", Toast.LENGTH_LONG).show();
//            Intent i = new Intent(MainActivity.this, ImageDisplay.class);
//            startActivity(i);
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            System.out.println("inside  the function" + data.getClass());
//            System.out.println("image path " + IMAGE_PATH);
//            System.out.println("data storage ");
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            String partFilename = currentDateFormat();
            System.out.println("you're here" + partFilename);
           String path=  storeCameraPhotoInSDCard(photo, partFilename);
//            imageView.setImageBitmap(photo);
            System.out.println("hi in sc" );
            Intent i = new Intent(MainActivity.this, ImageDisplay.class);
            //Bundle bundle= new Bundle();


            //bundle.putString("path", IMAGE_PATH);
            //bundle.putParcelable("path", outputFile);
            i.putExtra("data", photo);
            //bundle.putParcelable("data", photo);
            i.putExtra("path",path);
            startActivity(i);
        }
//        System.out.print("hello");
//        Intent i = new Intent(MainActivity.this, ImageDisplay.class);
//        i.putExtra("data", data);
//        startActivity(i);
    }
    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private String storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate){
        //System.out.print("hi in sc");

        outputFile = new File(Environment.getExternalStorageDirectory(), "photo_" + currentDate + ".jpg");
        System.out.println(outputFile );
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile.toString();
    }

}

