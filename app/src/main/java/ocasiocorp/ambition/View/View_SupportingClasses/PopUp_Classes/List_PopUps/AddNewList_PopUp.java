package ocasiocorp.ambition.View.View_SupportingClasses.PopUp_Classes.List_PopUps;

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
import android.widget.EditText;

import ocasiocorp.ambition.R;

/**
 * Created by Ocasiorey on 6/10/2016.
 */
public class AddNewList_PopUp extends DialogFragment {

    private static final String ARG_NEW_TITLE ="new_title";

    private EditText mEditText;
    private String newListTitle;
    private static Fragment fragment;
    public AddNewList_PopUp(){
    }

    public static AddNewList_PopUp newInstance(Fragment frg){
        fragment = frg;
        AddNewList_PopUp dialog = new AddNewList_PopUp();
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.addnewlist_popup, null);
        mEditText = (EditText)view.findViewById(R.id.EditText);
        return new AlertDialog.Builder(getActivity())
                .setView(view).setTitle(R.string.AddAmbition)
                .setPositiveButton(R.string.SaveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        newListTitle = mEditText.getText().toString();
                        Intent intent = new Intent();
                        intent.putExtra(ARG_NEW_TITLE, newListTitle);;
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
