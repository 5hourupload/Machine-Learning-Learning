package fhu.machinelearninglearning;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LinearRegression;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;


public class NB extends AppCompatActivity
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
        setContentView(R.layout.nb_layout);


        bluePaint.setARGB(255, 0, 0, 255);
        bluePaintFade.setARGB(127, 0, 0, 255);
        redPaint.setARGB(255, 255, 0, 0);
        redPaintFade.setARGB(127, 255, 0, 0);
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

        samples = new int[length][length];

        Button chooseDataButton = findViewById(R.id.choose_data_svm);
        Button resetButton = findViewById(R.id.restart_svm);
        Button redPoint = findViewById(R.id.svm_red_point);
        Button bluePoint = findViewById(R.id.svm_blue_point);

        redPoint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                currentPaint = redPaint;
            }
        });
        bluePoint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                currentPaint = bluePaint;
            }
        });

        chooseDataButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String[] colors = {"red", "green", "blue", "black"};

                AlertDialog.Builder builder = new AlertDialog.Builder(NB.this);
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




            String str1 = tempArff();
            InputStream is1 = new ByteArrayInputStream(str1.getBytes());
            br = new BufferedReader(new InputStreamReader(is1));
            Instances data1 = new Instances(br);
            data1.setClassIndex(data1.numAttributes() - 1);

            NaiveBayes nb = new NaiveBayes();
//            weka.classifiers.trees.J48 nb = new weka.classifiers.trees.J48();
//            LinearRegression nb = new LinearRegression();
            nb.buildClassifier(data);



            int interval = 10;
            for (int i = 0; i < length; i += interval)
            {
                for (int j = 0; j < length; j += interval)
                {
                    Instance instance = new DenseInstance(3);
                    instance.setValue(data.attribute("X"), i);
                    instance.setValue(data.attribute("Y"), j);
                    instance.setDataset(data);
                    int value = (int) nb.classifyInstance(instance);
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

                                    int value = (int) nb.classifyInstance(instance);
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

                                int value = (int) nb.classifyInstance(instance);
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
    private String tempArff()
    {
        return "@RELATION house\n" +
                "\n" +
                "@ATTRIBUTE X NUMERIC\n" +
                "@ATTRIBUTE Y NUMERIC\n" +
                "\n" +
                "@DATA\n" +
                "0,0 \n" +
                "1,2 \n" +
                "2,4 \n" +
                "3,6 \n" +
                "4,8 \n" +
                "5,10 \n" +
                "6,12";
    }
}
