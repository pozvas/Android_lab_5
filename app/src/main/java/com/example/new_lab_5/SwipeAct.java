package com.example.new_lab_5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.core.view.GravityCompat;

public class SwipeAct extends AppCompatActivity {

    private GestureDetectorCompat gestureDetectorCompat;
    PopupWindow popupWindow;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        popupWindow = new PopupWindow(this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_layout, null);
        popupWindow.setContentView(view);
        popupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hidePopupWindow();
                return true;
            }
        });


        gestureDetectorCompat = new GestureDetectorCompat(this, new MyGestureListener());
        DrawerLayout layout = findViewById(R.id.drawer_layout);
        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Pass the event to the GestureDetector
                return gestureDetectorCompat.onTouchEvent(event);
            }
        });

        TextView scoreTextView = view.findViewById(R.id.scoreTextView);

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score++;
                scoreTextView.setText(String.valueOf(score));
            }
        });

        Button resetButton = view.findViewById(R.id.resetButton);
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
        popupWindow.showAtLocation(findViewById(R.id.drawer_layout), Gravity.TOP, 0, displayRectangle.top);
    }

    private void hidePopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e1.getY() - e2.getY() < 0) {
                // Show the PopupWindow
                showPopupWindow();
            }
            return true;
        }
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
}