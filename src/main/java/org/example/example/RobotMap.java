package org.example.example;

import java.util.*;

public class RobotMap {

    private final int n;
    private final int m;

    private final int maxRobotCount;
    private final List<Robot> robots;

    public RobotMap(int n, int m) {
        this(n, m, 10);
    }

    public RobotMap(int n, int m, int maxRobotCount) {
        try {
            validateMapSize(n, m);
        } catch (PointValidationException e) {
            throw new RuntimeException(e);
        }
        this.n = n;
        this.m = m;
        this.maxRobotCount = maxRobotCount;
        this.robots = new ArrayList<>();
    }

    public Robot createRobot(Point point) throws RobotCreationException {
        final MapPoint robotPosition;
        try {
            validatePoint(point);
            validateRobot((ArrayList) robots);
            robotPosition = new MapPoint(point.getX(), point.getY());
        } catch (PointValidationException e) {
            throw new RobotCreationException(e.getMessage());
        }

        Robot robot = new Robot(robotPosition);
        robots.add(robot);
        return robot;
    }

    private void validateMapSize(int n, int m) throws PointValidationException {
        if (n <= 0 || m <= 0) {
            throw new PointValidationException("Введены некоректные данные");
        }
    }

    private void validateRobot(ArrayList robots) throws RobotCreationException {
        validateCountsOfRobots(robots);
    }

    private void validateCountsOfRobots(ArrayList robots) throws RobotCreationException {
        if (robots.size() >= maxRobotCount) {
            throw new RobotCreationException("Превышен лимит роботов" + maxRobotCount);
        }
    }

    private void validatePoint(Point point) throws PointValidationException {
        validatePointIsFree(point);
    }

    private void validatePointIsFree(Point point) throws PointValidationException {
        for (Robot robot : robots) {
            if (point.equals(robot.getPoint())) {
                throw new PointValidationException("Позиция " + point + " занята другим роботом: " + robot);
            }
        }
    }

    public class Robot {

        public static final Direction DEFAULT_DIRECTION = Direction.TOP;

        private final UUID id;
        private MapPoint point;
        private Direction direction;

        public Robot(MapPoint point) {
            this.id = UUID.randomUUID();
            this.point = point;
            this.direction = DEFAULT_DIRECTION;
        }

        public void move() throws RobotMoveException {
            move(1);
        }

        public void move(int x) throws RobotMoveException {
            final MapPoint newPoint;
            try {
                newPoint = switch (direction) {
                    case TOP -> new MapPoint(point.getX() - x, point.getY());
                    case RIGHT -> new MapPoint(point.getX(), point.getY() + x);
                    case BOTTOM -> new MapPoint(point.getX() + x, point.getY());
                    case LEFT -> new MapPoint(point.getX(), point.getY() - x);
                };

                validatePoint(newPoint);
            } catch (PointValidationException e) {
                throw new RobotMoveException(e.getMessage(), this);
            }

            this.point = newPoint;
        }

        public void changeDirection(Direction direction) {
            this.direction = direction;
        }

        public MapPoint getPoint() {
            return point;
        }

        @Override
        public String toString() {
            return "Robot-" + id + point;
        }
    }

    public class MapPoint extends Point {

        public MapPoint(int x, int y) throws PointValidationException {
            super(x, y);

            if (x < 0 || x > n || y < 0 || y > m) {
                throw new PointValidationException("Недопустимое значение Point: " + this);
            }
        }
    }

}
