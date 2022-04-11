package dam1.prog.tiendav2;

public class Order {
  private final int ID;
  private int[] products;

  public Order(int ID, int[] products) {
    this.ID = ID;
    this.products = products;
  }
}
