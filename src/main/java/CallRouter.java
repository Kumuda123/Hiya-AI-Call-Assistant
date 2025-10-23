import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CallRouter {
    
    private Random random;
    private Set<String> blockedNumbers = new HashSet<>();

    public CallRouter() {
        this.random = new Random();
    }
    
    
    public ActionResult blockNumber(String phoneNumber) {
        blockedNumbers.add(phoneNumber);
        System.out.println("\n[Mock API Call] Calling Hiya API: block number " + phoneNumber);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("[Mock API Response] Success: Number blocked in Hiya database");
        return new ActionResult(true, "Number " + phoneNumber + " has been successfully blocked and added to your spam list.");
    }

    public ActionResult unblockNumber(String phoneNumber) {
        if (blockedNumbers.remove(phoneNumber)) {
            return new ActionResult(true, "Number " + phoneNumber + " has been unblocked.");
        } else {
            return new ActionResult(false, "Number " + phoneNumber + " was not in the blocked list.");
        }
    }
    
    public Set<String> getBlockedNumbers() {
        return new HashSet<>(blockedNumbers);
    }

    public ActionResult sendToVoicemail(String phoneNumber, String callerName) {
        System.out.println("\n[Mock API Call] Calling Twilio API: redirect to voicemail for " + phoneNumber);
        
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("[Mock API Response] Call redirected to voicemail successfully");
        return new ActionResult(true, "The call from " + callerName + " has been sent to voicemail. You'll receive a notification when they leave a message.");
    }
    
    public ActionResult forwardCall(String phoneNumber, String destination) {
        System.out.println("\n[Mock API Call] Calling Twilio API: forward " + phoneNumber + " to " + destination);

        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String destinationNumber = getDestinationNumber(destination);
        System.out.println("[Mock API Response] Call forwarded to " + destinationNumber);
        return new ActionResult(true, "I've forwarded the call to your " + destination + " number (" + destinationNumber + "). Connecting now...");
    }
    
    public ActionResult scheduleCallback(String phoneNumber, String callerName, String time) {
        System.out.println("\n[Mock API Call] Calling Hiya API: schedule callback from " + phoneNumber + " at " + time);
        
        try {
            Thread.sleep(450);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("[Mock API Response] Callback scheduled successfully");
        return new ActionResult(true, "Got it! I'll remind you to call back " + callerName + " at " + time + ". The call has been sent to voicemail for now.");
    }
    
    public ActionResult pickupCall(String phoneNumber, String callerName) {
        System.out.println("\n[Mock API Call] Calling Twilio API: answer call from " + phoneNumber);

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("[Mock API Response] Call connected successfully");
        return new ActionResult(true, "Connecting you to " + callerName + " now. Have a great conversation!");
    }
    
    public ActionResult rejectCall(String phoneNumber, String callerName) {
        System.out.println("\n[Mock API Call] Calling Twilio API: reject/hang up call from " + phoneNumber);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("[Mock API Response] Call rejected successfully");
        return new ActionResult(true, "I've ended the call from " + callerName + ". They can still call you back later.");
    }
    
    private String getDestinationNumber(String destination) {
        switch (destination.toLowerCase()) {
            case "work":
                return "+1-555-0100";
            case "home":
                return "+1-555-0200";
            case "assistant":
                return "+1-555-0300";
            default:
                return "+1-555-0000";
        }
    }
    
    public static class ActionResult {
        private boolean success;
        private String message;
        
        public ActionResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
    }
}
