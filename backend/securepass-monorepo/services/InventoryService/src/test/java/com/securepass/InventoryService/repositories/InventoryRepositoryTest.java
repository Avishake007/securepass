//package com.securepass.InventoryService.repositories;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.Date;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//
//import com.securepass.InventoryService.configurations.TestConfig;
//import com.securepass.InventoryService.entities.Inventory;
//
//@EntityScan(basePackages = "com.securepass.InventoryService.entities")
//@EnableMongoRepositories(basePackages = "com.securepass.InventoryService.repositories")
//@ContextConfiguration(classes = TestConfig.class)
//@DataJpaTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestPropertySource(locations = "classpath:application-test.properties")
//public class InventoryRepositoryTest {
//	
//	@Autowired
//	InventoryRepository inventoryRepository;
//	
//	Inventory inventory;
//
//	@BeforeEach
//	public void setup() {
//		inventory =
//				inventory
//				.builder()
//				.name("Samsung Galaxy")
//				.price(10000)
//				.stock(5)
//				.category("Mobile Phone")
//				.createdAt(new Date())
//				.build();
//	}
//	
//	@Test
//	@Order(1)
//	@DisplayName("Test 1 : saveInventoryTest")
//	@Rollback(value = false)
//	public void saveInventoryTest() {
//		System.out.println("Test 1");
//		inventoryRepository.save(inventory);
//		
//		assertThat(inventory.getId()).isNotBlank();
//	}
//}
//
//
//
//
