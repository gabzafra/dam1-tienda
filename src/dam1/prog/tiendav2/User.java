package dam1.prog.tiendav2;

public class User {

  private final int ID;
  private String fullName;
  private boolean discount;
  private Order[] orderHistory;

  public User(int ID, String fullName, boolean discount,
      Order[] orderHistory) {
    this.ID = ID;
    this.fullName = fullName;
    this.discount = discount;
    this.orderHistory = orderHistory;
  }

  public int getID() {
    return ID;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public boolean hasDiscount() {
    return discount;
  }

  public void setDiscount(boolean discount) {
    this.discount = discount;
  }

  public Order[] getOrderHistory() {
    return orderHistory;
  }

  public void setOrderHistory(Order[] orderHistory) {
    this.orderHistory = orderHistory;
  }
}
