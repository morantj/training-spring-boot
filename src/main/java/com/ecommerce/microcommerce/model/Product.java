package com.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Api(description = "Sets the products object's properties.")

@Entity
//@JsonFilter("monFiltreDynamique")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "ID of the product.", required = true, position = 0)

    private int id;


    @NotNull
    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Name of the product.", required = true, position = 1)
    @Length(min=3, max=20, message = "Name should be from 3 to 20 caracters.")

    private String nom;


    @NotNull
    @Min(value = 1)
    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Initial price of the product.", required = true, position = 2)

    private int prix;


    @NotNull
    @JsonProperty(required = true)
    @ApiModelProperty(notes = "Purchasing price of the product.", required = true, position = 3)

    private int prixAchat;


    public Product() {
    }


    public Product(int id, String nom, int prix, int prixAchat) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.prixAchat = prixAchat;
    }


    @ApiOperation("Returns the product's ID.")

    public int getId() {
        return id;
    }


    @ApiOperation("Sets the product's ID.")

    public void setId(int id) {
        this.id = id;
    }


    @ApiOperation("Returns the product's name.")

    public String getNom() {
        return nom;
    }


    @ApiOperation("Sets the product's name.")

    public void setNom(String nom) {
        this.nom = nom;
    }


    @ApiOperation("Returns the product's price.")

    public int getPrix() {
        return prix;
    }


    @ApiOperation("Sets the product's price.")

    public void setPrix(int prix) {
        this.prix = prix;
    }


    @ApiOperation("Returns the product's purchasing price.")

    public int getPrixAchat() {
        return prixAchat;
    }


    @ApiOperation("Sets the product's purchasing price.")

    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                '}';
    }
}
