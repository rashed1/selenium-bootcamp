package util;

/**
 * Created by Rashed on 1/17/2016.
 */
    public class DBConnectTest {


    static ConnectDB db = null;
    public static void main(String[] args)throws Exception {

        db = new ConnectDB();
        db.readDataBase();


    }



}
