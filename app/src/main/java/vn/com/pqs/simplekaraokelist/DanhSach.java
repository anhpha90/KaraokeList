package vn.com.pqs.simplekaraokelist;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import vn.com.pqs.adapter.BaiHatAdapter;
import vn.com.pqs.model.BaiHat;

public class DanhSach extends Fragment {

        public DanhSach() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         View view =  inflater.inflate(R.layout.danhsach, container, false);
        ListView lvdanhsach = (ListView) view.findViewById(R.id.lvdanhsach);
        MainActivity main1 = (MainActivity) getActivity();
        main1.DsAdapter = new BaiHatAdapter(getActivity(),R.layout.items,main1.dsBaihat);
        main1.DsAdapter.notifyDataSetChanged();

        lvdanhsach.setAdapter(main1.DsAdapter);

        return view;
    }

}
