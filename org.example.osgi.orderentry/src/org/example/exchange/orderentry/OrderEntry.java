package org.example.exchange.orderentry;

import java.util.concurrent.atomic.AtomicReference;

import org.example.exchange.api.Exchange;
import org.example.exchange.model.Order;
import org.example.exchange.model.Side;

import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

@Component(
		provide = Object.class,
		immediate = true,
		properties = {
			"osgi.command.scope=orderentry",
			"osgi.command.function=orders|bid|offer"
	})
public class OrderEntry {
	
	private final AtomicReference<Exchange> exchangeRef = new AtomicReference<Exchange>();

	@Reference(type = '?')
	void setExchange(Exchange exchange) {
		exchangeRef.set(exchange);
	}
	void unsetExchange(Exchange exchange) {
		exchangeRef.compareAndSet(exchange, null);
	}

	public void orders() {
		Exchange exchange = exchangeRef.get();
		if (exchange == null) {
			System.err.println("Exchange service unavailable");
			return;
		}
		
		System.out.println("Listing orders:...");
		for (Order order : exchange.getAllOrders()) {
			System.out.printf("[%s]: Symbol %s, Quantity %d, Price %d%n", order.getID(), order.getSymbol(), order.getQuantity(), order.getPrice());
		}
		System.out.println("DONE");
	}
	
	public void bid(long quantity, String symbol, long price) throws Exception {
		Exchange exchange = exchangeRef.get();
		if (exchange == null) {
			System.err.println("Exchange service unavailable");
			return;
		}
		
		System.out.printf("Bidding for %d of %s at price %d%n", quantity, symbol, price);
		exchange.createOrder(symbol, Side.Bid, quantity, price);
	}
	
	public void offer(long quantity, String symbol, long price) throws Exception {
		Exchange exchange = exchangeRef.get();
		if (exchange == null) {
			System.err.println("Exchange service unavailable");
			return;
		}
		
		System.out.printf("Bidding for %d of %s at price %d%n", quantity, symbol, price);
		exchange.createOrder(symbol, Side.Offer, quantity, price);
	}
	
}
