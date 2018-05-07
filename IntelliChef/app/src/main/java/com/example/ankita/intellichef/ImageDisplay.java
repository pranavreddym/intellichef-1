package com.example.ankita.intellichef;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

/**
 * Created by panny on 26-04-2018.
 */

public class ImageDisplay extends AppCompatActivity implements OnClickListener{
    ImageView img;
    String img_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagedisplay);
        //Bundle bundle = getIntent().getExtras();
        img = findViewById(R.id.imagedisplay);
        Button upload = findViewById(R.id.uplaod);

//Extract the data…
        Bitmap photo = getIntent().getExtras().getParcelable("data");
        img_path= getIntent().getExtras().getString("path");
        System.out.print("in image display class:" + img_path);
        img.setImageBitmap(photo);
        upload.setOnClickListener(this);
//        Intent i= new Intent(this, MainActivity.class);
//        i.putExtra("data", photo);
//        startActivity(i);

        }

    @Override
    public void onClick(View view) {
        Intent awsIntent= new Intent(getApplicationContext(), uploadS3.class);
        awsIntent.putExtra("path" , img_path);

        startActivity(awsIntent);



        //clarifai


    }

  //  @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        System.out.println("in activity result");
//        if(requestCode==1)
//        {
//            final ClarifaiClient client = new ClarifaiBuilder("a590fa7b18de43c59e9eaabaa1c05f3d").buildSync();
//            Model<Concept> generalModel = client.getDefaultModels().generalModel();
//
//            PredictRequest<Concept> request = generalModel.predict().withInputs(
//                    ClarifaiInput.forImage("https://s3.amazonaws.com/intellichef-userfiles-mobilehub-2071945737/s3Folder/s3Key.jpg")
//            );
//            List<ClarifaiOutput<Concept>> result = request.executeSync().get();
//
//            System.out.println(result);
//        }
    }


