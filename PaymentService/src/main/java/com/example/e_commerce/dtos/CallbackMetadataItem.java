package com.example.e_commerce.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CallbackMetadataItem {

	@JsonProperty("Value")
	private String value;

	@JsonProperty("Name")
	private String name;
}