package org.example.exchange.impl;

import org.example.exchange.model.Order;
import org.example.exchange.model.Side;

public class OrderImpl implements Order {
	
	private String id;
	private String symbol;
	private Side side;
	private long quantity;
	private long price;
	
	public OrderImpl() {
	}

	public OrderImpl(String id, String symbol, Side side, long quantity, long price) {
		this.id = id;
		this.symbol = symbol;
		this.side = side;
		this.quantity = quantity;
		this.price = price;
	}
	
	public String getID() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}
	
	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
	public long getPrice() {
		return price;
	}
	
	public void setPrice(long price) {
		this.price = price;
	}
	
}
