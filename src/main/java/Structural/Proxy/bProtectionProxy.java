package Structural.Proxy;

// we have an ordinary car which doesn't have any driver checks.
// but we want to make sure that our driver is old enough to drive.
// so we make a proxyCar class that will only drive for a driver
// who is found to be old enough

public class bProtectionProxy {
    public static void main(String[] args) {
        Car car = new Car(new Driver(14));
        car.drive(); // << fine, but shouldn't be!

        Car carProxy = new CarProxy(new Driver(14));
        carProxy.drive();
    }
}

class CarProxy extends Car {

    public CarProxy(Driver driver) {
        super(driver);
    }

    @Override
    public void drive() {
        if(driver.age >= 16)
            super.drive();
        else
            System.out.println("Driver too young!");
    }
}

interface Drivable {
    void drive();
}

class Car implements Drivable {

    public Driver driver;

    public Car(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void drive() {
        System.out.println("Car is driving.");
    }
}

class Driver {
    public int age;

    public Driver(int age) {
        this.age = age;
    }

}