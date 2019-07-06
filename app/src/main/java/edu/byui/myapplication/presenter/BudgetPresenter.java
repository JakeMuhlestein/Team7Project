package edu.byui.myapplication.presenter;

import edu.byui.myapplication.DataRepository;
import edu.byui.myapplication.model.Budget;
import edu.byui.myapplication.model.TeamDatabase;

public class BudgetPresenter {
    //what's the boolean for suppose to be, success?
    public boolean updateCategory(String name, double amount) throws Exception {
        //DataRepository.getInstance().updateCategory()
        return true;
    }

    public Budget createCategory(Budget budget) throws Exception {
        return DataRepository.getInstance().createBudget(budget);
    }

//    public boolean updateCategory(Budget budget) throws Exception {
//        return DataRepository.getInstance().updateCategory(budget);
//    }
}
