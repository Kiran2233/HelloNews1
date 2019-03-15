package com.example.hellonews;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class Listnewsadapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> arr;

    public Listnewsadapter(Activity activity, ArrayList<HashMap<String, String>> arr) {
        this.activity = activity;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    Viewholder holder = null;
if(convertView ==null){holder = new Viewholder();
convertView = LayoutInflater.from(activity).inflate(R.layout.listitem,parent,false);
    holder.img = (ImageView) convertView.findViewById(R.id.img);
    holder.author = (TextView) convertView.findViewById(R.id.author);
    holder.title = (TextView) convertView.findViewById(R.id.title);
    holder.details = (TextView) convertView.findViewById(R.id.details);
    holder.time = (TextView) convertView.findViewById(R.id.time);
    convertView.setTag(holder);
}
else
{holder = (Viewholder) convertView.getTag();
}
        holder.img.setId(position);
        holder.author.setId(position);
        holder.title.setId(position);
        holder.details.setId(position);
        holder.time.setId(position);

        HashMap<String, String> song = new HashMap<>();
        song = arr.get(position);

        try{
            holder.author.setText(song.get(MainActivity.KEY_AUTHOR));
            holder.title.setText(song.get(MainActivity.KEY_TITLE));
            holder.time.setText(song.get(MainActivity.KEY_PUBLISHEDAT));
            holder.details.setText(song.get(MainActivity.KEY_DESCRIPTION));

            if(song.get(MainActivity.KEY_URLTOIMAGE).toString().length() < 5)
            {
                holder.img.setVisibility(View.GONE);
            }else{
                Picasso.with(activity)
                        .load(song.get(MainActivity.KEY_URLTOIMAGE).toString())
                        .resize(300, 200)
                        .into(holder.img);
            }
        }catch(Exception e) {}
        return convertView;
    }
}

class Viewholder {
    ImageView img;
    TextView author, title, details, time;
}