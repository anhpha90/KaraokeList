package vn.com.pqs.simplekaraokelist;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import vn.com.pqs.adapter.BaiHatAdapter;
import vn.com.pqs.model.BaiHat;
import vn.com.pqs.simplekaraokelist.R;


public class YeuThich extends Fragment{
    BaiHatAdapter YtAdapter;
    public YeuThich() {
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
        View view =  inflater.inflate(R.layout.yeuthich, container, false);
        ListView lvdanhsach = (ListView) view.findViewById(R.id.lvyeuthich);
        MainActivity main1 = (MainActivity) getActivity();

        YtAdapter = new BaiHatAdapter(getActivity(),R.layout.items,main1.dsYeuthich);
        YtAdapter.notifyDataSetChanged();
        lvdanhsach.setAdapter(YtAdapter);


        return view;
    }

}