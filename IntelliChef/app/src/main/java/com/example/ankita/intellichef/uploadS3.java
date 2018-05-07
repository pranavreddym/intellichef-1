package com.example.ankita.intellichef;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

/**
 * Created by ankita on 4/29/18.
 */

//public class uploadS3 {
//
//
//
//}




  import android.app.Activity;
          import android.util.Log;

          import com.amazonaws.mobile.client.AWSMobileClient;
          import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
          import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
          import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
          import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
          import com.amazonaws.services.s3.AmazonS3Client;

          import java.io.File;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

public class uploadS3 extends Activity {
   // String path = getIntent().getExtras().getParcelable("path");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String path = getIntent().getExtras().getString("path");
        System.out.println("in upload s3" + path);
        AWSMobileClient.getInstance().initialize(this).execute();
        uploadWithTransferUtility(path);
        //Bundle bundle= getIntent().getExtras();


    }

    public void uploadWithTransferUtility(String path) {

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
                        .build();

        TransferObserver uploadObserver =
                transferUtility.upload(
                        "s3Folder/s3Key.jpg",
                        new File(path));

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
//                    final ClarifaiClient client = new ClarifaiBuilder("a590fa7b18de43c59e9eaabaa1c05f3d").buildSync();
//                    Model<Concept> generalModel = client.getDefaultModels().generalModel();
//
//                    PredictRequest<Concept> request = generalModel.predict().withInputs(
//                            ClarifaiInput.forImage("https://s3.amazonaws.com/intellichef-userfiles-mobilehub-2071945737/s3Folder/s3Key.jpg")
//                    );
//                    List<ClarifaiOutput<Concept>> result = request.executeSync().get();
//
//                    System.out.println(result);
                    new ingredients().execute(new Object[]{getApplicationContext()});
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("YourActivity", "ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
                System.out.println("!!!!!!!");
                System.out.println(ex);
            }

        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }

        Log.d("YourActivity", "Bytes Transferrred: " + uploadObserver.getBytesTransferred());
        Log.d("YourActivity", "Bytes Total: " + uploadObserver.getBytesTotal());
    }
}