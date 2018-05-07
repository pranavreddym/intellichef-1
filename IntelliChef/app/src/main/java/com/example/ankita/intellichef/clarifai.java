package com.example.ankita.intellichef;

import android.app.Activity;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;

/**
 * Created by ankita on 5/4/18.
 */

public class clarifai extends Activity {

    final ClarifaiClient client = new ClarifaiBuilder("a590fa7b18de43c59e9eaabaa1c05f3d").buildSync();


}
