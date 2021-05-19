package com.josevahandika.akb_mobile.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.josevahandika.akb_mobile.BuildConfig;
import com.josevahandika.akb_mobile.R;
import com.josevahandika.akb_mobile.api.MenuAPI;
import com.josevahandika.akb_mobile.api.TransaksiAPI;
import com.josevahandika.akb_mobile.entity.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

public class MenuAfterQRActivity extends AppCompatActivity {
    private List<Menu> menuList;
    private RecyclerView recyclerView;
//    private AdapterMenu adapterMenu;
    private AdapterMenuAfterQR adapterMenuAfterQR;
    SearchView searchView;
    Context context;
    FloatingActionButton qrBtn,selesaiBtn;
    SharedPref sharedPref;
    Button bayarBtn;
    public static String ARRAY_LIST_INTENT = "ARRAY_LIST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setTitle("Menu");
        setContentView(R.layout.activity_menu_after_qr);
        //get data laundry


        //recycler view
        menuList = new ArrayList<Menu>();
        recyclerView = findViewById(R.id.recycler_view_menu_after_qr);
        searchView = findViewById(R.id.input_search);
        bayarBtn = findViewById(R.id.btnBayar);
        selesaiBtn = findViewById(R.id.btnSelesai);
        sharedPref = new SharedPref(this);
        adapterMenuAfterQR = new AdapterMenuAfterQR(menuList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterMenuAfterQR);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Menu> menuSearch = new ArrayList<>();
                for(Menu menu : menuList)
                {
                    if(menu.getNama_menu().toLowerCase().contains(s.toLowerCase()))
                    {
                        menuSearch.add(menu);
                    }
                }
                adapterMenuAfterQR.filter(menuSearch);
                return true;
            }
        });
        getMenu();
//        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());

        selesaiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataTerisi();
//                adapterMenuAfterQR.disableAddMinusButton();
                //startActivity(new Intent(MenuAfterQRActivity.this,ScanQRActivity.class));
            }
        });
        bayarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuAfterQRActivity.this,CartActivity.class));

            }
        });
    }
    public void showDataTerisi(){
        List<Menu> showMenu = new ArrayList<>();
        for(Menu menu : menuList)
        {
            if(menu.getTempJumlah() != 0)
            {
                showMenu.add(menu);
            }
        }
        addOrder(showMenu);
        finish();
        startActivity(getIntent());
//        adapterMenuAfterQR.filter(showMenu);
    }
    public JSONObject addOrderBody(List<Menu> listMenu)
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id_transaksi", sharedPref.getIdReservasi());

            JSONArray jsonArray = new JSONArray();
            for(Menu menu: listMenu)
            {
                JSONObject jsonObjectBody = new JSONObject();

                jsonObjectBody.put("id_menu", String.valueOf(menu.getId()));
                jsonObjectBody.put("jumlah", String.valueOf(menu.getTempJumlah()));
                jsonArray.put(jsonObjectBody);
            }
            jsonObject.put("data",jsonArray);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    public void addOrder(List<Menu> listMenu)
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(POST, MenuAPI.URL_STORE_DETAIL, addOrderBody(listMenu) , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MenuAfterQRActivity.this, "Berhasil menambah order!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuAfterQRActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + BuildConfig.token);
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 0) {
//            if (resultCode == Activity.RESULT_OK && data != null) {
//                String value = data.getStringExtra("SCAN_RESULT");
//            }
//        } else {
//            Toast.makeText(context, "SCAN GAGAL", Toast.LENGTH_SHORT).show();
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
    public void getMenu() {
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());

//        final ProgressDialog progressDialog;
//        progressDialog = new ProgressDialog(this.getApplicationContext());
//        progressDialog.setMessage("loading...");
//        progressDialog.setTitle("Mengambil semua data menu....");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, MenuAPI.URL_SELECT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    if (!menuList.isEmpty())
                        menuList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        int id = jsonObject.optInt("id");
                        String nama_menu = jsonObject.optString("nama_menu");
                        String deskripsi = jsonObject.optString("deskripsi");
                        String takaran_saji = jsonObject.optString("takaran_saji");
                        String harga = jsonObject.optString("harga");
                        String kategori = jsonObject.optString("kategori");
                        String unit = jsonObject.optString("unit");
                        String id_bahan = jsonObject.optString("id_bahan");

                        Menu menu = new Menu(id, nama_menu, takaran_saji, harga,kategori, unit,deskripsi, id_bahan, 0);

                        menuList.add(menu);

                    }
                    adapterMenuAfterQR.setMenuList(menuList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

//    public void sendAPITransaksi(){
//        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
//
//        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, TransaksiAPI.URL_UPDATE,
//                null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
////                progressDialog.dismiss();
//                try {
//                    JSONArray jsonArray = response.getJSONArray("data");
//
//                    if (!menuList.isEmpty())
//                        menuList.clear();
//
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//
//                        int id = jsonObject.optInt("id");
//                        String nama_menu = jsonObject.optString("nama_menu");
//                        String deskripsi = jsonObject.optString("deskripsi");
//                        String takaran_saji = jsonObject.optString("takaran_saji");
//                        String harga = jsonObject.optString("harga");
//                        String kategori = jsonObject.optString("kategori");
//                        String unit = jsonObject.optString("unit");
//                        String id_bahan = jsonObject.optString("id_bahan");
//
//                        Menu menu = new Menu(id, nama_menu, takaran_saji, harga,kategori, unit,deskripsi, id_bahan, 0);
//
//                        menuList.add(menu);
//
//                    }
//                    adapterMenuAfterQR.setMenuList(menuList);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                progressDialog.dismiss();
//                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        queue.add(stringRequest);
//    }
}
