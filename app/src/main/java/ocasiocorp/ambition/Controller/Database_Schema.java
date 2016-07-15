package ocasiocorp.ambition.Controller;

public class Database_Schema {

    public static final String DATABASE_NAME ="Ambitions.db";
    public static final int VERSION = 1;

    public static class Goals{
        public static final String NAME = "Goals";

        public static class Cols{

            public static final String GOALNAME = "NAME";
            public static final String ISIMPORTANT = "IsImportant";
            public static final String ID = "Id";
            public static final String IsDone = "Done";
            public static final String WHICH_LIST = "FIND_LIST";
        }
    }

    public static class GoalLists{
        public static final String NAME = "Lists";

        public static class Cols{
            public static final String LISTNAME = "name";
            public static final String ID = "Id";
        }
    }
}
