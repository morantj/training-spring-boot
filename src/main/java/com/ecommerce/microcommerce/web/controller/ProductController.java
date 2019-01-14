package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Api( description="API for CRUD operations of Product.")

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;


    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    @ApiOperation(value = "Returns MappingJacksonValue of unsorted products.")

    public MappingJacksonValue listeProduits() {

        Iterable<Product> produits = productDao.findAll();

        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }

    
    @RequestMapping(value = "/AdminProduits", method = RequestMethod.GET)
    @ApiOperation(value = "Returns MappingJacksonValue of products with their benefits.")

    public MappingJacksonValue calculerMargeProduit () {
    	
        Iterable<Product> produits = productDao.findAll();
        Iterator<Product> produitIterator = produits.iterator();
        
        Map<String, Integer> produitsProfit = new HashMap<>();

        while(produitIterator.hasNext()) {
        	Product tmp = produitIterator.next();        	
        	produitsProfit.put(tmp.toString(), tmp.getPrix() - tmp.getPrixAchat());
        }

        MappingJacksonValue produitsJackson = new MappingJacksonValue(produitsProfit);

        return produitsJackson;
    }

    
    @RequestMapping(value = "/ProduitsSorted", method = RequestMethod.GET)
    @ApiOperation(value = "Returns MappingJacksonValue of sorted products.")

    public MappingJacksonValue  trierProduitsParOrdreAlphabetique () {

        Iterable<Product> produits = productDao.findAllByOrderByNom();
        
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);

        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }


    @GetMapping(value = "/Produits/{id}")
    @ApiOperation(value = "Returns a Product selected by its ID.")

    public Product afficherUnProduit(
            @ApiParam("Id of the product to find. Cannot be empty.")
            @PathVariable int id) 
        {

        Product produit = productDao.findById(id);

        if(produit==null) throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
        
        return produit;
    }


    @PostMapping(value = "/Produits")
    @ApiOperation(value = "Adds a new product.")

    public ResponseEntity<Void> ajouterProduit(
            @ApiParam("RequestBody of the product to add. Cannot be empty.")
            @Valid @RequestBody Product product) 
        {

    	if(product.getPrix()==0) throw new ProduitGratuitException("HOPOP, Le prix n'est pas en rêgle, buvez un coup ça ira mieux..");

        Product productAdded =  productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();
        

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping (value = "/Produits/{id}")
    @ApiOperation(value = "Deletes a product selected by its ID.")

    public void supprimerProduit(
            @ApiParam("Id of the product to remove. Cannot be empty.")
            @PathVariable int id) 
        {

        productDao.delete(id);
    }

    @PutMapping (value = "/Produits")
    @ApiOperation(value = "Updates a product.")
    public void updateProduit(
            @ApiParam("RequestBody of the product to add. Cannot be empty.")
            @RequestBody Product product) 
        {

    	if(product.getPrix()==0) throw new ProduitGratuitException("HOPOP, Police des Datas. Le prix n'est pas en rêgle.");

        productDao.save(product);
        
    }


    @GetMapping(value = "test/produits/{prix}")
    public List<Product>  testeDeRequetes(@PathVariable int prix) {

        return productDao.chercherUnProduitCher(400);
    }



}
