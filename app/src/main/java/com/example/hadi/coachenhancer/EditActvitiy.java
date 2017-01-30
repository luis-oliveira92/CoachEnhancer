package com.example.hadi.coachenhancer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by Hadi on 1/29/2017.
 */

public class EditActvitiy extends AppCompatActivity  implements View.OnTouchListener, View.OnClickListener {

    private static final String TAG = "FingerPaint";
    DrawOnView drawView;
    ImageView iv;
    RelativeLayout footballCourt;
    ImageView btnBall;
    ImageView btnCone;
    ImageView btnPlayer;

    ImageView btnDraw;

    // Colors image view
    ImageView btnColorWhite;
    ImageView btnColorBlue;
    ImageView btnColorYellow;
    ImageView btnColorRed;
    ImageView btnColorEraser;

    ImageView btnIconEraser;

    ImageView btnXsize;
    ImageView btnXXsize;
    ImageView btnXXXsize;

    DrawOnView drawPanel;
    Boolean isDrawable;
    Boolean isDeletable;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R. layout.activity_edit);
        isDrawable = false;
        isDeletable = false;

        drawPanel = new DrawOnView(this);
        drawPanel.setEnabled(false);
        footballCourt = (RelativeLayout) findViewById(R.id.drawonme);

        footballCourt.addView(drawPanel);
        //drawPanel.setVisibility(View.INVISIBLE);

        // Ball
        btnBall = (ImageView) findViewById(R.id.ballic);
        btnBall.setOnClickListener(this);

        // Line
        btnDraw = (ImageView) findViewById(R.id.freelineic);
        btnDraw.setOnClickListener(this);

        // Cone
        btnCone = (ImageView) findViewById(R.id.coneic);
        btnCone.setOnClickListener(this);

        // color white
        btnColorWhite = (ImageView) findViewById(R.id.whiteic);
        btnColorWhite.setOnClickListener(this);

        // color blue
        btnColorBlue = (ImageView) findViewById(R.id.blueic);
        btnColorBlue.setOnClickListener(this);

        // color yellow
        btnColorYellow = (ImageView) findViewById(R.id.yelloic);
        btnColorYellow.setOnClickListener(this);

        // color red
        btnColorRed = (ImageView) findViewById(R.id.redic);
        btnColorRed.setOnClickListener(this);

        // color eraser
        btnColorEraser = (ImageView) findViewById(R.id.eraseric);
        btnColorEraser.setOnClickListener(this);

        // Icon eraser
        btnIconEraser = (ImageView) findViewById(R.id.iconeraseric);
        btnIconEraser.setOnClickListener(this);

        // X size
        btnXsize = (ImageView) findViewById(R.id.xsizeic);
        btnXsize.setOnClickListener(this);
        // 2X size
        btnXXsize = (ImageView) findViewById(R.id.xxsizeic);
        btnXXsize.setOnClickListener(this);
        // 3X size
        btnXXXsize = (ImageView) findViewById(R.id.xxxsizeic);
        btnXXXsize.setOnClickListener(this);

        iv = new ImageView(this);

        // lock screen orientation (stops screen clearing when rotating phone)
        //setRequestedOrientation(getResources().getConfiguration().orientation);

        iv.setOnTouchListener(this);
    }

    float x, y = 0.0f;
    boolean moving = false;

    private static final String TAGS = "MyActivity";
    int minX, minY, maxX, maxY;

    public boolean onTouch(View arg0, MotionEvent arg1) {
        switch (arg1.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(isDeletable)
                {
                    arg0.setVisibility(View.GONE);

                    moving = false;
                    isDeletable = false;
                    break;
                }
                moving = true;
                RelativeLayout rl = (RelativeLayout) findViewById(R.id.drawonme);

                minX = rl.getLeft();
                maxX = rl.getWidth();
                minY = rl.getTop();
                maxY = rl.getHeight();

                Log.d("maxX=", String.valueOf(maxX));
                Log.d("maxY=", String.valueOf(maxY));
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("maxX=", String.valueOf(maxX));
                Log.d("maxY=", String.valueOf(maxY));
                Log.d("X=", String.valueOf(x));
                Log.d("Y=", String.valueOf(y));
                if (moving) {
                    //x = arg1.getRawX() - iv.getWidth();
                    //y = arg1.getRawY() - iv.getHeight() * 3;
                    arg0.setX(LimitMovement(arg1.getRawX(),0,maxX-50));
                    arg0.setY(LimitMovement(arg1.getRawY(),0,maxY-50));
                }
                break;

            case MotionEvent.ACTION_UP:
                moving = false;
                break;
        }
        return true;
    }

    float LimitMovement(float curVar, float minVar, float maxVar) {
        if (curVar < minVar)
            return minVar;

        else if (curVar > maxVar)
            return maxVar;

        return curVar;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if statement prevents force close error when picture isn't selected
        if (resultCode == RESULT_OK) {
            Uri resultUri = data.getData();
            //String resultString = data.getData().toString();

            String drawString = resultUri.getPath();
            String galleryString = getGalleryPath(resultUri);

            // if Gallery app was used
            if (galleryString != null) {
                Log.d(TAG, galleryString);
                drawString = galleryString;
            }
            // else another file manager was used
            else {
                Log.d(TAG, drawString);
                //File Manager: "content://org.openintents.cmfilemanager/mimetype//mnt/sdcard/DCIM/Camera/IMG_20110909_210412.jpg"
                //ASTRO:        "file:///mnt/sdcard/DCIM/Camera/IMG_20110924_133324.jpg"
                if (drawString.contains("//")) {
                    drawString = drawString.substring(drawString.lastIndexOf("//"));
                }
            }

            // set the background to the selected picture
            if (drawString.length() > 0) {
                Drawable drawBackground = Drawable.createFromPath(drawString);
                drawView.setBackgroundDrawable(drawBackground);
            }
        }
    }

    // used when trying to get an image path from the URI returned by the Gallery app
    public String getGalleryPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);

        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnColorWhite.getId()||view.getId() == btnColorBlue.getId()||
                view.getId() == btnColorYellow.getId()||view.getId() == btnColorRed.getId()||
                view.getId() == btnIconEraser.getId()||view.getId() == btnColorEraser.getId()||
                view.getId() == btnXsize.getId()||view.getId() == btnXXsize.getId()||
                view.getId() == btnXXXsize.getId()){

            if(view.getId() == btnColorWhite.getId()){
                drawPanel.changeColour(0);
            } else if(view.getId() == btnColorBlue.getId()){
                drawPanel.changeColour(1);
            }else if(view.getId() == btnColorYellow.getId()){
                drawPanel.changeColour(6);
            }else if(view.getId() == btnColorRed.getId()){
                drawPanel.changeColour(5);
            }else if(view.getId() == btnColorEraser.getId()){ //Erase all Lines
                drawPanel.clearPoints();
            }else if(view.getId() == btnIconEraser.getId()){//Erase all Lines/ finally icons
                drawPanel.clearPoints();
            }else if(view.getId() == btnXsize.getId()){ //Change thickness to x
                drawPanel.changeWidth(5);
            }else if(view.getId() == btnXXsize.getId()){ //Change thickness to xx
                drawPanel.changeWidth(10);
            }else if(view.getId() == btnXXXsize.getId()){ //Change thickness to xxx
                drawPanel.changeWidth(15);
            }

            if(view.getId() == btnIconEraser.getId()){
                isDeletable = true;
            }else{
                isDeletable = false;
            }
            Log.d(TAG, String.valueOf(view.getId()));
        }

        if(view.getId() == btnBall.getId()||view.getId() == btnCone.getId()){
            Log.d(TAG, "I try to Icon");
            drawPanel.setEnabled(false);
            isDrawable=false;
            ImageView tempIcon = new ImageView(this);

            if(view.getId() == btnBall.getId()){
                tempIcon.setBackgroundResource(R.drawable.ballic);
            }else if(view.getId() == btnCone.getId()){
                tempIcon.setBackgroundResource(R.drawable.coneic);
            }

            tempIcon.setOnTouchListener(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40, 40);
            tempIcon.setLayoutParams(layoutParams);

            RelativeLayout l = (RelativeLayout)findViewById(R.id.drawonme);
            int maxXlimit = l.getWidth() -140;
            int maxYlimit = l.getHeight()-140;
            int minLimit = 80;

            Random rand = new Random();
            int xRand = rand.nextInt(maxXlimit-minLimit)+minLimit;
            int yRand = rand.nextInt(maxYlimit-minLimit)+minLimit;

            tempIcon.setX(xRand);
            tempIcon.setY(yRand);
            footballCourt.addView(tempIcon);
        }

        else if(view.getId() == btnDraw.getId()){
            if(isDrawable){
                drawPanel.setEnabled(false);
                isDrawable = false;
            }else{
                drawPanel.setEnabled(true);
                isDrawable = true;
            }
        }
    }

    public void backToMainMenu(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        Log.d("Error","I am here!!");
    }
}
