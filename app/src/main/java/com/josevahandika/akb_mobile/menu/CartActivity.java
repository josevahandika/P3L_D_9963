package com.josevahandika.akb_mobile.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.josevahandika.akb_mobile.entity.Cart;
import com.josevahandika.akb_mobile.entity.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.GET;

public class CartActivity extends AppCompatActivity {

    private List<Cart> listCart;
    private RecyclerView recyclerView;
    private AdapterMenu adapterMenu;
    private AdapterMenuAfterQR adapterMenuAfterQR;
    private AdapterCart adapterCart;
    SharedPref sharedPref;
    SearchView searchView;
    Context context;
    Button finishBtn;
    TextView displayTotalharga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        setTitle("Cart");
        setContentView(R.layout.activity_cart);

        //get data laundry

        //recycler view
        displayTotalharga = findViewById(R.id.totalBayar);
        listCart = new ArrayList<Cart>();
        recyclerView = findViewById(R.id.recycler_view_cart);
        searchView = findViewById(R.id.input_search);
        finishBtn = findViewById(R.id.btnFinishCart);
        adapterCart = new AdapterCart(listCart, this);
        sharedPref = new SharedPref(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCart);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Cart> menuSearch = new ArrayList<>();
                for(Cart menu : listCart)
                {
                    if(menu.getNama_menu().toLowerCase().contains(s.toLowerCase()))
                    {
                        menuSearch.add(menu);
                    }
                }
                adapterCart.filter(menuSearch);
                return true;
            }
        });
//        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishTransaksi();
            }
        });
        getAllList();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        SharedPref sharedPref = new SharedPref(this);
//        String reservasi = sharedPref.getReservasi();
//        if (reservasi.equalsIgnoreCase("0")){
//            recyclerView.setAdapter(adapterCart);
//        }else{
//            Intent intent = new Intent();
//            startActivity(new Intent(CartActivity.this, MenuAfterQRActivity.class));
//            finish();
//        }
//        getMenu();
//    }

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
    public void getAllList()
    {
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, MenuAPI.URL_GET_ITEM + sharedPref.getIdReservasi(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int tempTotal=0;
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    tempTotal = response.getInt("total_semuanya");

                    if (!listCart.isEmpty())
                        listCart.clear();

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String nama_menu = jsonObject.optString("nama_menu");
                        String total_harga = jsonObject.optString("subtotal");
                        String jumlah = jsonObject.optString("jumlah");

                        Cart cart = new Cart(nama_menu, total_harga, jumlah);

                        listCart.add(cart);
                    }
                    adapterCart.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                displayTotalharga.setText(String.valueOf(tempTotal));
                Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + BuildConfig.token);
                return params;
            }
        };
        queue.add(stringRequest);
    }
    public void finishTransaksi(){
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, TransaksiAPI.URL_FINISH + sharedPref.getIdReservasi(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                sharedPref.setIsLogin(false);
                sharedPref.setIdReservasi(0);
                //displayTotalHarga.setText(convertToCurrency(String.valueOf(tempTotal)));
                Toast.makeText(context, response.optString("message"), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(CartActivity.this,MenuActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + BuildConfig.token);
                return params;
            }
        };
        queue.add(stringRequest);
        }
    }