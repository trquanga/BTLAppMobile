package com.example.appnghenhac;

import static com.example.appnghenhac.MainActivity.listBaiHat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
// we specify the custom MyVieHolder which gives us access to our views
//adapter's role is to convert an object at a position into a list row item
public class MusicApdapter extends RecyclerView.Adapter<MusicApdapter.MyVieHolder> {

    // Provide a direct reference to each of the views within a data item
    // Hold view item_view.xml
    public class MyVieHolder extends RecyclerView.ViewHolder {

        //variable for any view that will be set as you render a row
        TextView file_name;
        TextView artist_name;
        ImageView album_art;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public MyVieHolder(@NonNull View itemView) {
            super(itemView);
            file_name = itemView.findViewById(R.id.music_file_name);
            artist_name = itemView.findViewById(R.id.music_artist_name);
            album_art = itemView.findViewById(R.id.album_image);
        }
    }


    private Context mContext;
    // Store a member variable for the musics
    private ArrayList<FileBaiHat> mFiles;

    MusicApdapter(Context mContext, ArrayList<FileBaiHat> mFiles) {
        this.mFiles = mFiles;
        //context allows activity access resources
        this.mContext = mContext;
    }

    //onCreateViewHolder to inflate the item layout and create the holder
    // chuyển layout đó thành một đối tượng trong file java
    //inflating a layout from XML and returning the holder

    @NonNull
    @Override
    public MyVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // tạo đối tượng view cho context cụ thể
        View view  = LayoutInflater.from(mContext).inflate(
                R.layout.item_view,
                /* Thông qua tham số này, hệ thống sẽ biết được nơi view được hiển thị và cách nó sẽ được định vị và bố trí */
                /* parent là fragment_danh_sach_bai_hat */
                parent,
                /*Không gắn vào parent*/
                false);
        // Chuyển thành đối tượng MyVieHolder
        return new MyVieHolder(view);
    }

    //onBindViewHolder to set the view attributes based on the data
    @Override
    public void onBindViewHolder(@NonNull MyVieHolder holder, @SuppressLint("RecyclerView") int position) {
        Uri uri = Uri.parse(listBaiHat.get(position).getPath());
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());

        holder.file_name.setText(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        holder.artist_name.setText(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
        byte[] image = new byte[0];
        try {
            image = getAlbumArt(mFiles.get(position).getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(image != null) {
            Glide.with(mContext).asBitmap()
                    .load(image)
                    .into(holder.album_art);
        }
        else {
            Glide.with(mContext)
                    .load(R.drawable.item_img)
                    .into(holder.album_art);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }




    private byte[] getAlbumArt(String uri) throws IOException {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
