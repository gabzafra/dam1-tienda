package dam1.prog.tiendav2;

public class Order {
  private final int ID;
  private int ownerID;
  private String status;

  public Order(int ID, int ownerID, String status) {
    this.ID = ID;
    this.ownerID = ownerID;
    this.status = status;
  }

  public int getID() {
    return ID;
  }

  public int getOwnerID() {
    return ownerID;
  }

  public void setOwnerID(int ownerID) {
    this.ownerID = ownerID;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}

