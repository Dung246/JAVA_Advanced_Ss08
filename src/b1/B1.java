package b1;
import java.util.*;

public class B1{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Device> devices = new ArrayList<>();
        HardwareConnection connection = null;
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Kết nối phần cứng");
            System.out.println("2. Tạo thiết bị");
            System.out.println("3. Bật thiết bị");
            System.out.println("4. Tắt thiết bị");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    connection = HardwareConnection.getInstance();
                    break;
                case 2:
                    System.out.println("Chọn loại:");
                    System.out.println("1. Đèn");
                    System.out.println("2. Quạt");
                    System.out.println("3. Điều hòa");
                    int type = sc.nextInt();
                    DeviceFactory factory = null;

                    if (type == 1) {
                        factory = new LightFactory();
                    }else if (type == 2) {
                        factory = new FanFactory();
                    }else if (type == 3) {
                        factory = new AirConditionerFactory();
                    }

                    if (factory != null) {
                        Device device = factory.createDevice();
                        devices.add(device);
                    }
                    break;

                case 3:
                    if (devices.isEmpty()) {
                        System.out.println("Chưa có thiết bị!");
                        break;
                    }

                    for (int i = 0; i < devices.size(); i++) {
                        System.out.println((i + 1) + ". Device " + (i + 1));
                    }

                    System.out.print("Chọn thiết bị: ");
                    int index = sc.nextInt() - 1;

                    if (index < 0 || index >= devices.size()) {
                        System.out.println("Lựa chọn không hợp lệ!");
                        break;
                    }

                    System.out.println("1. Bật");
                    System.out.println("2. Tắt");
                    System.out.print("Chọn chức năng: ");
                    int action = sc.nextInt();

                    if (action == 1) {
                        devices.get(index).turnOn();
                    } else if (action == 2) {
                        devices.get(index).turnOff();
                    } else {
                        System.out.println("Lựa chọn không hợp lệ!");
                    }
                    break;

                case 4:
                    System.out.println("Thoát");
                    return;
                default:
                    System.out.println("Nhập lại :");
                    break;
            }
        }
    }
}

//Singleton
class HardwareConnection {
    private static HardwareConnection instance;
    private HardwareConnection() {}
    public static HardwareConnection getInstance() {
        if (instance == null) {
            instance = new HardwareConnection();
            System.out.println("HardwareConnection: Đã kết nối phần cứng.");
        }
        return instance;
    }

    public void connect() {
        System.out.println("Đang sử dụng kết nối phần cứng...");
    }

    public void disconnect() {
        System.out.println("Ngắt kết nối phần cứng.");
    }
}

//Device
interface Device {
    void turnOn();
    void turnOff();
}

//Many Devices
class Light implements Device {
    public void turnOn() {
        System.out.println("Đèn: Bật sáng.");
    }

    public void turnOff() {
        System.out.println("Đèn: Tắt.");
    }
}

class Fan implements Device {
    public void turnOn() {
        System.out.println("Quạt: Quay.");
    }

    public void turnOff() {
        System.out.println("Quạt: Dừng.");
    }
}

class AirConditioner implements Device {
    public void turnOn() {
        System.out.println("Điều hòa: Làm mát.");
    }

    public void turnOff() {
        System.out.println("Điều hòa: Tắt.");
    }
}

// factory
abstract class DeviceFactory {
    public abstract Device createDevice();
}

class LightFactory extends DeviceFactory {
    public Device createDevice() {
        System.out.println("LightFactory: Đã tạo đèn mới.");
        return new b3.Light();
    }
}

class FanFactory extends DeviceFactory {
    public Device createDevice() {
        System.out.println("FanFactory: Đã tạo quạt mới.");
        return new b3.Fan();
    }
}

class AirConditionerFactory extends DeviceFactory {
    public Device createDevice() {
        System.out.println("AirConditionerFactory: Đã tạo điều hòa mới.");
        return new b3.AirConditioner();
    }
}