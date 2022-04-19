package dam1.prog.tiendav2;

public class ShoeModel {

  private final int ID;
  private String description;
  private String style;
  private double price;
  private int availableUnits;

  public ShoeModel(int ID, String description, String style, double price) {
    this.ID = ID;
    this.description = description;
    this.style = style;
    this.price = price;
    this.availableUnits = 0;
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

  public int getAvailableUnits() {
    return availableUnits;
  }

  public void setAvailableUnits(int availableUnits) {
    this.availableUnits = availableUnits;
  }
}
