import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ErrorHandler {
    
    private Random random;
    private int retryCount;
    private static final int MAX_RETRIES = 3;
    
    public ErrorHandler() {
        this.random = new Random();
        this.retryCount = 0;
    }
    
    public String handleUnclearCommand(String userInput) {
        retryCount++;
        
        if (retryCount >= MAX_RETRIES) {
            return "I'm having trouble understanding your request. Let me send this call to voicemail for now. " +
                   "You can say things like:\n" +
                   "  - 'Pick it up' to answer the call\n" +
                   "  - 'Cut it' to reject/hang up\n" +
                   "  - 'Block it' to block the caller forever\n" +
                   "  - 'Send to voicemail' to take a message\n" +
                   "  - 'Forward' to transfer the call\n" +
                   "  - 'Call me back after 7pm' to schedule a callback";
        }
        
        List<String> clarificationPrompts = Arrays.asList(
            "I didn't quite catch that. What would you like me to do with this call? You can say 'pick it up', 'cut it', 'block it', 'send to voicemail', 'forward', or 'call me back later'.",
            "Sorry, I'm not sure what you mean. Would you like to answer, reject, block this caller, send them to voicemail, forward the call, or schedule a callback?",
            "I want to make sure I understand. Could you please say one of these: 'pick up', 'cut it', 'block', 'voicemail', 'forward', or 'callback'?",
            "Hmm, I didn't understand that command. Try saying something like 'answer it', 'cut it' to reject, 'block it' to block permanently, 'send to voicemail', or 'forward'."
        );
        
        return clarificationPrompts.get(random.nextInt(clarificationPrompts.size()));
    }
    
    public String handleActionFailure(String action, String errorMessage) {
        List<String> fallbackResponses = Arrays.asList(
            "Oops, I ran into a problem while " + action + ". " + errorMessage + " Would you like me to try again or do something else?",
            "Sorry, there was an issue " + action + ". " + errorMessage + " What would you like to do instead?",
            "I encountered an error " + action + ": " + errorMessage + " Should I try a different approach?"
        );
        
        return fallbackResponses.get(random.nextInt(fallbackResponses.size()));
    }
    
    public String handleLowConfidence(String detectedAction, double confidence) {
        return String.format(
            "I think you want me to %s, but I'm only %.0f%% sure. Is that correct? (yes/no)",
            detectedAction,
            confidence * 100
        );
    }
    
    public void resetRetryCount() {
        retryCount = 0;
    }
    
    public boolean hasExceededRetries() {
        return retryCount >= MAX_RETRIES;
    }
    
    public String getSuggestions() {
        return "\nHere are some example commands you can try:\n" +
               "  • 'Pick it up' or 'Answer' - Answer the call\n" +
               "  • 'Cut it' or 'Reject' - Hang up (they can call back)\n" +
               "  • 'Block it' or 'Spam' - Block this number forever\n" +
               "  • 'Send to voicemail' - Let them leave a message\n" +
               "  • 'Forward' - Transfer to another number\n" +
               "  • 'Call me back after 7pm' - Schedule a callback reminder\n" +
               "  • 'Quit' - Exit the simulation";
    }
}
