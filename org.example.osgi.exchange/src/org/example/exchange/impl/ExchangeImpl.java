package org.example.exchange.impl;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.example.exchange.api.Exchange;
import org.example.exchange.model.Order;
import org.example.exchange.model.Side;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ExchangeImpl implements Exchange {
	
	private final Map<String, Order> orders = Collections.synchronizedMap(new LinkedHashMap<String, Order>());
	
	private final MappingJsonFactory jf = new MappingJsonFactory();
	private File jsonStore;
	
	@Activate
	void activate() throws Exception {
		jsonStore = new File("orders.json");
		if (!jsonStore.isFile())
			return;
	
		OrderImpl[] orderArr = jf.createJsonParser(jsonStore).readValueAs(OrderImpl[].class);
		for (Order order : orderArr) {
			orders.put(order.getID(), order);
		}
	}
	
	@Override
	public Order createOrder(String symbol, Side side, long quantity, long price) throws Exception {
		UUID uuid = UUID.randomUUID();
		OrderImpl order = new OrderImpl(uuid.toString()	, symbol, side, quantity, price);
		orders.put(order.getID(), order);
		new ObjectMapper(jf).writeValue(jsonStore, orders.values());
		return order;
	}
	
	@Override
	public Collection<Order> getAllOrders() {
		return Collections.unmodifiableCollection(orders.values());
	}

}
