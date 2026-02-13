package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
// private Collection products = new ArrayList<>();

//    public void addProduct(Product product) {
//        this.addProduct(product, 1);
//    }
//
//    public void addProduct(Product product, Integer quantity) {
//        this.products.put(product, quantity);
//    }
//
//    private Map products = new HashMap<>();
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


//    public BigDecimal getNetValue() {
//        BigDecimal value = BigDecimal.ZERO;
//        for (Product product : this.products.keySet()) {
//            Integer quantity = this.products.get(product);
//            BigDecimal price = product.getPrice();
//            price = price.multiply(BigDecimal.valueOf(quantity));
//            value = value.add(price);
//        }
//        return value;
//    }
//
//    public BigDecimal getTax() {
//
//        return getNetValue().subtract(getNetValue());
//    }
//
//    public BigDecimal getGrossValue() {
//        BigDecimal value = BigDecimal.ZERO;
//        for (Product product : this.products.keySet()) {
//            Integer quantity = this.products.get(product);
//            BigDecimal price = product.getPriceWithTax().substruct;
//            price = price.multiply(BigDecimal.valueOf(quantity));
//            value = value.add(price);
//        }
//        return value;
//    }
//}
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

