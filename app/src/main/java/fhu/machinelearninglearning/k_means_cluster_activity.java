package fhu.machinelearninglearning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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
