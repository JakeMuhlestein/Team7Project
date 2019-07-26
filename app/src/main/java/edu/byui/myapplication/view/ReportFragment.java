package edu.byui.myapplication.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.ReportView;
import edu.byui.myapplication.viewModel.ReportViewModel;
import edu.byui.myapplication.viewModel.ReportViewAdapter;

public class ReportFragment extends Fragment {

    private ReportViewModel reportViewModel;
    private final String TAG = "** ** ReportFragment: ";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container,false);

        RecyclerView recyclerView = view.findViewById(R.id.report_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        Log.d(TAG, "onCreate: About to initialize Report Adapter");
        final ReportViewAdapter adapter = new ReportViewAdapter();
        recyclerView.setAdapter(adapter);


        reportViewModel = ViewModelProviders.of(getActivity()).get(ReportViewModel.class);
        reportViewModel.getAllReports().observe(getViewLifecycleOwner(), new Observer<List<ReportView>>() {
            @Override
            public void onChanged(@Nullable List<ReportView> reportViews) {
                adapter.setReportViews(reportViews);
            }
        });

        Log.d(TAG, "onCreate: right before adapter.setOnItemClickListener");
        adapter.setOnItemClickListener(new ReportViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ReportView reportView) {
                Log.d(TAG, "adapter.setOnItemClickListener: onItemClick has been fired! \nReportView:" + reportView.toString());
                //nothing for now
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getActivity(), "Detailed Report Not Available", Toast.LENGTH_SHORT).show();
    }

}
