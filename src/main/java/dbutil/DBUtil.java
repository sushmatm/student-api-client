package dbutil;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBUtil {

    public static final String PROPERTIES_FILE_PATH = "/Users/sushma/Desktop/Project/StudentProfile/src/main/resources/db.properties";
    private static Logger logger = LogManager.getLogger(DBUtil.class);
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;

    public DBUtil() {
        try {
            Properties properties = new Properties();
            File file = new File(PROPERTIES_FILE_PATH);
            InputStream inputStream = new FileInputStream(file);
            properties.load(inputStream);
            String dbServerUrl = properties.getProperty("url");
            String userName = properties.getProperty("username");
            String password = properties.getProperty("password");
            //Class.forName(dbDriver);
            logger.info("connecting to selected data base");
            this.connection = DriverManager.getConnection(dbServerUrl, userName, password);
            logger.info("successfully connected!");
            this.statement = this.connection.createStatement();
            logger.info("statement executed successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeSelectQuery(String selectQuery) {
        ResultSet resultSet = null;
        try {
            resultSet = this.statement.executeQuery(selectQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public Map<String, Object> readRow(String query) throws SQLException {
        resultSet = this.statement.executeQuery(query);
        resultSetMetaData = resultSet.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        Map<String, Object> row = new HashMap<String, Object>();
        if (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                row.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
            }
        }
        resultSet.close();
        return row;
    }

    public int executeUpdateQuery(String updateQuery) {
        int result = 0;
        try {
            result = this.statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
