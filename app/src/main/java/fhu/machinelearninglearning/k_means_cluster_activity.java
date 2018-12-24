package fhu.machinelearninglearning;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;

public class k_means_cluster_activity extends AppCompatActivity {
    enum Names
    {
        CHOOSE_DATA, RESTART, ADD_CENTROID;
    }

    List<Button> buttons = new LinkedList<>();
    ImageView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.k_means_clustering);
        graph = findViewById(R.id.graphView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        graph.setMinimumWidth(width);
        graph.setMinimumHeight(width);
        buttons.add((Button) findViewById(R.id.choose_data));
        buttons.add((Button) findViewById(R.id.restartButton));
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
                String[] colors = {"red", "green", "blue", "black"};

                AlertDialog.Builder builder = new AlertDialog.Builder(k_means_cluster_activity.this);
                builder.setTitle("Pick a color");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (Button b : buttons) b.setVisibility(View.GONE);
                        buttons.get(Names.valueOf("RESTART").ordinal()).setVisibility(View.VISIBLE);
                        buttons.get(Names.valueOf("ADD_CENTROID").ordinal()).setVisibility(View.VISIBLE);

                    }
                });
                builder.show();
            }
        });

        buttons.get(Names.valueOf("ADD_CENTROID").ordinal()).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                graph.setOnTouchListener(new View.OnTouchListener() {
                    @Override public boolean onTouch(View view, MotionEvent motionEvent) {
                        int[] locations = new int[2];
                        view.getLocationOnScreen(locations);
                        System.out.println(motionEvent.getX() + " " + motionEvent.getY());
                        //view.getLocationInWindow(locations);
                        return false;
                    }
                });

            }
        });

        buttons.get(1).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MyDrawable mydrawing = new MyDrawable();
                graph.setImageDrawable(mydrawing);
            }
        });

    }
}