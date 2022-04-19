package dam1.prog.tiendav2;


public class Shoe {

  private final int modelId;
  private int desiredUnits;

  public Shoe(int modelId, int desiredUnits) {
    this.modelId = modelId;
    this.desiredUnits = desiredUnits;
  }

  public int getModelId() {
    return modelId;
  }

  public int getDesiredUnits() {
    return desiredUnits;
  }

  public void setDesiredUnits(int desiredUnits) {
    this.desiredUnits = desiredUnits;
  }
}
