package dam1.prog.tiendav2;

public class Shoe {
  private final int ID;
  private String description;
  private String style;
  private double price;

  public Shoe(int ID, String description, String style, double price) {
    this.ID = ID;
    this.description = description;
    this.style = style;
    this.price = price;
  }

  public int getID() {
    return ID;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStyle() {
    return style;
  }

  public void setStyle(String style) {
    this.style = style;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }
}
