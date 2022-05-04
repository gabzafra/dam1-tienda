package dam1.prog.tiendav2.models;

import java.util.ArrayList;

public class Order {

  private int ID;
  private int ownerID;
  private String status;
  private ArrayList<Shoe> productList;

  public Order(){
    this(-1,"");
  }

  public Order(int ownerID, String status) {
    this(-1,ownerID,status, new ArrayList<Shoe>());
  }

  public Order(int ID, int ownerID, String status, ArrayList<Shoe> productList) {
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

  public ArrayList<Shoe> getProductList() {
    return productList;
  }

  public void setProductList(ArrayList<Shoe> productList) {
    this.productList = productList;
  }
}

