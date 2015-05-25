package com.sabre.tripcase.tcp.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

/**
 * 
 * @author Venkat Pedapudi
 *
 */
public class ParsedOutputAggregator implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		if (oldExchange == null) {
            List<Map<String,List>> contentList = new ArrayList<Map<String,List>>();
            Map newContent = newExchange.getIn().getBody(Map.class);
            contentList.add(newContent);
            newExchange.getIn().setBody(contentList);
            return newExchange;
        }
        List<Map> contentList = oldExchange.getIn().getBody(List.class);
        Map newContent = newExchange.getIn().getBody(Map.class);
        contentList.add(newContent);
        oldExchange.getIn().setBody(contentList);
        return oldExchange;

		
	}

}
