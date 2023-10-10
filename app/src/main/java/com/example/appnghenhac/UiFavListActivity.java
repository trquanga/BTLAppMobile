package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class UiFavListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_fav_list);

        ListView fav_lv = (ListView) findViewById(R.id.fav_lv);

        ArrayList<HashMap<String, Object>> list_item = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("image", R.drawable.ic_launcher_background);
            hashMap.put("text1", "Ten bai hat");
            hashMap.put("text2", "Ten ca si");
            list_item.add(hashMap);
        }
        String[] from = {"image", "text1", "text2"};
        int to[] = {R.id.fav_lv_item_img, R.id.fav_lv_item_tv1, R.id.fav_lv_item_tv2};

        SimpleAdapter simpleAdapter =
                new SimpleAdapter(
                        this,
                        list_item,
                        R.layout.favlist_itemview,
                        from,
                        to);
        fav_lv.setAdapter(simpleAdapter);
    }
}