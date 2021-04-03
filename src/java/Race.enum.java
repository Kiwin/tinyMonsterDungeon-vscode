enum Race {
  HUMAN("Human", #ffdbac), ORC("Orc", #79C879);

  //Private Enum Constructor.
  public final String name;
  public final color skinColor;
  private Race(String name, color skinColor) {
    this.name = name;
    this.skinColor = skinColor;
  }
}