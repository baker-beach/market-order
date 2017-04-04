package com.bakerbeach.market.core.service.order.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrderAddressImpl implements OrderAddress {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String type;
	private String prefix;
	private String firstName;
	private String lastName;
	private String middleName;
	private String suffix;
	private String street1;
	private String street2;
	private String postcode;
	private String city;
	private String region;
	private String countryCode;
	private String email;
	private String telephone;
	private String fax;
	private String company;
	private Date createdAt;
	private Date updatedAt;
	private Set<String> tags = new HashSet<String>();
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String getMiddleName() {
		return middleName;
	}

	@Override
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Override
	public String getSuffix() {
		return suffix;
	}

	@Override
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String getStreet1() {
		return street1;
	}

	@Override
	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	@Override
	public String getStreet2() {
		return street2;
	}

	@Override
	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	@Override
	public String getPostcode() {
		return postcode;
	}

	@Override
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Override
	public String getCity() {
		return city;
	}

	@Override
	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String getCountryCode() {
		return countryCode;
	}

	@Override
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getTelephone() {
		return telephone;
	}

	@Override
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String getFax() {
		return fax;
	}

	@Override
	public void setFax(String fax) {
		this.fax = fax;
	}

	@Override
	public String getCompany() {
		return company;
	}

	@Override
	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

}
