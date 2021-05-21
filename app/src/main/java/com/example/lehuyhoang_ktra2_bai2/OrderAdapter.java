package com.example.lehuyhoang_ktra2_bai2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderVierHolder> {
    private List<Order> data = new ArrayList<>();
    private Context context;

    public OrderAdapter(Context c) {
        this.context = c;
    }

    public void setData(List<Order> x) {
        this.data = x;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderAdapter.OrderVierHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new OrderVierHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderVierHolder holder, int position) {
        Order x = data.get(position);
        if (x == null) return;
        holder.name.setText(String.valueOf(x.getName()));
        holder.start_location.setText(String.valueOf(x.getStart_location()));
        holder.start_date.setText(String.valueOf(x.getStart_date()));
        if (x.getPack() == 1) {
            holder.pack.setText("Have package");
        } else {
            holder.pack.setText("Don't have package");
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) return data.size();
        return 0;
    }

    public class OrderVierHolder extends RecyclerView.ViewHolder {
        private TextView name, start_location, start_date, pack;

        public OrderVierHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            start_location = itemView.findViewById(R.id.item_start_location);
            start_date = itemView.findViewById(R.id.item_start_date);
            pack = itemView.findViewById(R.id.item_pack);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getLayoutPosition();
                    Order x = data.get(pos);
                    Intent edit = new Intent(context, EditActivity2.class);
                    edit.putExtra("data", x);
//                    edit.putExtra("index", start_location.getSelectedItemPosition());
                    context.startActivity(edit);
                }
            });
        }
    }

    public int GetAllOrderHavePack() {
        int  f =0;
        for (Order x:data) {
            if (x.getPack()==1) {
                f++;
            }
        }
        return f;
    }
}
