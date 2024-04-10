package com.example.new_lab_5;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PageFragment extends Fragment {
    private int number;
    private String taskname;
    public PageFragment(int numb, String name) {
        number = numb;
        taskname = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView text = view.findViewById(R.id.textView_page);
        text.setText(taskname);
        return view;
    }
}
