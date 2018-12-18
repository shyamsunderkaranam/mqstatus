package com.kf.mqstatus;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.ibm.mq.MQQueue;

public class QueueManagerDAO {

	
	//final Map<String, String> qms;
	//ArrayList<Map> qmDetails;

	public QueueManagerDAO() {
		
	}
	
	public JSONObject getqmsDetails(String qmName) {

		JSONParser jsonParser = new JSONParser();
		JSONObject t;
		try (FileReader reader = new FileReader("config/QM.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
        	
            JSONArray employeeList = (JSONArray) obj;
            //System.out.println("JSON ARRAY IS :\n"+employeeList);
             
            //JSONArray jsonArray= (JSONArray) obj;
            Iterator<JSONArray> it = employeeList.iterator();
            int i=0;
            while(it.hasNext()){
            	t=(JSONObject)employeeList.get(i);
            	
            	//System.out.println(t.get("Name") +"  Parameter is "+qmName);
            	//String qName= (String)t.get("Name");
            	if(t.get("Name").equals(qmName)) {
                //System.out.println("Found it "+t);
                return t;}
                it.next();
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return new JSONObject();
	}

	public static void main(String[] args) {
		QueueManagerDAO qmd=new QueueManagerDAO();
		System.out.println(""+qmd.getqmsDetails("QMQINST1"));
	}
	
}
