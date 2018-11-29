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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "/updatequeuestatus/{name}", method = RequestMethod.PUT)
	public void removePutInhibition(@PathVariable("name") String name) throws Exception
	{
		final String queueName = "TEST";  
		MQQueue queue = null ;
		int openOptions = MQConstants.MQOO_FAIL_IF_QUIESCING | MQConstants.MQOO_SET;
		//queue = getQueueManager().accessQueue(queueName, openOptions, null, null, null);
		QueueManager qm=new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");
		int option1 = qm.getQueuePutStatus(queueName);
		if (option1==1){
			System.out.println("Start mesage sending");
			queue.setInhibitPut(MQC.MQQA_PUT_ALLOWED);
			System.out.println("Queue put allowed successfully");

		}
		
	}
	
	@CrossOrigin(allowedHeaders = "Access-Control-Allow-Origin")
	@RequestMapping(value = "/updatechannelStatus/DYNATRACE.SVRCONN", method = RequestMethod.PUT)
	@ResponseBody
	public String mqChannelUpdateStatus(String channelname) throws Exception{
		QueueManager qm=new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");
		channelname = "DYNATRACE.SVRCONN";
		return qm.channelRestart(channelname);
	}
	
	

}
