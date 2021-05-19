package com.josevahandika.akb_mobile.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.josevahandika.akb_mobile.R;
import com.josevahandika.akb_mobile.entity.Cart;
import com.josevahandika.akb_mobile.entity.Menu;

import java.util.List;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.adapterCartViewHolder>{
    private List<Cart> listCart;
    private Context context;
    View view;

    public AdapterCart(List<Cart> listCart, Context context) {
        this.listCart = listCart;
        this.context = context;
    }

    public void setMenuList(List<Cart> listCart) {
        this.listCart = listCart;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterCart.adapterCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.adapter_cart, parent, false);
        return new AdapterCart.adapterCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCart.adapterCartViewHolder holder, int position) {
        final Cart cart = listCart.get(position);

        holder.txtNamaMenu.setText(cart.getNama_menu());
        holder.txtHarga.setText(cart.getHarga());
        holder.txtJumlah.setText(cart.getTempJumlah());
    }
    public void filter(List<Cart> listCart){
        this.listCart=listCart;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return listCart.size();
    }

    public class adapterCartViewHolder extends RecyclerView.ViewHolder{
        TextView txtNamaMenu, txtHarga,txtJumlah;
        private CardView cardPesan;

        public adapterCartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaMenu = itemView.findViewById(R.id.tvNamaMenu);
            txtHarga = itemView.findViewById(R.id.tvHarga);
            txtJumlah = itemView.findViewById(R.id.tvJumlah);
        }

    }


}
