package edu.byui.myapplication.viewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.PayMethod;

public class PayMethodAdapter extends RecyclerView.Adapter<PayMethodAdapter.PayMethodHolder> {
    private List<PayMethod> payMethods = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public PayMethodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.paymethod_item, parent, false);
        return new PayMethodHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PayMethodHolder holder, int position) {
        PayMethod currentPayMethod = payMethods.get(position);
        holder.textViewPayType.setText(currentPayMethod.getPayType());
        holder.textViewAcctNum.setText(String.valueOf(currentPayMethod.getAcctNumber()));
    }

    @Override
    public int getItemCount() {
        return payMethods.size();
    }

    public void setPayMethods(List<PayMethod> payMethods) {
        this.payMethods = payMethods;
        notifyDataSetChanged();
    }

    class PayMethodHolder extends RecyclerView.ViewHolder {
        private TextView textViewPayType;
        private TextView textViewAcctNum;

        public PayMethodHolder(@NonNull View itemView) {
            super(itemView);
            textViewPayType = itemView.findViewById(R.id.text_view_payMethod_type);
            //update after finishing form
            textViewAcctNum = itemView.findViewById(R.id.text_view_payMethod_balance);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(payMethods.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PayMethod payMethod);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
