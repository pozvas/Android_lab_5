package com.example.new_lab_5;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;


public class ActivityTask2 extends AppCompatActivity {
    private BottomSheetBehavior bottomSheetBehavior;
    private PopupWindow popupWindow;
    private View popupView;
    private int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

        Button b = findViewById(R.id.button);
        b.setOnClickListener(v -> {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });

        CoordinatorLayout lowerCoordinatorLayout = findViewById(R.id.lowercoordinatorLayout);
        View bottomSheet = lowerCoordinatorLayout.findViewById(R.id.bottom_sheet);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // Обработайте изменение состояния Bottom Sheet
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // Обработайте смещение Bottom Sheet при прокрутке
            }
        });

        // Надуть разметку выдвигающегося окна
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.popup_window, null);

        // Создать PopupWindow
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // Установить фоновую прозрачность для PopupWindow
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Установить анимацию для PopupWindow (необязательно)
        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        // Обработать нажатие на кнопку для отображения PopupWindow
        Button showPopupButton = findViewById(R.id.show_popup_button);
        showPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Отобразить PopupWindow сверху
                showPopupWindow();
            }
        });

        // Обработать нажатие вне PopupWindow для его скрытия
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_OUTSIDE) {
                    hidePopupWindow();
                    return true;
                }
                return false;
            }
        });

        TextView text = findViewById(R.id.selectedMenuItem);
        Button button1 = bottomSheet.findViewById(R.id.button1);
        Button button2 = bottomSheet.findViewById(R.id.button2);

        button1.setOnClickListener(v -> {
            text.setText(button1.getText());
        });

        button2.setOnClickListener(v -> {
            text.setText(button2.getText());
        });

        Button button5 = popupView.findViewById(R.id.button5);

        button5.setOnClickListener(v -> {
            text.setText(button5.getText());
        });

        TextView scoreTextView = findViewById(R.id.scoreTextView);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score++;
                scoreTextView.setText(String.valueOf(score));
            }
        });

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = 0;
                scoreTextView.setText(String.valueOf(score));
            }
        });

    }

    private void showPopupWindow() {
        // Получить размеры экрана
        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        // Отобразить PopupWindow сверху
        popupWindow.showAtLocation(findViewById(R.id.coordinatorLayout), Gravity.TOP, 0, displayRectangle.top);
    }

    private void hidePopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
}