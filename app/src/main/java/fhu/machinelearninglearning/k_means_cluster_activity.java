package fhu.machinelearninglearning;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class k_means_cluster_activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.k_means_clustering);
        Button addCentroid = findViewById(R.id.addCentroid);
        Button startingPointsButton = findViewById(R.id.startingPointsButton);
        Button update = findViewById(R.id.updateButton);
        Button reassign = findViewById(R.id.reassignButton);
        Button restart = findViewById(R.id.restartButton);

        startingPointsButton.setOnClickListener(new View.OnClickListener()
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
                        // the user clicked on colors[which]
                    }
                });
                builder.show();
            }
        });



        restart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MyDrawable mydrawing = new MyDrawable();
                ImageView image = findViewById(R.id.graphView);
                image.setImageDrawable(mydrawing);
            }
        });


    }
}
