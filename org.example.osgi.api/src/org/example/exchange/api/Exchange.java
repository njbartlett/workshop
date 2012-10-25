package org.example.exchange.api;

import java.util.Collection;

import org.example.exchange.model.Order;
import org.example.exchange.model.Side;

import aQute.bnd.annotation.ProviderType;

@ProviderType
public interface Exchange {

	/**
	 * Submit an order to the exchange.
	 */
	Order createOrder(String symbol, Side side, long quantity, long price) throws Exception;
	
	/**
	 * Get all existing orders.
	 */
	Collection<Order> getAllOrders();
	
}
