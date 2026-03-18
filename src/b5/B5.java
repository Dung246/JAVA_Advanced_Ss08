package b5;
import java.util.*;
public class B5 {
    public static void main(String[] args) {
        Scanner inputs = new Scanner(System.in);
        LightDevice bedLight = new LightDevice();
        FanDevice roomFan = new FanDevice();
        AirConditionerDevice roomAC = new AirConditionerDevice();
        TemperatureSensor sensorCore = new TemperatureSensor();

        sensorCore.attach(roomFan);
        sensorCore.attach(roomAC);

        Command sleepCommand = new SleepModeCommand(bedLight, roomFan, roomAC);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Kích hoạt chế độ ngủ");
            System.out.println("2. Thay đổi nhiệt độ");
            System.out.println("3. Xem trạng thái thiết bị");
            System.out.println("4. Thoát");
            System.out.print("Chọn: ");

            int menuSelect = inputs.nextInt();

            switch (menuSelect) {
                case 1:
                    sleepCommand.execute();
                    break;

                case 2:
                    System.out.print("Nhập nhiệt độ: ");
                    int tempValue = inputs.nextInt();
                    sensorCore.setTemperature(tempValue);
                    break;

                case 3:
                    bedLight.showStatus();
                    roomFan.showStatus();
                    roomAC.showStatus();
                    break;

                case 4:
                    System.out.println("Thoát...");
                    return;
            }
        }
    }
}

interface Command {
    void execute();
}

class SleepModeCommand implements Command {
    private LightDevice lightRef;
    private FanDevice fanRef;
    private AirConditionerDevice acRef;

    public SleepModeCommand(LightDevice l, FanDevice f, AirConditionerDevice ac) {
        this.lightRef = l;
        this.fanRef = f;
        this.acRef = ac;
    }

    public void execute() {
        System.out.println("SleepMode: Tắt đèn");
        System.out.println("SleepMode: Điều hòa set 28°C");
        System.out.println("SleepMode: Quạt tốc độ thấp");

        lightRef.turnOff();
        acRef.setTemperature(28);
        fanRef.setLowSpeed();
    }
}

interface Observer {
    void update(int temperature);
}

interface Subject {
    void attach(Observer obs);
    void notifyObservers();
}

class TemperatureSensor implements Subject {
    private List<Observer> observerPool = new ArrayList<>();
    private int currentTemp;

    public void attach(Observer obs) {
        observerPool.add(obs);
    }

    public void notifyObservers() {
        for (Observer obs : observerPool) {
            obs.update(currentTemp);
        }
    }

    public void setTemperature(int tempVal) {
        currentTemp = tempVal;
        System.out.println("Cảm biến: Nhiệt độ = " + currentTemp);
        notifyObservers();
    }
}

class LightDevice {
    private boolean isOn = true;

    public void turnOff() {
        isOn = false;
        System.out.println("Đèn: Tắt");
    }

    public void showStatus() {
        System.out.println("Đèn: " + (isOn ? "Đang bật" : "Đang tắt"));
    }
}

class FanDevice implements Observer {
    private String speedState = "TẮT";

    public void setLowSpeed() {
        speedState = "THẤP";
        System.out.println("Quạt: Chạy tốc độ thấp");
    }

    public void update(int temperature) {
        if (temperature > 30) {
            speedState = "CAO";
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
        }
    }

    public void showStatus() {
        System.out.println("Quạt: " + speedState);
    }
}

class AirConditionerDevice implements Observer {
    private int currentSetting = 25;

    public void setTemperature(int temp) {
        currentSetting = temp;
        System.out.println("Điều hòa: Nhiệt độ = " + currentSetting);
    }

    public void update(int temperature) {
        if (temperature > 30) {
            System.out.println("Điều hòa: Giữ nhiệt độ ổn định = " + currentSetting);
        }
    }

    public void showStatus() {
        System.out.println("Điều hòa: " + currentSetting + "°C");
    }
}