package com.example.new_lab_5;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private Stack<Fragment> stack = new Stack<>();
    private TextView textViewStack;
    private FragmentManager fragmentManager;
    private Intent task2 = null;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewStack = findViewById(R.id.textView);
        Button buttonBack = findViewById(R.id.backBtn);
        Button buttonForward = findViewById(R.id.forwardBtn);
        fragmentManager = getSupportFragmentManager();

        PageFragment initialPage = new PageFragment(1, "Задача 1 1:04:2024");
        stack.push(initialPage);
        updateStack();
        updateFragment();

        buttonBack.setOnClickListener(v -> {
            if (stack.size() > 1) {
                stack.pop();
                updateStack();
                updateFragment();
            }
        });

        buttonForward.setOnClickListener(v -> {
            PageFragment pageFragment = new PageFragment(stack.size() + 1,
                    "Задача " + (stack.size() + 1) + " " + (stack.size() + 1)
                            + ".04.2024");
            stack.push(pageFragment);
            updateStack();
            updateFragment();
        });

        Button createPage = findViewById(R.id.createPageBtn);
        Button deletePage = findViewById(R.id.deletePageBtn);
        createPage.setOnClickListener(v -> {
            if (task2 == null) {
                task2 = new Intent(this, ActivityTask2.class);
                startActivityForResult(task2, 1);
            } else {
                startActivityForResult(task2, 1);
            }
        });
        deletePage.setOnClickListener(v -> {
            if (task2 != null) {
                finishActivity(1);
            }
        });

        Button textDialog = findViewById(R.id.textDialogBtn);
        Button dataDialog = findViewById(R.id.dataDialogBtn);
        Button timeDialog = findViewById(R.id.timeDialogBtn);
        textDialog.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Введите текст");

            final EditText input = new EditText(this);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String userInput = input.getText().toString();
                    TextView text = findViewById(R.id.textDialogTextView);
                    text.setText(userInput);
                }
            });

            builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        });
        dataDialog.setOnClickListener(v -> {
            new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    TextView text = findViewById(R.id.dataDialogTextView);
                    text.setText(day + ":" + month + ":" + year);
                }
            },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                    .show();
        });
        timeDialog.setOnClickListener(v -> {
            new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    TextView text = findViewById(R.id.timeDialogTextView);
                    text.setText(hourOfDay + ":" + minute);
                }
            },
                    Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                    Calendar.getInstance().get(Calendar.MINUTE), true)
                    .show();
        });

        TableLayout tableLayout = findViewById(R.id.table);
        //registerForContextMenu(tableLayout);
        for (int i = 0; i < 5; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setId(i);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < 2; j++) {
                TextView textView = new TextView(this);
                textView.setLayoutParams(new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f));
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(20);
                if (j == 0)
                    textView.setText("Задача " + (i + 1));
                else
                    textView.setText((i + 1) * 5 + ":04:2024");
                tableRow.addView(textView);
            }

            registerForContextMenu(tableRow);
            tableLayout.addView(tableRow);

        }

        Button webBtn = findViewById(R.id.webBtn);

        webBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, WebActivity.class));
        });

    }
    private void updateStack() {
        textViewStack.setText("Глубина стека: " + stack.size());

    }

    private void updateFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!stack.empty()) {
            fragmentTransaction.replace(R.id.frameLayout_container, stack.peek());
        } else {
            fragmentTransaction.remove(stack.peek());
        }
        fragmentTransaction.commit();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(v.getId(), 1, 0, "Red");
        menu.add(v.getId(), 2, 0, "Green");
        menu.add(v.getId(), 3, 0, "Blue");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 1:
                Log.i("Меню", "Red " + item.getGroupId());
                break;
            case 2:
                Log.i("Меню", "Green " + item.getGroupId());
                break;
            case 3:
                Log.i("Меню", "Blue " + item.getGroupId());
                break;
        }
        return super.onContextItemSelected(item);
    }

}
