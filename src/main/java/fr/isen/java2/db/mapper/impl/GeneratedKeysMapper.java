package fr.isen.java2.db.mapper.impl;

import fr.isen.java2.db.mapper.ResultMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by nicolas on 13/02/17.
 */
public class GeneratedKeysMapper implements ResultMapper<Integer>
{
    private List<Integer> integerList;


    @Override
    public List getParsedList() throws SQLException
    {
        return this.integerList;
    }


    @Override
    public void parseResultSet(ResultSet ids) throws SQLException
    {
        if (ids.next())
        {
            int generatedId = (int) ids.getLong(1);
            this.integerList.add(generatedId);
        } else
        {
            System.out.println("can't find generated id");
        }
    }


}
