public class TileMap {

  public final int mapWidth; //Describes the amount of tiles on the horizontal axis. 
  public final int mapHeight; //Describes the amount of tiles on the vertical axis.
  protected Tile[][] map; //Two-dimensional array for storing Tile-Objects.

  //Class Constructor 
  public TileMap(int mapWidth, int mapHeight) {
    this.mapWidth = mapWidth;
    this.mapHeight = mapHeight;
    map = new Tile[mapWidth][mapHeight];
  }

  //Method that fills the map-array with Tile-Objects.
  public void fillMap(int type, boolean solid) {
    for (int i = 0; i < mapWidth; i++) {
      for (int j = 0; j < mapHeight; j++) {
        map[i][j] = new Tile(type, solid);
      }
    }
  }
  public void fillMapEgdes(int type, boolean solid) {
    for (int i = 0; i < mapWidth; i++) {
      map[i][0] = new Tile(type, solid);
      map[i][mapHeight-1] = new Tile(type, solid);
    }
    for (int i = 0; i < mapHeight; i++) {
      map[0][i] = new Tile(type, solid);
      map[mapWidth-1][i] = new Tile(type, solid);
    }
  }
  public void randomizeMap(int min, int max) {
    for (int i = 0; i < mapWidth; i++) {
      for (int j = 0; j < mapHeight; j++) {
        map[i][j] = new Tile(round(random(max-min)+min));
      }
    }
  }
  public void fillMapTiled(int type1, int type2) {

    for (int i = 0; i < mapWidth; i++) {
      for (int j = 0; j < mapHeight; j++) {
        map[i][j] = new Tile((i+j)%2==0?type1:type2);
      }
    }
  }
  public Tile getTile(int x, int y) {
    return map[x][y];
  }
}

public class TileMapRenderer {

  public void draw(TileMap tileMap, float xOffset, float yOffset, float tileWidth, float tileHeight, color[] tileColorPalette) {
    for (int i = 0; i < tileMap.mapWidth; i++) {
      for (int j = 0; j < tileMap.mapHeight; j++) {

        //Select tile
        Tile currentTile = tileMap.getTile(i, j);

        //Calculate tile position
        float tileX = xOffset+i*tileWidth;
        float tileY = yOffset+j*tileHeight;

        //Draw tile
        fill(tileColorPalette[currentTile.type]);
        rect(tileX, tileY, tileWidth, tileHeight);
      }
    }
  }
}