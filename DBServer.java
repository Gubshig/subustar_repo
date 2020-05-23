public class DBServer {
    private static String[] student_columns = {"ur", "pw", "name", "age",
                                        "aboard", "academy", "email", "phone", "busnum", "rfidid"};

    private static String[] student_dttypes = {"nvarchar(32)", "nvarchar(128)", "nvarchar(8)", "int",
                                        "int", "nvarchar(32)", "nvarchar(64)", "int", "int", "nvarchar(16)"};

    private static String[] student_constrs = {"primary key", "not null", "not null", "not null",
                                        "not null", "not null", "not null", "not null", "not null", "not null"};

    private static String student_table = "student_table";

    private static String bus_table = "bus_table";

    private static String[] bus_columns = {"busnum", "location", "temperature", "speed", "humidity"};

    private static String[] bus_dttypes = {"int", "nvarchar(32)", "int", "float", "float"};

    private static String[] bus_constrs = {"primary key", "not null", "not null", "not null", "not null", "not null"};

    private static String teacher_table = "teacher_table";

    private static String[] teacher_columns = {"phone", "tname", "aboard"};

    private static String[] teacher_dttypes = {"nvarchar(16)", "nvarchar(16)", "int"};

    private static String[] teacher_constrs = {"primary key", "not null", "not null"};

    private static String weather_table = "weather_table";

    private static String[] weather_columns = {"date", "weather", "finedust"};

    private static String[] weather_dttypes = {"int", "nvarchar(16)", "nvarchar(16)"};

    private static String[] weather_constrs = {"primary key", "not null", "not null"};

    private static String academy_table = "academy_table";

    private static String[] academy_columns = {"name", "phone", "address", "type"};

    private static String[] academy_dttypes = {"nvarchar(32)", "int", "nvarchar(16)", "nvarchar(16)"};

    private static String[] academy_constrs = {"primary key", "not null", "not null", "not null"};

    private static String log_table = "log_table";

    private static String[] log_columns = {"ur", "time", "aboard"};

    private static String[] log_dttypes = {"nvarchar(64)", "nvarchar(64)", "nvarchar(64)"};

    private static String[] log_constrs = {"not null", "not null", "not null"};

    private static String db = "subustar";
    private static String un = "cit";
    private static String pw = "citcitcit";
    private static String ip = "27.35.9.249";
    private static String pt = "60000";

    public static void main(String args[] ) {
        SQLHandler sqlHandler = new SQLHandler(db, un, pw, ip, pt);

        sqlHandler.drop(student_table);
        sqlHandler.drop(bus_table);
        sqlHandler.drop(teacher_table);
        sqlHandler.drop(weather_table);
        sqlHandler.drop(academy_table);
        sqlHandler.drop(log_table);
        sqlHandler.create(student_table, student_columns, student_dttypes, student_constrs);
        sqlHandler.create(bus_table, bus_columns, bus_dttypes, bus_constrs);
        sqlHandler.create(teacher_table, teacher_columns, teacher_dttypes, teacher_constrs);
        sqlHandler.create(weather_table, weather_columns, weather_dttypes, weather_constrs);
        sqlHandler.create(academy_table, academy_columns, academy_dttypes, academy_constrs);
        sqlHandler.create(log_table, log_columns, log_dttypes, log_constrs);

        //셈플 데이터
        String samples[] = {
                "'gubshig', 'QWERTY', '김준호', '15', '0', 'cit', 'ytjk060704@gmail.com', '01058882666', '5678', '584191365850'",
                "'galsag', 'QWERTY', '박지호', '16', '1', 'citcit', 'asddd060704@gmail.com', '01058882677', '5679', '584191037985'"
        };

        sqlHandler.insert(student_table, samples[0]);
        sqlHandler.insert(student_table, samples[1]);
    }
}
