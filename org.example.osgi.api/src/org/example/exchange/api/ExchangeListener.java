package org.example.exchange.api;

import org.example.exchange.model.Order;

public interface ExchangeListener {

	void orderReceived(Exchange exchange, Order order);

}
