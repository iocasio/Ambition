package ocasiocorp.ambition.View.View_SupportingClasses.PopUp_Classes.List_PopUps;

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
import ocasiocorp.ambition.Model.GoalList;
import ocasiocorp.ambition.R;

/**
 * Created by Ocasiorey on 6/17/2016.
 */
public class MoreListOptions_PopUp extends DialogFragment {

    private static final String ARG_NEW_TITLE = "new_title";
    private static final String ARG_ID = "ID";
    public String List_String;
    private TextView textView1,textView2;
    private static GoalList mGoalList;

    public MoreListOptions_PopUp() {

    }

    //Builder method
    public static MoreListOptions_PopUp newInstance(GoalList list) {
        mGoalList = list;
        MoreListOptions_PopUp dialog = new MoreListOptions_PopUp();
        Bundle args = new Bundle();
        args.putString(ARG_NEW_TITLE, mGoalList.getTitle());
        dialog.setArguments(args);

        return dialog;
    }

    
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.moreoptions_popup, null);
        Bundle args = getArguments();
        final String goalString = args.getString(ARG_NEW_TITLE);
        textView1 = (TextView) view.findViewById(R.id.displayGoal);
        textView1.setText(goalString);

        textView2 = (TextView)view.findViewById(R.id.warningText);
        textView2.setText(R.string.careful);

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.moreOptionsTitle)
                .setPositiveButton(R.string.Delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        String id = mGoalList.getUuid().toString();
                        intent.putExtra(ARG_ID, id);
                        intent.putExtra(ARG_NEW_TITLE, goalString);
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
