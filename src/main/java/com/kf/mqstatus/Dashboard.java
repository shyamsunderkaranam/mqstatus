package com.kf.mqstatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
	@RequestMapping(value = "/getStatistics/{name}", method = RequestMethod.GET)
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
	@RequestMapping(value = "/listofLocalqueue", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList listofqueues() throws Exception {
		QueueManager qm=new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");
		return qm.listOfQueue();
	}
	
	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/listofAliasqueue", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList listofAliasqueues() throws Exception {
		QueueManager qm=new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");
		return qm.listOfAliasQueue();
	}
	
	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/channelStatus", method = RequestMethod.GET)
	@ResponseBody
	public Map mqChannelStatus() throws Exception {
		QueueManager qm=new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");
		return qm.checkChannelStatus();
	}
	
	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/listOfGetInhibitedQueues", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList listofGetInhibitedQueues() throws Exception {
		QueueManager qm=new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");
		return qm.getQueueGetStatus();
	}

	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/listOfPutInhibitedQueues", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList listofPutInhibitedQueues() throws Exception {
		QueueManager qm=new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");
		return qm.getQueuePutStatus();
	}

	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/listOfChannelsStopped", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList getChannelStatus() throws Exception {
		QueueManager qm=new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");
		return qm.getChannelStatus();
	}

	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/updatequeuestatusGet/{name}", method = RequestMethod.GET)
	public String removeGetInhibition(@PathVariable("name") String qname) throws Exception
	{
		System.out.println("\nFrom Resource "+qname+"\n");
		MQRead readQ = new MQRead();
		return readQ.removeInhibition1(qname);
		
	}
	
	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/updatequeuestatusPut/{name}", method = RequestMethod.GET)
	public String removePutInhibition(@PathVariable("name") String qname) throws Exception
	{
		System.out.println("\nFrom Resource "+qname+"\n");
		MQRead readQ = new MQRead();
		return readQ.removeInhibition2(qname);
		
	}

	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/updatechannelStatus/{name}", method = RequestMethod.PUT)
	@ResponseBody
	public String mqChannelUpdateStatus(@PathVariable("name") String channelname) throws Exception{
		QueueManager qm=new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");
		//channelname = "DYNATRACE.SVRCONN";
		return qm.channelRestart(channelname);
	}
	
	
	

}
