package ocasiocorp.ambition.View.View_SupportingClasses;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ocasiocorp.ambition.Controller.Database_Controller;
import ocasiocorp.ambition.Model.Goal;
import ocasiocorp.ambition.R;
import ocasiocorp.ambition.View.View_SupportingClasses.PopUp_Classes.Goal_PopUps.Edit_PopUp;
import ocasiocorp.ambition.View.View_SupportingClasses.PopUp_Classes.Goal_PopUps.MoreOptions_PopUp;

/**
 * Created by Ocasiorey on 6/7/2016.
 */
public class RecyclerView_Adapter{
    private ArrayList<Goal> Goals;
    MyAdapter adapter;
    Context context;
    private Database_Controller mDatabaseController;
    MediaPlayer mediaPlayer;
    Fragment fragment;

    public RecyclerView_Adapter(ArrayList<Goal> goals, Context cntxt, Fragment fragments) {
        Goals = goals;
        adapter = new MyAdapter(Goals);
        context = cntxt;
        mediaPlayer = new MediaPlayer();
        fragment = fragments;
        mDatabaseController = Database_Controller.get(context);
    }

    //////////////////////////////////ADAPTER\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<Goal> mGoals;


        public MyAdapter(ArrayList<Goal> goals) {
            mGoals = goals;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.recycler_view_item_layout, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Goal goal = mGoals.get(position);
            holder.bindGoal(goal);
        }

        @Override
        public int getItemCount() {
            return mGoals.size();
        }
    }
    ////////////////////////////////////ADAPTER\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


    ///////////////////////////////////VIEW HOLDER\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView, importantTXT;
        private CheckBox mCheckBox;
        private Goal mGoal;
        private ImageView imgView;


        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity context = (AppCompatActivity) v.getContext();
                    FragmentManager manager = context.getSupportFragmentManager();
                    Edit_PopUp dialog = Edit_PopUp.newInstance(mGoal);
                    dialog.setTargetFragment(fragment, 2);
                    dialog.show(manager, "EditDialog");
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //Add delete thingy
                    AppCompatActivity context = (AppCompatActivity) v.getContext();
                    FragmentManager manager = context.getSupportFragmentManager();
                    MoreOptions_PopUp dialog = MoreOptions_PopUp.newInstance(mGoal);
                    dialog.setTargetFragment(fragment, 1);
                    dialog.show(manager, "GoalDialog");
                    return true;
                }
            });
            mTextView = (TextView) itemView.findViewById(R.id.text);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            imgView = (ImageView) itemView.findViewById(R.id.image);
            importantTXT = (TextView) itemView.findViewById(R.id.Important_textView);
            mCheckBox.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mGoal.setFinished(mCheckBox.isChecked());
                    mDatabaseController.updateGoal(mGoal);
                    if (mCheckBox.isChecked() == true) {
                        boolean value = checkWinning(Goals,context);
                        PlaySound(value, mGoal.ismIsImportant(), v);
                    }
                }
            });
        }


        public void bindGoal(Goal goal) {
            mGoal = goal;
            mCheckBox.setChecked(mGoal.isFinished());
            Boolean setStar = mGoal.ismIsImportant();
            if (goal.ismIsImportant()){
                imgView.setBackgroundResource(R.mipmap.ic_stars_pressed);
                importantTXT.setText(R.string.isImportant);
            }else{
                imgView.setBackgroundResource(0);
                importantTXT.setText("");
            }
            mTextView.setText(mGoal.getmGoalName());
        }

        public boolean checkWinning(ArrayList<Goal> goals, Context context) {
            for (int i = 0; i < goals.size(); i++) {
                if (goals.get(i).isFinished() == false) {
                    return false;
                }
            }
            return true;
        }
        public void PlaySound(Boolean winningSound, boolean isImportant, View view) {
            if(winningSound){
                mediaPlayer = MediaPlayer.create(context,R.raw.victory);
                Snackbar snackbar = Snackbar.make(view, R.string.completion, Snackbar.LENGTH_LONG);
                snackbar.show();
                mediaPlayer.start();
            }else {
                if (isImportant) {
                    mediaPlayer = MediaPlayer.create(context, R.raw.collect);
                    mediaPlayer.start();
                    Snackbar snackbar = Snackbar.make(view, R.string.congrats, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    mediaPlayer = MediaPlayer.create(context, R.raw.success);
                    Snackbar snackbar = Snackbar.make(view, R.string.lessCongrats, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    mediaPlayer.start();
                }
            }
        }
    }
    ////////////////////////////////////VIEW HOLDER\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


    public MyAdapter getAdapter() {
        return adapter;
    }

    public Fragment getFragment() {
        return fragment;
    }
}
