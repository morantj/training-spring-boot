package com.ecommerce.microcommerce.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;

import net.minidev.json.parser.JSONParser;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ProductDao productDao;
    

	@Test
	public void contexLoads() throws Exception {
	    assertThat(mockMvc).isNotNull();
	}

	Product productA;
	Product productB;
	Product productC;
	
	@Before
	public void setUp() {
		
		productA = new Product();
		productA.setId(1);
		productA.setNom("cher");
		productA.setPrixAchat(100);
		productA.setPrix(200);
		
		productC = new Product();
		productC.setId(2);
		productC.setNom("pas cher");
		productC.setPrixAchat(25);
		productC.setPrix(50);
		
		productB = new Product();
		productB.setId(3);
		productB.setNom("moyen");
		productB.setPrixAchat(50);
		productB.setPrix(100);

        productDao.save(productA);
        productDao.save(productB);
        productDao.save(productC);
        
	}

    @Test
    public void listeProduitsTest() throws Exception {
        this.mockMvc.perform(get("/Produits"))
        	.andDo(print())
        	.andExpect(status().isOk())
            .andExpect(content().json("[{'id':1,'nom':'cher','prix':200,'prixAchat':100},{'id':2,'nom':'pas cher','prix':50,'prixAchat':25},{'id':3,'nom':'moyen','prix':100,'prixAchat':50}]"));
    }
    
    @Test
    public void calculerMargeProduitTest() throws Exception {
        this.mockMvc.perform(get("/AdminProduits"))
        	.andDo(print())
        	.andExpect(status().isOk())
            .andExpect(content().json("{\"Product{id=1, nom='cher', prix=200}\":100,\"Product{id=3, nom='moyen', prix=100}\":50,\"Product{id=2, nom='pas cher', prix=50}\":25}"));
    }
    
    @Test
    public void trierProduitsParOrdreAlphabetiqueTest() throws Exception {
        this.mockMvc.perform(get("/ProduitsSorted"))
        	.andDo(print())
        	.andExpect(status().isOk())
            .andExpect(content().json("[{'id':1,'nom':'cher','prix':200,'prixAchat':100},{'id':3,'nom':'moyen','prix':100,'prixAchat':50},{'id':2,'nom':'pas cher','prix':50,'prixAchat':25}]"));
    }
    
    @Test
    public void afficherUnProduitTest() throws Exception {
        this.mockMvc.perform(get("/Produits/1"))
        	.andDo(print())
        	.andExpect(status().isOk())
            .andExpect(content().json("{'id':1,'nom':'cher','prix':200,'prixAchat':100}"));
    }
    
    @Test
    public void ajouterProduitTest() throws Exception {

        this.mockMvc.perform(
    		post("/Produits")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content("{\"nom\":\"cher\",\"prix\":200,\"prixAchat\":100}")
            )
        	.andExpect(status().isCreated());
    }
    
    @Test
    public void supprimerProduitTest() throws Exception {
    	
    	this.mockMvc.perform( delete("/Produits/{id}", 1) ).andDo(print()).andExpect(status().isOk());
    }
    
    @Test
    public void updateProduitTest() throws Exception {
    	
    	this.mockMvc.perform(
            put("/Produits")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"nom\":\"tres cher\",\"prix\":2000,\"prixAchat\":1000}")
			)
            .andExpect(status().isOk());
    }
}
