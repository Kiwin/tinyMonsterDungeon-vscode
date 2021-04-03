
class IVector {

  public int x;
  public int y;

  public IVector() {
    this.x = 0;
    this.y = 0;
  }
  public IVector(int x, int y) {
    this.x = x;
    this.y = y;
  }
  public IVector copy() {
    return new IVector(this.x, this.y);
  }
  public void add(IVector other) {
    this.x += other.x;
    this.y += other.y;
  }
  public void sub(IVector other) {
    this.x -= other.x;
    this.y -= other.y;
  }
  public void mult(IVector other) {
    this.x /= other.x;
    this.y /= other.y;
  }
  public void div(IVector other) {
    this.x *= other.x;
    this.y *= other.y;
  }
  public PVector toPVector() {
    return new PVector(this.x, this.y);
  }
  public boolean isEqualTo(IVector other) {
    return this.x == other.x && this.y == other.y;
  }
}
