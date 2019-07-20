package edu.byui.myapplication.viewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Budget;

public class TransactionBudgetSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private List<Budget> budgetList = new ArrayList<>();

    @Override
    public int getCount() {
        return budgetList.size();
    }

    @Override
    public Object getItem(int position) {
        return budgetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return budgetList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BudgetHolder budgetHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.transaction_budget_spinner,parent,false);
            budgetHolder = new BudgetHolder();
            budgetHolder.budgetName = convertView.findViewById(R.id.spinner_budget_name);
            convertView.setTag(budgetHolder);
        } else {
            budgetHolder = (BudgetHolder) convertView.getTag();
        }

        Budget budget = budgetList.get(position);
        budgetHolder.budgetName.setText(budget.getName());

        return convertView;
    }

    public void setBudgetList(List<Budget>  budgetList) {
        this.budgetList = budgetList;
        notifyDataSetChanged();
    }

    class BudgetHolder {
        private TextView budgetName;
    }
}
