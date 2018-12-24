package fhu.machinelearninglearning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MachineLearningAlgorithmsMenuActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_learning_algorithms);

        Button startKNN = findViewById(R.id.start_KNN);
        startKNN.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), k_means_cluster_activity.class);
                startActivity(intent);
            }
        });

        Button startSVM = findViewById(R.id.start_SVM);
        startSVM.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SVM.class);
                startActivity(intent);
            }
        });
    }
}
