  //Meta fields
  public boolean GAMEOVER;
  public boolean VICTORY;
  
  //Mapping fields
  private final int mapWidth = 9, mapHeight = 9;
  private final float marginPercent = 0.2;
  private final color[] tileColors = new color[] {0, 64, 96}; //{0, #ff6666, #66ff66, #6666ff, #ffff66, #66ffff};
  private TileMap map;
  private TileMapRenderer mapRenderer;
  
  //Turn-based fields
  private int turnCount;
  
  //Render fields
  private float render_width;
  private float render_height;
  private float x, y, w, h; //x:mapOffsetX, y:mapOffsetY, w:mapDisplayWidth, h:mapDisplayHeight
  private float tileWidth; //Variable that describes the width of a tile;
  private float tileHeight; //Variable that describes the height of a tile;
  
  //Entity fields
  private gObj_Player player;
  private RoomDecorator roomDecorator;
  private GameObjectHandler objects;
  
  //Method that pre-processes stuff, setting settings and such.
  public void setup() {
    //Settings
    size(800, 800);
  
    smooth(12);
    ellipseMode(CORNER);
    //strokeWeight(min(tileWidth, tileHeight)*0.014);
    //stroke(0);
    noStroke();
    //Initialize.
    initialize();
  }
  
  //Method that initializes the map, objects and etc.
  public void initialize() {
    //Variables
    calculateDisplayVariables();
    turnCount = 0;
    GAMEOVER = false;
    VICTORY = false;
    //Map
    map = new TileMap(mapWidth, mapHeight);
    //map.randomizeMap(1, tileColors.length-1);
    map.fillMapTiled(1, 2);
    map.fillMapEgdes(0, true);

    mapRenderer = new TileMapRenderer();
  
    //Entities
    objects = new GameObjectHandler();
    player = new gObj_Player(1, 1);
    objects.add(player);

    roomDecorator = new RoomDecorator();
    roomDecorator.spawnRandOrcsEnemies(map, objects);
  }
  
  //Method that calculates where to display the map in the screen.
  public void calculateDisplayVariables() {
    float render_width = width;
    float render_height = height;
  
    float smallestDimension = min(render_width, render_height);
    /***
     Here we are creating a margin effect for the displayed map
     by scaling down the map display area accordingly to the 'marginPercent' variable,
     and afterwards calculating the offsets accordingly to the map display dimensions. 
     ***/
    w = smallestDimension*(1-marginPercent);
    h = w;
    x = (render_width-w)/2;
    y = (render_height-h)/2;
  
    float tileSize = min(w/mapWidth, h/mapHeight);
    tileWidth = tileSize;
    tileHeight = tileSize;
  }
  //returns if the tile value is in range of the map-dimensions.
  public boolean tileIsValid(IVector tilePosition) {
    return tilePosition.x >= 0 && tilePosition.x < mapWidth && tilePosition.y >= 0 && tilePosition.y < mapHeight;
  }
  //Method that return if a tile is occupied by either an object or a solid tile.
  public boolean tileIsOccupied(IVector tilePosition) {
    return tileIsOccupiedBySolid(tilePosition) || tileIsOccupiedByObject(tilePosition);
  }
  //Method that return if a tile is occupied by a solid tile.
  public boolean tileIsOccupiedBySolid(IVector tilePosition) {
    return tileIsValid(tilePosition) && map.getTile(tilePosition.x, tilePosition.y).isSolid == true;
  }
  //Method that return if a tile is occupied by an object.
  public boolean tileIsOccupiedByObject(IVector tilePosition) {
    if (tileIsValid(tilePosition))
    {
      for (GameObject obj : objects) {
        if (obj.position.isEqualTo(tilePosition)) {
          return true;
        }
      }
    }
    return false;
  }
  
  //Method that draws and updates the map, objects, ect.
  public void draw() {
    //Update
    objects.update();
  
    //Draw
    background(64);
    mapRenderer.draw(map,x, y, tileWidth, tileHeight, tileColors);
    objects.draw(x, y, tileWidth, tileHeight);
    drawHealthBar(tileWidth*0.2, tileHeight*0.2, tileWidth*0.8, tileHeight*0.8);
    //println(frameRate);
  }
  
  public void drawHealthBar(float x, float y, float w, float h) {
    float w2 = w*0.9;
    pushMatrix();
    translate(x, y);
  
    for (int i = 0; i < player.getHealth(); i++) {
      //Draw Hearts
      float x2 = w2*i;
      if (i == player.getHealth()-1 && player.getArmor() == 0) {
        float n = sin(frameCount*0.1)*30;
        fill(164+n, n, n);
      } else {
        fill(#880000);
      }
      beginShape();
      vertex(x2+w*0.25, 0);
      vertex(x2+w*0.5, h*0.25);
      vertex(x2+w*0.75, 0);
      vertex(x2+w, h*0.50);
      vertex(x2+w*0.5, h);
      vertex(x2, h*0.50);
      endShape(CLOSE);
    }
  
    //Draw Shields
    for (int i = player.getHealth(); i < player.getHealth()+player.getArmor(); i++) {
      float x2 = w2*i;
      float c = 120+i*20;
      if (i == player.getHealth()+player.getArmor()-1) {
        float n = sin(frameCount*0.1)*15;
        c+= n;
      }
      fill(c);
      beginShape();
      vertex(x2+w*0.5, 0);
      vertex(x2+0, h*0.5);
      vertex(x2+w*0.5, h);
      vertex(x2+w, h*0.5);
      endShape(CLOSE);
    }
    popMatrix();
  }
  public void onTurn() {
    if (!GAMEOVER) {
      objects.onTurnBegin(turnCount);
      objects.onTurn(turnCount);
      objects.onTurnEnd(turnCount);
      turnCount++;
      if (!player.isAlive()) {
        GAMEOVER = true;
      } else {
        if (objects.size() == 1) {
          VICTORY = true;
        }
      }
    }
  }
  //---Controls methods---//
  void mousePressed() {
  }
  void mouseReleased() {
  }
  void mouseClicked() {
  }
  void mouseWheel(MouseEvent me) {
  }
  void keyPressed() { 
    switch(key) {
    case 'n':
      initialize();
      break;
    }
    if (!GAMEOVER && !VICTORY) {
      boolean actionPerformed = false;
      switch(key) {
      case 'w':
        actionPerformed = player.moveOrAttack(Direction.NORTH);
        break;
      case 's':
        actionPerformed = player.moveOrAttack(Direction.SOUTH);
        break;
      case 'a':
        actionPerformed = player.moveOrAttack(Direction.WEST);
        break;
      case 'd':
        actionPerformed = player.moveOrAttack(Direction.EAST);
        break;
      }
      if (actionPerformed) {
        onTurn();
      }
    }
  }
  void keyReleased() {
  }
  void keyTyped() {
  }