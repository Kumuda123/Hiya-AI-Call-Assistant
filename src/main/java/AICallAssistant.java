import java.util.Scanner;

public class AICallAssistant {

    private CommandParser parser;
    private CallRouter router;
    private ErrorHandler errorHandler;
    private Scanner scanner;

    public AICallAssistant() {
        parser = new CommandParser();
        router = new CallRouter();
        errorHandler = new ErrorHandler();
        scanner = new Scanner(System.in);
    }

    public void run() {
        displayWelcomeMessage();

        boolean running = true;
        while (running) {
            IncomingCall call = CallSimulator.simulateIncomingCall_Random(router);
            CallDisplay.showIncomingCall(call);

            boolean callHandled = false;
            while (!callHandled) {
                String userInput = getUserInput();

                if (userInput.equalsIgnoreCase("quit") || userInput.equalsIgnoreCase("exit")) {
                    running = false;
                    callHandled = true;
                    System.out.println("\n👋 Thanks for testing the AI Call Assistant! Goodbye!");
                    break;
                }

                callHandled = CommandProcessor.processCommand(userInput, call, parser, router, errorHandler, scanner);
            }

            if (running && !askToSimulateAnotherCall()) {
                running = false;
                System.out.println("\n👋 Thanks for testing the AI Call Assistant! Goodbye!");
            }
        }

        scanner.close();
    }

    private void displayWelcomeMessage() {
        System.out.println("=".repeat(60));
        System.out.println("         🤖 AI CALL ASSISTANT");
        System.out.println("=".repeat(60));
        System.out.println("\nWelcome! I'm your AI call assistant. I'll help you manage");
        System.out.println("incoming calls using simple voice commands (text-based for demo).");
        System.out.println(errorHandler.getSuggestions());
        System.out.println("\n" + "=".repeat(60) + "\n");
    }

    private String getUserInput() {
        System.out.print("🎤 What would you like me to do? ");
        return scanner.nextLine().trim();
    }

    private boolean askToSimulateAnotherCall() {
        while (true) {
            System.out.print("\nWould you like to simulate another call? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (response.equals("yes") || response.equals("y")) return true;
            if (response.equals("no") || response.equals("n")) return false;
            System.out.println("Please answer 'yes' or 'no'.");
        }
    }

    public static void main(String[] args) {
        new AICallAssistant().run();
    }
}
