import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.lookups.v1.PhoneNumber;


public class CallSimulator {

    private static final Random random = new Random();

    // Twilio credentials
    private static final String ACCOUNT_SID = "AC4153624228545cbf001a20fe0c6e033d";
    private static final String AUTH_TOKEN = "01b38dd1e046dc2a1118dd6f81e92183";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN, "us1");
    }

    public static IncomingCall simulateIncomingCall() {
        List<String> testNumbers = Arrays.asList(
                "+14155552671", // US mobile
                "+18005551234", // Toll-free
                "+12025550123", 
                "+14699294647"
        );

        // Select a random number from the list
        String incomingNumber = testNumbers.get(random.nextInt(testNumbers.size()));

        String callerName = "Unknown";
        String carrierType = "Unknown";
        String spamStatus = "Unknown";

        try {
            PhoneNumber lookupResult = PhoneNumber.fetcher(new com.twilio.type.PhoneNumber(incomingNumber))
        .setType(Arrays.asList("carrier", "caller-name"))
        .fetch();
            if (lookupResult.getCallerName() != null && lookupResult.getCallerName().toString() != null) {
                callerName = lookupResult.getCallerName().toString();
            }

            // Correct access to Carrier data
            if (lookupResult.getCarrier() != null) {
                carrierType = lookupResult.getCarrier().toString(); // mobile, voip, landline
            }

            // Simple spam heuristic
            if (callerName != null && callerName.toLowerCase().contains("spam")) {
                spamStatus = "Spam Likely";
            } else if ("voip".equalsIgnoreCase(carrierType)) {
                spamStatus = "Potential Spam";
            } else {
                spamStatus = "Legitimate";
            }

        } catch (ApiException e) {
            System.err.println("Lookup failed for " + incomingNumber + ": " + e.getMessage());
            spamStatus = "Unknown (Lookup Failed)";
        }

        return new IncomingCall(incomingNumber, callerName, spamStatus, carrierType);
    }


    public static IncomingCall simulateIncomingCall_Random(CallRouter router) {
        List<IncomingCall> sampleCalls = Arrays.asList(
            new IncomingCall("+1-555-0123", "John Smith", "Legitimate", "Contact"),
            new IncomingCall("+1-800-SPAM-99", "Unknown Caller", "Likely Spam", "Telemarketer"),
            new IncomingCall("+1-212-555-0001", "NYC Marketing Inc", "Potential Spam", "Business"),
            new IncomingCall("+1-555-1234", "Mom", "Legitimate", "Family"),
            new IncomingCall("+1-866-000-0000", "Rewards Center", "Spam", "Scam Likely"),
            new IncomingCall("+1-415-555-5678", "Bank of America", "Legitimate", "Business"),
            new IncomingCall("+1-888-555-9012", "Unknown", "Potential Spam", "Telemarketer"),
            new IncomingCall("+1-310-555-4321", "Gym Membership", "Likely Spam", "Business"),
            new IncomingCall("+1-617-555-8765", "Alice Williams", "Legitimate", "Contact"),
            new IncomingCall("+1-877-555-3456", "Insurance Alert", "Spam", "Scam Likely"),
            new IncomingCall("+1-202-555-2345", "Congress Office", "Legitimate", "Government"),
            new IncomingCall("+1-800-555-1212", "Tech Support", "Potential Spam", "Business"),
            new IncomingCall("+1-305-555-6789", "Hotel Booking", "Legitimate", "Service"),
            new IncomingCall("+1-720-555-4321", "Unknown Caller", "Likely Spam", "Telemarketer"),
            new IncomingCall("+1-213-555-9876", "Mom’s Friend", "Legitimate", "Family"),
            new IncomingCall("+1-646-555-1122", "NYC Gym", "Potential Spam", "Business"),
            new IncomingCall("+1-800-555-4321", "Survey Center", "Spam", "Telemarketer"),
            new IncomingCall("+1-415-555-3333", "John’s Work", "Legitimate", "Business"),
            new IncomingCall("+1-212-555-4444", "NYC Pizza", "Potential Spam", "Business"),
            new IncomingCall("+1-888-555-9999", "Lottery Alert", "Spam", "Scam Likely")
        );
        IncomingCall call;
        do {
            call = sampleCalls.get(random.nextInt(sampleCalls.size()));
        } while (router.getBlockedNumbers().contains(call.getPhoneNumber()));

        return call;
    }
}