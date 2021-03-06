package fhu.machinelearninglearning;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.core.DenseInstance;
import weka.core.Drawable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
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
    LibSVM svm;

    Instances data;
    String options[];

    EditText cost;
    EditText gamma;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.svm_layout);

        setTitle("Support Vector Machine");

        cost = findViewById(R.id.svm_cost);
        cost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                graph.setImageBitmap(draw());
            }
        });
        gamma = findViewById(R.id.svm_gamma);
        gamma.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                graph.setImageBitmap(draw());
            }
        });
        bluePaint.setARGB(255, 0, 0, 255);
        bluePaintFade.setARGB(127, 0, 0, 255);
        redPaint.setARGB(255, 255, 0, 0);
        redPaintFade.setARGB(127, 255, 0, 0);
//        try
//        {
//            options = Utils.splitOptions("-K 0");
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }

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

        Spinner svmType = findViewById(R.id.svm_type);
        svmType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                try
                {
                    options = Utils.splitOptions("-K " + i + " -G 0.9");
                    if (i == 2)
                    {
                        options = Utils.splitOptions("-K 2 -C 1000.0");
                    }
                    graph.setImageBitmap(draw());
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
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
                return false;
            }
        });

        Button about = findViewById(R.id.svm_about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(SVM.this);
                dialog.setContentView(R.layout.dialog);
                TextView title = dialog.findViewById(R.id.about_title);
                title.setText(R.string.svm_title);
                TextView description = dialog.findViewById(R.id.about_description);
                description.setText(R.string.svm_description);
                Button launchWiki = dialog.findViewById(R.id.about_button_wiki);
                launchWiki.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Support-vector_machine"));
                        startActivity(intent);
                    }
                });
                Button launchSklearn = dialog.findViewById(R.id.about_button_sci);
                launchSklearn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        intent.setData(Uri.parse("https://scikit-learn.org/stable/modules/svm"));
                        startActivity(intent);
                    }
                });
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
            arff += p.getX() + ".0," + p.getY() + ".0," + (p.getPaint() == bluePaint ? "blue" : "red") + "\n";
        }
        return arff;
    }

    private Bitmap draw()
    {
        Bitmap bitmap = Bitmap.createBitmap(length/*width*/, length/*height*/, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);


        if (points.size() == 0) return bitmap;
        BufferedReader br;
        try
        {
            String str = getArff();
            InputStream is = new ByteArrayInputStream(str.getBytes());
            br = new BufferedReader(new InputStreamReader(is));
            data = new Instances(br);
            data.setClassIndex(data.numAttributes() - 1);
            svm = new LibSVM();
            String optionsClone[] = options.clone();
            svm.setOptions(options);
            svm.setCost(Double.parseDouble(cost.getText().toString()));
            svm.setGamma(Double.parseDouble(gamma.getText().toString()));

            svm.buildClassifier(data);
            options = optionsClone;

            shade(canvas, 0, 0, length / 2);
            shade(canvas, length / 2, 0, length / 2);
            shade(canvas, 0, length / 2, length / 2);
            shade(canvas, length / 2, length / 2, length / 2);

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

    private void shade(Canvas canvas, int x, int y, int sideLength) throws Exception
    {
        Paint startPaint = classify(x, y);
        if (startPaint == classify(x, y + sideLength - 1) &&
                startPaint == classify(x + sideLength - 1, y) &&
                startPaint == classify(x + sideLength - 1, y + sideLength - 1))
        {
            Paint clone = new Paint();
            Paint white = new Paint();
            clone.setColor(startPaint.getColor());
            clone.setAlpha(63);
            white.setARGB(255, 255, 255, 255);
//            canvas.drawRect(new Rect(x - 2, y - 2, x + sideLength + 2, y + sideLength + 2), startPaint);
            canvas.drawRect(new Rect(x, y, x + sideLength, y + sideLength), white);
            canvas.drawRect(new Rect(x, y, x + sideLength, y + sideLength), clone);
        }
        else
        {
            int newLength = sideLength / 2;
            if (sideLength % 2 == 1 && sideLength > 2) newLength++;
            shade(canvas, x, y, newLength);
            shade(canvas, x + newLength, y, newLength);
            shade(canvas, x, y + newLength, newLength);
            shade(canvas, x + newLength, y + newLength, newLength);
        }
    }

    private Paint classify(int x, int y) throws Exception
    {
        Instance instance = new DenseInstance(3);
        instance.setValue(data.attribute("X"), x);
        instance.setValue(data.attribute("Y"), y);
        instance.setDataset(data);
        int value = (int) svm.classifyInstance(instance);
        return value == 0 ? bluePaintFade : redPaintFade;

    }
}
