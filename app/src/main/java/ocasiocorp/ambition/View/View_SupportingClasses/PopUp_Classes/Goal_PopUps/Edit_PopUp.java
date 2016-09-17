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
import android.widget.CheckBox;
import android.widget.EditText;
import ocasiocorp.ambition.Model.Goal;
import ocasiocorp.ambition.Model.GoalList;
import ocasiocorp.ambition.R;

/**
 * Created by Ocasiorey on 6/8/2016.
 */
public class Edit_PopUp extends DialogFragment{

    //Identifiers 
    private static final String ARG_GOALNAME ="Goal";
    private static final String ARG_ID = "ID";
    private static final String ARG_IMPORTANT = "Important";
    private static final String ARG_NEW_TITLE ="new_title";
    
    private static Goal mGoal;
    private CheckBox mCheckbox;
    private EditText mEditText;
    private String goalString;
    private Boolean isImportant;
    private String id;
    private static GoalList list;

    //Default constructor
    public Edit_PopUp(){

    }

    //Builder method to input and return data for "Edit new Goal function" 
    public static Edit_PopUp newInstance(Goal goal){
        mGoal = goal;
        Edit_PopUp dialog = new Edit_PopUp();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IMPORTANT, goal.ismIsImportant());
        args.putString(ARG_GOALNAME, goal.getmGoalName());
        args.putString(ARG_ID, goal.getmId().toString());
        dialog.setArguments(args);

        return dialog;
    }

    //Builder method to edit List
    public static Edit_PopUp newListInstance(GoalList inputList){
        list = inputList;
        Edit_PopUp dialogTwo = new Edit_PopUp();
        Bundle args = new Bundle();
        args.putString(ARG_NEW_TITLE, list.getTitle());
        dialogTwo.setArguments(args);

        return dialogTwo;
    }

    
    //Create and inflate dialog
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_popup, null);
        Bundle args = getArguments();
        goalString = args.getString(ARG_GOALNAME);
        isImportant = args.getBoolean(ARG_IMPORTANT,false);
        id = args.getString(ARG_ID);
        mCheckbox = (CheckBox)view.findViewById(R.id.checkbox);
        mEditText = (EditText)view.findViewById(R.id.EditText);
        mEditText.setText(goalString);
        mCheckbox.setChecked(isImportant);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.moreOptionsTitle)
                .setPositiveButton(R.string.Edit_Button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        goalString = mEditText.getText().toString();
                        isImportant = mCheckbox.isChecked();
                        intent.putExtra(ARG_ID,id);
                        intent.putExtra(ARG_GOALNAME, goalString);
                        intent.putExtra(ARG_IMPORTANT, isImportant);
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
