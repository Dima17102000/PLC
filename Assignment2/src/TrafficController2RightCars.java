public class TrafficController2RightCars implements TrafficController {
    private TrafficRegistrar registrar;
    private int countLeft = 0;  // Vehicles from the left
    private int countRight = 0; // Vehicles from the right
    private final int MAX_LEFT = 1;
    private final int MAX_RIGHT = 2;

    public TrafficController2RightCars(TrafficRegistrar registrar) {
        this.registrar = registrar;
    }

    public synchronized void enterLeft(Vehicle vehicle) {
        // Wait if there are cars on the right side or if the left side is already occupied
        while (countRight > 0 || countLeft >= MAX_LEFT) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        countLeft++;
        registrar.registerLeft(vehicle);
    }

    public synchronized void enterRight(Vehicle vehicle) {
        // Wait if there are cars on the left side or if the right side is full
        while (countLeft > 0 || countRight >= MAX_RIGHT) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        countRight++;
        registrar.registerRight(vehicle);
    }

    public synchronized void leaveLeft(Vehicle vehicle) {
        // When a left vehicle leaves, decrease count and notify all waiting vehicles
        if (countLeft > 0) {
            countLeft--;
            registrar.deregisterLeft(vehicle);
            notifyAll();
        }
    }

    public synchronized void leaveRight(Vehicle vehicle) {
        // When a right vehicle leaves, decrease count and notify all waiting vehicles
        if (countRight > 0) {
            countRight--;
            registrar.deregisterRight(vehicle);
            notifyAll();
        }
    }

    public synchronized boolean isFree() {
        // Returns true if no vehicles are on the bridge
        return countLeft == 0 && countRight == 0;
    }
}

