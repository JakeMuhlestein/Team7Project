package edu.byui.myapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.Budget;
import edu.byui.myapplication.model.Vendor;
import edu.byui.myapplication.viewModel.AddEditBudgetActivity;
import edu.byui.myapplication.viewModel.AddVendorActivity;
import edu.byui.myapplication.viewModel.BudgetActivity;
import edu.byui.myapplication.viewModel.BudgetAdapter;
import edu.byui.myapplication.viewModel.BudgetViewModel;
import edu.byui.myapplication.viewModel.VendorAdapter;
import edu.byui.myapplication.viewModel.VendorViewModel;

import static android.app.Activity.RESULT_OK;

public class BudgetFragment extends Fragment {

    public static final int ADD_BUDGET_REQUEST = 1;
    public static final int EDIT_BUDGET_REQUEST = 2;
    private BudgetViewModel budgetViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vendor,container,false);

        FloatingActionButton buttonAddVendor = view.findViewById(R.id.button_add_vendor);
        buttonAddVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditBudgetActivity.class);
                startActivityForResult(intent, ADD_BUDGET_REQUEST);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final BudgetAdapter adapter = new BudgetAdapter();
        recyclerView.setAdapter(adapter);

        budgetViewModel = ViewModelProviders.of(getActivity()).get(BudgetViewModel.class);
        budgetViewModel.getAllBudgetItems().observe(getViewLifecycleOwner(), new Observer<List<Budget>>() {
            @Override
            public void onChanged(List<Budget> budgets) {
                adapter.setBudgets(budgets);
            }
        });

        //verify getAcivity();
        adapter.setOnItemClickListener(new BudgetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Budget budget) {
                Intent intent = new Intent(getActivity(), AddEditBudgetActivity.class);

                intent.putExtra(AddEditBudgetActivity.EXTRA_ID, budget.getId());
                intent.putExtra(AddEditBudgetActivity.EXTRA_BUDGET_NAME, budget.getName());
                intent.putExtra(AddEditBudgetActivity.EXTRA_BUDGET_AMOUNT, budget.getAmount());
                startActivityForResult(intent, EDIT_BUDGET_REQUEST);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BUDGET_REQUEST  && resultCode == RESULT_OK) {
            String budgetName = data.getStringExtra(AddEditBudgetActivity.EXTRA_BUDGET_NAME);
            int budgetAmount = data.getIntExtra(AddEditBudgetActivity.EXTRA_BUDGET_AMOUNT, 1);

            Budget budget = new Budget(budgetName, budgetAmount);
            budgetViewModel.insert(budget);
            Toast.makeText(getActivity(), "Budget item saved", Toast.LENGTH_SHORT).show();
        } else if(requestCode== EDIT_BUDGET_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditBudgetActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(getActivity(), "Budget can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String budgetName = data.getStringExtra(AddEditBudgetActivity.EXTRA_BUDGET_NAME);

            //verify
            int budgetAmount = data.getIntExtra(AddEditBudgetActivity.EXTRA_BUDGET_AMOUNT, 1);

            Budget budget = new Budget(budgetName, budgetAmount);
            budget.setId(id);
            budgetViewModel.update(budget);

            Toast.makeText(getActivity(), "Budget item updated", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(getActivity(), "Budget Not Saved", Toast.LENGTH_SHORT).show();
        }

    }
}
