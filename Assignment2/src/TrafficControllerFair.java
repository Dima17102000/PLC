import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TrafficControllerFair implements TrafficController {
    private final TrafficRegistrar registrar;
    private final Lock lock = new ReentrantLock(true);  // Fairer Lock
    private final Condition bridgeFree = lock.newCondition();
    private boolean bridgeOccupied = false;

    public TrafficControllerFair(TrafficRegistrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public void enterRight(Vehicle v) {
        lock.lock();
        try {
            while (bridgeOccupied) {
                bridgeFree.await();  // Warte, bis die Brücke frei ist
            }
            bridgeOccupied = true;
            registrar.registerRight(v);  // Fahrzeug von rechts registrieren
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Umgang mit Interruptions
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void enterLeft(Vehicle v) {
        lock.lock();
        try {
            while (bridgeOccupied) {
                bridgeFree.await();
            }
            bridgeOccupied = true;
            registrar.registerLeft(v);  // Fahrzeug von links registrieren
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void leaveLeft(Vehicle v) {
        lock.lock();
        try {
            bridgeOccupied = false;
            registrar.deregisterLeft(v);  // Fahrzeug von links deregistrieren
            bridgeFree.signalAll();  // Andere wartende Fahrzeuge benachrichtigen
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void leaveRight(Vehicle v) {
        lock.lock();
        try {
            bridgeOccupied = false;
            registrar.deregisterRight(v);  // Fahrzeug von rechts deregistrieren
            bridgeFree.signalAll();  // Andere wartende Fahrzeuge benachrichtigen
        } finally {
            lock.unlock();
        }
    }
}


