package robot;

import java.util.ArrayList;
import java.util.List;

import static robot.Direction.*;
import static robot.Instruction.*;

public class RoadBookCalculator {

    static RoadBook calculateRoadBook(Direction direction, Coordinates position, Coordinates destination, ArrayList<Instruction> instructions) {
        List<Direction> directionList = new ArrayList<Direction>();
        if (destination.getX() < position.getX())
            directionList.add(WEST);
        if (destination.getX() > position.getX())
            directionList.add(Direction.EAST);
        /*
            Le repère est à 0,0 en haut à gauche (voir specs)
            -> si la destination est plus haute que la position,
            on doit aller au NORTH pas au SOUTH. et inversément
            C'est cohérent avec les changements faits à MapTools.java
            basés sur le même motif
        if (destination.getY() < position.getY()) directionList.add(Direction.SOUTH);
        if (destination.getY() > position.getY()) directionList.add(Direction.NORTH);
        */
        if (destination.getY() > position.getY())
            directionList.add(Direction.SOUTH);
        if (destination.getY() < position.getY())
            directionList.add(Direction.NORTH);
        if (directionList.isEmpty())
            return new RoadBook(instructions);
        if (directionList.contains(direction)) {
            instructions.add(FORWARD);
            return calculateRoadBook(direction, MapTools.nextForwardPosition(position, direction), destination, instructions);
        } else {
            instructions.add(TURNRIGHT);
            return calculateRoadBook(MapTools.clockwise(direction), position, destination, instructions);
        }
    }
}