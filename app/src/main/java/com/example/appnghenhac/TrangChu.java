package com.example.appnghenhac;

import static com.example.appnghenhac.MainActivity.categories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrangChu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrangChu extends Fragment {


    ViewPager mViewPager;

    int currentSlide = 0;
    Timer timer;

    int[] images = {R.drawable.slide_img, R.drawable.slide_img2, R.drawable.slide_img3, R.drawable.slide_img4};

    SlideImageAdapter slideImageAdapter;

    RecyclerView mRecyclerView;
    CategoryAdapter categoryAdapter;
    View mView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrangChu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrangChu.
     */
    // TODO: Rename and change types and number of parameters
    public static TrangChu newInstance(String param1, String param2) {

        TrangChu fragment = new TrangChu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        autoScrollPager();
    }

    private void autoScrollPager() {
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentSlide == (images.length - 1)) {
                    currentSlide = 0;
                }
                mViewPager.setCurrentItem(currentSlide,true);
                currentSlide++;
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 3000, 3000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        mView = view;
        mViewPager = view.findViewById(R.id.view_pager);
        slideImageAdapter = new SlideImageAdapter(getContext(), images);
        mViewPager.setAdapter(slideImageAdapter);

        mRecyclerView = view.findViewById(R.id.category_recview);
        categoryAdapter = new CategoryAdapter(getContext(), categories);
        mRecyclerView.setAdapter(categoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(),
                RecyclerView.HORIZONTAL,
                false
        ));
        return view;
    }

}