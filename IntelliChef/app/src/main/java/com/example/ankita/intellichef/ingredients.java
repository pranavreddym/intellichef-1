package com.example.ankita.intellichef;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

public class ingredients extends AsyncTask {

    Context context;
    StringBuilder s= new StringBuilder();
    ArrayList<String> ingredients = new ArrayList<>();

                  //  Toast.makeText(getApplicationContext(), "in ingredients java", Toast.LENGTH_LONG).show();


    @Override
    protected String doInBackground(Object[] objects) {

         context= (Context)objects[0];
        final ClarifaiClient client = new ClarifaiBuilder("a590fa7b18de43c59e9eaabaa1c05f3d").buildSync();
        Model<Concept> generalModel = client.getDefaultModels().generalModel();
        loadWords(context.getResources().openRawResource(
                context.getResources().getIdentifier("ingredients.txt",
                        "raw", context.getPackageName())));
        PredictRequest<Concept> request = generalModel.predict().withInputs(
                ClarifaiInput.forImage("https://s3.amazonaws.com/intellichef-userfiles-mobilehub-2071945737/s3Folder/s3Key.jpg")
        );
        List<ClarifaiOutput<Concept>> result = request.executeSync().get();
        ListIterator<ClarifaiOutput<Concept>> c= result.listIterator();
        System.out.println("going inside while");

// *********Code to be chaged*********************
       int i=0;
//        while(c.hasNext())
//            i++;
//        System.out.println("het "+i);
        int i1= c.next().data().size();
        System.out.println("i1 is "+i1);
        c= result.listIterator();
        ClarifaiOutput<Concept> conceptClarifaiOutput= c.next();
        //System.out.println(" hello " +  conceptClarifaiOutput);


            int check=0;
            while(check<i1)
            {
                //System.out.println("check" + check);
                if (ingredients.contains(conceptClarifaiOutput.data().get(check).name()))
                    s.append(conceptClarifaiOutput.data().get(check).name() + "+") ;
                //System.out.println(conceptClarifaiOutput.data().get(check).name()+"+");
               check++;
            }
        System.out.println("crunchy ");
        System.out.println(s.toString());

               return s.toString();
    }

    @Override
    protected void onPostExecute(Object s) {

        Intent i = new Intent(context, recipeDisplay.class);
        //i.putExtra("clarifai", s);
        i.putExtra("result",(String) s);
        context.startActivity(i);
    }
    public void loadWords(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line ="";
            while ((line = br.readLine()) != null) {
                ingredients.add(line);
            }
            br.close();
        }catch (IOException ie){

        }

    }
}





