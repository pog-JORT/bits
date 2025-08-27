package main;

import entity.Entity;
import object.SuperObject;

public class CollisionChecker {

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity){

        int entityLeftWorldX = entity.worldX + entity.bounds.x;
        int entityRightWorldX = entity.worldX + entity.bounds.x + entity.bounds.width;
        int entityTopWorldY = entity.worldY + entity.bounds.y;
        int entityBottomWorldY = entity.worldY + entity.bounds.y + entity.bounds.height;

        int entityLeftCol = entityLeftWorldX/ gamePanel.tileSize;
        int entityRightCol = entityRightWorldX/ gamePanel.tileSize;
        int entityTopRow = entityTopWorldY/ gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY/ gamePanel.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                if(gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
                if(gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[entityLeftCol][entityBottomRow];
                if(gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNumber[entityRightCol][entityBottomRow];
                if(gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision){
                    entity.collisionOn = true;
                }
                break;

        }
    }

    public int checkObject(Entity entity, boolean player){
        int index = 999, idx = 0;

        for(SuperObject object: gamePanel.objects){

            if (object != null){
                // get entity's solid area position

                entity.bounds.x = entity.worldX + entity.bounds.x;
                entity.bounds.y = entity.worldY + entity.bounds.y;

                // get the objects solid area position

                object.bounds.x = object.worldX + object.bounds.x;
                object.bounds.y = object.worldY + object.bounds.y;

                switch(entity.direction){
                    case "up":
                        entity.bounds.y -= entity.speed;
                        if (entity.bounds.intersects(object.bounds)){
                            if(object.collision){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = idx;
                            }
                        }
                        break;
                    case "down":
                        entity.bounds.y += entity.speed;
                        if (entity.bounds.intersects(object.bounds)){
                            if(object.collision){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = idx;
                            }
                        }
                        break;
                    case "left":
                        entity.bounds.x -= entity.speed;
                        if (entity.bounds.intersects(object.bounds)){
                            if(object.collision){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = idx;
                            }
                        }
                        break;
                    case "right":
                        entity.bounds.x += entity.speed;
                        if (entity.bounds.intersects(object.bounds)){
                            if(object.collision){
                                entity.collisionOn = true;
                            }
                            if(player){
                                index = idx;
                            }
                        }
                        break;
                }
                entity.bounds.x = entity.boundsDefaultX;
                entity.bounds.y = entity.boundsDefaultY;

                object.bounds.x = object.boundsDefaultX;
                object.bounds.y = object.boundsDefaultY;

                if (index != 999) break;

            }
            idx++;

        }

        return index;
    }


    // npc or monster collision
    public int checkEntity(Entity entity, Entity[] entities){
        int index = 999, idx = 0;

        for(Entity npc: entities){

            if (npc != null){
                // get entity's solid area position

                entity.bounds.x = entity.worldX + entity.bounds.x;
                entity.bounds.y = entity.worldY + entity.bounds.y;

                // get the objects solid area position

                npc.bounds.x = npc.worldX + npc.bounds.x;
                npc.bounds.y = npc.worldY + npc.bounds.y;

                switch(entity.direction){
                    case "up":
                        entity.bounds.y -= entity.speed;
                        if (entity.bounds.intersects(npc.bounds)){
                            entity.collisionOn = true;
                            index = idx;
                        }
                        break;
                    case "down":
                        entity.bounds.y += entity.speed;
                        if (entity.bounds.intersects(npc.bounds)){
                            entity.collisionOn = true;
                            index = idx;
                        }
                        break;
                    case "left":
                        entity.bounds.x -= entity.speed;
                        if (entity.bounds.intersects(npc.bounds)){
                            entity.collisionOn = true;
                            index = idx;
                        }
                        break;
                    case "right":
                        entity.bounds.x += entity.speed;
                        if (entity.bounds.intersects(npc.bounds)){
                            entity.collisionOn = true;
                            index = idx;
                        }
                        break;
                }
                entity.bounds.x = entity.boundsDefaultX;
                entity.bounds.y = entity.boundsDefaultY;

                npc.bounds.x = npc.boundsDefaultX;
                npc.bounds.y = npc.boundsDefaultY;

                if (index != 999) break;

            }
            idx++;

        }
        return index;
    }

    public void checkPlayer(Entity entity){
        // get entity's solid area position

        entity.bounds.x = entity.worldX + entity.bounds.x;
        entity.bounds.y = entity.worldY + entity.bounds.y;

        // get the objects solid area position

        gamePanel.player.bounds.x = gamePanel.player.worldX + gamePanel.player.bounds.x;
        gamePanel.player.bounds.y = gamePanel.player.worldY + gamePanel.player.bounds.y;

        switch(entity.direction){
            case "up":
                entity.bounds.y -= entity.speed;
                if (entity.bounds.intersects(gamePanel.player.bounds)){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entity.bounds.y += entity.speed;
                if (entity.bounds.intersects(gamePanel.player.bounds)){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entity.bounds.x -= entity.speed;
                if (entity.bounds.intersects(gamePanel.player.bounds)){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entity.bounds.x += entity.speed;
                if (entity.bounds.intersects(gamePanel.player.bounds)){
                    entity.collisionOn = true;
                }
                break;
        }
        entity.bounds.x = entity.boundsDefaultX;
        entity.bounds.y = entity.boundsDefaultY;

        gamePanel.player.bounds.x = gamePanel.player.boundsDefaultX;
        gamePanel.player.bounds.y = gamePanel.player.boundsDefaultY;
    }


}
