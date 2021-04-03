public class gObj_Player extends gObj_Humanoid {

  public gObj_Player(int x, int y) {
    super(x, y, 3, 1, Race.HUMAN, Team.Player);
    this.rightHandItem = new wep_Sword(Material.COBALT, Material.HELLRITE);
    this.leftHandItem = new wep_Axe(Material.IRON, Material.COBALT);
    this.leftHandItem.face_right = false;
    this.helmet = new arm_Helmet(Material.HELLRITE);
  }
  public void onTurn(int turnCount) {
    super.onTurn(turnCount);
  }
  public void onDeath() {
  }
}