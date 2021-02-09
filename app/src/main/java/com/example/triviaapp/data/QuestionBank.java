package com.example.triviaapp.data;

import android.app.Application;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.triviaapp.controller.AppController;
import com.example.triviaapp.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class QuestionBank extends AppCompatActivity
{


    ArrayList<Question> questionArrayList = new ArrayList<>();

    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack)
    {
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i<response.length(); i++)
                        {

                            try {

                                Question question = new Question(response.getJSONArray(i).get(0).toString(), response.getJSONArray(i).getBoolean(1));

                                //Add the questions to the list

                                questionArrayList.add(question);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        if (callBack != null)
                        {
                            callBack.processFinished(questionArrayList);
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(arrayRequest);
        return questionArrayList;
    }
}
