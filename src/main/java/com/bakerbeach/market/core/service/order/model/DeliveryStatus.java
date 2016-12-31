package com.bakerbeach.market.core.service.order.model;

import java.util.Date;

public interface DeliveryStatus {
	
	public final static String NOT_SENT = "0-NOT_SENT";
	public final static String IN_TRANSPORTATION = "1-IN_TRANSPORTATION";
	
	public final static String IN_TRANSPORTATION_POSTPONEMENT_AFTER_ARRANGING_WITH_CLIENT = "1-IN_TRANSPORTATION_POSTPONEMENT_AFTER_ARRANGING_WITH_CLIENT";
	
	public final static String IN_DELIVERY = "2_A-IN_DELIVERY";
	
	public final static String NOT_DELIVERED = "2_B-NOT_DELIVERED";
	
	public final static String READY_TO_COLLECT = "3-READY_TO_COLLECT";
	
	public final static String READY_TO_COLLECT_AT_POST_OFFICE_FIRST_DELIVERY_TRY_NOT_SUCCESFUL = "3-READY_TO_COLLECT_AT_POST_OFFICE_FIRST_DELIVERY_TRY_NOT_SUCCESFUL";
	public final static String READY_TO_COLLECT_AT_POST_OFFICE = "3-READY_TO_COLLECT_AT_POST_OFFICE";
	public final static String READY_TO_COLLECT_AT_PACKSTATION = "3-READY_TO_COLLECT_AT_PACKSTATION";

	
	public final static String ERROR = "-4-ERROR";
	public final static String ERROR_DELAY = "-4-ERROR_DELAY";
	
	public final static String DELIVERED = "5_A-DELIVERED";
	
	public final static String DELIVERED_COLLECTED_AT_PACKSTATION = "5_A-DELIVERED_COLLECTED_AT_PACKSTATION";
	public final static String DELIVERED_TO_AUTHORIZED_PERSON = "5_A-DELIVERED_TO_AUTHORIZED_PERSON";
	public final static String DELIVERED_TO_DIFFERENT_PERSON = "5_A-DELIVERED_TO_DIFFERENT_PERSON";
	public final static String DELIVERED_TO_FAMILY_MEMBER = "5_A-DELIVERED_TO_FAMILY_MEMBER";
	public final static String DELIVERED_TO_GIVEN_PLACE = "5_A-DELIVERED_TO_GIVEN_PLACE";
	public final static String DELIVERED_TO_NEIGHBOUR = "5_A-DELIVERED_TO_NEIGHBOUR";
	public final static String DELIVERED_TO_PARTNER = "5_A-DELIVERED_TO_PARTNER";
	public final static String DELIVERED_SUCCESSFULLY = "5_A-DELIVERED_SUCCESSFULLY";
	public final static String RETURN = "5_B-RETURN";
	
	public final static String RETURN_PACKET_DAMAGED_IN_TRANSPORTATION = "5_B-RETURN_PACKET_DAMAGED_IN_TRANSPORTATION";
	public final static String RETURN_SENDING_NOT_AGB_CONFORM = "5_B-RETURN_SENDING_NOT_AGB_CONFORM";
	public final static String RETURN_NO_COLLECTION_BY_CLIENT_BEFORE_STORAGE_TIME_LIMIT = "5_B-RETURN_NO_COLLECTION_BY_CLIENT_BEFORE_STORAGE_TIME_LIMIT";
	public final static String RETURN_UNKNOWN_RECIPIENT = "5_B-RETURN_UNKNOWN_RECIPIENT";
	public final static String RETURN_MOVED_RECIPIENT = "5_B-RETURN_MOVED_RECIPIENT";
	
	String getServiceProvider();
	String getTrackingNumber();
	String getStatus();
	Date getTimestamp();
	String getRecipientName();
	String getDetails();
	Integer getStatusNumber();
	
	void setServiceProvider(String serviceProvider);
	void setTrackingNumber(String trackingNumber);
	void setStatus(String status);
	void setTimestamp(Date timestamp);
	void setRecipientName(String recipientName);
	void setDetails(String details);
	String toString();
}