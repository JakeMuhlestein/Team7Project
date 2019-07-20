package edu.byui.myapplication.view;

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

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.PayMethod;
import edu.byui.myapplication.viewModel.AddEditPayMethodActivity;
import edu.byui.myapplication.viewModel.PayMethodAdapter;
import edu.byui.myapplication.viewModel.PayMethodViewModel;

import static android.app.Activity.RESULT_OK;

public class PayMethodFragment extends Fragment {

    public static final int ADD_PAYMETHOD_REQUEST = 1;
    public static final int EDIT_PAYMETHOD_REQUEST = 2;
    private PayMethodViewModel payMethodViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_paymethod,container,false);

        FloatingActionButton buttonAddPayMethod = view.findViewById(R.id.button_add);
        buttonAddPayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditPayMethodActivity.class);
                startActivityForResult(intent, ADD_PAYMETHOD_REQUEST);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final PayMethodAdapter adapter = new PayMethodAdapter();
        recyclerView.setAdapter(adapter);

        payMethodViewModel = ViewModelProviders.of(getActivity()).get(PayMethodViewModel.class);
        payMethodViewModel.getAllPayMethodItems().observe(getViewLifecycleOwner(), new Observer<List<PayMethod>>() {
            @Override
            public void onChanged(List<PayMethod> payMethods) {
                adapter.setPayMethods(payMethods);
            }
        });

        adapter.setOnItemClickListener(new PayMethodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PayMethod payMethod) {
                Intent intent = new Intent(getActivity(), AddEditPayMethodActivity.class);

                intent.putExtra(AddEditPayMethodActivity.EXTRA_ID, payMethod.getId());
                intent.putExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_PAY_TYPE, payMethod.getPayType());
                intent.putExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_ACCT_NUMBER, payMethod.getAcctNumber());
                intent.putExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_BALANCE,payMethod.getBalance());
                intent.putExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_EXP_DATE,payMethod.getExpDate());
                intent.putExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_POINTS, payMethod.getPoints());
                startActivityForResult(intent, EDIT_PAYMETHOD_REQUEST);

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PAYMETHOD_REQUEST  && resultCode == RESULT_OK) {
            String payMethodType = data.getStringExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_PAY_TYPE);
            String payMethodAcctNum = data.getStringExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_ACCT_NUMBER);
            double payMethodBalance = data.getDoubleExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_BALANCE, 1234);
            double payMethodPoints = data.getDoubleExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_POINTS, 1234);
            String payMethodExpDate = data.getStringExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_EXP_DATE);
            Date expDate = Date.valueOf(payMethodExpDate);



            PayMethod payMethod = new PayMethod(payMethodType, payMethodAcctNum, payMethodBalance, payMethodPoints, expDate);



            payMethodViewModel.insert(payMethod);
            Toast.makeText(getActivity(), "Payment item saved", Toast.LENGTH_SHORT).show();
        } else if(requestCode== EDIT_PAYMETHOD_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditPayMethodActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(getActivity(), "Payment item can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String payMethodType = data.getStringExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_PAY_TYPE);
            String payMethodAcctNum = data.getStringExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_ACCT_NUMBER);
            double payMethodBalance = data.getDoubleExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_BALANCE, 1234);
            double payMethodPoints = data.getDoubleExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_POINTS, 1234);
            String payMethodExpDate = data.getStringExtra(AddEditPayMethodActivity.EXTRA_PAYMETHOD_EXP_DATE);
            Date expDate = Date.valueOf(payMethodExpDate);


            PayMethod payMethod = new PayMethod(payMethodType, payMethodAcctNum, payMethodBalance, payMethodPoints, expDate);
            payMethod.setId(id);
            payMethodViewModel.update(payMethod);

            Toast.makeText(getActivity(), "Pay Method item updated", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(getActivity(), "Pay Method Not Saved", Toast.LENGTH_SHORT).show();
        }

    }
}
