package fhu.machinelearninglearning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import weka.classifiers.functions.LinearRegression;

public class MachineLearningAlgorithmsMenuActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_learning_algorithms);

        Algorithm algorithms[] = new Algorithm[6];
        algorithms[0] = new Algorithm(R.drawable.k_means_clustering_icon, "K-Means Clustering",
                "Unsupervised Clustering Algorithm", k_means_cluster_activity.class);
        algorithms[1] = new Algorithm(R.drawable.svm_icon, "Support Vector Machine",
                "Supervised Learning Algorithm", SVM.class);
        algorithms[2] = new Algorithm(R.drawable.nb_icon, "Naive Bayes", "Supervised Learning Algorithm", NB.class);
        algorithms[3] = new Algorithm(R.drawable.dectree_icon, "Decision Tree", "Supervised Learning Algorithm", DecisionTree.class);

        algorithms[4] = new Algorithm(R.drawable.linreg_icon, "Linear Regression", "Regression Model", LinReg.class);
        algorithms[5] = new Algorithm(R.drawable.logreg_icon, "Logistic Regression", "Regression Model", LogReg.class);

        //uses custom adapter to create an image with two rows of text in a single row through custom_row.xml
        ListAdapter queAdapter = new ListViewAdapter(this, algorithms);
        ListView myListView = findViewById(R.id.algorithm_list_view);
        myListView.setAdapter(queAdapter);

//        Button startKNN = findViewById(R.id.start_KNN);
//        startKNN.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getApplicationContext(), k_means_cluster_activity.class);
//                startActivity(intent);
//            }
//        });
//
//        Button startSVM = findViewById(R.id.start_SVM);
//        startSVM.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getApplicationContext(), SVM.class);
//                startActivity(intent);
//            }
//        });
    }
}
