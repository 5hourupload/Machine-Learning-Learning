package fhu.machinelearninglearning;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.core.DenseInstance;
import weka.core.Drawable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;


public class SVM extends AppCompatActivity
{
    List<point> points = new LinkedList<>();
    ImageView graph;
    Paint bluePaint = new Paint();
    Paint bluePaintFade = new Paint();
    Paint redPaint = new Paint();
    Paint redPaintFade = new Paint();

    int length = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.svm_layout);

        bluePaint.setARGB(255,0,0,255);
        bluePaintFade.setARGB(127,0,0,255);
        redPaint.setARGB(255,255,0,0);
        redPaintFade.setARGB(127,255,0,0);
//        for (int i = 0; i < 10; i++)
//        {
//            points.add(new point(((int) (Math.random() * 400)), (int)(Math.random() * 400) , i %2 == 0?bluePaint: redPaint));
//        }

        graph = findViewById(R.id.SVMView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        graph.setMinimumWidth(width);
        graph.setMinimumHeight(width);
        length = width;

        Button chooseDataButton = findViewById(R.id.choose_data_svm);
        Button resetButton = findViewById(R.id.restart_svm);
        Button redPoint = findViewById(R.id.svm_red_point);
        Button bluePoint = findViewById(R.id.svm_blue_point);

        chooseDataButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String[] colors = {"red", "green", "blue", "black"};

                AlertDialog.Builder builder = new AlertDialog.Builder(SVM.this);
                builder.setTitle("Pick a color");
                builder.setItems(colors, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                    }
                });
                builder.show();
            }
        });

//        buttons.get(Names.valueOf("CHOOSE_TYPE").ordinal()).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//
//                String[] colors = {"red", "green", "blue", "black"};
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(SVM.this);
//                builder.setTitle("Pick a color");
//                builder.setItems(colors, new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which)
//                    {
//                        for (Button b : buttons) b.setVisibility(View.GONE);
//                        buttons.get(Names.valueOf("RESTART").ordinal()).setVisibility(View.VISIBLE);
//                        buttons.get(Names.valueOf("CHOOSE_TYPE").ordinal()).setVisibility(View.VISIBLE);
//                        buttons.get(Names.valueOf("TRAIN_MODEL").ordinal()).setVisibility(View.VISIBLE);
//
//                    }
//                });
//                builder.show();
//
//            }
//        });

        resetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                points.clear();
            }
        });


        graph.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View view, MotionEvent motionEvent) {
                int[] locations = new int[2];
                view.getLocationOnScreen(locations);
                points.add(new point((int) motionEvent.getX(), (int) motionEvent.getY(), bluePaint));
                graph.setImageBitmap(draw());
                System.out.println(motionEvent.getX() + " " + motionEvent.getY());
                //view.getLocationInWindow(locations);
                return false;
            }
        });
    }

    private String getArff()
    {
        String arff = "@RELATION iris\n" +
                "\n" +
                "@ATTRIBUTE X\tREAL\n" +
                "@ATTRIBUTE Y \tREAL\n" +
                "@ATTRIBUTE class \t{blue,red}\n" +
                "\n" +
                "@DATA\n";


        for (point p : points)
        {
            arff += p.getX() + "," + p.getY() + "," + (p.getPaint() == bluePaint? "blue" : "red") + "\n";
        }
        System.out.println(arff);
        return arff;
    }
    private Bitmap draw()
    {
        Bitmap bitmap = Bitmap.createBitmap(length/*width*/, length/*height*/, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        for (point p : points)
        {
            System.out.println("drawingggg " + p.getX() + " " + p.getY());
            canvas.drawOval(p.getX()-3, p.getY()-3, p.getX() + 3, p.getY() + 3, p.getPaint());
        }
        BufferedReader br;
        try
        {
            String str = getArff();
            InputStream is = new ByteArrayInputStream(str.getBytes());
            br = new BufferedReader(new InputStreamReader(is));
            Instances data = new Instances(br);
            data.setClassIndex(data.numAttributes() - 1);
            SMO smo = new SMO();
            String[] options = Utils.splitOptions("-C 1.0 -L 0.001 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers" +
                    ".functions.supportVector.PolyKernel -C 250007 -E 1.0\"");
            smo.setOptions(options);
            smo.buildClassifier(data);

            for (int i = 0 ; i < length; i+=1)
            {
                for (int j = 0 ; j < length; j+=1)
                {
                    Instance instance = new DenseInstance(3);
                    instance.setValue(data.attribute("X"), i);
                    instance.setValue(data.attribute("Y"), j);
                    instance.setDataset(data);

                    canvas.drawPoint(i,j,smo.classifyInstance(instance) == 0? bluePaintFade : redPaintFade);
//                    System.out.println("The instance: " + instance);
//                    System.out.println(smo.classifyInstance(instance));

                }
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }
}
