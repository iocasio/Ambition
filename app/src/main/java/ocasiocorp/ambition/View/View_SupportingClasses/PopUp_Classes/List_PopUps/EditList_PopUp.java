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
import android.widget.EditText;
import ocasiocorp.ambition.R;

/**
 * Created by Ocasiorey on 6/16/2016.
 */
public class EditList_PopUp extends DialogFragment {
    private static final String ARG_NEW_TITLE ="new_title";
    private EditText mEditText;
    public String List_String;

    //Default constructor
    public EditList_PopUp(){

    }

    //Builder method
    public static EditList_PopUp newListInstance(String inputList){
        EditList_PopUp dialog = new EditList_PopUp();
        Bundle args = new Bundle();
        args.putString(ARG_NEW_TITLE, inputList);
        dialog.setArguments(args);

        return dialog;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.editlist_popup, null);
        Bundle args = getArguments();
        List_String = args.getString(ARG_NEW_TITLE);
        mEditText = (EditText)view.findViewById(R.id.EditText);
        mEditText.setText(List_String);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.moreOptionsTitle)
                .setPositiveButton(R.string.Edit_Button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        List_String = mEditText.getText().toString();
                        intent.putExtra(ARG_NEW_TITLE, List_String);
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
