package com.securepass.InventoryService.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securepass.InventoryService.dtos.AllInventoryResponseDto;
import com.securepass.InventoryService.dtos.BaseInventoryResponseDto;
import com.securepass.InventoryService.dtos.InventoryRequestDto;
import com.securepass.InventoryService.dtos.InventoryResponseDto;
import com.securepass.InventoryService.entities.Inventory;
import com.securepass.InventoryService.services.specification.IdempotencyService;
import com.securepass.InventoryService.services.specification.InventoryService;

@WebMvcTest(InventoryController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InventoryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockitoBean
	private InventoryService inventoryService;
	
	@MockitoBean
	private IdempotencyService idempotencyService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	BaseInventoryResponseDto baseInventoryResponseDto;
	
	InventoryRequestDto inventoryRequestDto;
	
	Inventory inventory; 
	
	InventoryResponseDto inventoryResponseDto;
	
	AllInventoryResponseDto allInventoryResponseDto;
	
	@BeforeEach
	public void setup() {
		baseInventoryResponseDto = baseInventoryResponseDto
		.builder()
		.responseCode("0")
		.responseStatus("Inventory created successfully")
		.build();
		
		inventoryRequestDto = new InventoryRequestDto("sf", 0.9, 0, "faaf");
		
		inventory = inventory
		.builder()
		.id("123")
		.name("AC")
		.price(45000.7)
		.stock(3)
		.category("Electronic")
		.createdAt(new Date())
		.build();
		
		inventoryResponseDto = inventoryResponseDto
				.builder()
				.responseCode("0")
				.responseStatus("")
				.inventory(inventory)
				.build();
		
		allInventoryResponseDto = allInventoryResponseDto
				.builder()
				.responseCode("0")
				.responseStatus("")
				.inventoryList(List.of(
						new Inventory("0", "Phone", 6780.8, 2, "Electronic", new Date()),
						new Inventory("0", "EarPhone", 9980.8, 2, "Electronic", new Date())
						))
				.build();
		
//		allInventoryResponseDto
//		.builder()
//		.responseCode("0")
//		.responseStatus("")
//		.inventoryList(List.of(new Inventory("124", null, 0, 0, null, null)))
		
		
	
	}
	
	@Test
	@DisplayName("Test 1 : Save Inventory Test")
	@Order(1)
	public void saveInventoryTest() throws Exception {
		
		
		
		//precondition
		when(inventoryService.createOrder(any(InventoryRequestDto.class))).thenReturn(baseInventoryResponseDto);
		
		 // action
        ResultActions response = mockMvc.perform(post("/api/v1/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .header("idempotent-key", "2323vc")
                .content(objectMapper.writeValueAsString(inventoryRequestDto)));

        // verify
        response.
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.responseCode",
                       is( baseInventoryResponseDto.getResponseCode())))
                .andExpect(jsonPath("$.responseStatus",
                        is(baseInventoryResponseDto.getResponseStatus())));
	}
	
	@Test
	@DisplayName("Test 2 : Get Inventory By Product Test")
	@Order(2)
	public void getInventoryByProductTest() throws Exception {
		when(inventoryService.getInventoryById("123")).thenReturn(inventoryResponseDto);
		
		mockMvc.perform(get("/api/v1/inventory/{productId}", inventory.getId()))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.responseCode",
                is( inventoryResponseDto.getResponseCode())))
         .andExpect(jsonPath("$.responseStatus",
                 is(inventoryResponseDto.getResponseStatus())))
         .andExpect(jsonPath("$.inventory.id",
                 is( inventoryResponseDto.getInventory().getId())));
		
	}
	
	@Test
	@DisplayName("Test 3 : Increase Stocks By Units Test")
	@Order(3)
	public void increaseStocksByUnitsTest() throws Exception {
		when(inventoryService.increaseStocksByUnits("123", 2)).thenReturn(baseInventoryResponseDto);
		
		mockMvc.perform(patch("/api/v1/inventory/{productId}/increase/{units}","123",2))
		.andExpect(status().isAccepted())
		.andDo(print())
		.andExpect(jsonPath("$.responseCode", is(baseInventoryResponseDto.getResponseCode())))
		.andExpect(jsonPath("$.responseStatus", is(baseInventoryResponseDto.getResponseStatus())));
		
	}
	
	@Test
	@DisplayName("Test 4 : Decrease Stocks By Units Test")
	@Order(4)
	public void decreaseStocksByUnitsTest() throws Exception {
		when(inventoryService.reduceStocksByUnits("123", 2)).thenReturn(baseInventoryResponseDto);
		
		mockMvc.perform(patch("/api/v1/inventory/{productId}/reduce/{units}","123",2))
		.andExpect(status().isAccepted())
		.andDo(print())
		.andExpect(jsonPath("$.responseCode", is(baseInventoryResponseDto.getResponseCode())))
		.andExpect(jsonPath("$.responseStatus", is(baseInventoryResponseDto.getResponseStatus())));
		
	}
	
	
	@Test
	@DisplayName("Test 5 : Get All Inventories Test")
	@Order(5)
	public void getAllInventoriesTest() throws Exception {
		
		when(inventoryService.getAllInventories()).thenReturn(allInventoryResponseDto);
		
		mockMvc.perform(get("/api/v1/inventory/all"))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.responseCode",is(allInventoryResponseDto.getResponseCode())))
		.andExpect(jsonPath("$.responseStatus", is(allInventoryResponseDto.getResponseStatus())))
		.andExpect(jsonPath("$.inventoryList[0].id",is(allInventoryResponseDto.getInventoryList().get(0).getId())));
	
	}
	
	
	

}
