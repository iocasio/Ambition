package ocasiocorp.ambition.Model;

import java.util.UUID;

/**
 * Created by Ocasiorey on 6/8/2016.
 */
public class GoalList {
    private String title;
    private UUID uuid;

    public GoalList(){
        uuid = UUID.randomUUID();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getUuid(){
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
