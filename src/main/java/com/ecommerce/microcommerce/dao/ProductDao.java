package com.ecommerce.microcommerce.dao;

import com.ecommerce.microcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.swagger.annotations.ApiOperation;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    @ApiOperation("Returns a Product selected by its ID.")
    Product findById(int id);

    @ApiOperation("Returns a List<Product> at a price higher than parameter.")
    List<Product> findByPrixGreaterThan(int prixLimit);

    @ApiOperation("Returns a List<Product> with same name as parameter.")
    List<Product> findByNomLike(String recherche);

    @ApiOperation("Returns a List<Product> at a price higher than parameter.")
    @Query("SELECT id, nom, prix FROM Product p WHERE p.prix > :prixLimit")
    List<Product>  chercherUnProduitCher(@Param("prixLimit") int prix);
    
    @ApiOperation("Returns a List<Product> sorted by name.")
    List<Product> findAllByOrderByNom();
}
