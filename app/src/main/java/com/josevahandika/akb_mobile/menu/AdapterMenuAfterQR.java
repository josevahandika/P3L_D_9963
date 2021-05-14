package com.josevahandika.akb_mobile.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.josevahandika.akb_mobile.R;
import com.josevahandika.akb_mobile.entity.Menu;

import java.util.List;

public class AdapterMenuAfterQR extends RecyclerView.Adapter<AdapterMenuAfterQR.adapterMenuAfterQRViewHolder>{
    private List<Menu> menuList;
    private Context context;
    View view;

//    public AdapterPesan(List<Pesan> pesanList, List<Pesan> pesanFilterList, Context context, AdapterPesan.deleteItemListener deleteItemListener) {
//        this.pesanList = pesanList;
//        this.pesanFilterList = pesanFilterList;
//        this.context = context;
//        this.deleteItemListener = deleteItemListener;
//    }

    public AdapterMenuAfterQR(List<Menu> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterMenuAfterQR.adapterMenuAfterQRViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_menu_after_qr, parent, false);
        return new adapterMenuAfterQRViewHolder(view,menuList);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterMenuAfterQR.adapterMenuAfterQRViewHolder holder, int position) {
        final Menu menu = menuList.get(position);

        holder.txtNamaMenu.setText(menu.getNama_menu());
        holder.txtHarga.setText(menu.getHarga());
        holder.txtDeskripsi.setText(menu.getDeskripsi());
        holder.txtKategori.setText(menu.getKategori());
        holder.txtJumlah.setText(String.valueOf(menu.getTempJumlah()));
    }
    public void filter(List<Menu> menuList){
        this.menuList=menuList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class adapterMenuAfterQRViewHolder extends RecyclerView.ViewHolder{
        TextView txtNamaMenu, txtHarga, txtDeskripsi, txtKategori, txtJumlah;
        private CardView cardPesan;
        Button btnAdd, btnMinus;
        public adapterMenuAfterQRViewHolder(@NonNull View itemView, List<Menu> menuList) {
            super(itemView);
            txtNamaMenu = itemView.findViewById(R.id.tvNamaMenu);
            txtHarga = itemView.findViewById(R.id.tvHarga);
            txtDeskripsi = itemView.findViewById(R.id.tvDeskripsi);
            txtKategori = itemView.findViewById(R.id.tvKategori);
            btnAdd = itemView.findViewById(R.id.addButton);
            btnMinus = itemView.findViewById(R.id.minusButton);
            txtJumlah = itemView.findViewById(R.id.tvJumlah);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int jumlah = Integer.valueOf(txtJumlah.getText().toString());
                    jumlah++;
                    txtJumlah.setText(String.valueOf(jumlah));
                    for (Menu menu : menuList)
                    {
                        if (menu.getNama_menu().equalsIgnoreCase(txtNamaMenu.getText().toString()))
                        {
                            menu.setTempJumlah(jumlah);
                        }
                    }
                    //menuList.get(getAdapterPosition()).setTempJumlah(jumlah);
                }
            });
            btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int jumlah = Integer.valueOf(txtJumlah.getText().toString());
                    if (jumlah != 0) {
                        jumlah--;
                    }
                    txtJumlah.setText(String.valueOf(jumlah));
                    for (Menu menu : menuList)
                    {
                        if (menu.getNama_menu().equalsIgnoreCase(txtNamaMenu.getText().toString()))
                        {
                            menu.setTempJumlah(jumlah);
                        }
                    }
                    //menuList.get(getAdapterPosition()).setTempJumlah(jumlah);
                }
            });
        }

    }

}
