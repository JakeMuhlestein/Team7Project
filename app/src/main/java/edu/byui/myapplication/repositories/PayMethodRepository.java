package edu.byui.myapplication.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;


import edu.byui.myapplication.model.PayMethod;
import edu.byui.myapplication.model.PayMethodDao;
import edu.byui.myapplication.model.TeamDatabase;

public class PayMethodRepository {
    private PayMethodDao payMethodDao;
    private LiveData<List<PayMethod>> allPayMethodItems;

    public PayMethodRepository(Application application) {
        TeamDatabase teamDatabase = TeamDatabase.getInstance(application);
        payMethodDao = teamDatabase.getPayMethodDao();
        allPayMethodItems = payMethodDao.getAllPaymentMethods();
    }

    public void insert(PayMethod payMethod) {
        new InsertPayMethodAsyncTask(payMethodDao).execute(payMethod);
    }

    public void update(PayMethod payMethod) {
        new UpdatePayMethodAsyncTask(payMethodDao).execute(payMethod);
    }

    public void delete(PayMethod payMethod) {
        new DeletePayMethodAsyncTask(payMethodDao).execute(payMethod);
    }

    public LiveData<List<PayMethod>> getAllPayMethodItems() {
        return allPayMethodItems;
    }

    private static class InsertPayMethodAsyncTask extends AsyncTask<PayMethod, Void, Void> {
        private PayMethodDao payMethodDao;

        private InsertPayMethodAsyncTask(PayMethodDao payMethodDao) {
            this.payMethodDao = payMethodDao;
        }

        @Override
        protected Void doInBackground(PayMethod... payMethods) {
            payMethodDao.insertPayMethod(payMethods[0]);
            return null;
        }
    }

    private static class UpdatePayMethodAsyncTask extends AsyncTask<PayMethod, Void, Void> {
        private PayMethodDao payMethodDao;

        private UpdatePayMethodAsyncTask(PayMethodDao payMethodDao) {
            this.payMethodDao = payMethodDao;
        }

        @Override
        protected Void doInBackground(PayMethod... payMethods) {
            payMethodDao.updatePayMethod(payMethods[0]);
            return null;
        }
    }

    private static class DeletePayMethodAsyncTask extends AsyncTask<PayMethod, Void, Void> {
        private PayMethodDao payMethodDao;

        private DeletePayMethodAsyncTask(PayMethodDao payMethodDao) {
            this.payMethodDao = payMethodDao;
        }

        @Override
        protected Void doInBackground(PayMethod... payMethods) {
            payMethodDao.deletePayMethod(payMethods[0]);
            return null;
        }
    }

    private static class DeleteAllPayMethodsAsyncTask extends AsyncTask<Void, Void, Void> {
        private PayMethodDao payMethodDao;

        private DeleteAllPayMethodsAsyncTask(PayMethodDao payMethodDao) {
            this.payMethodDao = payMethodDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            payMethodDao.deleteAllPayMethodItems();
            return null;
        }
    }

}