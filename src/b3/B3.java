package b3;
import java.util.*;
public class B3 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        RemoteControl remoteControl = new RemoteControl();

        // thiết bị
        Light livingLight = new Light();
        Fan ceilingFan = new Fan();
        AirConditioner roomAC = new AirConditioner();

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Gán nút");
            System.out.println("2. Nhấn nút");
            System.out.println("3. Undo");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int menuChoice = input.nextInt();

            switch (menuChoice) {
                case 1:
                    System.out.print("Chọn nút (1-5): ");
                    int buttonSlot = input.nextInt();

                    System.out.println("Chọn command:");
                    System.out.println("1. Bật đèn");
                    System.out.println("2. Tắt đèn");
                    System.out.println("3. Bật quạt");
                    System.out.println("4. Tắt quạt");
                    System.out.println("5. Set nhiệt độ điều hòa");
                    int commandType = input.nextInt();

                    Command selectedCommand = null;

                    switch (commandType) {
                        case 1:
                            selectedCommand = new LightOnCommand(livingLight);
                            break;
                        case 2:
                            selectedCommand = new LightOffCommand(livingLight);
                            break;
                        case 3:
                            selectedCommand = new FanOnCommand(ceilingFan);
                            break;
                        case 4:
                            selectedCommand = new FanOffCommand(ceilingFan);
                            break;
                        case 5:
                            System.out.print("Nhập nhiệt độ: ");
                            int desiredTemp = input.nextInt();
                            selectedCommand = new ACSetTemperatureCommand(roomAC, desiredTemp);
                            break;
                    }

                    if (selectedCommand != null) {
                        remoteControl.setCommand(buttonSlot, selectedCommand);
                        System.out.println("Đã gán command cho nút " + buttonSlot);
                    }
                    break;

                case 2:
                    System.out.print("Nhấn nút (1-5): ");
                    int pressedSlot = input.nextInt();
                    remoteControl.pressButton(pressedSlot);
                    break;

                case 3:
                    remoteControl.undo();
                    break;

                case 0:
                    System.out.println("Thoát...");
                    return;
            }
        }
    }
}

interface Command {
    void execute();
    void undo();
}

class Light {
    public void on() {
        System.out.println("Đèn: Bật");
    }

    public void off() {
        System.out.println("Đèn: Tắt");
    }
}

class Fan {
    public void on() {
        System.out.println("Quạt: Bật");
    }

    public void off() {
        System.out.println("Quạt: Tắt");
    }
}

class AirConditioner {
    private int currentTemp = 25;

    public void setTemperature(int temp) {
        currentTemp = temp;
        System.out.println("Điều hòa: Nhiệt độ = " + currentTemp);
    }

    public int getTemperature() {
        return currentTemp;
    }
}

class LightOnCommand implements Command {
    private Light lightDevice;

    public LightOnCommand(Light lightDevice) {
        this.lightDevice = lightDevice;
    }

    public void execute() {
        lightDevice.on();
    }

    public void undo() {
        lightDevice.off();
        System.out.println("Undo: Đèn Tắt");
    }
}

class LightOffCommand implements Command {
    private Light lightDevice;

    public LightOffCommand(Light lightDevice) {
        this.lightDevice = lightDevice;
    }

    public void execute() {
        lightDevice.off();
    }

    public void undo() {
        lightDevice.on();
        System.out.println("Undo: Đèn Bật");
    }
}

class FanOnCommand implements Command {
    private Fan fanDevice;

    public FanOnCommand(Fan fanDevice) {
        this.fanDevice = fanDevice;
    }

    public void execute() {
        fanDevice.on();
    }

    public void undo() {
        fanDevice.off();
        System.out.println("Undo: Quạt Tắt");
    }
}

class FanOffCommand implements Command {
    private Fan fanDevice;

    public FanOffCommand(Fan fanDevice) {
        this.fanDevice = fanDevice;
    }

    public void execute() {
        fanDevice.off();
    }

    public void undo() {
        fanDevice.on();
        System.out.println("Undo: Quạt Bật");
    }
}

class ACSetTemperatureCommand implements Command {
    private AirConditioner acDevice;
    private int newTemperature;
    private int oldTemperature;

    public ACSetTemperatureCommand(AirConditioner acDevice, int temp) {
        this.acDevice = acDevice;
        this.newTemperature = temp;
    }

    public void execute() {
        oldTemperature = acDevice.getTemperature();
        acDevice.setTemperature(newTemperature);
    }

    public void undo() {
        acDevice.setTemperature(oldTemperature);
        System.out.println("Undo: Điều hòa về " + oldTemperature);
    }
}

class RemoteControl {
    private Map<Integer, Command> commandSlots = new HashMap<>();
    private Stack<Command> commandHistory = new Stack<>();

    public void setCommand(int slotNumber, Command command) {
        commandSlots.put(slotNumber, command);
    }

    public void pressButton(int slotNumber) {
        Command command = commandSlots.get(slotNumber);
        if (command != null) {
            command.execute();
            commandHistory.push(command);
        } else {
            System.out.println("Chưa gán command cho nút này!");
        }
    }

    public void undo() {
        if (!commandHistory.isEmpty()) {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
        } else {
            System.out.println("Không có lệnh để undo!");
        }
    }
}