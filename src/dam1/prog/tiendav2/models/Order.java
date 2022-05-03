package dam1.prog.tiendav2.models;

public class Order {

  private int ID;
  private int ownerID;
  private String status;
  private Shoe[] productList;

  public Order(){
    this(-1,-1,"");
  }

  public Order(int ID, int ownerID, String status) {
    this.ID = ID;
    this.ownerID = ownerID;
    this.status = status;
    this.productList = new Shoe[0];
  }

  public Order(int ID, int ownerID, String status, Shoe[] productList) {
    this.ID = ID;
    this.ownerID = ownerID;
    this.status = status;
    this.productList = productList;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
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

  public Shoe[] getProductList() {
    return productList;
  }

  public void setProductList(Shoe[] productList) {
    this.productList = productList;
  }
}

