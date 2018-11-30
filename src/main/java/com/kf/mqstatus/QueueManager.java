package com.kf.mqstatus;
import com.ibm.mq.jms.MQConnectionFactory;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.pcf.MQCFST;
import com.ibm.mq.pcf.PCFAgent;
import com.ibm.mq.pcf.PCFException;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;
import com.ibm.mq.pcf.PCFParameter;

public class QueueManager {
	
	public MQQueueManager queueManager = null;
	private final String host;
	private final int port;
	private final String channel;
	private final String manager;
	private final MQQueueManager qmgr;
	private MQConnectionFactory mqCF;

	public QueueManager(String host, int port, String channel, String manager) throws MQException {
		this.host = host;
		this.port = port;
		this.channel = channel;
		this.manager = manager;
		this.qmgr = createQueueManager();
	}

	@SuppressWarnings("unchecked")
	private MQQueueManager createQueueManager() throws MQException {
		MQEnvironment.channel = channel;
		MQEnvironment.port = port;
		MQEnvironment.hostname = host;
		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES);
		return new MQQueueManager(manager);
	}

	/*public static void main(String args[]) throws MQException {
		
		QueueManager qm = new QueueManager("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN", "FMQINST1");

		System.out.println(qm.depthOf("TEST"));
		qm.checkChannelStatus();

	}*/
	
	
	/*public synchronized Map getStatistics() throws Exception {
		// System.out.println("inside");
		final Map<String, Object> stats = new LinkedHashMap();
		MQQueue queue = null;
		ArrayList<String> queuelist  = new ArrayList<String>();
	    queuelist = listOfQueue();

	    String queueName ="";
		for (int i = 0; i < queuelist.size(); i++)
		{
		try {
					
    		queueName = queuelist.get(i);
			queue = qmgr.accessQueue(queueName, MQC.MQOO_INQUIRE | MQC.MQOO_INPUT_AS_Q_DEF, null, null, null);
            stats.put("qName", queueName);
			stats.put("Description", queue.getDescription().trim());
			stats.put("CurrentDepth", new Integer(queue.getCurrentDepth()));
			stats.put("OpenOutputCount", new Integer(queue.getOpenOutputCount()));
			stats.put("OpenInputCount", new Integer(queue.getOpenInputCount()));

			if (queue.getInhibitGet() == MQC.MQQA_GET_INHIBITED) {
				stats.put("InhibitGet", Boolean.TRUE);
			} else {
				stats.put("InhibitGet", Boolean.FALSE);
			}

			if (queue.getInhibitPut() == MQC.MQQA_PUT_INHIBITED) {
				stats.put("InhibitPut", Boolean.TRUE);
			} else {
				stats.put("InhibitPut", Boolean.FALSE);
			}

			if (queue.getShareability() == MQC.MQQA_SHAREABLE) {
				stats.put("Sharable", Boolean.TRUE);
			} else {
				stats.put("Sharable", Boolean.FALSE);
			}

			if (queue.getTriggerControl() == MQC.MQTC_ON) {
				stats.put("TriggerControl", Boolean.TRUE);
				stats.put("TriggerData", queue.getTriggerData());
				stats.put("TriggerDepth", new Integer(queue.getTriggerDepth()));
				stats.put("TriggerMessagePriority", new Integer(queue.getTriggerMessagePriority()));

				switch (queue.getTriggerType()) {
				case MQC.MQTT_NONE:
					stats.put("TriggerType", "None");
					break;

				case MQC.MQTT_DEPTH:
					stats.put("TriggerType", "Depth");
					break;

				case MQC.MQTT_EVERY:
					stats.put("TriggerType", "Every");
					break;

				case MQC.MQTT_FIRST:
					stats.put("TriggerType", "First");
					break;

				default:
					stats.put("TriggerType", "Unknown");
				}
			} else {
				stats.put("TriggerControl", Boolean.FALSE);
			}

			stats.put("MaximumDepth", new Integer(queue.getMaximumDepth()));
			stats.put("MaximumMessageLength", new Integer(queue.getMaximumMessageLength()));
			}
		catch (MQException ex) {
			if (ex.reasonCode != 2033) {
				ex.printStackTrace();
				System.out.println("its not a 2033 error");
			} else {
				System.out.println("PCF calls gave a 2033 reason code, ignoring");
			}
		} catch (Exception ex) {
			throw new Exception(ex);
		} finally {
			if (queue != null) {
				try {
					queue.close();
				} catch (MQException ex) {
					System.out.println("ignoring error closing queue: ");
				}
			}
			}
		}

		System.out.println("stats output");

		Iterator it = stats.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			System.out.println(pairs.getKey() + " = " + pairs.getValue());
		}

		return stats;
	}*/
	
	public synchronized Map getStatistics(String queueName) throws Exception {
		// System.out.println("inside");
		final Map<String, Object> stats = new LinkedHashMap();
		MQQueue queue = null;
		ArrayList<String> queuelist  = new ArrayList<String>();
		    queuelist = listOfQueue();
		try {
		    //queueName = queuelist.get(i);
		queue = qmgr.accessQueue(queueName, MQC.MQOO_INQUIRE | MQC.MQOO_INPUT_AS_Q_DEF, null, null, null);
		            stats.put("qName", queueName);
		stats.put("Description", queue.getDescription().trim());
		stats.put("CurrentDepth", new Integer(queue.getCurrentDepth()));
		stats.put("OpenOutputCount", new Integer(queue.getOpenOutputCount()));
		stats.put("OpenInputCount", new Integer(queue.getOpenInputCount()));

		if (queue.getInhibitGet() == MQC.MQQA_GET_INHIBITED) {
		stats.put("InhibitGet", Boolean.TRUE);
		} else {
		stats.put("InhibitGet", Boolean.FALSE);
		}

		if (queue.getInhibitPut() == MQC.MQQA_PUT_INHIBITED) {
		stats.put("InhibitPut", Boolean.TRUE);
		} else {
		stats.put("InhibitPut", Boolean.FALSE);
		}

		if (queue.getShareability() == MQC.MQQA_SHAREABLE) {
		stats.put("Sharable", Boolean.TRUE);
		} else {
		stats.put("Sharable", Boolean.FALSE);
		}

		if (queue.getTriggerControl() == MQC.MQTC_ON) {
		stats.put("TriggerControl", Boolean.TRUE);
		stats.put("TriggerData", queue.getTriggerData());
		stats.put("TriggerDepth", new Integer(queue.getTriggerDepth()));
		stats.put("TriggerMessagePriority", new Integer(queue.getTriggerMessagePriority()));

		switch (queue.getTriggerType()) {
		case MQC.MQTT_NONE:
		stats.put("TriggerType", "None");
		break;

		case MQC.MQTT_DEPTH:
		stats.put("TriggerType", "Depth");
		break;

		case MQC.MQTT_EVERY:
		stats.put("TriggerType", "Every");
		break;

		case MQC.MQTT_FIRST:
		stats.put("TriggerType", "First");
		break;

		default:
		stats.put("TriggerType", "Unknown");
		}
		} else {
		stats.put("TriggerControl", Boolean.FALSE);
		}

		stats.put("MaximumDepth", new Integer(queue.getMaximumDepth()));
		stats.put("MaximumMessageLength", new Integer(queue.getMaximumMessageLength()));
		}
		catch (MQException ex) {
		if (ex.reasonCode != 2033) {
		ex.printStackTrace();
		System.out.println("its not a 2033 error");
		} else {
		System.out.println("PCF calls gave a 2033 reason code, ignoring");
		}
		} catch (Exception ex) {
		throw new Exception(ex);
		} finally {
		if (queue != null) {
		try {
		queue.close();
		} catch (MQException ex) {
		System.out.println("ignoring error closing queue: ");
		}
		}
		}

		System.out.println("stats output");

		Iterator it = stats.entrySet().iterator();
		while (it.hasNext()) {
		Map.Entry pairs = (Map.Entry) it.next();
		System.out.println(pairs.getKey() + " = " + pairs.getValue());
		}

		return stats;
		}
	
	public Map<String, String> checkChannelStatus() throws MQException {

		String checkStatus = "";

		String channelName = "";
		
		final Map<String, String> channel = new LinkedHashMap();

		PCFMessageAgent agent = new PCFMessageAgent("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN");

		PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_CHANNEL_STATUS);

		// add a parameter designating the name of the channel for which status
		// is requested

		request.addParameter(CMQCFC.MQCACH_CHANNEL_NAME, "*");

		// add a parameter designating the instance type (current) desired

		request.addParameter(CMQCFC.MQIACH_CHANNEL_INSTANCE_TYPE, CMQC.MQOT_CURRENT_CHANNEL);

		PCFMessage[] responses;

		try {

			responses = agent.send(request);

			for (int j = 0; j < responses.length; j++) {

				String temp = "";

				temp = responses[j].getStringParameterValue(CMQCFC.MQCACH_CHANNEL_NAME);

				channelName = temp.trim();

				int chlStatus = responses[j].getIntParameterValue(CMQCFC.MQIACH_CHANNEL_STATUS);

				String[] chStatusText = {

						"", "BINDING", "STARTING", "RUNNING/INACTIVE",

						"STOPPING", "RETRYING", "STOPPED",

						"REQUESTING", "PAUSED",

						"", "", "", "", "INITIALIZING"

				};

				checkStatus = chStatusText[chlStatus];
				channel.put(channelName, checkStatus);


			}

		return channel;
		}

		catch (IOException e) {

			e.printStackTrace();

		}
		return channel;

	}
	
	public ArrayList<String> listOfQueue() throws MQException

	{

		System.out.println("entering");
		ArrayList<String> queuelist  = new ArrayList<String>();
		try

		{
			PCFMessageAgent agent = new PCFMessageAgent("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN");

			PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q_NAMES);

			request.addParameter(CMQC.MQCA_Q_NAME, "*");

			request.addParameter(CMQC.MQIA_Q_TYPE, MQC.MQQT_LOCAL);

			PCFMessage[] responses = agent.send(request);

			String[] names = (String[]) responses[0].getParameterValue(CMQCFC.MQCACF_Q_NAMES);	
			
			for (int i = 0; i < names.length; i++)

			{
				names[i] = names[i].trim();
				if (names[i].startsWith("AMQ"))
				continue;
				queuelist.add(names[i]);

			}

		}

		catch (PCFException pcfe)

		{

			System.err.println("PCF error: " + pcfe);

		}

		catch (MQException mqe)

		{

			System.err.println(mqe);

		}

		catch (IOException ioe)

		{

			System.err.println(ioe);

		}
		return queuelist;

	}

	public ArrayList<String> listOfAliasQueue() throws MQException

	{
		ArrayList<String> queuelist  = new ArrayList<String>();
		try

		{
			PCFMessageAgent agent = new PCFMessageAgent("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN");

			PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q_NAMES);

			request.addParameter(CMQC.MQCA_Q_NAME, "*");

			request.addParameter(CMQC.MQIA_Q_TYPE,MQC.MQQT_ALIAS);


			PCFMessage[] responses = agent.send(request);

			String[] names = (String[]) responses[0].getParameterValue(CMQCFC.MQCACF_Q_NAMES);	


			for (int i = 0; i < names.length; i++)

			{
				names[i] = names[i].trim();
				if (names[i].startsWith("AMQ") || names[i].startsWith("SYSTEM."))
					continue;
				queuelist.add(names[i]);



			}

		}

		catch (PCFException pcfe)

		{

			System.err.println("PCF error: " + pcfe);

		}

		catch (MQException mqe)

		{

			System.err.println(mqe);

		}

		catch (IOException ioe)

		{

			System.err.println(ioe);

		}
		return queuelist;

	}

	public String channelRestart(String channelname) throws MQException
	{
		String checkStatus = "";
		String channelName = "";
		

		PCFMessageAgent agent = new PCFMessageAgent("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN");
		

		// build a request
		PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_CHANNEL_STATUS);
		// add a parameter designating the name of the channel for which status
		// is requested

		request.addParameter(CMQCFC.MQCACH_CHANNEL_NAME, "*");

		// add a parameter designating the instance type (current) desired

		request.addParameter(CMQCFC.MQIACH_CHANNEL_INSTANCE_TYPE, CMQC.MQOT_CURRENT_CHANNEL);

		PCFMessage[] responses;

		try {
			

			responses = agent.send(request);

			for (int j = 0; j < responses.length; j++) {

				// get the channel name and trim the spaces

				String temp = "";

				temp = responses[j].getStringParameterValue(CMQCFC.MQCACH_CHANNEL_NAME);

				channelName = temp.trim();

				// System.out.println(channelName);

				int chlStatus = responses[j].getIntParameterValue(CMQCFC.MQIACH_CHANNEL_STATUS);

				System.out.println("channel status: " + chlStatus);

				String[] chStatusText = {

						"", "BINDING", "STARTING", "RUNNING/INACTIVE",

						"STOPPING", "RETRYING", "STOPPED",

						"REQUESTING", "PAUSED",

						"", "", "", "", "INITIALIZING"

				};
				
				if(channelName == "DYNATRACE.SVRCONN" && chlStatus!=3)
				{
					PCFMessage response;
					PCFParameter [] parameters = new PCFParameter [] {
					          new MQCFST (CMQCFC.MQCACH_CHANNEL_NAME, channelName),
					          };
					MQMessage [] pcfResponses = agent.send (CMQCFC.MQCMD_START_CHANNEL, 
                             parameters);
					response = new PCFMessage(pcfResponses[0]);
				
				}

				checkStatus = chStatusText[chlStatus];

				System.out.println("chl: " + channelName + " STATUS: " + checkStatus);

			}


		}

		catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
		return channelName;

	}
	
	public int getQueuePutStatus(String queueName) throws MQException, Exception
	{
		int flag = 0 ;
		MQQueue queue = null ;
		queue = qmgr.accessQueue(queueName, MQC.MQOO_INQUIRE | MQC.MQOO_INPUT_AS_Q_DEF, null, null, null);
		flag = queue.getInhibitPut();

		if (queue != null)
		{
			try
			{
				queue.close() ;
			}
			catch (MQException ex)
			{
				System.out.println("ignoring error closing queue: " ) ;
			}
		}

		return flag;
	}
	
	public ArrayList<String> getQueueGetStatus() throws MQException, Exception
	{
		int flag = 0 ;
		MQQueue queue = null ;
		boolean queueInhibitflag=false;
		ArrayList<String> queuelist  = new ArrayList<String>();
		String queueNames[]={"TEST","TEST1","TEST2","SALESORDER.ATG.INBOUND.WMB","SEQUENTIAL.ECCGENERIC.WMB.OUTBOUND.WMB","ZCRMXIF_PARTNER_SAVE01.CRM.INBOUND.LOCAL"};
	
		for(int i =0;i<queueNames.length;i++)
		{
			String queueName = queueNames[i];
			queue = qmgr.accessQueue(queueName, MQC.MQOO_INQUIRE | MQC.MQOO_INPUT_AS_Q_DEF, null, null, null);
			flag = queue.getInhibitGet();
			if(flag==1) {
				queuelist.add(queueNames[i]);
				queueInhibitflag = true;
			}//if(flag==1) {
			else
				System.out.println(queueName+" Queue is not inhibited\n");
			if (queue != null)
			{
				try
				{
				queue.close() ;
				}
				catch (MQException ex)
				{
					System.out.println("ignoring error closing queue: " ) ;
				}
			}//if (queue != null)
		}//For Loop
	
		if(queueInhibitflag == false) {
			
			System.out.println("\nNo Queues are inhibited");
			queuelist.add("No Queues are inhibited");
			
		} //if(queueInhibitflag == false) {
		
		return queuelist;
	}
	public String removeGetInhibition(String queueName) throws Exception
	{ 
	MQQueue queue = null ;
	int openOptions = MQConstants.MQOO_FAIL_IF_QUIESCING | MQConstants.MQOO_SET;
	queue = getQueueManager().accessQueue(queueName, openOptions, null, null, null);
	//int option1 = getQueueGetStatus();
	//if (option1==1){
	//System.out.println("Start mesage received");
	queue.setInhibitGet(MQC.MQQA_GET_ALLOWED);
	//System.out.println("Queue get allowed successfully");

	//}
	/*else {
	            System.out.println("Stop mesage received");
	            queue.setInhibitGet(MQC.MQQA_GET_INHIBITED);
	            System.out.println("Queue inhibitted successfully");
	        }*/
	
	return "SUCCESS";
	}
	
	private synchronized MQQueueManager getQueueManager() throws Exception
	{
	if (queueManager == null)
	{
	MQEnvironment.channel = "MQPREPRDSUP.SVRCONN";

	MQEnvironment.port = 1430;

	MQEnvironment.hostname = "unxs0614.ghanp.kfplc.com";
	System.out.println(MQEnvironment.hostname);

	MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES);
	if (mqCF.getSecurityExit() != null) {
	Class clazz = getClass().getClassLoader().loadClass(mqCF.getSecurityExit()) ;
	MQSecurityExit securityExit = null ;
	if (mqCF.getSecurityExitInit() != null) {
	securityExit = (MQSecurityExit) clazz.getConstructor(String.class).newInstance(mqCF.getSecurityExitInit()) ;
	} else {
	securityExit = (MQSecurityExit) clazz.newInstance() ;
	}
	MQEnvironment.securityExit = securityExit ;
	}

	queueManager = new MQQueueManager(mqCF.getQueueManager());
	}

	return queueManager;
	}
}
