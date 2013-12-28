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
            Version originale du code :
           =============================
        if (destination.getY() < position.getY()) directionList.add(Direction.SOUTH);
        if (destination.getY() > position.getY()) directionList.add(Direction.NORTH);

            Justification des modifications :
           ===================================
            Le repère est à 0,0 en haut à gauche (voir specs)
                    -> si la destination est plus haute que la position,
            on doit aller au NORTH pas au SOUTH. et inversément
            C'est cohérent avec les changements faits à MapTools.java
            basés sur le même motif
         */
        if (destination.getY() > position.getY())
            directionList.add(Direction.SOUTH);
        if (destination.getY() < position.getY())
            directionList.add(Direction.NORTH);

        if (directionList.isEmpty())
            return new RoadBook(instructions);
        /*
            Version originale du code :
            =============================
        if (directionList.contains(direction)) {

            Justification des modifications :
            ===================================
            Il faut que ça soit la dernière direction de la liste qui match avec la direction actuelle
        */
        if (directionList.get(directionList.size() - 1) == direction) {
            instructions.add(FORWARD);
            return calculateRoadBook(direction, MapTools.nextForwardPosition(position, direction), destination, instructions);
        } else {
            /*
                Version originale du code :
                =============================
            instructions.add(TURNRIGHT);
            return calculateRoadBook(MapTools.clockwise(direction), position, destination, instructions);

                Justification des modifications :
                ===================================
                La rotation se faisait toujours à droite -> ajout if/else pour utiliser la rotation la plus optimisée
            */
            if((4 + direction.ordinal() - directionList.get(directionList.size() - 1).ordinal()) % 4 == 3)
            {
                instructions.add(TURNLEFT);
                return calculateRoadBook(MapTools.counterclockwise(direction), position, destination, instructions);
            }
            else
            {
                instructions.add(TURNRIGHT);
                return calculateRoadBook(MapTools.clockwise(direction), position, destination, instructions);
            }
        }
    }
}