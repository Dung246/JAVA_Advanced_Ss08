package b2;
import java.util.*;

public class B2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TemperatureSensor sensor = new ThermometerAdapter(new OldThermometer());
        SmartHomeFacade facade = new SmartHomeFacade(sensor);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Xem nhiệt độ");
            System.out.println("2. Rời nhà");
            System.out.println("3. Chế độ ngủ");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    facade.getCurrentTemperature();
                    break;
                case 2:
                    facade.leaveHome();
                    break;
                case 3:
                    facade.sleepMode();
                    break;
                case 4:
                    System.out.println("Thoát");
                    return;
            }
        }
    }
}
// Interface mới
interface TemperatureSensor {
    double getTemperatureCelsius();
}
// Cảm biến cũ
class OldThermometer {
    public int getTemperatureFahrenheit() {
        return 78; // testt
    }
}

// Adapter
class ThermometerAdapter implements TemperatureSensor {
    private OldThermometer oldThermometer;

    public ThermometerAdapter(OldThermometer oldThermometer) {
        this.oldThermometer = oldThermometer;
    }

    @Override
    public double getTemperatureCelsius() {
        int f = oldThermometer.getTemperatureFahrenheit();
        return (f - 32) * 5.0 / 9;
    }
}

class SmartHomeFacade {
    private TemperatureSensor sensor;
    public SmartHomeFacade(TemperatureSensor sensor) {
        this.sensor = sensor;
    }
    public void getCurrentTemperature() {
        double temp = sensor.getTemperatureCelsius();
        System.out.printf("Nhiệt độ hiện tại: %.1f°C\n", temp);
    }
    public void leaveHome() {
        System.out.println("FACADE: Tắt đèn");
        System.out.println("FACADE: Tắt quạt");
        System.out.println("FACADE: Tắt điều hòa");
    }
    public void sleepMode() {
        System.out.println("FACADE: Tắt đèn");
        System.out.println("FACADE: Điều hòa set 28°C");
        System.out.println("FACADE: Quạt chạy tốc độ thấp");
    }
}
