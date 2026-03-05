package steps;

import view.ProductsView;
import io.appium.java_client.AppiumDriver;

public class ProductStep {

    ProductsView productsView;

    public ProductStep(AppiumDriver driver){

        productsView = new ProductsView(driver);

    }

public void agregarProducto(String nombreProducto, int cantidad){

    if (cantidad <= 0) {
        throw new IllegalArgumentException("debe agrgarse productos mayor a cero");
    }

    if (nombreProducto == null || nombreProducto.isBlank()) {
        throw new IllegalArgumentException("El nombre del producto es obligatorio");
    }

    for(int i = 0; i < cantidad; i++){
        productsView.clickAddToCart();

    }

}

    public void abrirCarrito(){

        productsView.openCart();

    }

}