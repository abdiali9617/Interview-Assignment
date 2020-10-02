package com.example.demo;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class splitterAggregator implements AggregationStrategy {
	
	 private static final Logger logger = LoggerFactory.getLogger(splitterAggregator.class);
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		 Exchange exchangeToReturn;
	        StringBuffer csvBuffer;
	        String csv = newExchange.getIn().getBody(String.class);
	        if (oldExchange == null)
	        {
	        	logger.info("batchId: " + newExchange.getProperty("batchId") + " String is appended and put onto a new line");
	            csvBuffer = new StringBuffer();
	            csvBuffer.append(csv);

	            logger.info("batchId: " + newExchange.getProperty("batchId") + " New Body is being set");
	            String csvNew = csvBuffer.toString();
	            newExchange.getIn().setBody(csvNew);
	            exchangeToReturn = newExchange;
	        }
	        else
	        {
	        	logger.info("batchId: " + oldExchange.getProperty("batchId") + "Old Exchange is not null");
	            csvBuffer = (StringBuffer) oldExchange.getIn().getBody(StringBuffer.class);

	        	logger.info("batchId: " + oldExchange.getProperty("batchId") + "String is appended and put onto a new line");
	            // 
	            csvBuffer.append("\r\n");
	            csvBuffer.append(csv);
	            
	            logger.info("batchId: " + oldExchange.getProperty("batchId") + "New Body is being set");
	            String csvNew = csvBuffer.toString();
	            oldExchange.getIn().setBody(csvNew);

	            exchangeToReturn = oldExchange;
	        }
	        return exchangeToReturn;
	}

}
