package ehb.demo.model;

public class CartItem {
    private Product product;
    private int aantal;
    private int dagen;

    public CartItem(Product product, int aantal, int dagen) {
        this.product = product;
        this.aantal = aantal;
        this.dagen = dagen;
    }

    public Product getProduct() { return product; }
    public int getAantal() { return aantal; }
    public int getDagen() { return dagen; }
    public double getSubtotaal() { return product.getPrijsPerDag() * aantal * dagen; }
}