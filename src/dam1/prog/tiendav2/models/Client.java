package dam1.prog.tiendav2.models;

public class Client {

  private int ID;
  private String fullName;
  private boolean discount;


  public Client() {
    this("", false);
  }

  public Client(String fullName, boolean discount) {
    this(-1, fullName, discount);
  }

  public Client(int ID, String fullName, boolean discount) {
    this.ID = ID;
    this.fullName = fullName;
    this.discount = discount;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
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
