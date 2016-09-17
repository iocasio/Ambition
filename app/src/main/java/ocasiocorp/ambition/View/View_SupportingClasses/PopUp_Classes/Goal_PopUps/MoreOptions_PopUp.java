package ocasiocorp.ambition.View.View_SupportingClasses.PopUp_Classes.Goal_PopUps;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import ocasiocorp.ambition.Model.Goal;
import ocasiocorp.ambition.R;

/**
 * Created by Ocasiorey on 5/18/2016.
 */
public class MoreOptions_PopUp extends DialogFragment {

    private static final String ARG_GOALNAME ="Goal";
    private static final String ARG_ID = "ID";
    private TextView textView;
    private static Goal mGoal;

    // Default constructor
    public MoreOptions_PopUp(){

    }

    //Builder method for inputting data
    public static MoreOptions_PopUp newInstance(Goal goal){
        mGoal = goal;
        MoreOptions_PopUp dialog = new MoreOptions_PopUp();
        Bundle args = new Bundle();
        args.putString(ARG_GOALNAME, goal.getmGoalName());
        dialog.setArguments(args);

        return dialog;
    }

    // Create Dialog and inflate widgets
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.moreoptions_popup, null);
        Bundle args = getArguments();
        String goalString = args.getString(ARG_GOALNAME);
        textView = (TextView)view.findViewById(R.id.displayGoal);
        textView.setText(goalString);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.moreOptionsTitle)
                .setPositiveButton(R.string.Delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        //Grab the id 
                        String id = mGoal.getmId().toString();
                        intent.putExtra(ARG_ID,id);
                        //Return result code and the ID for the goal
                        getTargetFragment().onActivityResult(
                                getTargetRequestCode(),
                                Activity.RESULT_OK,
                                intent
                        );
                    }
                })
                .setNegativeButton(R.string.CancelButton, null)
                .create();

    }
}
