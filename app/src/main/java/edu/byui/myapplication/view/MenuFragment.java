package edu.byui.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.byui.myapplication.R;

public class MenuFragment extends Fragment {

    private Button item1;
    private Button item2;
    private Button item3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu,container,false);
        item1 = (Button) view.findViewById(R.id.button);

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FragmentContainer)getActivity()).setViewPager(1);
            }
        });


        return view;

    }
}
