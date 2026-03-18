package b4;
import java.util.*;
public class B4 {
    public static void main(String[] args) {
        Scanner inputReader = new Scanner(System.in);

        TemperatureSensor sensorHub = new TemperatureSensor();
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Đăng ký thiết bị");
            System.out.println("2. Set nhiệt độ");
            System.out.println("3. Thoát");
            System.out.print("Chọn: ");

            int userChoice = inputReader.nextInt();

            switch (userChoice) {
                case 1:
                    System.out.println("Chọn thiết bị:");
                    System.out.println("1. Quạt");
                    System.out.println("2. Máy tạo ẩm");
                    int deviceChoice = inputReader.nextInt();

                    if (deviceChoice == 1) {
                        Observer fanObserver = new FanObserver();
                        sensorHub.attach(fanObserver);
                        System.out.println("Quạt: Đã đăng ký nhận thông báo");
                    } else if (deviceChoice == 2) {
                        Observer humidifierObserver = new HumidifierObserver();
                        sensorHub.attach(humidifierObserver);
                        System.out.println("Máy tạo ẩm: Đã đăng ký");
                    }
                    break;
                case 2:
                    System.out.print("Nhập nhiệt độ: ");
                    int newTemperature = inputReader.nextInt();
                    sensorHub.setTemperature(newTemperature);
                    break;
                case 3:
                    System.out.println("Thoát...");
                    return;
            }
        }
    }
}

interface Observer {
    void update(int temperatureValue);
}
interface Subject {
    void attach(Observer obs);
    void detach(Observer obs);
    void notifyObservers();
}

class TemperatureSensor implements Subject {
    private List<Observer> observerList = new ArrayList<>();
    private int currentTemperature;

    public void attach(Observer obs) {
        observerList.add(obs);
    }

    public void detach(Observer obs) {
        observerList.remove(obs);
    }

    public void notifyObservers() {
        for (Observer obs : observerList) {
            obs.update(currentTemperature);
        }
    }

    public void setTemperature(int tempValue) {
        currentTemperature = tempValue;
        System.out.println("Cảm biến: Nhiệt độ = " + currentTemperature);
        notifyObservers();
    }
}

class FanObserver implements Observer {
    public void update(int temperatureValue) {
        if (temperatureValue < 20) {
            System.out.println("Quạt: Nhiệt độ thấp, tự động TẮT");
        } else if (temperatureValue <= 25) {
            System.out.println("Quạt: Nhiệt độ trung bình, chạy vừa");
        } else {
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
        }
    }
}

class HumidifierObserver implements Observer {
    public void update(int temperatureValue) {
        System.out.println("Máy tạo ẩm: Điều chỉnh độ ẩm cho nhiệt độ " + temperatureValue);
    }
}