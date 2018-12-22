package fhu.machinelearninglearning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        Button machineLearningAlgs = findViewById(R.id.machine_learning_algorithms);
        Button neuralNetworks = findViewById(R.id.neural_networks);
        machineLearningAlgs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), MachineLearningAlgorithmsMenuActivity.class);
                startActivity(intent);
            }
        });

    }
}
