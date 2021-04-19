package com.josevahandika.akb_mobile.menu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.josevahandika.akb_mobile.R;
import com.josevahandika.akb_mobile.api.MenuAPI;
import com.josevahandika.akb_mobile.entity.Menu;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.DELETE;

public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.adapterMenuViewHolder>  {
    private List<Menu> menuList;
    private Context context;
    View view;

//    public AdapterPesan(List<Pesan> pesanList, List<Pesan> pesanFilterList, Context context, AdapterPesan.deleteItemListener deleteItemListener) {
//        this.pesanList = pesanList;
//        this.pesanFilterList = pesanFilterList;
//        this.context = context;
//        this.deleteItemListener = deleteItemListener;
//    }

    public AdapterMenu(List<Menu> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterMenu.adapterMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_menu, parent, false);
        return new adapterMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterMenuViewHolder holder, int position) {
        final Menu menu = menuList.get(position);

        holder.txtNamaMenu.setText(menu.getNama_menu());
        holder.txtHarga.setText(menu.getHarga());
        holder.txtDeskripsi.setText(menu.getDeskripsi());
        holder.txtKategori.setText(menu.getKategori());
    }
    public void filter(List<Menu> menuList){
        this.menuList=menuList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class adapterMenuViewHolder extends RecyclerView.ViewHolder{
        TextView txtNamaMenu, txtHarga, txtDeskripsi, txtKategori;
        private CardView cardPesan;

        public adapterMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaMenu = itemView.findViewById(R.id.tvNamaMenu);
            txtHarga = itemView.findViewById(R.id.tvHarga);
            txtDeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            txtKategori = itemView.findViewById(R.id.tvKategori);
        }

    }


}
