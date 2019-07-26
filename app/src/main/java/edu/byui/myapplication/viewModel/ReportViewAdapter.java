package edu.byui.myapplication.viewModel;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.byui.myapplication.R;
import edu.byui.myapplication.model.ReportView;

public class ReportViewAdapter extends RecyclerView.Adapter<ReportViewAdapter.ReportViewHolder> {
    private final static String TAG = "ReportViewAdapter: ";
    //TODO: change from livedata to plain list?
    private List<ReportView> reportViews = new ArrayList<>();
    private ReportViewAdapter.OnItemClickListener listener;

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item, parent, false);

        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        ReportView currentReportView = reportViews.get(position);
        holder.reportViewName.setText(currentReportView.getName());
        holder.reportViewAmount.setText(String.valueOf(currentReportView.getAmount()));
        holder.reportViewSpentAmount.setText(String.valueOf(currentReportView.getBudgetSpent()) + " of ");
        if (currentReportView.getAmount() < currentReportView.getBudgetSpent()) {
            holder.reportViewSpentAmount.setTextColor(Color.RED);
        } /* else {
            holder.reportViewSpentAmount.setTextColor(Color.);
        } */

    }

    @Override
    public int getItemCount() {
        return reportViews.size();
    }

    public void setReportViews (List<ReportView> reportViews) {
        this.reportViews = reportViews;
        notifyDataSetChanged();
    }



    // inner class which the enclosing class extends ? a little chicken and egg thing going on here.
    class ReportViewHolder extends RecyclerView.ViewHolder {
        private TextView reportViewName;
        private TextView reportViewAmount;
        private TextView reportViewSpentAmount;

        public ReportViewHolder(@NonNull View reportViewView) {
            super(reportViewView);
            // this is where the reportView assignment happens.
            reportViewName = reportViewView.findViewById(R.id.recycler_view_report_budget_name);
            reportViewAmount = reportViewView.findViewById(R.id.recycler_view_report_budget_amount);
            reportViewSpentAmount = reportViewView.findViewById(R.id.recycler_view_report_budget_spent);

            reportViewView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Log.d(TAG, "reportViewView.setOnClickListener: onClick has been fired! Position is "
                            + position);
                    // at this point i think listener is always set to null. Why?
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(reportViews.get(position));
                    }
                }
            });

        }

    }

    public interface OnItemClickListener {
        void onItemClick(ReportView reportView);
    }

    /**
     * Arg!
     * @param listener
     */
    public void setOnItemClickListener(ReportViewAdapter.OnItemClickListener listener) {

        this.listener = listener;
    }


}
