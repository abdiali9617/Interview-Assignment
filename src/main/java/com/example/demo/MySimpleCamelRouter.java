package com.example.demo;

import org.apache.camel.BeanInject;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class MySimpleCamelRouter extends RouteBuilder {
	
	  @BeanInject
	  private PayloadProcessor processor;
	  
	  // Completion Interval for the aggregator to finish
	  private static final long BATCH_TIME_OUT = 60000L;
	  // Completion Size for the aggregator to finish
      private static final int MAX_RECORDS = 10;
	
  @Override
  public void configure() throws Exception {
	  
	// CSV Data Format
	  CsvDataFormat csv = new CsvDataFormat();
	  csv.setDelimiter(',');
	  csv.setFormatName("EXCEL");
	  
	  // Setting up Rest Config
    restConfiguration()
      .component("servlet")
      .bindingMode(RestBindingMode.auto);
    
    // Setting up the path
     rest()
     .path("/payload")
     
     .get("/")
     .route()
     .transform(simple("Waiting for payload"))
     .endRest()
     
     
     .post("/")
     .route()
     .log("Payload Received: ${body}")
     .split(body())
     .process(processor) // Processes payload and remove event
     .marshal(csv) // Transforms to csv
     .aggregate(constant(true), new splitterAggregator()) //Formats the csv correctly
     .completionInterval(BATCH_TIME_OUT)
     .completionSize(10)
     .completionPredicate(recordSizePredicate())
     .log("Payload Processed: ${body}")
     .to("file:C:\\outputFolder");
   }
  
  private Predicate recordSizePredicate() {
	  return new RecordSizePredicate(MAX_RECORDS);
	  }
}
