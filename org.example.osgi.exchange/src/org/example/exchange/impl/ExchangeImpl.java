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
import aQute.bnd.annotation.metatype.Configurable;
import aQute.bnd.annotation.metatype.Meta;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(designateFactory = ExchangeImpl.Config.class)
public class ExchangeImpl implements Exchange {
	
	static interface Config {
		@Meta.AD(required = false, deflt = "orders.json")
		String storeFile();
	}
	
	private final Map<String, Order> orders = Collections.synchronizedMap(new LinkedHashMap<String, Order>());
	
	private final MappingJsonFactory jf = new MappingJsonFactory();
	private File jsonStore;
	
	@Activate
	void activate(Map<String, Object> configProps) throws Exception {
		Config config = Configurable.createConfigurable(Config.class, configProps);
		jsonStore = new File(config.storeFile());
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
