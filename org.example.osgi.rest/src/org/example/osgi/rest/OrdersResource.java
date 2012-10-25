package org.example.osgi.rest;

import java.net.URI;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.example.exchange.api.Exchange;
import org.example.exchange.model.Order;
import org.example.exchange.model.Side;

@Path("/orders")
public class OrdersResource {
	
	@Context
	UriInfo uriInfo;
	
	@Inject
	Exchange exchange;
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String listHtml() {
		Collection<Order> orders = exchange.getAllOrders();
		
		StringBuilder builder = new StringBuilder();
		builder.append("<html><head><title>Exchange Orders</title></head><body>");
		builder.append("<h1>Orders</h1>");
		builder.append("<ul>");
		for (Order order : orders) {
			URI orderUri = uriInfo.getAbsolutePathBuilder().path(order.getID()).build();
			String line = String.format("<li><a href='%s'>Symbol %s; Quantity %d; Price %d</a></li>", orderUri, order.getSymbol(), order.getQuantity(), order.getPrice());
			builder.append(line);
		}
		builder.append("</ul>");
		
		builder.append("<form method='POST'>");
		builder.append("Symbol: <input name='symbol'/> Quantity: <input name='quantity'/> Price: <input name='price'/>");
		builder.append("<input type='submit' value='Bid'/>");
		builder.append("</form>");
		builder.append("</body></html>");
		
		return builder.toString();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response postOrder(@FormParam("symbol") String symbol, @FormParam("quantity") long quantity, @FormParam("price") long price) throws Exception {
		Order order = exchange.createOrder(symbol, Side.Bid, quantity, price);
		URI orderUri = uriInfo.getAbsolutePathBuilder().path(order.getID()).build();
		return Response.created(orderUri).build();
	}
}
