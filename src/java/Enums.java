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
public enum Direction {
  NORTH("North", 0, -1), 
    NORTH_EAST("North-east", 1, -1), 
    NORTH_WEST("North-west", -1, -1), 
    EAST("East", 1, 0), 
    WEST("West", -1, 0), 
    SOUTH("South", 0, 1), 
    SOUTH_EAST("South-east", 1, 1), 
    SOUTH_WEST("South-west", -1, 1);

  public final float x, y;
  public final String name;
  private Direction(String name, int x, int y) {
    this.name = name;
    this.x = x;
    this.y = y;
  }
}
public enum Team {
  Player, Enemy
}

public enum Material {
  WOOD("Wood", 1, #663300), 
    IRON("Iron", 2, #999999), 
    COBALT("Cobalt", 3, #6666ff),
    HELLRITE("Hellrite", 4, #ff6666);

  public final String name;
  public final color colour;
  public final int effeciency;

  private Material(String name, int effeciency, color colour) {
    this.name = name;
    this.effeciency = effeciency;
    this.colour = colour;
  }
}