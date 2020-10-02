package com.example.demo;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

public class RecordSizePredicate implements Predicate {

	 public int size;
	 int listSize = 0;

     public RecordSizePredicate(int size) {
             this.size = size;
     }
	
	@Override
	public boolean matches(Exchange exchange) {
		 if (exchange != null) {
			 // if the current size of the batch is greater than 10 then return true 
			 listSize += (int) exchange.getProperty("payload size");
				if (listSize == size) {
					listSize-=10;
					if(listSize <= 0) {
						listSize = 0;
					}
					 System.out.println("new payload size" + listSize);
                     return true;
            }
     }
		return false;
	}
}
