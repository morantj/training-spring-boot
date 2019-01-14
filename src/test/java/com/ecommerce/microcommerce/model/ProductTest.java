package com.ecommerce.microcommerce.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTest {
	
	Product product;
	
	@Before
	public void setUp() {
		
		product = new Product();
		
	}
	
	@Test
	public void IdTest() {    
		
		product.setId(1);
		
		assertEquals(product.getId(), 1);
		
	}
	
	@Test
	public void nomTest() {        
		
		product.setNom("test");
		
		assertEquals(product.getNom(), "test");
		
	}
	
	@Test
	public void prixTest() {       
		
		product.setPrix(100);
		
		assertEquals(product.getPrix(), 100);
		
	}
	
	@Test
	public void prixAchatTest() {  
		
		product.setPrixAchat(50);
		
		assertEquals(product.getPrixAchat(), 50);
		
	}
}
