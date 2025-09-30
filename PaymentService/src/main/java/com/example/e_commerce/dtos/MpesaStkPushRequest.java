package com.example.e_commerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MpesaStkPushRequest{

	@JsonProperty("TransactionType")
	private String transactionType; //"CustomerPayBillOnline"

	@JsonProperty("Amount")
	private double amount;

	@JsonProperty("CallBackURL")
	private String callBackURL; //ava

	@JsonProperty("PhoneNumber")
	private String phoneNumber;

	@JsonProperty("PartyA")
	private String partyA; //ava

	@JsonProperty("PartyB")
	private String partyB; //ava

	@JsonProperty("AccountReference")
	private String accountReference; //ava

	@JsonProperty("TransactionDesc")
	private String transactionDesc; //ava

	@JsonProperty("BusinessShortCode")
	private String businessShortCode; //ava

	@JsonProperty("Timestamp")
	private String timestamp; //ava

	@JsonProperty("Password")
	private String password; //ava
}