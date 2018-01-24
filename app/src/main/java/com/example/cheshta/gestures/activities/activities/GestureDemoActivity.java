package com.example.cheshta.gestures.activities.activities;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

import com.example.cheshta.gestures.R;
import com.example.cheshta.gestures.activities.gestures.OnDoubleTapListener;
import com.example.cheshta.gestures.activities.gestures.OnSwipeTouchListener;


public class GestureDemoActivity extends AppCompatActivity {

    Button btnLongClick, btnDoubleTap;
    TextView tvSwipeMe, tvDragMe, tvDropZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_demo);

        btnDoubleTap = findViewById(R.id.btnDoubleTap);
        btnLongClick = findViewById(R.id.btnLongClick);
        tvDragMe = findViewById(R.id.tvDragMe);
        tvDropZone = findViewById(R.id.tvDropZone);
        tvSwipeMe = findViewById(R.id.tvSwipeMe);

        btnLongClick.setOnLongClickListener(longClickListener);
        btnDoubleTap.setOnTouchListener(new MyOnDoubleTapListener(this));
        tvSwipeMe.setOnTouchListener(new MyOnSwipeTouchListener(this));
        tvDragMe.setOnTouchListener(new MyTouchListener());
        tvDropZone.setOnDragListener(new MyDragListener());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private View.OnLongClickListener longClickListener = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View view) {
            showToast("Long Clicked!");
            return true;
        }
    };

    private class MyOnDoubleTapListener extends OnDoubleTapListener{

        public MyOnDoubleTapListener(Context c) {
            super(c);
        }

        @Override
        public void onDoubleTap(MotionEvent e) {
            showToast("Double Tapped!");
        }
    };

    private class MyOnSwipeTouchListener extends OnSwipeTouchListener{

        public MyOnSwipeTouchListener(Context c) {
            super(c);
        }

        @Override
        public void onSwipeRight() {
            showToast("Swiped right!");
        }

        @Override
        public void onSwipeLeft() {
            showToast("Swiped left!");
        }

        @Override
        public void onSwipeUp() {
            showToast("Swiped up!");
        }

        @Override
        public void onSwipeDown() {
            showToast("Swiped down!");
        }
    };

    private final class MyTouchListener implements OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private class MyDragListener implements OnDragListener{

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(Color.GREEN);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(Color.RED);
                    break;
                case DragEvent.ACTION_DROP:
                    // Handle the dragged view being dropped over a target view
                    View draggedTextView = (View) event.getLocalState();
                    // Get View dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    dropTarget.setText("Dropped!");
                    // Get owner of the dragged view
                    ViewGroup owner = (ViewGroup) draggedTextView.getParent();
                    // Remove the dragged view
                    owner.removeView(draggedTextView);
                    // Display toast
                    showToast("Dropped into zone!");
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (event.getResult()) { // drop succeeded
                        v.setBackgroundColor(Color.GREEN);
                    } else { // drop failed
                        final View draggedView = (View) event.getLocalState();
                        draggedView.post(new Runnable(){
                            @Override
                            public void run() {
                                draggedView.setVisibility(View.VISIBLE);
                            }
                        });
                        v.setBackgroundColor(Color.RED);
                    }
                default:
                    break;
            }
            return true;
        }
    }
}


