package fhu.machinelearninglearning;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import weka.classifiers.functions.LinearRegression;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;


public class LinReg extends AppCompatActivity
{
    List<point> points = new LinkedList<>();
    ImageView graph;
    Paint bluePaint = new Paint();
    Paint bluePaintFade = new Paint();
    Paint redPaint = new Paint();
    Paint redPaintFade = new Paint();
    Paint currentPaint = bluePaint;

    int samples[][];

    int length = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lin_reg_layout);

        setTitle("Linear Regression");

        bluePaint.setARGB(255, 0, 0, 255);
        bluePaintFade.setARGB(127, 0, 0, 255);
        redPaint.setARGB(255, 255, 0, 0);
        redPaintFade.setARGB(127, 255, 0, 0);
//        for (int i = 0; i < 10; i++)
//        {
//            points.add(new point(((int) (Math.random() * 400)), (int)(Math.random() * 400) , i %2 == 0?bluePaint: redPaint));
//        }

        graph = findViewById(R.id.linreg_view);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        graph.setMinimumWidth(width);
        graph.setMinimumHeight(width);
        length = width;

        samples = new int[length][length];

        Button resetButton = findViewById(R.id.linreg_reset);

        resetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                points.clear();
                graph.setImageBitmap(draw());
            }
        });


        graph.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                int[] locations = new int[2];
                view.getLocationOnScreen(locations);
                points.add(new point((int) motionEvent.getX(), (int) motionEvent.getY()));
                graph.setImageBitmap(draw());
                System.out.println(motionEvent.getX() + " " + motionEvent.getY());
                //view.getLocationInWindow(locations);
                return false;
            }
        });

        Button about = findViewById(R.id.linreg_about);
        about.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // custom dialog
                final Dialog dialog = new Dialog(LinReg.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Title...");

                // set the custom dialog components - text, image and button
//                TextView text = (TextView) dialog.findViewById(R.id.text);
//                text.setText("Android custom dialog example!");
//                ImageView image = (ImageView) dialog.findViewById(R.id.image);
//                image.setImageResource(R.drawable.ic_launcher);

//                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
//                dialogButton.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });

                dialog.show();
            }
        });
    }

    private String getArff()
    {
        String arff = "@RELATION iris\n" +
                "\n" +
                "@ATTRIBUTE X\tREAL\n" +
                "@ATTRIBUTE Y \tREAL\n" +
                "\n" +
                "@DATA\n";


        for (point p : points)
        {
            arff += p.getX() + "," + p.getY() + "\n";
        }
        System.out.println(arff);
        return arff;
    }

    private Bitmap draw()
    {
        Bitmap bitmap = Bitmap.createBitmap(length/*width*/, length/*height*/, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);


        BufferedReader br;
        try
        {
            String str = getArff();
            InputStream is = new ByteArrayInputStream(str.getBytes());
            br = new BufferedReader(new InputStreamReader(is));
            Instances data = new Instances(br);
            data.setClassIndex(data.numAttributes() - 1);

            LinearRegression lr = new LinearRegression();
            lr.buildClassifier(data);
            for (int i = 0; i < length; i++)
            {
                Instance instance = new DenseInstance(2);
                instance.setValue(data.attribute("X"), i);
                instance.setDataset(data);
                canvas.drawPoint(i,(int)lr.classifyInstance(instance),bluePaint);
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }
        for (point p : points)
        {
            canvas.drawOval(p.getX() - 10, p.getY() - 10, p.getX() + 10, p.getY() + 10, p.getPaint());
        }
        return bitmap;
    }

}
