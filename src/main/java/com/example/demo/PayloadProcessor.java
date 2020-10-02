package com.example.demo;

import java.util.LinkedHashMap;
import java.util.List;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PayloadProcessor implements Processor{

	// Logger 
	 private static final Logger logger = LoggerFactory.getLogger(PayloadProcessor.class);
	@Override
	public void process(Exchange exchange) throws Exception {
		
		// Splitting the JSON file into different variables 
		@SuppressWarnings("unchecked")
		String str = ((LinkedHashMap<String, String>) exchange.getIn().getBody()).get("batchId");
		@SuppressWarnings("unchecked")
		List<Record> rec = ((LinkedHashMap<String, List<Record>>) exchange.getIn().getBody()).get("records");
		
		// Setting the current size of the payload to be used in the predicate
		exchange.setProperty("payload size", rec.size());
		// Setting batch to be a property to be used in logs 
		exchange.setProperty("batchId", str);
		
		logger.info("batchId: " + exchange.getProperty("batchId") + " The Payload is being processed and the Event portion is being removed");
		
		// Removing the Events Portion of the Payload 
		for(int i = 0; i < rec.size(); i++) {
			
			String[] parts = String.valueOf(rec.get(i)).split(",");
			Record record = new Record();
			
			String[] TransId = parts[0].split("=");
			record.setTransId(TransId[1]);
			
			String[] TransTms = parts[1].split("=");
			record.setTransTms(TransTms[1]);
			
			String[] rcNum = parts[2].split("=");
			record.setRcNum(rcNum[1]);
			
			String[] clientId = parts[3].split("=");
			record.setClientId(clientId[1]);
			
			rec.remove(i);
			rec.add(i, record);
		}
		logger.info("batchId: " + exchange.getProperty("batchId") + " Payload has been process");
		
		//Setting the Body to the new value
		exchange.getOut().setBody(rec);
	}
}
