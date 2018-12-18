package com.kf.mqstatus;

// import com.ibm.mq.pcf.*;
import java.util.ArrayList;

import com.ibm.*;

import com.ibm.mq.pcf.*;

import java.io.IOException;
import java.security.KeyStore.Entry;
import java.text.DecimalFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import java.util.GregorianCalendar;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarException;

import com.ibm.mq.*;

import com.ibm.mq.constants.CMQC;

import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.pcf.MQCFST;
import com.ibm.mq.pcf.PCFAgent;
import com.ibm.mq.pcf.PCFException;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;
import com.ibm.mq.pcf.PCFParameter;

public class MQRead {

	public MQQueueManager queueManager = null;

	public int port = 1430;

	public String hostname = "unxs0614.ghanp.kfplc.com";

	public String channel = "MQPREPRDSUP.SVRCONN";

	public String qManager = "FMQINST1";

	public String inputQName = "ZCALLLIST01.ECC.INBOUND.LOCAL";

	private MQConnectionFactory mqCF;

	public MQRead(String host, int port, String channel, String manager) throws MQException {
		this.hostname = host;
		this.port = port;
		this.channel = channel;
		this.qManager = manager;
		this.port = port;
		//System.out.println("Host:"+host+" port:"+port+" channel:"+channel+" QManager:"+manager);
		

	}

	public void init(String[] args) throws IllegalArgumentException

	{

		// Set up MQ environment

		MQEnvironment.hostname = hostname;

		MQEnvironment.channel = channel;

		MQEnvironment.port = port;

		MQEnvironment.userID = "paulso01";

	}

	public static void main(String[] args) throws Exception

	{

		/*MQRead readQ = new MQRead(hostname,port,channel,qManager);

		try

		{

			Calendar start = Calendar.getInstance();

			start.set(2018, 2, 20, 1, 10, 0);

			Calendar end = Calendar.getInstance();

			end.set(2018, 2, 25, 23, 10, 0);

			readQ.init(args);

			readQ.selectQMgr();

			List catchvalue = readQ.read(start,end);

			System.out.println("List value returned in main:"+catchvalue.size());

			//readQ.listOfQueue();

			//System.out.println("*");

			//readQ.checkChannelStatus();
              //readQ.listOfAliasQueue1();
			//readQ.getStatistics();
			readQ.removeGetInhibition("TEST");
			//readQ.removePutInhibition();
			//readQ.channelRestart();

		}

		catch (IllegalArgumentException e)

		{

			System.exit(1);

		}

		catch (MQException e)

		{

			System.out.println(e);

			System.exit(1);

		}*/

	}

	public String removeInhibition1(String qname) throws Exception {
		String[] args = null;
		//MQRead readQ = new MQRead();

		try

		{

			Calendar start = Calendar.getInstance();

			start.set(2018, 2, 20, 1, 10, 0);

			Calendar end = Calendar.getInstance();

			end.set(2018, 2, 25, 23, 10, 0);

			init(args);

			selectQMgr();

			List catchvalue = read(start,end);

			System.out.println("List value returned in main:"+catchvalue.size());

			removeGetInhibition(qname);

		}

		catch (IllegalArgumentException e)

		{

			System.exit(1);

		}

		catch (MQException e)

		{

			System.out.println(e);

			System.exit(1);

		}
		
		return "SUCCESS";

	}
	
	public String removeInhibition2(String qname) throws Exception {
		String[] args = null;
		//MQRead readQ = new MQRead();

		try

		{

			Calendar start = Calendar.getInstance();

			start.set(2018, 2, 20, 1, 10, 0);

			Calendar end = Calendar.getInstance();

			end.set(2018, 2, 25, 23, 10, 0);

			init(args);

			selectQMgr();

			List catchvalue = read(start,end);

			System.out.println("List value returned in main:"+catchvalue.size());

			removePutInhibition(qname);

		}

		catch (IllegalArgumentException e)

		{

			System.exit(1);

		}

		catch (MQException e)

		{

			System.out.println(e);

			System.exit(1);

		}
		
		return "SUCCESS";

	}

	public void selectQMgr() throws MQException

