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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class k_means_cluster_activity extends AppCompatActivity
{
    enum Names
    {
        CHOOSE_DATA, RESTART, ADD_CENTROID, UPDATE, REASSIGN
    }

    List<Button> buttons = new LinkedList<>();
    ImageView graph;
    int length = 0;

    List<point> centroids = new LinkedList<>();
    List<point> points = new LinkedList<>();
    ArrayList<Integer> paints = new ArrayList<>();

    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.k_means_clustering);
        setTitle("K-Means Clustering");

        graph = findViewById(R.id.kmc_graph);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        graph.setMinimumWidth(width);
        graph.setMinimumHeight(width);
        length = width;
        //Black                 Blue                    Gray                   Green
//        paints.add(-16777216); paints.add(-16776961); paints.add(-7829368); paints.add(-16711936);
        //Magenta             Red                 White          Yellow              Cyan
//        paints.add(-65281); paints.add(-65536); paints.add(-1); paints.add(-256); paints.add(-16711681);

        paints.add(Color.RED);
        paints.add(Color.GREEN);
        paints.add(Color.BLUE);
        paints.add(Color.MAGENTA);
        paints.add(Color.CYAN);
        paints.add(-23296);//orange
        paints.add(-8388480);//purple
        paints.add(Color.DKGRAY);
        paints.add(Color.LTGRAY);
        paints.add(Color.YELLOW);


        text = findViewById(R.id.kmc_text);

        text.setText("Choose data type");
        buttons.add((Button) findViewById(R.id.kmc_choose_data));
        buttons.add((Button) findViewById(R.id.kmc_restartButton));
        buttons.add((Button) findViewById(R.id.addCentroid));
        buttons.add((Button) findViewById(R.id.updateButton));
        buttons.add((Button) findViewById(R.id.reassignButton));
        for (Button b : buttons) b.setVisibility(View.GONE);
        buttons.get(Names.valueOf("CHOOSE_DATA").ordinal()).setVisibility(View.VISIBLE);
        buttons.get(Names.valueOf("CHOOSE_DATA").ordinal()).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String[] pointOptions = {"Random", "Clusters", "Smiley Face"};

                AlertDialog.Builder builder = new AlertDialog.Builder(k_means_cluster_activity.this);
                builder.setTitle("Pick a color");
                builder.setItems(pointOptions, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        for (Button b : buttons) b.setVisibility(View.GONE);
                        buttons.get(Names.valueOf("RESTART").ordinal()).setVisibility(View.VISIBLE);
                        buttons.get(Names.valueOf("ADD_CENTROID").ordinal()).setVisibility(View.VISIBLE);

                        switch (which)
                        {
                            case 0:
                                points = pointGenerator.generateRandomPoints(200, length);
                                break;
                            case 1:
                                points = pointGenerator.generateClusteredPoints(200, length);
                                break;
                            case 2:
                                points = pointGenerator.generateSmileyFace(200, length);
                                break;
                        }
                        graph.setImageBitmap(draw());
                        text.setText("Tap screen to add centroids!");

                    }
                });

                graph.setOnTouchListener(new View.OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent)
                    {
                        int[] locations = new int[2];
                        view.getLocationOnScreen(locations);
                        Paint tempColor = new Paint();
                        if (centroids.size() < paints.size())
                        {
                            tempColor.setColor(paints.get(centroids.size()));
                        }
                        else
                        {
                            text.setText("Max centroids reached");
                            return false;
                        }
                        centroids.add(new point((int) motionEvent.getX(), (int) motionEvent.getY(), tempColor));
                        //System.out.println(motionEvent.getX() + " " + motionEvent.getY());
                        //view.getLocationInWindow(locations);
                        groupPoints();
                        graph.setImageBitmap(draw());
                        return false;
                    }
                });
                builder.show();

                buttons.get(Names.valueOf("ADD_CENTROID").ordinal()).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        buttons.get(Names.valueOf("ADD_CENTROID").ordinal()).setVisibility(View.GONE);
                        buttons.get(Names.valueOf("UPDATE").ordinal()).setVisibility(View.VISIBLE);
                        buttons.get(Names.valueOf("REASSIGN").ordinal()).setVisibility(View.VISIBLE);
                        text.setText("Update centroids and reassign points!");
                    }
                });
            }
        });


        buttons.get(Names.valueOf("RESTART").ordinal()).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                for (Button b : buttons) b.setVisibility(View.GONE);
                buttons.get(Names.valueOf("CHOOSE_DATA").ordinal()).setVisibility(View.VISIBLE);
                centroids.clear();
                text.setText("Choose data type");

            }
        });

        buttons.get(Names.valueOf("REASSIGN").ordinal()).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                groupPoints();
                graph.setImageBitmap(draw());
            }
        });

        buttons.get(Names.valueOf("UPDATE").ordinal()).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (centroids.size()==0)return;
                Map<Paint, List<point>> avgLocations = new HashMap<>();
                for (point p : centroids)
                {
                    List<point> temp = new LinkedList<>();
                    avgLocations.put(p.getPaint(), temp);
                }
                for (point p : points)
                {
                    List<point> temp = avgLocations.get(p.getPaint());
                    System.out.println(avgLocations.size() + " )000000000000000000");
                    temp.add(p);
                    avgLocations.put(p.getPaint(), temp);
                }
                for (point c : centroids)
                {
                    List<point> points = avgLocations.get(c.getPaint());
                    double meanX = 0;
                    double meanY = 0;
                    for (point p : points)
                    {
                        meanX += p.getX();
                        meanY += p.getY();
                    }
                    meanX /= points.size();
                    meanY /= points.size();
                    if (points.size() != 0)
                    {
                        c.setX((int) meanX);
                        c.setY((int) meanY);
                    }
                }
                graph.setImageBitmap(draw());
            }
        });
    }

    private void groupPoints()
    {
        for (point p : points)
        {
            double shortestDistance = length + 100;
            for (point c : centroids)
            {
                double distance = Math.sqrt(Math.pow((c.getX() - p.getX()), 2) + Math.pow((c.getY() - p.getY()), 2));
                if (distance < shortestDistance)
                {
                    shortestDistance = distance;
                    p.setPaint(c.getPaint());
                }
            }
        }
    }

    private Bitmap draw()
    {
        Bitmap bitmap = Bitmap.createBitmap(length/*width*/, length/*height*/, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        if (centroids.size() != 0)
        {
            shade(canvas, 0, 0, length / 2);
            shade(canvas, length / 2, 0, length / 2);
            shade(canvas, 0, length / 2, length / 2);
            shade(canvas, length / 2, length / 2, length / 2);
        }
        for (point p : points)
        {
            canvas.drawOval(p.getX() - 10, p.getY() - 10, p.getX() + 10, p.getY() + 10, p.getPaint());
        }

        for (point p : centroids)
        {
            canvas.drawOval(p.getX() - 20, p.getY() - 20, p.getX() + 20, p.getY() + 20, p.getPaint());
        }
        return bitmap;
    }

    private void shade(Canvas canvas, int x, int y, int sideLength)
    {
        Paint startPaint = getClosestPaint(x, y);
        if (startPaint == getClosestPaint(x, y + sideLength - 1) &&
                startPaint == getClosestPaint(x + sideLength - 1, y) &&
                startPaint == getClosestPaint(x + sideLength - 1, y + sideLength - 1))
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

    private Paint getClosestPaint(int x, int y)
    {
        Paint local = null;
        double shortestDistance = length * 2;

        for (point c : centroids)
        {
            double distance = Math.sqrt(Math.pow((c.getX() - x), 2) + Math.pow((c.getY() - y), 2));
            if (distance < shortestDistance)
            {
                shortestDistance = distance;
                local = c.getPaint();
            }
        }
        return local;
    }
}