package vn.com.pqs.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.com.pqs.model.BaiHat;
import vn.com.pqs.simplekaraokelist.MainActivity;
import vn.com.pqs.simplekaraokelist.R;

/**
 * Created by long on 03/12/2016.
 */
public class BaiHatAdapter extends ArrayAdapter<BaiHat> {
    //đối số 1: màn hình sử dụng layout này (giao diện này)
    Activity context;
    //layout cho từng dòng muốn hiển thị (làm theo khách hàng)
    int resource;
    //danh sách nguồn dữ liệu muốn hiển thị lên giao diện
    List<BaiHat> objects;
    MainActivity main2 = (MainActivity) getContext();
    public BaiHatAdapter(Activity context, int resource, List<BaiHat> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource=resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource, null);
        TextView txtMs = (TextView) row.findViewById(R.id.ms);
        TextView txtBh = (TextView) row.findViewById(R.id.bh);
        TextView txtCs = (TextView) row.findViewById(R.id.cs);
        TextView txtLr = (TextView) row.findViewById(R.id.lr);
        ImageButton imglike = (ImageButton) row.findViewById(R.id.imageButton);
        final BaiHat baiHat = this.objects.get(position);
        txtMs.setText(baiHat.getTxtms());
        txtBh.setText(baiHat.getTenBh());
        txtCs.setText(baiHat.getTxtcs());
        txtLr.setText(baiHat.getTxtLr());

               imglike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               xulythich(baiHat);
            }
        });
        imglike.setImageResource(baiHat.getImg());
        return row;
    }

    private void xulythich(BaiHat baiHat) {
        String mschon = baiHat.getTxtms().toString();
        if(baiHat.getThich()){
            updateThich(mschon,false);
         baiHat.setThich(false);
         baiHat.setImg(R.drawable.addfav);
         notifyDataSetChanged();
                  }else{
            updateThich(mschon,true);
            baiHat.setThich(true);
           baiHat.setImg(R.drawable.added);
            notifyDataSetChanged();
        }
        main2.addDsYeuThich();
        notifyDataSetChanged();
        notifyAll();
                    }

    private void updateThich(String ms,boolean thich) {
        ContentValues values = new ContentValues();
        if(thich==false){
        values.put("YEUTHICH","0");}
        else{
            values.put("YEUTHICH","1");
        }
       int ret = main2.database.update("ArirangSongList",values,"MABH='"+ms+"'",null);
        notifyDataSetChanged();
    if(ret==0){
        Toast.makeText(context,"false",Toast.LENGTH_SHORT).show();
    }else{
        Toast.makeText(context,"true",Toast.LENGTH_SHORT).show();
    }
    }
}
