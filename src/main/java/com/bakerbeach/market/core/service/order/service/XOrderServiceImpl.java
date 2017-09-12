package com.bakerbeach.market.core.service.order.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.ProducerTemplate;
import org.apache.commons.collections4.CollectionUtils;
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
import com.bakerbeach.market.core.api.model.Coupon;
import com.bakerbeach.market.core.api.model.Customer;
import com.bakerbeach.market.core.api.model.ShopContext;
import com.bakerbeach.market.core.service.order.dao.OrderDao;
import com.bakerbeach.market.core.service.order.dao.OrderDaoException;
import com.bakerbeach.market.inventory.api.model.TransactionData;
import com.bakerbeach.market.inventory.api.service.InventoryService;
import com.bakerbeach.market.inventory.api.service.InventoryServiceException;
import com.bakerbeach.market.order.api.model.Order;
import com.bakerbeach.market.order.api.model.OrderItem;
import com.bakerbeach.market.order.api.model.OrderList;
import com.bakerbeach.market.order.api.service.OrderService;
import com.bakerbeach.market.order.api.service.OrderServiceException;
import com.bakerbeach.market.payment.api.service.PaymentService;
import com.bakerbeach.market.payment.api.service.PaymentServiceException;
import com.bakerbeach.market.sequence.service.SequenceService;
import com.bakerbeach.market.sequence.service.SequenceServiceException;

public class XOrderServiceImpl implements OrderService {
	protected static final Logger log = LoggerFactory.getLogger(XOrderServiceImpl.class.getSimpleName());

	@Autowired(required = false)
	private CartService cartService;

	private SequenceService sequenceService;

	private PaymentService paymentService;

	private InventoryService inventoryService;

	protected Map<String, OrderDao> orderDaos;

	private static final String ORDER_ID_SEQUENCE_POSTFIX = "_order_id";

	@Autowired
	@Qualifier("producerTemplate")
	private ProducerTemplate producerTemplate;

