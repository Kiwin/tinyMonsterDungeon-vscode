public abstract class Item {

  public final String name;
  public boolean face_right;

  public Item(String name) {
    this.name = name;
    face_right = true;
  }
  public abstract void draw(float x, float y, float w, float h);
  public void use(GameObject caster) {
    this.onUse(caster);
  }
  protected abstract void onUse(GameObject caster);
}

public abstract class itm_Shield extends itm_Tool {
  public itm_Shield(String name, Material material_primary, Material material_secondary) {
    super(name, material_primary, material_secondary);
  }
}
public abstract class itm_Tool extends Item {

  public final Material material_primary;
  public final Material material_secondary;
  protected int durability;
  public itm_Tool(String name, Material material_primary, Material material_secondary) {
    super(name);
    this.material_primary = material_primary;
    this.material_secondary = material_secondary;
    this.durability = material_primary.effeciency;
  }
  public boolean isBroken() {
    return this.durability <= 0;
  }
}
public abstract class itm_Armor extends Item {
  private int durability;
  public final Material material_primary;
  public itm_Armor(String name, Material material_primary) {
    super(name);
    this.material_primary = material_primary;
    this.durability = material_primary.effeciency;
  }
  public boolean isBroken() {
    return this.durability <= 0;
  }
  public int getArmor() {
    return this.durability;
  }

  public int blockDamage(int damage) {
    if (!this.isBroken()) {
      if (damage >= this.durability) {
        this.durability = 0;
        return damage - this.durability;
      } else {
        this.durability -= damage;
        return 0;
      }
    }
    return damage;
  }
}
public abstract class itm_Weapon extends itm_Tool {

  protected final Material material_secondary;

  public itm_Weapon(String name, Material material_primary, Material material_secondary) {
    super(name, material_primary, material_secondary);
    this.material_secondary = material_secondary;
  }
  public int getStrength() {
    return this.material_secondary.effeciency;
  }
}

public class arm_Helmet extends itm_Armor {
  public arm_Helmet(Material material_primary) {
    super("Helmet", material_primary);
  }
  public void draw(float x, float y, float w, float h) {
    fill(this.material_primary.colour);
    float helmetX = x-w*0.02;
    float helmetY = y-h*0.05;
    float helmetW = w*1.04;
    float helmetH = h*0.33;
    rect(helmetX, helmetY, helmetW, helmetH);
    //rect(helmetX+helmetW*, helmetY, helmetW, helmetH);
  }
  public void onUse(GameObject caster) {
  }
}
public class arm_Chestplate extends itm_Armor {

  public arm_Chestplate(Material material_primary) {
    super("Chestplate", material_primary);
  }
  public void draw(float x, float y, float w, float h) {
  }
  public void onUse(GameObject caster) {
  }
}

public class wep_Sword extends itm_Weapon {

  public wep_Sword(Material material_primary, Material material_secondary) {
    super("Sword", material_primary, material_secondary);
  }
  public void draw(float x, float y, float tileWidth, float tileHeight) {
    pushMatrix();
    translate(x, y);
    float swordBladeWidth = tileWidth*0.05;
    float swordBladeHeight = tileHeight*0.3;
    float swordParryWidth = tileWidth*0.15;
    float swordParryHeight = tileHeight*0.05;
    float swordHandleWidth = tileWidth*0.05;
    float swordHandleHeight = tileHeight*0.1;

    //Draw blade
    fill(material_secondary.colour);
    rect(-swordBladeWidth/2, 0, swordBladeWidth, -swordBladeHeight);
    //Draw Parry
    rect(-swordParryWidth/2, 0, swordParryWidth, swordParryHeight);
    //Draw Handle
    fill(material_primary.colour);
    rect(-swordHandleWidth/2, swordParryHeight, swordHandleWidth, swordHandleHeight);

    popMatrix();
  }
  public void onUse(GameObject caster) {
  }
}
public class wep_Axe extends itm_Weapon {

  public wep_Axe(Material material_primary, Material material_secondary) {
    super("Axe", material_primary, material_secondary);
  }
  public void draw(float x, float y, float tileWidth, float tileHeight) {
    float axeHandleWidth = tileWidth*0.05;
    float axeHandleHeight = tileHeight*0.3;
    float axeHeadWidth = tileWidth*0.1;
    float axeHeadHeight = tileHeight*0.15;


    pushMatrix();
    translate(x, y);
    if (!face_right) {
      scale(-1, 1);
    }
    //Draw Handle
    fill(material_primary.colour);
    rect(-axeHandleWidth/2, -axeHandleHeight/3, axeHandleWidth, axeHandleHeight);
    //Draw Head
    fill(material_secondary.colour);
    beginShape();
    vertex(0, -axeHandleHeight/3);
    vertex(axeHeadWidth, axeHeadHeight-axeHandleHeight/3);
    vertex(axeHeadWidth, -axeHandleHeight/3);
    endShape(CLOSE);

    popMatrix();
  }
  public void onUse(GameObject caster) {
  }
}

public class shd_Shield extends itm_Shield {
  public shd_Shield(Material material_primary, Material material_secondary) {
    super("Shield", material_primary, material_secondary);
  }
  public void draw(float x, float y, float tileWidth, float tileHeight) {
    float shieldWidth = tileWidth*0.3;
    float shieldHeight = tileHeight*0.3;
    float shieldKnotWidth = tileWidth*0.1;
    float shieldKnotHeight = tileHeight*0.1;

    pushMatrix();
    translate(x, y);
    fill(material_primary.colour);
    ellipse(-shieldWidth*0.5, -shieldHeight*0.5, shieldWidth, shieldHeight);
    fill(material_secondary.colour);
    ellipse(-shieldKnotWidth*0.5, -shieldKnotHeight*0.5, shieldKnotWidth, shieldKnotHeight);
    popMatrix();
  }
  public void onUse(GameObject caster) {
  }
}