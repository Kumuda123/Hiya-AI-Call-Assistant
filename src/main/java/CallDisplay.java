public class CallDisplay {

    public static void showIncomingCall(IncomingCall call) {
        System.out.println("📱 INCOMING CALL");
        System.out.println("─".repeat(60));
        System.out.println("📞 From: " + call.getCallerName() + " (" + call.getPhoneNumber() + ")");
        System.out.println("🏷️  Category: " + call.getCategory());
        System.out.println("🔍 Hiya Rating: " + call.getHiyaRating());
        System.out.println("─".repeat(60));
        System.out.println();
    }
}
