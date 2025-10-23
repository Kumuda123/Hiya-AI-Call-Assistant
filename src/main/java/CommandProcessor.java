import java.util.Scanner;
import java.util.Set;

public class CommandProcessor {

    public static boolean processCommand(
            String userInput,
            IncomingCall call,
            CommandParser parser,
            CallRouter router,
            ErrorHandler errorHandler,
            Scanner scanner
    ) {
        CommandParser.ParsedCommand parsedCommand = parser.parse(userInput);

        if (parsedCommand.getAction() == CommandParser.Action.UNKNOWN) {
            String clarification = errorHandler.handleUnclearCommand(userInput);
            System.out.println("\n🤖 AI Assistant: " + clarification + "\n");

            if (errorHandler.hasExceededRetries()) {
                CallRouter.ActionResult result = router.sendToVoicemail(call.getPhoneNumber(), call.getCallerName());
                errorHandler.resetRetryCount();
                if (result.isSuccess()) {
                    System.out.println("✅ 🤖 AI Assistant: " + result.getMessage() + "\n");
                    return true;
                } else {
                    String fallbackMessage = errorHandler.handleActionFailure(
                            "sending to voicemail", result.getMessage());
                    System.out.println("\n❌ 🤖 AI Assistant: " + fallbackMessage + "\n");
                    return false;
                }
            }
            return false;
        }

        if (!parsedCommand.isConfident()) {
            String confirmationPrompt = errorHandler.handleLowConfidence(
                    parser.getActionDescription(parsedCommand.getAction()), parsedCommand.getConfidence());
            System.out.println("\n🤖 AI Assistant: " + confirmationPrompt);
            System.out.print("Your response: ");
            String confirmation = scanner.nextLine().trim();
            if (!confirmation.equalsIgnoreCase("yes") && !confirmation.equalsIgnoreCase("y")) {
                System.out.println("\n🤖 AI Assistant: No problem! What would you like me to do instead?\n");
                return false;
            }
        }

        errorHandler.resetRetryCount();
        return executeAction(parsedCommand, call, router, parser, errorHandler, scanner);
    }

    private static boolean executeAction(
            CommandParser.ParsedCommand command,
            IncomingCall call,
            CallRouter router,
            CommandParser parser,
            ErrorHandler errorHandler,
            Scanner scanner
    ) {
        CallRouter.ActionResult result = null;

        switch (command.getAction()) {
            case BLOCK:
                result = router.blockNumber(call.getPhoneNumber());
                break;

            case SHOW_BLOCKED:
                Set<String> blocked = router.getBlockedNumbers();
                if (blocked.isEmpty()) {
                    System.out.println("\n🤖 AI Assistant: No blocked numbers yet.\n");
                } else {
                    System.out.println("\n🤖 AI Assistant: Blocked Numbers:");
                    blocked.forEach(num -> System.out.println(" - " + num));
                    System.out.println();
                }
                return false;

            case REJECT:
                result = router.rejectCall(call.getPhoneNumber(), call.getCallerName());
                break;

            case VOICEMAIL:
                result = router.sendToVoicemail(call.getPhoneNumber(), call.getCallerName());
                break;

            case FORWARD:
                String destination = command.getParameter();
                if (destination == null || destination.equals("default")) {
                    System.out.print("\n🤖 AI Assistant: Where should I forward this call? (work/home/assistant): ");
                    destination = scanner.nextLine().trim().toLowerCase();
                    if (destination.isEmpty()) destination = "work";
                }
                result = router.forwardCall(call.getPhoneNumber(), destination);
                break;

            case SCHEDULE_CALLBACK:
                String time = command.getParameter() != null ? command.getParameter() : "later";
                result = router.scheduleCallback(call.getPhoneNumber(), call.getCallerName(), time);
                break;

            case PICKUP:
                result = router.pickupCall(call.getPhoneNumber(), call.getCallerName());
                break;

            default:
                System.out.println("\n🤖 AI Assistant: I'm not sure how to handle that command.\n");
                return false;
        }

        if (result != null && result.isSuccess()) {
            System.out.println("\n✅ 🤖 AI Assistant: " + result.getMessage() + "\n");
            return true;
        } else if (result != null) {
            String fallbackMessage = errorHandler.handleActionFailure(
                    parser.getActionDescription(command.getAction()), result.getMessage());
            System.out.println("\n❌ 🤖 AI Assistant: " + fallbackMessage + "\n");
            return false;
        }

        return false;
    }
}
