package mytunes.dal;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DatabaseConnector {

    private SQLServerDataSource dataSource;

    public DatabaseConnector()
    {
        //kamilas DB
        /*
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setUser("CSe20B_10");
        dataSource.setPassword("database000");
        dataSource.setDatabaseName("ItunesGROUP2");

         */

        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setUser("vCSe20B_8");
        dataSource.setPassword("potatoe2020");
        dataSource.setDatabaseName("ItunesGROUP2");
    }

    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }

}

