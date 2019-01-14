package com.ecommerce.microcommerce.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.controller.ProductController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDaoTest {
	
	@Autowired
	private ProductController productController;
	
	@Autowired
	private ProductDao productDao;
	
	@Test
	public void contexLoads() throws Exception {
	    assertThat(productController).isNotNull();
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
		
		productB = new Product();
		productB.setId(2);
		productB.setNom("moyen");
		productB.setPrixAchat(50);
		productB.setPrix(100);
		
		productC = new Product();
		productC.setId(3);
		productC.setNom("pas cher");
		productC.setPrixAchat(25);
		productC.setPrix(50);

        productDao.save(productA);
        productDao.save(productB);
        productDao.save(productC);
	}

    @Test
    public void findByIdTest() {
        
        Product found = productDao.findById(1);

        assertEquals(found.getId(), productA.getId());
        
        assertNull(productDao.findById(0));
    };
    

    @Test
    public void findByPrixGreaterThanTest() {        
        List<Product> found = productDao.findByPrixGreaterThan(100);
        
        List<Product> notFound = productDao.findByPrixGreaterThan(200);

        assertEquals(found.size(), 1);
        
        assertEquals(notFound.size(), 0);
    };
    
    @Test
    public void chercherUnProduitCherTest() {
    	List<Product> found = productDao.findByPrixGreaterThan(100);
        
        List<Product> notFound = productDao.findByPrixGreaterThan(200);

        assertEquals(found.size(), 1);
        
        assertEquals(notFound.size(), 0);
    }
    

    @Test
    public void findByNomLikeTest() {        
        List<Product> found = productDao.findByNomLike("cher");
        
        List<Product> notFound = productDao.findByNomLike("not found");
        
        assertEquals(found.size(), 1);
        
        assertEquals(notFound.size(), 0);
    };
    
    @Test
    public void findAllByOrderByNomTest() {
    	List<Product> found = productDao.findAllByOrderByNom();
    	

        assertEquals(found.get(0).getNom(), "cher");
        assertEquals(found.get(1).getNom(), "moyen");
        assertEquals(found.get(2).getNom(), "pas cher");
    }

}
