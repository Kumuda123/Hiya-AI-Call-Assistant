import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandParser {
    
    public enum Action {
        BLOCK,
        REJECT,
        VOICEMAIL,
        FORWARD,
        SCHEDULE_CALLBACK,
        PICKUP,
        UNKNOWN, 
        SHOW_BLOCKED
    }
    
    public static class ParsedCommand {
        private Action action;
        private String parameter;
        private double confidence;
        
        public ParsedCommand(Action action, String parameter, double confidence) {
            this.action = action;
            this.parameter = parameter;
            this.confidence = confidence;
        }
        
        public Action getAction() {
            return action;
        }
        
        public String getParameter() {
            return parameter;
        }
        
        public double getConfidence() {
            return confidence;
        }
        
        public boolean isConfident() {
            return confidence >= 0.7;
        }
    }
    
    private static final Map<Action, List<String>> COMMAND_PATTERNS = new HashMap<>();
    
    static {
        COMMAND_PATTERNS.put(Action.BLOCK, Arrays.asList(
            "block it", "block", "block this number", "block caller", 
            "spam", "stop this caller", "add to blocklist"
        ));
        
        COMMAND_PATTERNS.put(Action.REJECT, Arrays.asList(
            "cut it", "cut", "reject", "hang up", "decline", 
            "dismiss", "end it"
        ));
        
        COMMAND_PATTERNS.put(Action.VOICEMAIL, Arrays.asList(
            "send to voicemail", "voicemail", "vm", "leave a message",
            "take a message", "go to voicemail"
        ));
        
        COMMAND_PATTERNS.put(Action.FORWARD, Arrays.asList(
            "forward to work", "forward", "transfer", "forward to",
            "send to", "transfer to", "redirect"
        ));
        
        COMMAND_PATTERNS.put(Action.SCHEDULE_CALLBACK, Arrays.asList(
            "call me back", "callback", "call back", "schedule callback",
            "call later", "remind me", "schedule"
        ));
        
        COMMAND_PATTERNS.put(Action.PICKUP, Arrays.asList(
            "pick it up", "pick up", "answer", "answer it", "take it",
            "take the call", "get it", "accept"
        ));

        COMMAND_PATTERNS.put(Action.SHOW_BLOCKED, Arrays.asList(
            "Show blocked", "blocked"
        ));
    }
    
    public ParsedCommand parse(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return new ParsedCommand(Action.UNKNOWN, null, 0.0);
        }
        
        String normalizedInput = userInput.toLowerCase().trim();
        
        for (Map.Entry<Action, List<String>> entry : COMMAND_PATTERNS.entrySet()) {
            Action action = entry.getKey();
            List<String> patterns = entry.getValue();
            
            for (String pattern : patterns) {
                if (normalizedInput.contains(pattern)) {
                    double confidence = calculateConfidence(normalizedInput, pattern);
                    String parameter = extractParameter(normalizedInput, action);
                    return new ParsedCommand(action, parameter, confidence);
                }
            }
        }
        
        return new ParsedCommand(Action.UNKNOWN, null, 0.0);
    }
    
    private double calculateConfidence(String input, String pattern) {
        if (input.equals(pattern)) {
            return 1.0;
        }
        
        if (input.startsWith(pattern) || input.endsWith(pattern)) {
            return 0.9;
        }
        
        if (input.contains(pattern)) {
            return 0.8;
        }
        
        return 0.7;
    }
    
    private String extractParameter(String input, Action action) {
        switch (action) {
            case FORWARD:
                if (input.contains("work")) {
                    return "work";
                } else if (input.contains("home")) {
                    return "home";
                } else if (input.contains("assistant")) {
                    return "assistant";
                }
                return "default";
                
            case SCHEDULE_CALLBACK:
                if (input.contains("7pm") || input.contains("7 pm")) {
                    return "7pm";
                } else if (input.contains("tomorrow")) {
                    return "tomorrow";
                } else if (input.contains("later")) {
                    return "later today";
                } else if (input.matches(".*\\d+\\s*(am|pm).*")) {
                    String[] parts = input.split("\\s+");
                    for (int i = 0; i < parts.length; i++) {
                        if (parts[i].matches("\\d+.*") && i + 1 < parts.length && 
                            (parts[i + 1].contains("am") || parts[i + 1].contains("pm"))) {
                            return parts[i] + parts[i + 1];
                        } else if (parts[i].matches("\\d+(am|pm)")) {
                            return parts[i];
                        }
                    }
                }
                return "later";
                
            default:
                return null;
        }
    }
    
    public String getActionDescription(Action action) {
        switch (action) {
            case BLOCK:
                return "blocking this number permanently";
            case REJECT:
                return "rejecting this call";
            case VOICEMAIL:
                return "sending call to voicemail";
            case FORWARD:
                return "forwarding the call";
            case SCHEDULE_CALLBACK:
                return "scheduling a callback";
            case PICKUP:
                return "answering the call";
            default:
                return "unknown action";
        }
    }
}
