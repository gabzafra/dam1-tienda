package dam1.prog.tiendav2.models;


public class Shoe {

  private int modelId;
  private int desiredUnits;

  public Shoe(){
    this(-1,1);
  }

  public Shoe(int modelId, int desiredUnits) {
    this.modelId = modelId;
    this.desiredUnits = desiredUnits;
  }

  public int getModelId() {
    return modelId;
  }

  public void setModelId(int modelId) {
    this.modelId = modelId;
  }

  public int getDesiredUnits() {
    return desiredUnits;
  }

  public void setDesiredUnits(int desiredUnits) {
    this.desiredUnits = desiredUnits;
  }
}