	{

		queueManager = new MQQueueManager(qManager);

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

	public ArrayList<String> listOfAliasQueue1() throws MQException

	{

		System.out.println("entering");
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


	public List read(Calendar start, Calendar end) throws MQException

	{

		int openOptions = MQC.MQGMO_WAIT | MQC.MQOO_OUTPUT | MQC.MQOO_BROWSE | MQC.MQOO_INQUIRE;

		List returndata = new ArrayList();

		MQQueue queue = queueManager.accessQueue(inputQName,

				openOptions,

				null, // default q manager

				null, // no dynamic q name

				null); // no alternate user id

		System.out.println("Queue manager is now connected.\n");

		int depth = queue.getCurrentDepth();

		System.out.println("Current depth: " + depth + "\n");

		if (depth == 0)

		{

			return returndata;

		}

		MQGetMessageOptions getOptions = new MQGetMessageOptions();

		getOptions.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT;

		while (true)

		{

			MQMessage message = new MQMessage();

			try

			{

				queue.get(message, getOptions);

				byte[] b = new byte[message.getMessageLength()];

				message.readFully(b);

				// System.out.println(new String(b));

				GregorianCalendar puttime;

				puttime = message.putDateTime;

				SimpleDateFormat sdf = new SimpleDateFormat("dd MM YYYY HH:mm:ss");

				Calendar calendar = new GregorianCalendar();

				calendar = message.putDateTime;

				int date = calendar.get(Calendar.DAY_OF_MONTH);

				DecimalFormat formatter = new DecimalFormat("00");

				String dateFormatted = formatter.format(date);

				int month = calendar.get(Calendar.MONTH) + 1;

				String monthFormatted = formatter.format(month);

				int year = calendar.get(Calendar.YEAR);

				int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // 24 hour
				// clock

				String hourOfDayFormatted = formatter.format(hourOfDay);

				int minute = calendar.get(Calendar.MINUTE);

				String minuteFormatted = formatter.format(minute);

				int second = calendar.get(Calendar.SECOND);

				String secondFormatted = formatter.format(second);

				Date putdate = calendar.getTime();

				// System.out.println("Calendar value :"+calendar);

				// System.out.println("Start value :"+start);

				if (calendar.compareTo(start) > 0 && calendar.compareTo(end) < 0) {

					System.out.println(new String(b));

					System.out.println("Message Date " + dateFormatted + " " + monthFormatted + " " + year + " "
							+ hourOfDayFormatted + ":" + minuteFormatted + ":" + secondFormatted);

					returndata.add(new String(b));

				}

				System.out.println("Start date " +start.getTime());

				System.out.println("End Date " +end.getTime());

				System.out.println("Put Date " +putdate);

				System.out.println("Return value "+returndata.get(0));

				message.clearMessage();

			}

			catch (IOException e)

			{

				System.out.println("IOException during GET: " + e.getMessage());

				break;

			}

			catch (MQException e)

			{

				if (e.completionCode == 2 && e.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {

					if (depth > 0)

					{

						System.out.println("All messages read.");

					}

				}

				else

				{

					System.out.println("GET Exception: " + e);

				}

				break;

			}

		}

		queue.close();

		queueManager.disconnect();

		System.out.println("value returned ****");

		return returndata;

	}

	public void checkChannelStatus() throws MQException {

		String checkStatus = "";

		String channelName = "";

		PCFMessageAgent agent = new PCFMessageAgent("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN");

		// build a request

		// PCFMessage request = new PCFMessage (CMQCFC.MQCMD_INQUIRE_Q_NAMES);

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

				// System.out.println("channel status: " + chlStatus);

				String[] chStatusText = {

						"", "BINDING", "STARTING", "RUNNING/INACTIVE",

						"STOPPING", "RETRYING", "STOPPED",

						"REQUESTING", "PAUSED",

						"", "", "", "", "INITIALIZING"

				};

				checkStatus = chStatusText[chlStatus];

				System.out.println("chl: " + channelName + " STATUS: " + checkStatus);

			}


		}

		catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}


	/*public void queueStatus() throws IOException

	{

		try {

			PCFMessageAgent pcfAgent = new PCFMessageAgent(queueManager);

			// Prepare PCF command to inquire queue status (status type)

			PCFMessage inquireQueueStatus = new PCFMessage(CMQCFC.MQCMD_INQUIRE_Q_STATUS);

			//inquireQueueStatus.addParameter(CMQC.MQCA_Q_NAME, "TEST");

			inquireQueueStatus.addParameter(CMQCFC.MQIACF_Q_STATUS_TYPE, CMQCFC.MQIACF_Q_STATUS);

			inquireQueueStatus.addParameter(CMQCFC.MQIACF_Q_STATUS_ATTRS, new int[] {

					CMQC.MQCA_Q_NAME, CMQC.MQIA_CURRENT_Q_DEPTH,

					CMQCFC.MQCACF_LAST_GET_DATE, CMQCFC.MQCACF_LAST_GET_TIME,

					CMQCFC.MQCACF_LAST_PUT_DATE, CMQCFC.MQCACF_LAST_PUT_TIME,

					CMQCFC.MQIACF_OLDEST_MSG_AGE, CMQC.MQIA_OPEN_INPUT_COUNT,

					CMQC.MQIA_OPEN_OUTPUT_COUNT, CMQCFC.MQIACF_UNCOMMITTED_MSGS });

			PCFMessage[] pcfResp = pcfAgent.send(inquireQueueStatus);

			int queueGetTime = pcfResp[0].getIntParameterValue(CMQCFC.MQIACF_Q_STATUS_TYPE);

			System.out.println(queueGetTime);

			// Prepare PCF command to reset queue statistics

			/*
	 * PCFMessage queueResetStats = new
	 * PCFMessage(CMQCFC.MQCMD_RESET_Q_STATS);
	 * 
	 * queueResetStats.addParameter(CMQC.MQCA_Q_NAME, "TEST");
	 * 
	 * 
	 * 
	 * PCFMessage [] pcfResp3 = pcfAgent.send(queueResetStats);
	 * 
	 * 
	 * 
	 * //int queueMsgDeqCount =
	 * pcfResp3[0].getIntParameterValue(CMQC.MQIA_OPEN_INPUT_COUNT);
	 * 
	 * int queueMsgEnqCount =
	 * pcfResp3[0].getIntParameterValue(CMQCFC.MQCACF_LAST_GET_TIME);
	 * 
	 * 
	 * 
	 * //System.out.println(queueMsgDeqCount);
	 * 
	 * System.out.println(queueMsgEnqCount);


		}

		catch (MQException mqe) {

			System.err.println("PCF Message Agent creation ended with reason code "

					+ mqe.reasonCode);

		}

	}*/

	@SuppressWarnings("unchecked")
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

	public synchronized Map getStatistics() throws Exception
	{
		//System.out.println("inside");
		Map<String,Object>  stats= new LinkedHashMap();
		Map<String,Object>  stats1= new LinkedHashMap();
		MQQueue queue = null ;
		//int openOptions = MQConstants.MQOO_FAIL_IF_QUIESCING | MQConstants.MQOO_SET;
		ArrayList<String> queuelist  = new ArrayList<String>();
		queuelist = listOfQueue();
		String queueName ="";
		for (int i = 0; i < queuelist.size(); i++)
		{

			try
			{
				queueName = queuelist.get(i);
				queue = getQueueManager().accessQueue(queueName, MQC.MQOO_INQUIRE | MQC.MQOO_INPUT_AS_Q_DEF, null, null, null);
				stats.put("qName", queueName);
				System.out.println(queueName);
				stats.put("Description", queue.getDescription().trim());
				stats.put("CurrentDepth", new Integer(queue.getCurrentDepth()));
				stats.put("OpenOutputCount", new Integer(queue.getOpenOutputCount()));
				stats.put("OpenInputCount", new Integer(queue.getOpenInputCount()));
				if (queue.getInhibitGet() == MQC.MQQA_GET_INHIBITED)
				{
					//stats.put("InhibitGet", Boolean.TRUE);
					queue.setInhibitGet( MQConstants.MQQA_GET_ALLOWED );

				}
				else
				{
					stats.put("InhibitGet", Boolean.FALSE);

				}
				if (queue.getInhibitPut() == MQC.MQQA_PUT_INHIBITED)
				{
					stats.put("InhibitPut", Boolean.TRUE);

				}
				else
				{
					stats.put("InhibitPut", Boolean.FALSE);

				}
				if (queue.getShareability() == MQC.MQQA_SHAREABLE)
				{
					stats.put("Sharable", Boolean.TRUE);
				}
				else
				{
					stats.put("Sharable", Boolean.FALSE);
				}

				if (queue.getTriggerControl() == MQC.MQTC_ON)
				{
					stats.put("TriggerControl", Boolean.TRUE);
					stats.put("TriggerData", queue.getTriggerData());
					stats.put("TriggerDepth", new Integer(queue.getTriggerDepth()));
					stats.put("TriggerMessagePriority", new Integer(queue.getTriggerMessagePriority()));

					switch (queue.getTriggerType())
					{
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
				}

				stats.put("MaximumDepth", new Integer(queue.getMaximumDepth()));
				stats.put("MaximumMessageLength", new Integer(queue.getMaximumMessageLength()));
				stats1.putAll(stats);
				
			}

			catch (MQException ex)
			{
				if (ex.reasonCode != 2033)
				{
					System.out.println("its not a 2033 error");
				}
				else
				{
					System.out.println("PCF calls gave a 2033 reason code, ignoring") ;
				}

			}

			catch (Exception ex)
			{
				throw new Exception(ex) ;
			}
			
			finally
			{

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
			}
			
		}
		return stats1;


	}



	public ArrayList<String> getQueueGetStatus() throws MQException, Exception
	{
		int flag = 0 ;
		MQQueue queue = null ;
		ArrayList<String> queuelist  = new ArrayList<String>();
		String queueNames[]={"SALESORDER.ATG.INBOUND.WMB","SEQUENTIAL.ECCGENERIC.WMB.OUTBOUND.WMB","ZCRMXIF_PARTNER_SAVE01.CRM.INBOUND.LOCAL"};

		for(int i =0;i<queueNames.length;i++)
		{
			String queueName = queueNames[i];
			queue = getQueueManager().accessQueue(queueName, MQC.MQOO_INQUIRE | MQC.MQOO_INPUT_AS_Q_DEF, null, null, null);
			flag = queue.getInhibitGet();
			if(flag==1)
				queuelist.add(queueNames[i]);
			else
				 System.out.println(" No Ques are inhibited");
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
		}

		return queuelist;
	}

	public int getQueuePutStatus(String qName) throws MQException, Exception
	{
		int flag = 0 ;
		MQQueue queue = null ;

		final String queueName = qName;
		queue = getQueueManager().accessQueue(queueName, MQC.MQOO_INQUIRE | MQC.MQOO_INPUT_AS_Q_DEF, null, null, null);
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

	public void removePutInhibition(String qname) throws Exception
	{
		final String queueName = qname;  
		MQQueue queue = null ;
		int openOptions = MQConstants.MQOO_FAIL_IF_QUIESCING | MQConstants.MQOO_SET;
		queue = getQueueManager().accessQueue(queueName, openOptions, null, null, null);
		int option1 = getQueuePutStatus(qname);
		if (option1==1){
			System.out.println("Start mesage sending");
			queue.setInhibitPut(MQC.MQQA_PUT_ALLOWED);
			System.out.println("Queue put allowed successfully");

		}
		/*else {
	            System.out.println("Stop mesage sending");
	            queue.setInhibitPut(MQC.MQQA_PUT_INHIBITED);
	            System.out.println("Queue inhibitted successfully");
	        }*/
	}

	public void removeGetInhibition(String queueName) throws Exception
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
	}


	public void channelRestart() throws MQException
	{
		String checkStatus = "";
		PCFAgent pcfAgent = null;
		String channelName = "";


		PCFMessageAgent agent = new PCFMessageAgent("unxs0614.ghanp.kfplc.com", 1430, "MQPREPRDSUP.SVRCONN");


		// build a request

		//PCFMessage request = new PCFMessage (CMQCFC.MQCMD_INQUIRE_Q_NAMES);
		PCFMessage request = new PCFMessage(CMQCFC.MQCMD_INQUIRE_CHANNEL_STATUS);
		PCFMessage request2 = new PCFMessage(CMQCFC.MQCMD_INQUIRE_CHANNEL_STATUS);
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

				if(chlStatus!=3)
				{
					PCFMessage response;
					PCFParameter [] parameters = new PCFParameter [] {
							new MQCFST (CMQCFC.MQCACH_CHANNEL_NAME, channelName),
					};
					//PCFMessage   request2 = new PCFMessage (CMQCFC.MQCMD_START_CHANNEL);
					//request2.addParameter(CMQCFC.MQCMD_START_CHANNEL,channelName);
					//responses = agent.send (request2);
					MQMessage [] pcfResponses = agent.send (CMQCFC.MQCMD_START_CHANNEL, 
							parameters);
					response = new PCFMessage(pcfResponses[0]);
					//System.out.println(pcfResponses.toString());

				}

				checkStatus = chStatusText[chlStatus];

				System.out.println("chl: " + channelName + " STATUS: " + checkStatus);

			}


		}

		catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}
}
