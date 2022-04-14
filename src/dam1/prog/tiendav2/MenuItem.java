package dam1.prog.tiendav2;

public record MenuItem(int optionNumber, String optionLabel) {

  public int getOptionNumber() {
    return optionNumber;
  }

  public String getOptionLabel() {
    return optionLabel;
  }
}
