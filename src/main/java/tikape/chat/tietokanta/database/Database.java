package tikape.chat.tietokanta.database;


import java.sql.*;

public class Database {

    private String databaseAddress;
    private Boolean debug = true;
    private String username;
    private String password;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }
    
    public Database(String databaseAddress, String username, String password) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        if (username == null || password == null) {
            return DriverManager.getConnection(databaseAddress);
        }
        return DriverManager.getConnection(databaseAddress, username, password);
    }
    
    public int update(String updateQuery) throws SQLException {
        Statement stmt = getConnection().createStatement();
        int changes = stmt.executeUpdate(updateQuery);

        if(debug) {
            System.out.println("---");
            System.out.println(updateQuery);
            System.out.println("Changed rows: " + changes);
            System.out.println("---");
        }
        stmt.close();

        return changes;
    }
}