package ocasiocorp.ambition.View.View_SupportingClasses;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import java.util.ArrayList;
import java.util.UUID;
import ocasiocorp.ambition.Controller.Database_Controller;
import ocasiocorp.ambition.Model.Goal;
import ocasiocorp.ambition.Model.GoalList;
import ocasiocorp.ambition.R;
import ocasiocorp.ambition.View.View_SupportingClasses.PopUp_Classes.List_PopUps.AddNewList_PopUp;
import ocasiocorp.ambition.View.View_SupportingClasses.PopUp_Classes.Goal_PopUps.AddNew_PopUp;
import ocasiocorp.ambition.View.View_SupportingClasses.PopUp_Classes.List_PopUps.EditList_PopUp;
import ocasiocorp.ambition.View.View_SupportingClasses.PopUp_Classes.List_PopUps.MoreListOptions_PopUp;

/**
 * A simple {@link Fragment} subclass.
 */
public class List_Fragment extends Fragment {
    private RecyclerView mRecyclerView;
    private static final String ARG_TITLE = "title";
    private static final String ARG_GOALNAME ="Goal";
    private static final String ARG_IMPORTANT = "Important";
    private static final String ARG_NEW_TITLE ="new_title";
    private Database_Controller mDatabaseController;
    private static final String ARG_ID = "ID";
    private ArrayList<Goal> goalList;
    private LinearLayoutManager manager;
    public RecyclerView.Adapter adapter;
    public ReceiverThread UiThread;
    private FragmentManager fragmentManager;
    private String list_title;
    private FloatingActionMenu materialDesignFAM;
    private FloatingActionButton floatingActionButton1, floatingActionButton2;
    private viewPagerCallBack callBack;
    private Button title_button;
    private MediaPlayer player;

