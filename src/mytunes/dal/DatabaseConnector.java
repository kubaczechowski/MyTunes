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
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setUser("");
        dataSource.setPassword("");
        dataSource.setDatabaseName("");
    }

    public Connection getConnection() throws SQLServerException
    {
        return dataSource.getConnection();
    }

}

