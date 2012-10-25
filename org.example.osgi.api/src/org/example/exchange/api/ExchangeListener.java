package org.example.exchange.api;

import org.example.exchange.model.Order;

import aQute.bnd.annotation.ConsumerType;

@ConsumerType
public interface ExchangeListener {

	void orderReceived(Exchange exchange, Order order);

}
