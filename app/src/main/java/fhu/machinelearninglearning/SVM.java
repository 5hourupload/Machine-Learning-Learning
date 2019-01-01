package fhu.machinelearninglearning;

import android.app.Dialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
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
    Paint currentPaint = bluePaint;

    int samples[][];

    int length = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.svm_layout);

        setTitle("Support Vector Machine");

        bluePaint.setARGB(255, 0, 0, 255);
        bluePaintFade.setARGB(127, 0, 0, 255);
        redPaint.setARGB(255, 255, 0, 0);
        redPaintFade.setARGB(127, 255, 0, 0);

        graph = findViewById(R.id.SVMView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        graph.setMinimumWidth(width);
        graph.setMinimumHeight(width);
        length = width;

        samples = new int[length][length];

        Button resetButton = findViewById(R.id.restart_svm);

        Spinner spinner = findViewById(R.id.svm_spinner);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (i == 0) currentPaint = bluePaint;
                if (i == 1) currentPaint = redPaint;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
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
                points.add(new point((int) motionEvent.getX(), (int) motionEvent.getY(), currentPaint));
                graph.setImageBitmap(draw());
                System.out.println(motionEvent.getX() + " " + motionEvent.getY());
                //view.getLocationInWindow(locations);
                return false;
            }
        });

        Button about = findViewById(R.id.svm_about);
        about.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // custom dialog
                final Dialog dialog = new Dialog(SVM.this);
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
                "@ATTRIBUTE class \t{blue,red}\n" +
                "\n" +
                "@DATA\n";


        for (point p : points)
        {
            arff += p.getX() + "," + p.getY() + "," + (p.getPaint() == bluePaint ? "blue" : "red") + "\n";
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
//            SMO smo = new SMO();
//            NB smo = new NB();
//            String[] options = Utils.splitOptions("-C 1.0 -L 0.001 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers" +
//                    ".functions.supportVector.PolyKernel -C 250007 -E 1.0\"");

            //poly
            String[] options = Utils.splitOptions("-K \"weka.classifiers" +
                    ".functions.supportVector.PolyKernel -E 2.0\"");

            options = Utils.splitOptions("-C 1.0 -L 0.0010 -P 1.0E-12 -N 0 -V -1 -W 1 -K \"weka.classifiers.functions.supportVector.RBFKernel -C 250007 -G 0.01\"");


//            smo.setOptions(options);
            LibSVM smo = new LibSVM();
            options = Utils.splitOptions("-K 2 -C 1000000.0 -w1 1 -W0 1");
            smo.setOptions(options);
            smo.buildClassifier(data);

            int interval = 10;
            for (int i = 0; i < length; i += interval)
            {
                for (int j = 0; j < length; j += interval)
                {
                    Instance instance = new DenseInstance(3);
                    instance.setValue(data.attribute("X"), i);
                    instance.setValue(data.attribute("Y"), j);
                    instance.setDataset(data);
                    int value = (int) smo.classifyInstance(instance);
                    samples[i][j] = value;
                }
            }

            for (int i = 0; i < length; i += interval)
            {
                for (int j = 0; j < length; j += interval)
                {
                    if (i + interval < length && j + interval < length)
                    {
                        if (samples[i][j] == samples[i][j + interval] &&
                                samples[i][j] == samples[i + interval][j + interval] &&
                                samples[i][j] == samples[i + interval][j])
                        {
                            canvas.drawRect(new Rect(i, j, i + interval, j + interval), samples[i][j] == 0 ? bluePaintFade : redPaintFade);


                        }
                        else
                        {
                            for (int k = i; k < i + interval; k++)
                            {
                                for (int l = j; l < j + interval; l++)
                                {
                                    if (k >= length) continue;
                                    if (l >= length) continue;
                                    Instance instance = new DenseInstance(3);
                                    instance.setValue(data.attribute("X"), k);
                                    instance.setValue(data.attribute("Y"), l);
                                    instance.setDataset(data);

                                    int value = (int) smo.classifyInstance(instance);
                                    samples[k][l] = value;
                                    canvas.drawPoint(k, l, value == 0 ? bluePaintFade : redPaintFade);
                                }
                            }
                        }
                    }
                    else
                    {
                        for (int k = i; k < i + interval; k++)
                        {
                            for (int l = j; l < j + interval; l++)
                            {
                                if (k >= length) break;
                                if (l >= length) continue;
                                Instance instance = new DenseInstance(3);
                                instance.setValue(data.attribute("X"), k);
                                instance.setValue(data.attribute("Y"), l);
                                instance.setDataset(data);

                                int value = (int) smo.classifyInstance(instance);
                                samples[k][l] = value;
                                canvas.drawPoint(k, l, value == 0 ? bluePaintFade : redPaintFade);
                            }
                        }
                    }
                }

//                    System.out.println("The instance: " + instance);
//                    System.out.println(smo.classifyInstance(instance));
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