    public List_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            list_title= bundle.getString(ARG_TITLE);
            Log.d("TAG", list_title + " LIST TITLE!");
        }
        Log.d("TAG", "ONCREATEVIEW BEING CALLED " + list_title);
        mDatabaseController = mDatabaseController.get(getContext());
        title_button = (Button)view.findViewById(R.id.title_button);
        title_button.setText(list_title);
        title_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditList_PopUp dialog = EditList_PopUp.newListInstance(list_title);
                dialog.setTargetFragment(List_Fragment.this, 4);
                dialog.show(fragmentManager, "EditListDialog");
            }
        });
        title_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                GoalList list = mDatabaseController.getList(list_title);
                MoreListOptions_PopUp dialog = MoreListOptions_PopUp.newInstance(list);
                dialog.setTargetFragment(List_Fragment.this, 5);
                dialog.show(fragmentManager,"MoreListOptions");
                return true;
            }
        });
        player = new MediaPlayer();
        goalList = mDatabaseController.getAllListsGoals(list_title);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        manager = new LinearLayoutManager(getActivity());
        UiThread = new ReceiverThread();
        mRecyclerView.setLayoutManager(manager);
        RecyclerView_Adapter wrapperClass = new RecyclerView_Adapter(goalList,getContext(),this);
        adapter = wrapperClass.adapter;
        materialDesignFAM = (FloatingActionMenu)view.findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton)view.findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton)view.findViewById(R.id.material_design_floating_action_menu_item2);
        materialDesignFAM.setMenuButtonColorNormalResId(R.color.colorAccent);
        materialDesignFAM.setMenuButtonColorPressedResId(R.color.colorAccent);
        floatingActionButton1.setColorNormalResId(R.color.colorAccent);
        floatingActionButton1.setColorPressedResId(R.color.colorAccent);
        floatingActionButton2.setColorNormalResId(R.color.colorAccent);
        floatingActionButton2.setColorPressedResId(R.color.colorAccent);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = List_Fragment.this;
                AddNew_PopUp popUp = AddNew_PopUp.newInstance(fragment);
                popUp.setTargetFragment(fragment, 0);
                popUp.show(fragmentManager, "PopUpDialog");
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = List_Fragment.this;
                AddNewList_PopUp popUp = AddNewList_PopUp.newInstance(fragment);
                popUp.setTargetFragment(fragment, 3);
                popUp.show(fragmentManager, "PopUpDialog");
            }
        });
        mRecyclerView.setAdapter(adapter);
        fragmentManager= getFragmentManager();
        return view;
    }

    public class ReceiverThread extends Thread {
        @Override
        public void run() {
            super.run();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    goalList.clear();
                    ArrayList<Goal>newGoalList = mDatabaseController.getAllListsGoals(list_title);
                    goalList.addAll(newGoalList);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG: RESULT ", Integer.toString(requestCode));
        if(requestCode ==  0){
            if(resultCode == Activity.RESULT_OK){
                Goal newGoal = new Goal();
                String GoalName = data.getStringExtra(ARG_GOALNAME);
                if (GoalName.isEmpty()) {
                    Toast.makeText(getContext(), R.string.NoGoal, Toast.LENGTH_SHORT).show();
                } else {
                    newGoal.setmIsImportant(data.getBooleanExtra(ARG_IMPORTANT, false));
                    newGoal.setmGoalName(GoalName);
                    newGoal.setWhich_List(list_title);
                    //fab.show();
                    Log.d("TAG", list_title + " LIST TITLE!");
                    mDatabaseController.insertGoal(newGoal);
                }
            }
        }else{
            if(requestCode == 1){
                if(resultCode == Activity.RESULT_OK){;
                    String id = data.getStringExtra(ARG_ID);
                    player = MediaPlayer.create(getContext(), R.raw.game_over);
                    player.start();
                    Toast.makeText(getContext(),R.string.flush, Toast.LENGTH_LONG).show();
                    mDatabaseController.deleteGoal(id);
                }
            }else{
                if(requestCode == 2){
                    if(resultCode == Activity.RESULT_OK){
                        String goalName = data.getStringExtra(ARG_GOALNAME);
                        if(goalName.isEmpty()){
                            Toast.makeText(getContext(),R.string.NoGoal,Toast.LENGTH_LONG);
                        }else{
                            Boolean isImportant = data.getBooleanExtra(ARG_IMPORTANT, false);
                            UUID id = UUID.fromString(data.getStringExtra(ARG_ID));
                            Goal goal = mDatabaseController.getGoal(id.toString());
                            goal.setmIsImportant(isImportant);
                            goal.setmGoalName(goalName);
                            mDatabaseController.updateGoal(goal);
                        }
                    }
                }
            }if(requestCode == 3){
                if(resultCode == Activity.RESULT_OK){
                    String title = data.getStringExtra(ARG_NEW_TITLE);
                    if(title.isEmpty()){
                        Toast.makeText(getContext(), R.string.NoGoal, Toast.LENGTH_LONG).show();
                    }else{
                        ArrayList<GoalList> currentLists = mDatabaseController.getAllLists();
                        if(currentLists.size() == 23){
                            Toast.makeText(getContext(), R.string.ListLimit, Toast.LENGTH_LONG);
                        }else{
                            for(int i = 0; i < currentLists.size(); i ++){
                                if(currentLists.get(i).getTitle().equalsIgnoreCase(title)){
                                    Toast.makeText(getContext(),R.string.duplicate_list, Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                            Log.d("list added", "This was still added ..");
                            GoalList list = new GoalList();
                            list.setTitle(title);
                            mDatabaseController.insertList(list);
                            callBack.updateLists(1);
                            return;
                        }
                    }
                }
            }
            if(requestCode == 4){
                if(resultCode == Activity.RESULT_OK){
                    String newTitle = data.getStringExtra(ARG_NEW_TITLE);
                    if(newTitle.isEmpty()){
                        Toast.makeText(getContext(), R.string.NoGoal, Toast.LENGTH_LONG).show();
                    }else{
                        GoalList list = mDatabaseController.getList(list_title);
                        ArrayList<GoalList> currentLists = mDatabaseController.getAllLists();
                        for(int i = 0; i < currentLists.size(); i ++){
                            if(currentLists.get(i).getTitle().equalsIgnoreCase(newTitle)){
                                Toast.makeText(getContext(),R.string.duplicate_list, Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                        list_title = newTitle;
                        title_button.setText(newTitle);
                        ArrayList<Goal> updateGoalList = mDatabaseController.getAllListsGoals(list.getTitle());
                        for(int i =0; i< updateGoalList.size(); i++){
                            updateGoalList.get(i).setWhich_List(newTitle);
                            mDatabaseController.updateGoal(updateGoalList.get(i));
                        }
                        list.setTitle(newTitle);
                        Log.d("TAGGER2", list.getTitle());
                        mDatabaseController.updateList(list);
                    }
                }
            }
            if(requestCode == 5){
                if(resultCode == Activity.RESULT_OK){
                    String id = data.getStringExtra(ARG_ID);
                    String title = data.getStringExtra(ARG_NEW_TITLE);
                    GoalList list = mDatabaseController.getList(title);
                    mDatabaseController.deleteList(id);
                    ArrayList<Goal> deleteGoalList = mDatabaseController.getAllListsGoals(list.getTitle());
                    for(int i =0; i< deleteGoalList.size(); i++){
                        mDatabaseController.deleteGoal(deleteGoalList.get(i).getmId().toString());
                    }
                    callBack.updateLists(2);
                    return;
                }
            }
        }
        UiThread.run();
    }

    public ReceiverThread getUiThread() {
        return UiThread;
    }

    public interface viewPagerCallBack{
        public void updateLists(int resultCode);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callBack = (viewPagerCallBack)activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBack = (viewPagerCallBack)context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
        player.reset();
        player.release();
    }
}
