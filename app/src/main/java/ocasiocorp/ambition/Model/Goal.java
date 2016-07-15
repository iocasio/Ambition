package ocasiocorp.ambition.Model;

import java.util.UUID;

/**
 * Created by Ocasiorey on 5/15/2016.
 */
public class Goal{
    private String mGoalName;
    private boolean mIsImportant;
    private UUID mId;
    private boolean isFinished;
    private String Which_List;

    public Goal(){
        mId = UUID.randomUUID();
    }

    public String getmGoalName() {
        return mGoalName;
    }

    public void setmGoalName(String mGoalName) {
        this.mGoalName = mGoalName;
    }

    public boolean ismIsImportant() {
        return mIsImportant;
    }

    public void setmIsImportant(boolean mIsImportant) {
        this.mIsImportant = mIsImportant;
    }

    public UUID getmId() {
        return mId;
    }

    public void setmId(UUID mId) {
        this.mId = mId;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getWhich_List() {
        return Which_List;
    }

    public void setWhich_List(String which_List) {
        Which_List = which_List;
    }
}
