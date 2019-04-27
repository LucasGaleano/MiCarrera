package com.example.pyrca.micarrera.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.pyrca.micarrera.R;

import java.util.List;


public class FotosAdapter extends BaseAdapter {
        private Context mContext;
        private String[] mLetters;
        public String pathName;
        public Bitmap bmp;
        public List<String> lFotos;


    public FotosAdapter(Context context, List<String> lFotos){
            this.mContext = context;
            this.mLetters = lFotos.toArray(new String[0]);
            this.lFotos=lFotos;
        }


    @Override
    public int getCount() {
        return mLetters.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            // get layout from xml file
            gridView = inflater.inflate(R.layout.fotos_grid_item, null);

            // pull views
            ImageView letterView = (ImageView) gridView
                    .findViewById(R.id.grid_item_foto);

            pathName = this.lFotos.get(position);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            bmp = BitmapFactory.decodeFile(pathName,options);
            letterView.setImageBitmap(bmp);

        } else {
            gridView = (View) convertView;
        }
        return gridView;
    }


}

