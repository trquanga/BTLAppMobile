package com.example.appnghenhac;

import static com.example.appnghenhac.MainActivity.listBaiHat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhSachBaiHat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachBaiHat extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    RecyclerView recyclerView;
    MusicApdapter musicApdapter;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DanhSachBaiHat() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhSachBaiHat.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhSachBaiHat newInstance(String param1, String param2) {
        DanhSachBaiHat fragment = new DanhSachBaiHat();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Tạo đối tượng view tương ứng từ fragment_danh_sach_bai_hat
        View view = inflater.inflate(R.layout.fragment_danh_sach_bai_hat, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        if(!(listBaiHat.size() < 1)) {
                                            //truyền vào context cua class
            musicApdapter = new MusicApdapter(getContext(), listBaiHat);
            //để kết nối dữ liệu và hiển thị nó trên giao diện người dùng.
            recyclerView.setAdapter(musicApdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(
                    getContext(),
                    RecyclerView.VERTICAL,
                    false));
        }
        return view;
    }
}