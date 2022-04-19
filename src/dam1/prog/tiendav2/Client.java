package dam1.prog.tiendav2;

public class Client {

  private final int ID;
  private String fullName;
  private boolean discount;

  public Client(int ID, String fullName, boolean discount) {
    this.ID = ID;
    this.fullName = fullName;
    this.discount = discount;
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
}
