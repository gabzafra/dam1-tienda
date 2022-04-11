package dam1.prog.tiendav2;

public class User {

  private final int ID;
  private String fullName;
  private boolean discount;
  private int[] orderHistory;

  public User(int ID, String fullName, boolean discount,
      int[] orderHistory) {
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

  public int[] getOrderHistory() {
    return orderHistory;
  }

  public void setOrderHistory(int[] orderHistory) {
    this.orderHistory = orderHistory;
  }
}
