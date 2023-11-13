package com.example.appnghenhac;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
// we specify the custom MyVieHolder which gives us access to our views
//adapter's role is to convert an object at a position into a list row item
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Hold view item_view.xml
    public class MyViewHolder extends RecyclerView.ViewHolder {

        //variable for any view that will be set as you render a row
        TextView category_text;
        ImageView category_img;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            category_text = itemView.findViewById(R.id.category_text);
            category_img = itemView.findViewById(R.id.category_img);
        }
    }


    private Context mContext;
    // Store a member variable for the musics
    private ArrayList<Category> categories;

    CategoryAdapter(Context mContext, ArrayList<Category> categories) {
        this.categories = categories;
        //context allows activity access resources
        this.mContext = mContext;
    }

    //onCreateViewHolder to inflate the item layout and create the holder
    // chuyển layout đó thành một đối tượng trong file java
    //inflating a layout from XML and returning the holder

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // tạo đối tượng view cho context cụ thể
        View view  = LayoutInflater.from(mContext).inflate(
                R.layout.category_item,
                /* Thông qua tham số này, hệ thống sẽ biết được nơi view được hiển thị và cách nó sẽ được định vị và bố trí */
                /* parent là fragment_danh_sach_bai_hat */
                parent,
                /*Không gắn vào parent*/
                false);
        // Chuyển thành đối tượng MyVieHolder
        return new MyViewHolder(view);
    }

    //onBindViewHolder to set the view attributes based on the data
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.category_text.setText(categories.get(position).getName());
        holder.category_img.setImageResource(categories.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
