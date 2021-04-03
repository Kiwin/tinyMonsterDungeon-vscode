//Simple class representing a tile.
public class Tile {

  public int type;
  public boolean isSolid;

  public Tile(int type) {
    this.type = type;
    isSolid = false;
  }

  public Tile(int type, boolean isSolid) {
    this.type = type;
    this.isSolid = isSolid;
  }

  public void onTurn() {
    //The main object does nothing.
  }
}