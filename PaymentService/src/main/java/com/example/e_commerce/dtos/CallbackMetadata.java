package com.example.e_commerce.dtos;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CallbackMetadata{

	@JsonProperty("Item")
	private List<CallbackMetadataItem> item;
}