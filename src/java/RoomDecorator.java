public class RoomDecorator {
    public void spawnRandOrcsEnemies(TileMap tileMap, GameObjectHandler objecthandler){
        for (int i = 0; i < 15; i++) {
            IVector position = new IVector();
            do {
                position.x = round(random(0, mapWidth-1));
                position.y = round(random(0, mapHeight-1));
            } while (tileIsOccupied(position));
            objecthandler.add(new gObj_Orc(position.x, position.y));
        }
    }
}