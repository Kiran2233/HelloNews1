package com.example.hellonews;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView listNews ;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listNews = (ListView) findViewById(R.id.listnews);
        if (Function.isNetworkAvailable(getApplicationContext())){
            GetNews gn = new GetNews();
            gn.execute();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    class GetNews extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            String xml = "";
            String urlparams= "";
            xml= Function.excuteGet("https://newsapi.org/v2/top-headlines?" +
                    "country=us&" +
                    "apiKey=5d7703f7f90640f299f8d73287d4a86e",urlparams);
            return xml;
        }



        @Override
        protected void onPostExecute(String xml) {


              if (xml.length() > 10) {
                  try{
                  JSONObject jsob = new JSONObject(xml);
                  JSONArray jray = jsob.optJSONArray("articles");
                  for (int i = 0; i < jray.length(); i++) {
                      JSONObject job = jray.getJSONObject(i);
                      HashMap<String, String> map = new HashMap<>();
                      map.put(KEY_AUTHOR, job.optString("Author").toString());
                      map.put(KEY_DESCRIPTION, job.optString("Description").toString());
                      map.put(KEY_URL, job.optString(KEY_URL).toString());
                      map.put(KEY_TITLE, job.optString(KEY_TITLE).toString());
                      map.put(KEY_URLTOIMAGE, job.optString(KEY_URLTOIMAGE).toString());
                      map.put(KEY_PUBLISHEDAT, job.optString(KEY_PUBLISHEDAT).toString());
                      dataList.add(map);
                  }
              }

            catch(JSONException e) {
                Toast.makeText(getApplicationContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
            }
            Listnewsadapter adapter = new Listnewsadapter(MainActivity.this,dataList);
          listNews.setAdapter(adapter);
          listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  Intent i = new Intent(MainActivity.this,Fullnews.class);
                  i.putExtra("url",dataList.get(+position).get(KEY_URL));
                  startActivity(i);
              }
          });
            }
            else{
        Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_SHORT).show();
    }
        }
}

}
