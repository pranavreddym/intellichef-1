package com.example.ankita.intellichef;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

/**
 * Created by ankita on 5/4/18.
 */


   // import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;
    public interface MyInterface {

        /**
         * Invoke the Lambda function "AndroidBackendLambdaFunction".
         * The function name is the method name.
         */
        @LambdaFunction
        String getRecipe(String request);

    }

