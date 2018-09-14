package com.upay.commons.csv;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract interface ResultSetHelper
{
  public abstract String[] getColumnNames(ResultSet paramResultSet)
    throws SQLException;
  
  public abstract String[] getColumnValues(ResultSet paramResultSet)
    throws SQLException, IOException;
}


/* Location:              C:\Users\admin\Downloads\opencsv-2.4.jar!\au\com\bytecode\opencsv\ResultSetHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */