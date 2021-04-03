public abstract class GameObject {

  //Meta fields
  public boolean deleteable;

  //Position fields
  protected IVector position;
  protected PVector render_position;
  protected Direction facing_direction; //Field describes which direction the GameObject is currently facing.

  //Combat fields
  protected int health; //Variable describes the GameObjects current health.
  protected int health_max; //Variable describes how much health the GameObject maximum can have.
  protected int strength; //Variable describes how much damage the GameObject can deal.
  private boolean healable; //Variable used to dictate of the GameObject can heal.
  private boolean teamCertain;
  private boolean raceCertain;

  //Team & Identification fields
  public final Race race;
  public Team team;

  //Class Constructor
  public GameObject(int x, int y, int hp, int str, Race race, Team team) {
    this.position = new IVector(x, y);
    this.render_position = position.toPVector();
    this.healable = true;
    this.health = hp;
    this.health_max = hp;
    this.strength = str;
    this.race = race;
    this.team = team;
    this.teamCertain = true;
    this.raceCertain = true;
    this.deleteable = true;
  }
  //Method that returns of the GameObject is alive.
  public boolean isAlive() {
    return health > 0;
  }
  //Method for healing the GameObject.
  public void heal(int healingPoints) {
    if (this.healable) {
      this.health += healingPoints;
      this.health = constrain(this.health, 0, this.health_max);
    }
  }
  //Method for reducing the GameObject's health.
  public void takeDamage(int damagePoints) {
    this.health -= damagePoints;
  }

  //Method that makes a given GameObject take damage;
  //GameObject Will not attack if same race or team by default.
  public void attack(GameObject target) {
    if (this.isAlive()) {
      if ((!teamCertain || this.team != target.team) && (!raceCertain || this.race != target.race)) {
        target.takeDamage(this.strength);
        this.onAttack(target);
      }
    }
  }

  //Method that walks in a given direction.
  public boolean walkInDirection(Direction direction) {
    //Calculate
    IVector newPosition = this.position.copy();
    newPosition.x += direction.x;
    newPosition.y += direction.y;
    return moveTo(newPosition);
  }

  //Method for moving the GameObject to a position or attacking if moving is not possible.
  //Method returns if the player successfully performed the action.
  public boolean moveOrAttack(IVector newPosition) {
    if (tileIsOccupied(newPosition) && !tileIsOccupiedBySolid(newPosition)) {
      GameObject target = objects.getObjectAt(newPosition);
      if (target != null) {
        this.attack(target);
        return true;
      } else {
        return false;
      }
    } else {
      return moveTo(newPosition);
    }
  }
  public boolean moveOrAttack(Direction direction) {
    IVector newPosition = this.position.copy();
    newPosition.x += direction.x;
    newPosition.y += direction.y;

    if (tileIsOccupied(newPosition) && !tileIsOccupiedBySolid(newPosition)) {
      GameObject target = objects.getObjectAt(newPosition);
      if (target != null) {
        this.attack(target);
        return true;
      } else {
        return false;
      }
    } else {
      return moveTo(newPosition);
    }
  }

  //Method that moves the GameObject in a direction if possible.
  //Method returns if the player successfully performed the action.
  public boolean moveTo(IVector newPosition) {
    if (this.canMoveTo(newPosition)) { //Can move to new position.
      this.position = newPosition;
      return true;
    } else { //Can't move to new position.
      return false;
    }
  }

  //method that returns if the GameObject can move to the given position
  public boolean canMoveTo(IVector position) {
    return !tileIsOccupied(position);
  }

  public void kill() {
    this.health = 0;
    this.healable = false;
  }

  private void onAttack(GameObject target) {
    render_position.add(target.position.toPVector()).div(2);
  }
  //---Getters and Setters---//
  public int getHealth() {
    return this.health;
  }

  //---Abstract methods---//
  public abstract int getStrength();
  public abstract int getArmor();
  public abstract void update();
  public abstract void draw(float xOffset, float yOffset, float tileWidth, float tileHeight);

  //---Abstract "listeners"---//
  public abstract void onDeath(); //Should be called once when the GameObject dies.
  public abstract void onTurnBegin(int turnCount); //Should be called first and once when a turn ends.
  public abstract void onTurn(int turnCount); //Should be called once between onTurnBegin and onTurnEnd is called.
  public abstract void onTurnEnd(int turnCount); //Should be called last and once when a turn ends.
}