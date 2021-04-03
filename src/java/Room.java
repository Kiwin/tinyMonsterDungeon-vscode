public interface Room {
    public int getWidth();
    public int getHeight();
    
    public boolean addObject(GameObject object);
    public boolean removeObject(GameObject object);
}

public class BaseRoom { // implements Room {
    
    private final int width;
    private final int height;

    public BaseRoom(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }
}