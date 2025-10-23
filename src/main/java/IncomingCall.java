public class IncomingCall {
    private String phoneNumber;
    private String callerName;
    private String hiyaRating;
    private String category;

    public IncomingCall(String phoneNumber, String callerName, String hiyaRating, String category) {
        this.phoneNumber = phoneNumber;
        this.callerName = callerName;
        this.hiyaRating = hiyaRating;
        this.category = category;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCallerName() {
        return callerName;
    }

    public String getHiyaRating() {
        return hiyaRating;
    }

    public String getCategory() {
        return category;
    }
}