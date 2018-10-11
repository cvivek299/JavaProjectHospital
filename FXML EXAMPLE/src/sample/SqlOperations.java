package sample;

import connectivity.ConnectionClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlOperations {
    
    public static float floatSelect(String sql) throws SQLException {
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        float value=0;
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            //Ensure we start with first row
            resultSet.beforeFirst();
            if (resultSet.next()) {
                value=resultSet.getFloat(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(resultSet!=null)
                resultSet.close();

            if(statement!=null)
                statement.close();

            if(connection!=null)
                connection.close();
        }

        return value;

    }

    public static int intSelect(String sql) throws SQLException {
        int value=0;
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            //Ensure we start with first row
            resultSet.beforeFirst();
            if (resultSet.next()) {
                value=resultSet.getInt(1);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(resultSet!=null)
                resultSet.close();

            if(statement!=null)
                statement.close();

            if(connection!=null)
                connection.close();
        }
        return value;
    }

    public static int countInTable(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            //Ensure we start with first row
            resultSet.beforeFirst();
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)
                resultSet.close();

            if (statement != null)
                statement.close();

            if (connection != null)
                connection.close();
        }
        return count;

    }

    public static boolean checkIfAnyPresentAlready(String sql) throws SQLException {

        return (countInTable(sql)!=0);
    }

    public static void insert(String sql) throws SQLException {

        Connection connection=null;
        Statement statement=null;
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(statement!=null)
                statement.close();

            if(connection!=null)
                connection.close();
        }

    }

    public static void update(String sql) throws SQLException {
        Connection connection=null;
        Statement statement=null;
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(statement!=null)
                statement.close();

            if(connection!=null)
                connection.close();
        }
    }



}
