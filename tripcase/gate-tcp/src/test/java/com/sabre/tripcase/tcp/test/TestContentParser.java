package com.sabre.tripcase.tcp.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sabre.tripcase.tcp.common.constants.Message;
import com.sabre.tripcase.tcp.parse.ContentParserApi;

public class TestContentParser {
	
	private static Logger LOG = LoggerFactory.getLogger(TestContentParser.class);
	
	public static ClassPathXmlApplicationContext appCtx=null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {
			LOG.info("START TestParser.setUpBeforeClass");
			appCtx = new ClassPathXmlApplicationContext("classpath:META-INF/spring/test.parser.config.xml");
			LOG.info("END TestParser.setUpBeforeClass");
		} catch (Exception ex) {
			LOG.error("Exception: ",ex);
		}
	}

	@Test
	public void testParser(){		
		try {
			LOG.info("START TestParser.testParser");
			Thread.sleep(2000);
			ContentParserApi contentParser=appCtx.getBean("contentParser", ContentParserApi.class);
			contentParser.processMessage(getEmailText(),"12345");
			contentParser.processMessage(getEmailText2(),"22222");
		    LOG.info("END TestParser.testParser");
			Thread.sleep(2000);
		} catch (Exception ex) {
			LOG.error("Exception: ",ex);
		}
	}
	
		 
	@AfterClass
	public static void tearDownAfterClass(){
	}
	
	

   private List<Message> getEmailText(){
	   List<Message> listMessage=new ArrayList<Message>();
	   Message message=new Message();
	   String str="ITINERARY"+
					"AIR"+
					" Flight/Equip.: Jetblue Airways Corp 311"+
					"Depart: Boston(BOS) Monday, Jul 22 10:40"+
					"Arrive: Chicago(ORD) Monday, Jul 22 12:17"+
					"Stops: non-stop;     Miles: 865"+
					"Class: Coach / Economy"+
					"Status: Confirmed"+
					"Seats Requested: 17B"+
					//"AIR"+
					" Flight/Equip.: Jetblue Airways Corp 112"+
					"Depart: Chicago(ORD) Wednesday, Jul 24 18:25"+
					"Arrive: Boston(BOS) Wednesday, Jul 24 21:38"+
					"Stops: non-stop;     Miles: 865"+
					"Class: Coach / Economy"+
					"Status: Confirmed"+
					"Seats Requested: 15B";
	   message.setContent(str);
	   listMessage.add(message);
	  return listMessage;
   }
   
 private List<Message> getEmailText2(){
	 List<Message> listMessage=new ArrayList<Message>();
	   Message message=new Message();
	   String str="ITINERARY"+
					"AIR"+
					" Flight/Equip.: united Airways Corp 312"+
					"Depart: hyder(BOS) Monday, Jul 23 10:40"+
					"Arrive: bang(ORD) Monday, Jul 23 12:17"+
					"Stops: non-stop;     Miles: 865"+
					"Class: Coach / Economy"+
					"Status: Confirmed"+
					"Seats Requested: 17B"+
					//"AIR"+
					" Flight/Equip.: united Airways Corp 412"+
					"Depart: bang(ORD) Wednesday, Jul 25 18:25"+
					"Arrive: hyd(BOS) Wednesday, Jul 25 21:38"+
					"Stops: non-stop;     Miles: 865"+
					"Class: Coach / Economy"+
					"Status: Confirmed"+
					"Seats Requested: 15B";
	   message.setContent(str);
	   listMessage.add(message);
	  return listMessage;
   }
   }
