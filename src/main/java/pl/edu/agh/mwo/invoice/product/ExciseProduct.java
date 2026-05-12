package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public abstract class ExciseProduct extends Product {

    private static final BigDecimal EXCISE = new BigDecimal("5.56");

    protected ExciseProduct(String name, BigDecimal price, BigDecimal taxPercent) {
        super(name, price, taxPercent);
    }


    @Override
    public BigDecimal getPriceWithTax() {
        return super.getPriceWithTax().add(EXCISE);
    }
}
