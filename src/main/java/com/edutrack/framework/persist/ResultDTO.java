package com.edutrack.framework.persist;

import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ResultDTO
{

    public ResultDTO()
    {
        rs = null;
        statement = null;
    }

    public void close()
    {
        try
        {
            if(rs != null)
                rs.close();
        }
        catch(Exception e)
        {
            System.out.println("resultset-Error!");
        }
        try
        {
            if(statement != null)
                statement.close();
        }
        catch(Exception e)
        {
            System.out.println("Statement-Error!");
        }
    }

    public ResultSet rs;
    public PreparedStatement statement;
}