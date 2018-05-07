package com.example.ankita.intellichef;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.lambdainvoker.*;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

import java.util.ArrayList;

import static com.example.ankita.intellichef.AuthenticatorActivity.credentialsProvider;


public class recipeDisplay extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;

    private static CustomAdapter adapter;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String s= credentialsProvider.getIdentityPoolId();

        String result= getIntent().getStringExtra("result");
        System.out.println("here in recipe display" + result);
        //new code connecting to lambda

        // Create an instance of CognitoCachingCredentialsProvider
        CognitoCachingCredentialsProvider cognitoProvider = new CognitoCachingCredentialsProvider(
                this.getApplicationContext(),s, Regions.US_EAST_1);

// Create LambdaInvokerFactory, to be used to instantiate the Lambda proxy.
        LambdaInvokerFactory factory = new LambdaInvokerFactory(this.getApplicationContext(),
                Regions.US_EAST_1, cognitoProvider);

// Create the Lambda proxy object with a default Json data binder.
// You can provide your own data binder by implementing
// LambdaDataBinder.
        final MyInterface myInterface = factory.build(MyInterface.class);

        //RequestClass request = new RequestClass("John", "Doe");
// The Lambda function invocation results in a network call.
// Make sure it is not called from the main thread.
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.
                try {
                    return myInterface.getRecipe(params[0]);
                } catch (LambdaFunctionException lfe) {
                    Log.e("Tag", "Failed to invoke echo", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {


                // Do a toast
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                showRecipeList();
            }
        }.execute(result);


    }
    void showRecipeList() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDialog = new Dialog(this);

        listView=(ListView)findViewById(R.id.list);


        dataModels= new ArrayList<>();

        dataModels.add(new DataModel("Apple Pie", "Android 1.0", "1","September 23, 2008"));
        dataModels.add(new DataModel("Banana Bread", "Android 1.1", "2","February 9, 2009"));
        dataModels.add(new DataModel("Cupcake", "Android 1.5", "3","April 27, 2009"));
        dataModels.add(new DataModel("Donut","Android 1.6","4","September 15, 2009"));
        dataModels.add(new DataModel("Eclair", "Android 2.0", "5","October 26, 2009"));
        dataModels.add(new DataModel("Froyo", "Android 2.2", "8","May 20, 2010"));
        dataModels.add(new DataModel("Gingerbread", "Android 2.3", "9","December 6, 2010"));
        dataModels.add(new DataModel("Honeycomb","Android 3.0","11","February 22, 2011"));
        dataModels.add(new DataModel("Ice Cream Sandwich", "Android 4.0", "14","October 18, 2011"));
        dataModels.add(new DataModel("Jelly Bean", "Android 4.2", "16","July 9, 2012"));
        dataModels.add(new DataModel("Kitkat", "Android 4.4", "19","October 31, 2013"));
        dataModels.add(new DataModel("Lollipop","Android 5.0","21","November 12, 2014"));
        dataModels.add(new DataModel("Marshmallow", "Android 6.0", "23","October 5, 2015"));

        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);
                //Toast.makeText(getApplicationContext(), " hello", Toast.LENGTH_LONG).show();
//                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
                ShowPopup();

            }
        });

    }
    public void ShowPopup(){
        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.custompopup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), WebViewPage.class);
                startActivity(i);
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
