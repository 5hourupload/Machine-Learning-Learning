package fhu.machinelearninglearning;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Creates a list view using a base template of custom_row and an array
 * Created by Johnny on 3/17/2018.
 */

class ListViewAdapter extends ArrayAdapter<Algorithm>
{
    ListViewAdapter(Context context, Algorithm[] questions)
    {
        super(context, R.layout.algorithm_info, questions);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        //creates a new layoutinflater
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.algorithm_info, parent, false);
        final Context context = parent.getContext();
        //gets the position of the question from the questions array
        final Algorithm algorithmItem = getItem(position);

        //creates an editable object
        TextView titleView = (TextView) customView.findViewById(R.id.algorithm_title);
        TextView typeView = (TextView) customView.findViewById(R.id.algorithm_type);
        ImageView iconView = (ImageView) customView.findViewById(R.id.algorithm_icon);

        titleView.setText(algorithmItem.title);
        typeView.setText(algorithmItem.type);

        iconView.setImageResource(algorithmItem.drawable);//sets image as a checkmark

        customView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), algorithmItem.cls);
                context.startActivity(intent);
            }
        });
        return customView;//returns the deisgned row
    }
}