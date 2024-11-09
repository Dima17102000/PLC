public class TrafficController2RightCars implements TrafficController {
    private TrafficRegistrar registrar;
    private int countLeft = 0;  // Счётчик машин слева
    private int countRight = 0; // Счётчик машин справа
    private final int MAX_LEFT = 1;   // Максимум одна машина слева
    private final int MAX_RIGHT = 2;  // Максимум две машины справа

    public TrafficController2RightCars(TrafficRegistrar registrar) {
        this.registrar = registrar;
    }

    public synchronized void enterLeft(Vehicle vehicle) {
        // Машина слева ждёт, если на мосту уже находится максимальное количество машин слева
        while (countLeft >= MAX_LEFT) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        countLeft++; // Разрешаем въезд машине слева
        registrar.registerLeft(vehicle);
        //System.out.println("Vehicle from left entered. Left count: " + countLeft);
    }

    public synchronized void enterRight(Vehicle vehicle) {
        // Машина справа ждёт, если на мосту уже находится максимальное количество машин справа
        while (countRight >= MAX_RIGHT) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        countRight++; // Разрешаем въезд машине справа
        registrar.registerRight(vehicle);
        //System.out.println("Vehicle from right entered. Right count: " + countRight);
    }

    public synchronized void leaveLeft(Vehicle vehicle) {
        // Машина покидает мост слева
        if (countLeft > 0) {
            countLeft--;
            registrar.deregisterLeft(vehicle);
            //System.out.println("Vehicle from left left. Left count: " + countLeft);
            notifyAll(); // Уведомляем ожидающие машины слева
        }
    }

    public synchronized void leaveRight(Vehicle vehicle) {
        // Машина покидает мост справа
        if (countRight > 0) {
            countRight--;
            registrar.deregisterRight(vehicle);
            //System.out.println("Vehicle from right left. Right count: " + countRight);
            notifyAll(); // Уведомляем ожидающие машины справа
        }
    }
}

