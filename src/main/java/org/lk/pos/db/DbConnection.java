package org.lk.pos.db;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    private static  DbConnection instance;
    private  final Connection connection;

    private DbConnection(){

        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("/application.properties"));
            String username=properties.getProperty("app.datasource.username");
            String password=properties.getProperty("app.datasource.password");
            String url=properties.getProperty("app.datasource.url");
             connection = DriverManager.getConnection(url, username, password);
             generateSchema();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    private void generateSchema() throws URISyntaxException {


        try {
            URL url = getClass().getResource("/schema.sql");
            Path path = Paths.get(url.toURI());
            String dbScript = Files.readAllLines(path).stream().reduce((prevLine, currLine) -> prevLine + currLine).get();
            connection.createStatement().execute(dbScript);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static DbConnection getInstance(){
        return (instance==null)? instance=new DbConnection():instance;
    }

    public  Connection getConnection(){
        return connection;
    }
}
