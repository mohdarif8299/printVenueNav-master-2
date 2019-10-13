 package com.example.myapplication;
 import android.Manifest;
 import android.annotation.TargetApi;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.content.SharedPreferences;
 import android.content.pm.PackageManager;
 import android.content.res.Configuration;
 import android.database.Cursor;
 import android.os.Build;
 import android.os.Bundle;
 import android.os.Environment;
 import android.provider.ContactsContract;
 import android.view.Menu;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.Button;
 import android.widget.ExpandableListAdapter;
 import android.widget.ExpandableListView;
 import android.widget.LinearLayout;
 import android.widget.TextView;
 import android.widget.Toast;
 import androidx.annotation.NonNull;
 import androidx.annotation.Nullable;
 import androidx.appcompat.app.ActionBarDrawerToggle;
 import androidx.appcompat.app.AlertDialog;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.app.ActivityCompat;
 import androidx.core.content.ContextCompat;
 import androidx.core.view.GravityCompat;
 import androidx.drawerlayout.widget.DrawerLayout;
 import androidx.fragment.app.Fragment;
 import androidx.fragment.app.FragmentTransaction;
 import com.example.myapplication.Adapter.CustomExpendableListAdapter;
 import com.example.myapplication.Adapter.ItemsAdapter;
 import com.example.myapplication.Fragment.CardFragment;
 import com.example.myapplication.Fragment.CardProduct;
 import com.example.myapplication.Fragment.Custom_Fragment;
 import com.example.myapplication.Fragment.HomeFragment;
 import com.example.myapplication.Fragment.ProductsFragments;
 import com.example.myapplication.Helper.FragmentnavigationManager;
 import com.example.myapplication.Interface.NavigationManager;
 import com.example.myapplication.PreferenceManager.MyPreference;

 import org.json.JSONObject;

 import java.io.File;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.List;
 import java.util.Map;
 import java.util.TreeMap;
public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String,List<String>> lstChild;
    private NavigationManager navigationManager;
    FragmentTransaction ft;
    public static final int PERMISSION_REQUEST_CONTACT = 100;
    public static final String VCF_DIRECTORY = "/vcf_contacts";
    private File vcfFile;
    FileWriter fw;
    List<String> title;
    Context c=getBaseContext();
    Button button;
    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
