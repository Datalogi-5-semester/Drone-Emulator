package sample;

import javafx.scene.shape.Arc;

public class Drone {
    Arc drone;
    double height = 20;
    double x;
    double y;
    int speed = 0;

    boolean onGround = true;

    public Drone(Arc drone) {
        this.x = drone.getLayoutX();
        this.y = drone.getLayoutY();
        this.drone = drone;
    }

    public void speedUp() {
        speed += 5;
    }

    public void speedDown() {
        if (speed >= 5)
            speed -= 5;
    }

    public void downRight() {
        this.x += speed;
        this.y += speed;
        drone.setLayoutX(x);
        drone.setLayoutY(y);
    }

    public void rightRight() {
        this.x += speed;
        drone.setLayoutX(x);
    }

    public void upRight() {
        this.x += speed;
        this.y -= speed;
        drone.setLayoutX(x);
        drone.setLayoutY(y);
    }

    public void upUp() {
        this.y -= speed;
        drone.setLayoutY(y);
    }

    public void upLeft() {
        this.x -= speed;
        this.y -= speed;
        drone.setLayoutX(x);
        drone.setLayoutY(y);
    }

    public void leftLeft() {
        this.x -= speed;
        drone.setLayoutX(x);
    }

    public void downLeft() {
        this.x -= speed;
        this.y += speed;
        drone.setLayoutX(x);
        drone.setLayoutY(y);
    }

    public void downDown() {
        this.y += speed;
        drone.setLayoutY(y);
    }

    public void goForward() {
        if (!onGround) {
            double angle = drone.getStartAngle();
            switch ((int) angle) {
                case 315 -> downDown();
                case 270 -> downLeft();
                case 225 -> leftLeft();
                case 180 -> upLeft();
                case 135 -> upUp();
                case 90 -> upRight();
                case 45 -> rightRight();
                case 0 -> downRight();
            }
        }
    }

    public void goBackwards() {
        if (!onGround) {
            double angle = drone.getStartAngle();
            switch ((int) angle) {
                case 315 -> upUp();
                case 270 -> upRight();
                case 225 -> rightRight();
                case 180 -> downRight();
                case 135 -> downDown();
                case 90 -> downLeft();
                case 45 -> leftLeft();
                case 0 -> upLeft();
            }
        }
    }

    public void goLeft() {
        if (!onGround) {
            double angle = drone.getStartAngle();
            switch ((int) angle) {
                case 315 -> rightRight();
                case 270 -> downRight();
                case 225 -> downDown();
                case 180 -> downLeft();
                case 135 -> leftLeft();
                case 90 -> upLeft();
                case 45 -> upUp();
                case 0 -> upRight();
            }
        }
    }

    public void goRight() {
        if (!onGround) {
            double angle = drone.getStartAngle();
            switch ((int) angle) {
                case 315 -> leftLeft();
                case 270 -> upLeft();
                case 225 -> upUp();
                case 180 -> upRight();
                case 135 -> rightRight();
                case 90 -> downRight();
                case 45 -> downDown();
                case 0 -> downLeft();
            }
        }
    }

    public void takeOff() {
        if (drone.getRadiusX() == 20) {
            this.height = this.height + 20;
            drone.setRadiusX(height);
            drone.setRadiusY(height);
            onGround = false;
        }
    }

    public void heightUp() {
        if (drone.getRadiusX() >= 40) {
            this.height = this.height + 20;
            drone.setRadiusX(height);
            drone.setRadiusY(height);
        }
    }

    public void heightDown() {
        if (drone.getRadiusX() >= 40) {
            this.height = this.height - 20;
            drone.setRadiusX(height);
            drone.setRadiusY(height);
        }
    }

    public void land() {
        drone.setRadiusX(20);
        drone.setRadiusY(20);
        height = drone.getRadiusX();
        onGround = true;
    }

    public void turnRight() {
        if (!onGround) {
            if (drone.getStartAngle() == 0)
                drone.setStartAngle(360);
            drone.setStartAngle(drone.getStartAngle() - 45);
        }
    }

    public void turnLeft() {
        if (!onGround) {
            drone.setStartAngle(drone.getStartAngle() + 45);
            if (drone.getStartAngle() == 360)
                drone.setStartAngle(0);
        }
    }

    public double getHeight() {
        return height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isOnGround() {
        return onGround;
    }
}
