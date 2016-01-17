package util;

/**
 * Created by Rashed on 1/17/2016.
 */
public class TestConnectDB {
    public static void main(String[] args) throws Exception{
        ConnectDB db = new ConnectDB();
        System.out.println(db.readDataBase());
    }


}
