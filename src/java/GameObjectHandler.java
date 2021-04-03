public class GameObjectHandler extends ArrayList<GameObject> {

  //Class Constructor.
  public GameObjectHandler() {
  }

  //Method that updates all objects in the list.
  public void update() {
    for (GameObject obj : this) {
      obj.update();
    }
  }

  //Method that deletes all dead objects from the list.
  //It also calls the objects onDeath() method before deleting it.
  public void garbageCollect() {
    /***
     The for-loop loops through the object list backwards to avoid 
     error from the elements inside the list shuffling around when trying to access them..
     ***/
    for (int i = this.size()-1; i >= 0; i--) { 
      GameObject obj = this.get(i);
      if (!obj.isAlive() && obj.deleteable) {
        obj.onDeath();
        this.remove(obj);
      }
    }
  }

  //Method that draws all objects in the list.
  public void draw(float xOffset, float yOffset, float tileWidth, float tileHeight) {
    for (GameObject obj : this) {
      obj.draw(xOffset, yOffset, tileWidth, tileHeight);
    }
  }
  //Method that calls all objects in the lists onTurn method.
  public void onTurnBegin(int turnCount) {
    for (GameObject obj : this) {
      obj.onTurnBegin(turnCount);
    }
    garbageCollect();
  }
  public void onTurn(int turnCount) {
    for (GameObject obj : this) {
      obj.onTurn(turnCount);
    }
    garbageCollect();
  }
  public void onTurnEnd(int turnCount) {
    for (GameObject obj : this) {
      obj.onTurnEnd(turnCount);
    }
    garbageCollect();
  }

  //Method that *!TRIES!* to return the object at given position
  //If it fails it will return 'null'
  public GameObject getObjectAt(IVector position) {
    for (GameObject obj : this) {
      if (obj.position.isEqualTo(position)) {
        return obj;
      }
    }
    return null;
  }
}