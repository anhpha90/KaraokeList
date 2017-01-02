package vn.com.pqs.simplekaraokelist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import vn.com.pqs.adapter.BaiHatAdapter;
import vn.com.pqs.model.BaiHat;

public class MainActivity extends AppCompatActivity {
    ArrayList<BaiHat> dsBaiHatTimKiem;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    public ViewPager viewPager;
    MaterialSearchView searchView;
    ArrayList<BaiHat> dsBaihat;
    public ArrayList<BaiHat> dsYeuthich;
     BaiHatAdapter baiHatAdapter;
    public BaiHatAdapter DsAdapter;
    public BaiHatAdapter YtAdapter;

    private int[] tabIcons = {R.drawable.ds, R.drawable.yt, R.drawable.tt
    };
    public static String DATABASE_NAME = "Arirang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dsYeuthich = new ArrayList<>();
        dsBaihat = new ArrayList<>();

        addDatabase();
        addDsBaiHat();
       // addDsYeuThich();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                ListView lvdanhsach = (ListView) findViewById(R.id.lvdanhsach);
                baiHatAdapter = new BaiHatAdapter(MainActivity.this, R.layout.items, dsBaihat);
                baiHatAdapter.notifyDataSetChanged();
                lvdanhsach.setAdapter(baiHatAdapter);

            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && !newText.isEmpty()) {
                    dsBaiHatTimKiem = new ArrayList<>();
                    for (BaiHat objects : dsBaihat) {
                        newText = newText.toLowerCase();
                        if (objects.getTenBh().toLowerCase().contains(newText)) {
                            dsBaiHatTimKiem.add(objects);
                        }
                    }
                    ListView lvdanhsach = (ListView) findViewById(R.id.lvdanhsach);
                    baiHatAdapter = new BaiHatAdapter(MainActivity.this, R.layout.items, dsBaiHatTimKiem);
                    lvdanhsach.setAdapter(baiHatAdapter);
                } else {
                    ListView lvdanhsach = (ListView) findViewById(R.id.lvdanhsach);
                    baiHatAdapter = new BaiHatAdapter(MainActivity.this, R.layout.items, dsBaihat);
                    lvdanhsach.setAdapter(baiHatAdapter);
                }

                return true;
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }
        private void addDsBaiHat() {
        dsBaihat.clear();
        dsYeuthich.clear();

        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.query("ArirangSongList", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String mabh = cursor.getString(0);
            String tenbh = cursor.getString(1);
            String casi = cursor.getString(3);
            String lr = cursor.getString(2);
            int yeuthich = cursor.getInt(5);
            boolean blthich;
            if (yeuthich == 0) {
                blthich = false;
            } else {
                blthich = true;
            }
            BaiHat baiHat = new BaiHat();
            baiHat.setTxtms(mabh);
            baiHat.setTenBh(tenbh);
            baiHat.setTxtcs(casi);
            baiHat.setTxtLr(lr);
            baiHat.setThich(blthich);
            if (blthich == true) {
                baiHat.setImg(R.drawable.added);
                dsYeuthich.add(baiHat);
            }
            if (blthich == false) {
                baiHat.setImg(R.drawable.addfav);
            }
            dsBaihat.add(baiHat);

        }

        cursor.close();

    }

    private void addDatabase() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Sao chép CSDL vào hệ thống thành công", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = layDuongDanLuuTru();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) {
                f.mkdir();
            }
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception ex) {
            Log.e("Loi_SaoChep", ex.toString());
        }
    }

    private String layDuongDanLuuTru() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new DanhSach(), "  ZANHSÁCH");
        adapter.addFrag(new YeuThich(), "  YÊUTHÍCH");
        adapter.addFrag(new LienHe(), "  LIÊNHỆ");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public int getItemPosition(Object object) {

            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
            //return mFragmentTitleList.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            switch (position) {
                case 0:

                    break;
                case 1:

                    break;
                case 2:

                    break;
            }
            return super.instantiateItem(container, position);
        }

    }


}
