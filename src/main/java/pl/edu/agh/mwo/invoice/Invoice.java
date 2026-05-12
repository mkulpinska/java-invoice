package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private static long nextNumber = 1;

    private final long number;

    private Map<Product, Integer> products = new HashMap<Product, Integer>();

    public Invoice() {
        this.number = nextNumber++;
    }

    public long getNumber() {
        return number;
    }

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        int newQuantity = products.getOrDefault(product, 0) + quantity;
        products.put(product, newQuantity);
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public String getInvoiceText() {
        StringBuilder sb = new StringBuilder();

        // Nagłówek z numerem faktury
        sb.append("Faktura nr: ").append(this.number).append("\n");

        // Lista produktów
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();

            sb.append(product.getName())
                    .append(", ")
                    .append(quantity)
                    .append(" szt., ")
                    .append(product.getPrice())
                    .append("\n");
        }

        // Podsumowanie liczby pozycji
        sb.append("Liczba pozycji: ").append(products.size());

        return sb.toString();
    }

}
