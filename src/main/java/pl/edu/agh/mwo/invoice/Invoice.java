package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {

private final Map<Product, Integer> products = new HashMap<>();

    public void addProduct(Product product) {
        this.addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.products.put(product, quantity);
    }

public BigDecimal getNetValue() {
    BigDecimal value = BigDecimal.ZERO;

    for (Map.Entry<Product, Integer> entry : products.entrySet()) {
        Product product = entry.getKey();
        Integer quantity = entry.getValue();

        BigDecimal price = product.getPrice()
                .multiply(BigDecimal.valueOf(quantity));

        value = value.add(price);
    }
    return value;
}

    public BigDecimal getTax() {
        BigDecimal tax = BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            BigDecimal productTax = product.getPrice()
                    .multiply(product.getTaxPercent())
                    .multiply(BigDecimal.valueOf(quantity));

            tax = tax.add(productTax);
        }
        return tax;
    }

    public BigDecimal getGrossValue() {
        return getNetValue().add(getTax());
    }
}

