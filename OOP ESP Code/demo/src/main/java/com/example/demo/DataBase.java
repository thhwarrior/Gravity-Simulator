package com.example.demo;

import java.sql.*;
import java.time.LocalTime;

public class DataBase {
    static int id = 0;

    public static int getLastID(String table) throws SQLException{
        int num = 0;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Simulator_DB","root", "root_123");
        Statement statement = connection.createStatement();
        String query = "SELECT MAX(`S.no`) FROM `" + table + "`";
        ResultSet result = statement.executeQuery(query);
        while(result.next()) {
            num = result.getInt(1);
        }
        return num;
    }

    public static int DB_Info_Insert(String user, int planets, String duration) throws SQLException{
        id = getLastID("Simulation_Info") + 1;
        int num = 0;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Simulator_DB","root", "root_123");
        String query = "INSERT INTO `Simulator_DB`.`Simulation_Info`(`S.no`,`USER`,`No. of Planets`,`Duration`) VALUES(?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1,id);
        statement.setString(2,user);
        statement.setInt(3,planets);
        statement.setString(4,duration);
        num = statement.executeUpdate(); //returns 1 if row successfully inserted
        connection.close();
        return num;
    }

    //new paste
    public static int DB_getTotalTables() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Simulator_DB","root", "root_123");
        DatabaseMetaData meta = (DatabaseMetaData) connection.getMetaData();
        ResultSet result = meta.getTables(null,null,null, new String[]{"TABLE"});
        int num = 0;
        while (result.next()){
            System.out.println(result.getString("TABLE_NAME"));
            num++;
        }
        return num;
    }

    public static void DB_Create_Table() throws SQLException{
        int tableNo = DB_getTotalTables() - 1;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Simulator_DB","root", "root_123");
        String query = "CREATE TABLE `Simulator_DB`.`Simulation_Log_" + tableNo + "`(`S.no` INT NOT NULL AUTO_INCREMENT, `Time` VARCHAR(50) NULL, `Description` VARCHAR(150) NULL, PRIMARY KEY(`S.no`))";
        Statement statement = connection.createStatement();
        statement.execute(query);
        connection.close();
    }


    public static int DB_Detail_Insert(String log) throws SQLException{
        int tableNo = DB_getTotalTables() - 2;
        String time = (LocalTime.now()).toString();
        int num = 0;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Simulator_DB","root", "root_123");
        String query = "INSERT INTO `Simulator_DB`.`Simulation_Log_" + tableNo + "`(`Time`,`Description`) VALUES(?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1,time);
        statement.setString(2, log);
        num = statement.executeUpdate();
        connection.close();
        return num;
    }


    public static void main(String[] args) {
        try{
            //creating connection instance/object
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/Simulator_DB","root", "root_123");
//            //creating a statement instance/object
//            Statement statement = connection.createStatement();
//            //creating table for simulation info
//            //statement.execute("CREATE TABLE `Simulator_DB`.`Simulation_Info`(`S.no` INT NOT NULL, `USER` VARCHAR(50) NULL, `No. of Planets` INT NULL, `Duration` VARCHAR(50) NULL, PRIMARY KEY(`S.no`))");
//            //testing info insert method
//            DB_Info_Insert("Ali", 3, "5mins");
//            DB_Info_Insert("Hamaz", 2, "5mins");
//            DB_Info_Insert("Ali", 2, "5mins");
//            DB_Info_Insert("Hamza", 3, "5mins");
//            DB_Info_Insert("Omer", 1, "5mins");
////            System.out.println(getLastID());
//
//            //creating log table for storing simulation details
//            //statement.execute("CREATE TABLE `Simulator_DB`.`Simulation_Log`(`S.no` INT NOT NULL, `Time` VARCHAR(50) NULL, `Description` VARCHAR(150) NULL, PRIMARY KEY(`S.no`))");
//            //testing detail insert method
            DB_Detail_Insert("Created Planet: ABC");
            DB_Detail_Insert("Created Planet: DEF");
            DB_Detail_Insert("Created Planet: GHI");
            DB_Detail_Insert("Created Planet: JKL");
            DB_Detail_Insert("Created Planet: MNO");
            DB_Detail_Insert("Created Planet: PQR");
            DB_Detail_Insert("Created Planet: STU");
            DB_Detail_Insert("Created Planet: VWX");
            DB_Detail_Insert("Created Planet: YZ1");
            DB_Detail_Insert("Created Planet: 12A");
            DB_Detail_Insert("Created Planet: 31B");

//            System.out.println(DB_getTotalTables());
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }
}