	@Override
	public Order order(Cart cart, Customer customer, ShopContext shopContext) throws OrderServiceException {
		TransactionData transactionData = null;

		String shopCode = shopContext.getShopCode();

		try {
			Order order = newOrder(cart, customer, shopContext);
			try {
				orderDaos.get(shopCode).saveOrUpdateOrder(order);
			} catch (OrderDaoException e1) {
				throw new OrderServiceException(new MessageImpl("foo",MessageImpl.TYPE_ERROR,"internal.error", null, null));
			}

			try {
				transactionData = inventoryService.decrement(order);
			} catch (InventoryServiceException ise) {
				throw new OrderServiceException(ise.getMessages());
			}

			if (CollectionUtils.isNotEmpty(cart.getCoupons())) {
				try {
					Coupon coupon = cart.getCoupons().get(0);
					cartService.setIndividualUse(coupon, customer.getId(), order.getId(), cart,
							shopContext.getShopCode());
					coupon.getCode();
				} catch (CartServiceException cse) {
					cart.getCoupons().clear();

					throw new OrderServiceException(cse.getMessages());
				}
			}

			try {
				paymentService.doOrder(order);
				
				// TODO: get order status from payment serviec
				order.setStatus(Order.STATUS_SUBMIT);
			} catch (PaymentServiceException pse) {
				throw new OrderServiceException(pse.getMessages());
			}

			try {
				orderDaos.get(shopCode).saveOrUpdateOrder(order);
			} catch (OrderDaoException e1) {
				throw new OrderServiceException(new MessageImpl("foo",MessageImpl.TYPE_ERROR,"internal.error", null, null));
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
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	public Order cancelOrder(String orderId) throws OrderServiceException {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Order cancelOrder(String shopCode, String orderId) throws OrderServiceException {
		try {
			Order order = orderDaos.get(shopCode).findById(orderId);
			order.setStatus(Order.STATUS_CANCELED);
			orderDaos.get(shopCode).saveOrUpdateOrder(order);
			paymentService.doCancel(order);
			return order;
		} catch (OrderDaoException | PaymentServiceException e) {
			throw new OrderServiceException();
		}

	}

	@Override
	public String getNextOrderId(ShopContext shopContext) throws OrderServiceException {
		try {
			String key = shopContext.getOrderSequenceCode() + ORDER_ID_SEQUENCE_POSTFIX;
			Long maxRandomOffset = shopContext.getOrderSequenceRandomOffset();
			
			return sequenceService.generateId(key, maxRandomOffset).toString();
		} catch (SequenceServiceException e) {
			throw new OrderServiceException();
		}
	}

	@Override
	public Order findOrderById(String shopCode, String orderId) throws OrderServiceException {
		try {
			return orderDaos.get(shopCode).findById(orderId);
		} catch (OrderDaoException e) {
			throw new OrderServiceException();
		}
	}

	@Override
	public OrderList findOrderByCustomerIdAndShopCode(String customerId, String shopCode) throws OrderServiceException {
		return findOrderByCustomerIdAndShopCode(customerId, shopCode, "-id", null, null);
	}

	@Override
	public OrderList findOrderByCustomerIdAndShopCode(String customerId, String shopCode, String sort, Integer limit,
			Integer offset) throws OrderServiceException {
		try {
			return orderDaos.get(shopCode).findByCustomerIdAndShop(customerId, shopCode, "-id", limit, offset);
		} catch (OrderDaoException e) {
			throw new OrderServiceException();
		}
	}

	@SuppressWarnings("unchecked")
	private Order newOrder(Cart cart, Customer customer, ShopContext shopContext) throws OrderServiceException {
		try {
			Order order = orderDaos.get(shopContext.getShopCode()).newInstance();

			order.setCustomerId(customer.getId());
			order.setCustomerEmail(customer.getEmail());
			order.setId(shopContext.getOrderId());
			order.setShopCode(shopContext.getShopCode());
			order.setCurrencyCode(shopContext.getCurrentCurrency().getIsoCode());
			order.setTotal(order.newTotal(cart.getTotal()));
			order.setBillingAddress(order.newAddress(shopContext.getBillingAddress()));
			order.setShippingAddress(order.newAddress(shopContext.getShippingAddress()));
			order.setStatus(Order.STATUS_TMP);
			order.addAttributes(
					(HashMap<String, Object>) shopContext.getSessionData().get(ADDITIONAL_ORDER_INFORMATIONS));
			order.setCreatedAt(new Date());
			cart.getItems().forEach((k, ci) -> {
				try {
					order.addItem(newOrderItem(ci, order));
				} catch (OrderServiceException e) {
					log.error(ExceptionUtils.getStackTrace(e));
				}
			});

			return order;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new OrderServiceException();
		}

	}
	
	private OrderItem newOrderItem(CartItem ci, Order order) throws OrderServiceException {
		OrderItem oi = order.newItem();
		
		oi.setCode(ci.getCode());
		oi.setGtin(ci.getGtin());
		oi.setBrand(ci.getBrand());
		oi.setSupplier(ci.getSupplier());
		oi.setQuantity(ci.getQuantity());
		oi.setDiscount(ci.getDiscount());
		oi.setIsVolatile(ci.isVolatile());
		oi.setUnitPrices(ci.getUnitPrices());
		oi.setTotalPrices(ci.getTotalPrices());
		oi.setTaxCode(ci.getTaxCode());
		oi.setTaxPercent(ci.getTaxPercent());
		oi.setQualifier(ci.getQualifier());
		oi.setTitle(ci.getTitle());
		oi.setImages(ci.getImages());
		oi.setType(ci.getType());
		
		ci.getOptions().forEach((key, cio) -> {
			OrderItem.Option option = newOption(cio, oi);
			oi.putOption(option.getCode(), option);
		});

		return oi;
	}
	
	private OrderItem.Option newOption(CartItem.Option cio, OrderItem oi) {
		OrderItem.Option option = oi.newOption(cio.getCode());

		option.setGtin(cio.getGtin());
		option.setQuantity(cio.getQuantity());
		option.setTag(cio.getTag());
		option.setUnitPrices(cio.getUnitPrices());
		option.setTitle(cio.getTitle());
				
		return option;		
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

	public void setOrderDaos(Map<String, OrderDao> orderDaos) {
		this.orderDaos = orderDaos;
	}

	public InventoryService getInventoryService() {
		return inventoryService;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@Override
	public Order findOrderById(String orderId) throws OrderServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderList findOrderByStatusAndShopCode(String status, String shopCode, String sort, Integer limit,
			Integer offset) throws OrderServiceException {
		// TODO Auto-generated method stub
		try {
			return orderDaos.get(shopCode).findByStatusAndShop(status, shopCode, sort, limit, offset);
		} catch (OrderDaoException e) {
			throw new OrderServiceException();
		}
	}
	
	@Override
	public void saveOrUpdate(Order order) throws OrderServiceException{
		try {
			orderDaos.get(order.getShopCode()).saveOrUpdateOrder(order);
		} catch (OrderDaoException e) {
			throw new OrderServiceException();
		}
	}

}
