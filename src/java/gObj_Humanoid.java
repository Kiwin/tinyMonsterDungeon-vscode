public abstract class gObj_Humanoid extends GameObject {

  public itm_Tool rightHandItem;
  public itm_Tool leftHandItem;
  public arm_Helmet helmet;
  public arm_Chestplate chestplate;

  public gObj_Humanoid(int x, int y, int hp, int str, Race race, Team team) {
    super(x, y, hp, str, race, team);
    this.rightHandItem = null;
    this.leftHandItem = null;
  }

  public void draw(float xOffset, float yOffset, float tileWidth, float tileHeight) {
    //Calculate
    float x = xOffset+this.render_position.x*tileWidth;
    float y = yOffset+this.render_position.y*tileHeight-tileHeight*0.15;
    float n = frameCount*0.05;
    float normalShake = sin(n)*tileWidth*0.020;
    float victoryShake = sin(n*2)*tileWidth*0.1;
    float victoryHeadShake = sin(n*2)*tileWidth*0.05;
    boolean dance = GAMEOVER || VICTORY;
    float headX = x+tileWidth*0.4;
    float headY = y+tileWidth*0.35+(dance?victoryHeadShake:normalShake);
    float headW = tileWidth*0.2;
    float headH = tileWidth*0.2;
    //Body
    fill(255);
    rect(x+tileWidth*0.3, y+tileHeight*0.50, tileWidth*0.4, tileHeight*0.5);
    //Head
    fill(this.race.skinColor);
    rect(headX, headY, headW, headH);
    if (this.rightHandItem != null) {
      this.rightHandItem.draw(x+tileWidth*0.8, y+tileWidth*0.6-(dance?victoryShake:normalShake), tileWidth, tileHeight);
    }
    if (this.leftHandItem != null) {
      this.leftHandItem.draw(x+tileWidth*0.2, y+tileWidth*0.6-(dance?victoryShake:normalShake), tileWidth, tileHeight);
    }
    if (this.helmet != null) {
      this.helmet.draw(headX, headY, headW, headH);
    }
  }

  public void update() {
    this.render_position.lerp(this.position.toPVector(), 0.1);
    if (this.render_position.dist(this.position.toPVector()) < 0.01) {
      this.render_position = this.position.toPVector();
    }
  }

  public int getStrength() {
    int str = this.strength;
    if (this.rightHandItem != null && this.rightHandItem instanceof itm_Weapon) {
      str += ((itm_Weapon) this.rightHandItem).getStrength();
    }
    if (this.leftHandItem != null && this.leftHandItem instanceof itm_Weapon) {
      str += ((itm_Weapon) this.leftHandItem).getStrength();
    }
    return str;
  }
  public int getArmor() {
    int arm = 0;
    if (this.helmet != null) {
      arm += this.helmet.getArmor();
    }
    if (this.chestplate != null) {
      arm += this.chestplate.getArmor();
    }
    return arm;
  }

  @Override
    public void takeDamage(int damagePoints) {
    int dmg = damagePoints;
    if (this.helmet != null) {
      dmg = this.helmet.blockDamage(dmg);
    }
    if (this.chestplate != null) {
      dmg = this.chestplate.blockDamage(dmg);
    }
    if (dmg > 0) {
      this.health -= dmg;
      //onTakeDamage
    }
  }
  public void onTurnBegin(int turnCount) {
  }
  public void onTurn(int turnCount) {
  }
  public void onTurnEnd(int turnCount) {
    if (this.rightHandItem != null) {
      if (this.rightHandItem.isBroken()) {
        this.rightHandItem = null;
      }
    }
    if (this.leftHandItem != null) {
      if (this.leftHandItem.isBroken()) {
        this.leftHandItem = null;
      }
    }
    if (this.helmet != null) {
      if (this.helmet.isBroken()) {
        this.helmet = null;
      }
    }
    if (this.chestplate != null) {
      if (this.chestplate.isBroken()) {
        this.chestplate = null;
      }
    }
  }
}