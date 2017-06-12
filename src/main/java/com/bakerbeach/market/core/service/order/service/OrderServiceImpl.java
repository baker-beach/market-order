package com.bakerbeach.market.core.service.order.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bakerbeach.market.cart.api.service.CartService;
import com.bakerbeach.market.cart.api.service.CartServiceException;
import com.bakerbeach.market.commons.MessageImpl;
import com.bakerbeach.market.core.api.model.Cart;
import com.bakerbeach.market.core.api.model.CartItem;
import com.bakerbeach.market.core.api.model.CartItem.CartItemComponent;
import com.bakerbeach.market.core.api.model.CartItem.CartItemOption;
import com.bakerbeach.market.core.api.model.Coupon;
import com.bakerbeach.market.core.api.model.Customer;
import com.bakerbeach.market.core.api.model.Order;
import com.bakerbeach.market.core.api.model.OrderItem;
import com.bakerbeach.market.core.api.model.ShopContext;
import com.bakerbeach.market.core.service.order.model.SimpleOrder;
import com.bakerbeach.market.core.service.order.model.SimpleOrderItem;
import com.bakerbeach.market.core.service.order.model.SimpleOrderItem.SimpleOrderItemComponent;
import com.bakerbeach.market.core.service.order.model.SimpleOrderItem.SimpleOrderItemOption;
import com.bakerbeach.market.inventory.api.model.TransactionData;
import com.bakerbeach.market.inventory.api.service.InventoryService;
import com.bakerbeach.market.inventory.api.service.InventoryServiceException;
import com.bakerbeach.market.order.api.model.OrderList;
import com.bakerbeach.market.order.api.service.OrderService;
import com.bakerbeach.market.order.api.service.OrderServiceException;
import com.bakerbeach.market.payment.api.service.PaymentService;
import com.bakerbeach.market.payment.api.service.PaymentServiceException;
import com.bakerbeach.market.sequence.service.SequenceService;
import com.bakerbeach.market.sequence.service.SequenceServiceException;

