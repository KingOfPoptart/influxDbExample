package com.neverwinterdp.influxdbExample;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Serie;


public class influxdb {
  public static void main(String args[]) {
    String host = "http://jenkinsdp.do.demandcube.com:8086";
    String user = "root";
    String pw = "root";
    String databaseName = "testDb";
    String seriesName = "testSeries";
        
    
    InfluxDB influxDB = InfluxDBFactory.connect(host, user, pw);
    
    clearAndCreateDatabase(influxDB, databaseName);
    Random rand = new Random();
    
    System.err.println("Starting");
    for(int i=0; i<100; i++){
      Serie serie = new Serie.Builder(seriesName)
        .columns("randomInt", "randomInt2")
        .values(rand.nextInt(1000000),rand.nextInt(5000))
        .values(rand.nextInt(1000000),rand.nextInt(5000))
        .values(rand.nextInt(1000000),rand.nextInt(5000))
        .values(rand.nextInt(1000000),rand.nextInt(5000))
        .build();
      influxDB.write(databaseName, TimeUnit.SECONDS, serie);
      System.err.println(".");
    }
    
    System.err.println("Done");
    
  }
  
  
  
  public static void clearAndCreateDatabase(InfluxDB influxDB, String databaseName ){
    try{
      influxDB.deleteDatabase(databaseName);
    } catch(java.lang.RuntimeException e){
      if(e.getMessage() == "Database "+databaseName+" doesn't exist"){
        //Handle database not existing
      }
      else
        e.printStackTrace();
    }
    
    try{
      influxDB.createDatabase(databaseName);
    } catch(java.lang.RuntimeException e){
      if(e.getMessage() == "database "+databaseName+" exists"){
        //Handle database already existing
      }
      else
        e.printStackTrace();
    }
  }
}