package fhu.machinelearninglearning;

import android.content.DialogInterface;
import android.graphics.Color;
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
import weka.core.Instance;
import weka.core.Instances;
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
    enum Names
    {
        CHOOSE_DATA, RESTART, CHOOSE_TYPE, TRAIN_MODEL
    }

    List<point> points = new LinkedList<>();
    List<Button> buttons = new LinkedList<>();
    ImageView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.svm_layout);

        points.add(new point(0, 0, Color.valueOf(0, 0, 0)));
        points.add(new point(4, 4, Color.valueOf(255, 255, 255)));

        graph = findViewById(R.id.SVMView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        graph.setMinimumWidth(width);
        graph.setMinimumHeight(width);

        buttons.add((Button) findViewById(R.id.choose_data_svm));
        buttons.add((Button) findViewById(R.id.restart_svm));
        buttons.add((Button) findViewById(R.id.choose_type_svm));
        buttons.add((Button) findViewById(R.id.train_model_svm));
        for (Button b : buttons) b.setVisibility(View.GONE);
        buttons.get(Names.valueOf("CHOOSE_DATA").ordinal()).setVisibility(View.VISIBLE);
        buttons.get(Names.valueOf("CHOOSE_DATA").ordinal()).setOnClickListener(new View.OnClickListener()
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
                        for (Button b : buttons) b.setVisibility(View.GONE);
                        buttons.get(Names.valueOf("RESTART").ordinal()).setVisibility(View.VISIBLE);
                        buttons.get(Names.valueOf("CHOOSE_TYPE").ordinal()).setVisibility(View.VISIBLE);

                    }
                });
                builder.show();
            }
        });

        buttons.get(Names.valueOf("CHOOSE_TYPE").ordinal()).setOnClickListener(new View.OnClickListener()
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
                        for (Button b : buttons) b.setVisibility(View.GONE);
                        buttons.get(Names.valueOf("RESTART").ordinal()).setVisibility(View.VISIBLE);
                        buttons.get(Names.valueOf("CHOOSE_TYPE").ordinal()).setVisibility(View.VISIBLE);
                        buttons.get(Names.valueOf("TRAIN_MODEL").ordinal()).setVisibility(View.VISIBLE);

                    }
                });
                builder.show();

            }
        });

        buttons.get(Names.valueOf("RESTART").ordinal()).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                for (Button b : buttons) b.setVisibility(View.GONE);
                buttons.get(Names.valueOf("CHOOSE_DATA").ordinal()).setVisibility(View.VISIBLE);
            }
        });
        buttons.get(Names.valueOf("TRAIN_MODEL").ordinal()).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                BufferedReader br;
                try
                {
                    String str = getArff();
                    InputStream is = new ByteArrayInputStream(str.getBytes());
                    br = new BufferedReader(new InputStreamReader(is));
                    Instances data = new Instances(br);

                    data.setClassIndex(data.numAttributes() - 1);

//                Instances trainingData = new Instances(data, 0, 14);
//                Instances testingData = new Instances(data, 14, 5);
//                Evaluation evaluation = new Evaluation(data);

                    SMO smo = new SMO();
                    smo.buildClassifier(data);


//                evaluation.evaluateModel(smo, testingData);
//                System.out.println(evaluation.toSummaryString());

                    // Test instance
                    for (double i = 0 ; i < 4; i+=.25)
                    {
                        Instance instance = new DenseInstance(3);
                        instance.setValue(data.attribute("X"), i);
                        instance.setValue(data.attribute("Y"), i);
                        instance.setDataset(data);
                        System.out.println("The instance: " + instance);
                        System.out.println(smo.classifyInstance(instance));
                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
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
            arff += p.getX() + "," + p.getY() + "," + (p.getColor().blue() == 0? "blue" : "red") + "\n";
        }
        return arff;
    }
}
