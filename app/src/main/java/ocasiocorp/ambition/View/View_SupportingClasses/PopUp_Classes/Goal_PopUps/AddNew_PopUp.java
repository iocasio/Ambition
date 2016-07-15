package ocasiocorp.ambition.View.View_SupportingClasses.PopUp_Classes.Goal_PopUps;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import ocasiocorp.ambition.R;


/**
 * Created by Ocasiorey on 5/17/2016.
 */
public class AddNew_PopUp extends DialogFragment{
    private static final String ARG_GOALNAME ="Goal";
    private static final String ARG_IMPORTANT = "Important";

    private CheckBox mCheckbox;
    private EditText mEditText;
    private String GoalText;
    private boolean isImportant;
    private static Fragment fragment;
    public AddNew_PopUp(){
    }

    public static AddNew_PopUp newInstance(Fragment frg){
        fragment = frg;
        AddNew_PopUp dialog = new AddNew_PopUp();
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.classes_addnew, null);
        mCheckbox = (CheckBox)view.findViewById(R.id.checkbox);
        mEditText = (EditText)view.findViewById(R.id.EditText);
        return new AlertDialog.Builder(getActivity())
                .setView(view).setTitle(R.string.AddAmbition)
                .setPositiveButton(R.string.SaveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GoalText = mEditText.getText().toString();
                        isImportant = mCheckbox.isChecked();
                        System.out.println(isImportant+ " HERE'S THE PROBLEM!");
                        Intent intent = new Intent();
                        intent.putExtra(ARG_GOALNAME, GoalText);
                        intent.putExtra(ARG_IMPORTANT, isImportant);;
                        fragment.onActivityResult(
                                getTargetRequestCode(),
                                Activity.RESULT_OK,
                                intent
                        );
                    }
                })
                .setNegativeButton(R.string.CancelButton,null)
                .create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

}