public class OrderServiceImpl implements OrderService {
	protected static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class.getSimpleName());
	
	@Autowired(required=false)
	private CartService cartService;
	
	private SequenceService sequenceService;

	private PaymentService paymentService;

	private InventoryService inventoryService;

	private OrderDao orderDao;

	private static final String ORDER_ID_SEQUENCE_POSTFIX = "_order_id";

	@Autowired
	@Qualifier("producerTemplate")
	private ProducerTemplate producerTemplate;

	@Override
	public Order order(Cart cart, Customer customer, ShopContext shopContext) throws OrderServiceException {
		TransactionData transactionData = null;

		try {
			Order order = buildOrder(cart, customer, shopContext);
			try {
				orderDao.saveOrUpdateOrder(order);
			} catch (OrderDaoException e1) {
				throw new OrderServiceException(new MessageImpl(MessageImpl.TYPE_ERROR,"internal.error"));
			}

			try {
				transactionData = inventoryService.decrement(order);
			} catch (InventoryServiceException ise) {
				throw new OrderServiceException(ise.getMessages());
			}

			if (!cart.getCoupons().isEmpty()) {
				try {
					Coupon coupon = cart.getCoupons().get(0);
					cartService.setIndividualUse(coupon, customer.getId(), order.getId(), cart, shopContext.getShopCode());
					coupon.getCode();
				} catch (CartServiceException cse) {
					cart.getCoupons().clear();
					
					throw new OrderServiceException(cse.getMessages());					
				}
			}
			
			try {
				paymentService.doOrder(order);
			} catch (PaymentServiceException pse) {
				throw new OrderServiceException(pse.getMessages());
			}
			
			try {
				orderDao.saveOrUpdateOrder(order);
			} catch (OrderDaoException e1) {
				throw new OrderServiceException(new MessageImpl(MessageImpl.TYPE_ERROR,"internal.error"));
			}
			
			try {
				inventoryService.confirm(transactionData, order);
			} catch (InventoryServiceException ise) {
				log.error(ExceptionUtils.getStackTrace(ise));
			}
			
			sendOrderMessage(order);
			
			return order;
		} catch (OrderServiceException e) {
			try {
				inventoryService.rollBack(transactionData);
			} catch (InventoryServiceException ise) {
				log.error(ExceptionUtils.getStackTrace(ise));
			}
			throw e;
		}
	}

	private void sendOrderMessage(Order order) {
		try {
			Map<String, Object> payload = new HashMap<String, Object>();
			payload.put("order_id", order.getId());
			payload.put("shop_code", order.getShopCode());
			
			producerTemplate.sendBody("direct:order", payload);
		} catch(Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	public Order cancelOrder(String orderId) throws OrderServiceException {
		try {
			SimpleOrder order = orderDao.findById(orderId);
			order.setStatus(Order.STATUS_CANCELED);
			orderDao.saveOrUpdateOrder(order);
			paymentService.doCancel(order);
			return order;
		} catch (OrderDaoException | PaymentServiceException e) {
			throw new OrderServiceException();
		}

	}

	@Override
	public String getNextOrderId(ShopContext shopContext) throws OrderServiceException {
		try {
			return sequenceService.generateId(shopContext.getShopCode() + ORDER_ID_SEQUENCE_POSTFIX).toString();
		} catch (SequenceServiceException e) {
			throw new OrderServiceException();
		}
	}

	@Override
	public Order findOrderById(String orderId) throws OrderServiceException {
		try {
			return orderDao.findById(orderId);
		} catch (OrderDaoException e) {
			throw new OrderServiceException();
		}
	}
	
	@Override
	public Order findOrderById(String shopCode, String orderId) throws OrderServiceException {
		log.warn("unsupported parameter shopCode");
		return findOrderById(orderId);
	}
	
	@Override
	public OrderList findOrderByCustomerIdAndShopCode(String customerId, String shopCode) throws OrderServiceException {
		try {
			return orderDao.findByCustomerId(customerId, shopCode, null, null, null);
		} catch (OrderDaoException e) {
			throw new OrderServiceException();
		}
	}

	@Override
	public OrderList findOrderByCustomerIdAndShopCode(String customerId, String shopCode, String sort, Integer limit, Integer offset) throws OrderServiceException {
		try {
			// TODO: implement order by ---
			return orderDao.findByCustomerId(customerId, shopCode, null, limit, offset);
		} catch (OrderDaoException e) {
			throw new OrderServiceException();
		}
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private Order buildOrder(Cart cart, Customer customer, ShopContext shopContext) {

		SimpleOrder order = new SimpleOrder();
		order.setCustomerId(customer.getId());
		order.setCustomerEmail(customer.getEmail());
		order.setId(shopContext.getOrderId());
		order.setShopCode(shopContext.getShopCode());
		order.setCurrency(shopContext.getCurrency());
		order.setTotal(cart.getTotal().getGross());
		order.setBillingAddress(shopContext.getBillingAddress());
		order.setShippingAddress(shopContext.getShippingAddress());
		order.setStatus(Order.STATUS_SUBMIT);
		order.setAdditionalInformations((HashMap<String, Object>) shopContext.getSessionData().get(ADDITIONAL_ORDER_INFORMATIONS));
		order.setCreatedAt(new Date());
		for (CartItem ci : cart.getCartItems()) {
			order.getItems().add(buildOrderItem(ci));
		}
		return order;
	}

	private OrderItem buildOrderItem(CartItem cartItem) {
		SimpleOrderItem orderItem = new SimpleOrderItem();

		orderItem.setGtin(cartItem.getGtin());
		orderItem.setBrand(cartItem.getBrand());
		orderItem.setSupplier(cartItem.getSupplier());
		orderItem.setQuantity(cartItem.getQuantity());
		orderItem.setDiscount(cartItem.getDiscount());
		orderItem.setVolatile(cartItem.isVolatile());
		orderItem.setUnitPrice(cartItem.getUnitPrice());
		orderItem.setTotalPrice(cartItem.getTotalPrice());
		orderItem.setTaxCode(cartItem.getTaxCode());
		orderItem.setTaxPercent(cartItem.getTaxPercent());
		orderItem.setQualifier(cartItem.getQualifier());
		orderItem.setTitle1(cartItem.getTitle1());
		orderItem.setTitle2(cartItem.getTitle2());
		orderItem.setTitle2(cartItem.getTitle3());
		orderItem.setImageUrl1(cartItem.getImageUrl1());
		orderItem.setImageUrl2(cartItem.getImageUrl2());
		orderItem.setSize(cartItem.getSize());
		orderItem.setColor(cartItem.getColor());
		
		orderItem.getAttributes().putAll(cartItem.getAttributes());

		for (String componentKey : cartItem.getComponents().keySet()) {
			CartItemComponent cic = cartItem.getComponents().get(componentKey);
			SimpleOrderItemComponent soic = new SimpleOrderItemComponent(cic.getName());
			for (String optionKey : cic.getOptions().keySet()) {
				CartItemOption cio = cic.getOptions().get(optionKey);
				SimpleOrderItemOption soio = new SimpleOrderItemOption(cio.getGtin());
				soio.setQuantity(cio.getQuantity());
				soio.setTitle1(cio.getTitle1());
				soio.setTitle2(cio.getTitle2());
				soio.setTitle3(cio.getTitle3());
				soio.setTotalPrice(cio.getTotalPrice());
				soio.setUnitPrice(cio.getUnitPrice());
				soic.getOptions().put(optionKey, soio);
			}
			orderItem.getComponents().put(componentKey, soic);
		}
		return orderItem;
	}

	public SequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public PaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	/**
	 * @return the orderDao
	 */
	public OrderDao getOrderDao() {
		return orderDao;
	}

	/**
	 * @param orderDao
	 *            the orderDao to set
	 */
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	/**
	 * @return the inventoryService
	 */
	public InventoryService getInventoryService() {
		return inventoryService;
	}

	/**
	 * @param inventoryService
	 *            the inventoryService to set
	 */
	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}


	@Override
	public Order cancelOrder(String shopCode, String orderId) throws OrderServiceException {
		log.warn("unsupported parameter shopCode");
		return cancelOrder(orderId);	
	}

}
