package com.kf.mqstatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.mq.MQC;
import com.ibm.mq.MQQueue;
import com.ibm.mq.constants.MQConstants;

@SpringBootApplication
@RestController
public class Dashboard {

	public static void main(String[] args) {
		SpringApplication.run(Dashboard.class, args);
	}

	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/{qmName}/listOfAllQueues", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray getAllQueues(@PathVariable("qmName") String qmName) throws Exception {
		QueueManagerDAO qmd=new QueueManagerDAO();
		JSONObject qmDetails = qmd.getqmsDetails(qmName);
		int portNumber = ((Long)qmDetails.get("port")).intValue();
		QueueManager qm=new QueueManager(qmDetails.get("Server").toString(),portNumber, qmDetails.get("channel").toString(), qmDetails.get("Name").toString());
		return qm.listOfQueues();
	}
	
	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/{qmName}/getStatistics/{name}", method = RequestMethod.GET)
	@ResponseBody
	public Map mqStatus(@PathVariable("name") String name) throws Exception {
	QueueManager qm=new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");
	return qm.getStatistics(name);
	}
	/*@RequestMapping(value = "/mqStatus", method = RequestMethod.GET)
	@ResponseBody
	public Map mqStatus() throws Exception {
		QueueManager qm=new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");
		return qm.getStatistics();
	}*/
	
	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/{qmName}/listofLocalqueue", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList listofqueues(@PathVariable("qmName") String qmName) throws Exception {
		QueueManagerDAO qmd=new QueueManagerDAO();
		JSONObject qmDetails = qmd.getqmsDetails(qmName);
		int portNumber = ((Long)qmDetails.get("port")).intValue();
		QueueManager qm=new QueueManager(qmDetails.get("Server").toString(),portNumber, qmDetails.get("channel").toString(), qmDetails.get("Name").toString());
		return qm.listOfQueue();
	}
	
	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/{qmName}/listofAliasqueue", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList listofAliasqueues(@PathVariable("qmName") String qmName) throws Exception {
		QueueManagerDAO qmd=new QueueManagerDAO();
		JSONObject qmDetails = qmd.getqmsDetails(qmName);
		int portNumber = ((Long)qmDetails.get("port")).intValue();
		QueueManager qm=new QueueManager(qmDetails.get("Server").toString(),portNumber, qmDetails.get("channel").toString(), qmDetails.get("Name").toString());
		return qm.listOfAliasQueue();
	}
	
	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/{qmName}/channelStatus", method = RequestMethod.GET)
	@ResponseBody
	public Map mqChannelStatus(@PathVariable("qmName") String qmName) throws Exception {
		QueueManagerDAO qmd=new QueueManagerDAO();
		JSONObject qmDetails = qmd.getqmsDetails(qmName);
		int portNumber = ((Long)qmDetails.get("port")).intValue();
		QueueManager qm=new QueueManager(qmDetails.get("Server").toString(),portNumber, qmDetails.get("channel").toString(), qmDetails.get("Name").toString());
		return qm.checkChannelStatus();
	}
	
	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/{qmName}/listOfGetInhibitedQueues", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList listofGetInhibitedQueues(@PathVariable("qmName") String qmName) throws Exception {
		QueueManagerDAO qmd=new QueueManagerDAO();
		JSONObject qmDetails = qmd.getqmsDetails(qmName);
		int portNumber = ((Long)qmDetails.get("port")).intValue();
		QueueManager qm=new QueueManager(qmDetails.get("Server").toString(),portNumber, qmDetails.get("channel").toString(), qmDetails.get("Name").toString());
		return qm.getQueueGetStatus();
	}

	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/{qmName}/listOfPutInhibitedQueues", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList listofPutInhibitedQueues(@PathVariable("qmName") String qmName) throws Exception {
		QueueManagerDAO qmd=new QueueManagerDAO();
		JSONObject qmDetails = qmd.getqmsDetails(qmName);
		int portNumber = ((Long)qmDetails.get("port")).intValue();
		QueueManager qm=new QueueManager(qmDetails.get("Server").toString(),portNumber, qmDetails.get("channel").toString(), qmDetails.get("Name").toString());
		return qm.getQueuePutStatus();
	}

	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/{qmName}/listOfChannelsStopped", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList getChannelStatus(@PathVariable("qmName") String qmName) throws Exception {
		QueueManagerDAO qmd=new QueueManagerDAO();
		JSONObject qmDetails = qmd.getqmsDetails(qmName);
		int portNumber = ((Long)qmDetails.get("port")).intValue();
		QueueManager qm=new QueueManager(qmDetails.get("Server").toString(),portNumber, qmDetails.get("channel").toString(), qmDetails.get("Name").toString());
		return qm.getChannelStatus();
	}

	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/{qmName}/updatequeuestatusGet/{name}", method = RequestMethod.GET)
	public String removeGetInhibition(@PathVariable("qmName") String qmName,@PathVariable("name") String qname) throws Exception
	{
		System.out.println("\nFrom Resource "+qname+"\n");
		QueueManagerDAO qmd=new QueueManagerDAO();
		JSONObject qmDetails = qmd.getqmsDetails(qmName);
		int portNumber = ((Long)qmDetails.get("port")).intValue();
		MQRead readQ = new MQRead(qmDetails.get("Server").toString(),portNumber, qmDetails.get("channel").toString(), qmDetails.get("Name").toString());
		return readQ.removeInhibition1(qname);
		
	}
	
	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/{qmName}/updatequeuestatusPut/{name}", method = RequestMethod.GET)
	public String removePutInhibition(@PathVariable("qmName") String qmName,@PathVariable("name") String qname) throws Exception
	{
		System.out.println("\nFrom Resource "+qname+"\n");
		System.out.println("\nFrom Resource "+qname+"\n");
		QueueManagerDAO qmd=new QueueManagerDAO();
		JSONObject qmDetails = qmd.getqmsDetails(qmName);
		int portNumber = ((Long)qmDetails.get("port")).intValue();
		MQRead readQ = new MQRead(qmDetails.get("Server").toString(),portNumber, qmDetails.get("channel").toString(), qmDetails.get("Name").toString());
		return readQ.removeInhibition2(qname);
		
	}

	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/{qmName}/updatechannelStatus/{name}", method = RequestMethod.GET)
	@ResponseBody
	public String mqChannelUpdateStatus(@PathVariable("qmName") String qmName,@PathVariable("name") String channelname) throws Exception
	{
		QueueManager qm=new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");
		//channelname = "DYNATRACE.SVRCONN";
		return qm.channelRestart(channelname);
	}
	
	
	

}
