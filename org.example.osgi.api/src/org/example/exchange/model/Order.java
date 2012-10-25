package org.example.exchange.model;


public interface Order {
	
	String getID();
	String getSymbol();
	Side getSide();
	long getQuantity();
	long getPrice();
	
}
