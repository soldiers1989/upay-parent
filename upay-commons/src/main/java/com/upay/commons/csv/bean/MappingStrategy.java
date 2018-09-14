package com.upay.commons.csv.bean;

import com.upay.commons.csv.CSVReader;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;

public abstract interface MappingStrategy<T>
{
  public abstract PropertyDescriptor findDescriptor(int paramInt)
    throws IntrospectionException;
  
  public abstract T createBean()
    throws InstantiationException, IllegalAccessException;
  
  public abstract void captureHeader(CSVReader paramCSVReader)
    throws IOException;
}


/* Location:              C:\Users\admin\Downloads\opencsv-2.4.jar!\au\com\bytecode\opencsv\bean\MappingStrategy.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */