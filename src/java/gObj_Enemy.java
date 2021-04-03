public class gObj_Orc extends gObj_Humanoid {

  private final int moveTurnOffset;

  public gObj_Orc(int x, int y) {
    super(x, y, 1, 1, Race.ORC, Team.Enemy);
    moveTurnOffset = round(random(1));
    float ran = random(1);
    if (ran <= 0.25) {
      this.helmet = new arm_Helmet(Material.WOOD);
    } else if (ran <= 0.40) {
      this.helmet = new arm_Helmet(Material.IRON);
    } else if (ran <= 0.50) {
      this.helmet = new arm_Helmet(Material.COBALT);
    }
    rightHandItem = new wep_Sword(Material.WOOD, Material.WOOD);
    if (leftHandItem != null) {
      leftHandItem.face_right = false;
    }
  }
  @Override
    public void onTurn(int turnCount) {
    super.onTurn(turnCount);
    if ((turnCount+moveTurnOffset)%2==0) { //Checks if should move this round.
      int checks = 0;
      Direction direction;
      do {
        direction = Direction.values()[round(random(Direction.values().length-1))];
        checks++;
      } while (!this.moveOrAttack(direction) && checks < 20);
    }
  }
  public void onDeath() {
  }
}