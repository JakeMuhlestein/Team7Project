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
import edu.byui.myapplication.model.Budget;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetHolder> {
    private List<Budget> budgets = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public BudgetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.budget_item, parent, false);
        return new BudgetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetHolder holder, int position) {
        Budget currentBudget = budgets.get(position);
        holder.textViewBudget.setText(currentBudget.getName());
        holder.textViewAmount.setText(String.valueOf(currentBudget.getAmount()));
    }

    @Override
    public int getItemCount() {
        return budgets.size();
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
        notifyDataSetChanged();
    }

    class BudgetHolder extends RecyclerView.ViewHolder {
        private TextView textViewBudget;
        private TextView textViewAmount;

        public BudgetHolder(@NonNull View itemView) {
            super(itemView);
            textViewBudget = itemView.findViewById(R.id.text_view_budget_name);
            textViewAmount = itemView.findViewById(R.id.text_view_budget_amount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(budgets.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Budget budget);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
