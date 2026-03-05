package runner;

import driver.MobileDriverManager;
import steps.ProductStep;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import io.appium.java_client.AppiumDriver;

public class TestRunner {

    AppiumDriver driver;

    @Test
    public void agregarBackpack(){

        driver = MobileDriverManager.getDriver();
        ProductStep productStep = new ProductStep(driver);

        productStep.agregarProducto("Sauce Labs Backpack",1);
        productStep.abrirCarrito();
    }

    @Test
    public void agregarTshirt(){

        driver = MobileDriverManager.getDriver();
        ProductStep productStep = new ProductStep(driver);

        productStep.agregarProducto("Sauce Labs Bolt T-Shirt",1);
        productStep.abrirCarrito();
    }

    @Test
    public void agregarBikeLight(){

        driver = MobileDriverManager.getDriver();
        ProductStep productStep = new ProductStep(driver);

        productStep.agregarProducto("Sauce Labs Bike Light",2);
        productStep.abrirCarrito();
    }

    @AfterEach
    public void cerrar(){
        MobileDriverManager.quitDriver();
    }
}