//        else if(f instanceof productCustomize){
//            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.container,new PensFragment());
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            ft.commit();
//        }
        else if (f instanceof CardProduct){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,new CardFragment());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
        else if (f instanceof ProductsFragments){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,new HomeFragment());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
        else if(f instanceof Custom_Fragment){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,new ProductsFragments());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }

        }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_CONTACT: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContact();
                } else {
                    Toast.makeText(this, "No Permissions ", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }
    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSION_REQUEST_CONTACT);
                }
            } else {
                getContact();
            }
        } else {
            getContact();
        }
    }
    public void getContact() {
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        File vdfDirectory=this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (!vdfDirectory.exists()) {
            vdfDirectory.mkdirs();
        }
        vcfFile = new File(vdfDirectory, "android_"+ Calendar.getInstance().getTimeInMillis() + ".vcf");
        Toast.makeText(this,vdfDirectory.getPath()+"",Toast.LENGTH_LONG).show();

        try {
            fw = new FileWriter(vcfFile);
            fw.write("BEGIN:VCARD\r\n");
            fw.write("VERSION:3.0\r\n");
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"fileWriter ni chala"+e,Toast.LENGTH_LONG).show();
        }
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            try {
                fw.write("FN:" + name + "\r\n");
                fw.write("TEL;TYPE=WORK,VOICE:" + phoneNumber + "\r\n");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this,"No Contact ",Toast.LENGTH_LONG).show();
            }
        }
        try {
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        phones.close();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (sharedpreferences.contains(Email)) {
//            email.setText(sharedpreferences.getString(Email, ""));
//        }
        ft = getSupportFragmentManager().beginTransaction();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        expandableListView = findViewById(R.id.navlist);
        navigationManager = FragmentnavigationManager.getmInstance(this);
        askForContactPermission();
        View listHeaderView = getLayoutInflater().inflate(R.layout.nav_header,null,false);
        expandableListView.addHeaderView(listHeaderView);
        getData();
        addDrawersItem();
        setUpDrawer();
        if (savedInstanceState == null)
            selectFirstItemAsDefault();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("ColorPress");
        TextView sign = findViewById(R.id.signin);
        LinearLayout home_layout = findViewById(R.id.home);
        home_layout.setOnClickListener(v1->{
            mDrawerLayout.closeDrawer(GravityCompat.START);
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            HomeFragment homeFragment=new HomeFragment();
            fragmentTransaction.replace(R.id.container,homeFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        });
        if(new MyPreference(getApplicationContext()).isUserLogedOut()) {
            sign.setOnClickListener(v->{
                Intent intent = new Intent(MainActivity.this, LoginSignUpMain.class);
                startActivity(intent);
            });
        }
        else {
            sign.setText("Hello  "+new MyPreference(getApplicationContext()).getName());
            sign.setEnabled(false);
        }
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    private void selectFirstItemAsDefault() {
        if (navigationManager!=null){
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            HomeFragment homeFragment=new HomeFragment();
            fragmentTransaction.replace(R.id.container,homeFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }

    private void setUpDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.open, R.string.close){
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("ColorPress");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle("ColorPress");
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    private void addDrawersItem() {
        adapter = new CustomExpendableListAdapter(this, lstTitle, lstChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(lstTitle.get(groupPosition));
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                getSupportActionBar().setTitle("");
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String key = (String) lstChild.keySet().toArray()[groupPosition];
                List<String> list = lstChild.get(key);
                if(list.get(childPosition).equals("Logout")) {
                    SharedPreferences preferences =getSharedPreferences("LoginDetails",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                return false;
            }
        });
    }
    private void getData(){
        title = new ArrayList<>();
        title.add("Office Products");
        title.add("Business Cards");
        title.add("Clothing");
        title.add("Photo Gifts");
        title.add("Office Supplies");
        title.add("Marketing Materials");
        List<String> manageAccount = new ArrayList<>();
        if (new MyPreference(getApplicationContext()).isUserLogedOut()){
            title.add("Share And Feedback");
            manageAccount.add("Share App");
            manageAccount.add("Feddback");
        }
        else {
            title.add("Manage Account");
            manageAccount.add("Logout");
            manageAccount.add("Your Orders");
        }
        List<String> productForOffice = new ArrayList();
        productForOffice.add("Letterheads");
        productForOffice.add("Notebooks");
        productForOffice.add("Labels");
        productForOffice.add("Brochures");
        productForOffice.add("Stamps");
        List<String> photoGifts = new ArrayList();
        photoGifts.add("Calender");
        photoGifts.add("Photo Album");
        List<String> clothings = new ArrayList();
        clothings.add("T-Shirts");
        clothings.add("Shirts");
        clothings.add("Bags");
        clothings.add("Caps");
        List<String> officeSupplies = new ArrayList();
        officeSupplies.add("Mugs");
        officeSupplies.add("Mouse Pads");
        officeSupplies.add("USB Flash Drives");
        officeSupplies.add("Keychanis");
        officeSupplies.add("Pens");
        List<String> businessCard = new ArrayList();
        businessCard.add("Visiting Cards");
        businessCard.add("Invitation Cards");
        List<String> marketingMaterials = new ArrayList();
        marketingMaterials.add("Posters");
        marketingMaterials.add("Banners");
        marketingMaterials.add("Stickers");
        lstChild = new TreeMap<>();
        lstChild.put(title.get(0),productForOffice);
        lstChild.put(title.get(1),businessCard);
        lstChild.put(title.get(2),clothings);
        lstChild.put(title.get(3),photoGifts);
        lstChild.put(title.get(4),officeSupplies);
        lstChild.put(title.get(5),marketingMaterials);
        lstChild.put(title.get(6),manageAccount);
        lstTitle = new ArrayList<>(lstChild.keySet());
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);

        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem cart=menu.findItem(R.id.cart);

        cart.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                /*Toast.makeText(getBaseContext(),"cart",Toast.LENGTH_LONG).show();*/
                startActivity(new Intent(getBaseContext(), CartActivity.class));
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

}






