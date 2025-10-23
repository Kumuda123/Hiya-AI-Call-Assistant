# 📞 AI Call Assistant for Hiya — Proof of Concept  

## 🧩 Overview  
This Java console-based **Proof of Concept (PoC)** demonstrates an **AI Call Assistant** that simulates intelligent call handling through natural language commands.  
It allows users to manage incoming calls hands-free — answering, rejecting, blocking, forwarding, or scheduling callbacks — as if interacting with a real AI assistant.  

---

## 🎯 Purpose & Goals  
- Demonstrate **conversational AI logic** for intelligent call management  
- Showcase **tool-calling and command pattern** design with mock telephony APIs  
- Implement **robust error handling** and user feedback mechanisms  
- Provide a **foundation for future voice-based or telephony-integrated AI systems**  

---

## 💬 Supported Commands  

| Action | Example Phrases | Description |
|--------|----------------|--------------|
| **Pickup** | “pick it up”, “answer”, “take it”, “accept” | Answers the incoming call |
| **Reject** | “cut it”, “hang up”, “decline” | Hangs up without blocking |
| **Block** | “block it”, “spam”, “block this number” | Blocks the number permanently |
| **Voicemail** | “send to voicemail”, “leave a message” | Sends caller to voicemail |
| **Forward** | “forward”, “transfer”, “redirect” | Forwards the call to a chosen destination |
| **Callback** | “call me back after 7pm”, “schedule callback” | Schedules a callback |
| **ShowBlocked** | “show blocked”, “blocked” | Displays all blocked numbers |
| **Quit** | “quit”, “exit” | Exits the application |

---

## 🧠 Command Examples  

- `"pick it up"` → Answers and connects the call  
- `"cut it"` → Rejects the call (caller can try later)  
- `"block it"` → Blocks the number permanently  
- `"send to voicemail"` → Sends caller to voicemail  
- `"forward"` → Asks where to forward (work/home/assistant)  
- `"call me back after 7pm"` → Schedules callback at 7pm  
- `"show blocked"` → Lists all blocked numbers  
- `"quit"` → Exits the application  

---

## 🔗 Third-Party Integration (Twilio Lookup API)

This project integrates **Twilio’s Lookup API** to simulate real-world number validation and caller insights.  
When a random incoming number is generated, the system checks whether it’s legitimate, identifies the carrier type, and applies a simple spam heuristic.

### ⚙️ Functionality Overview  
- **Caller Validation:** Fetches caller name and carrier data using Twilio Lookup API.  
- **Spam Heuristic:**  
  - Marks **Spam Likely** if the caller name contains the word *spam*.  
  - Flags **Potential Spam** if the carrier type is *VOIP*.  
  - Otherwise labels the number as **Legitimate**.  
- **Fallback Handling:** Defaults to `"Unknown"` if lookup fails.  

> 💡 This logic can be replaced or enhanced with **Hiya’s API** for more advanced reputation scoring and spam classification.  

---

## 🧩 Mock API Endpoints  

| Method | Description |
|---------|-------------|
| `pickupCall()` | Simulates Twilio call answer/connect |
| `rejectCall()` | Simulates hang-up without blocking |
| `blockNumber()` | Simulates Hiya block API (permanent block) |
| `sendToVoicemail()` | Simulates Twilio voicemail redirect |
| `forwardCall()` | Simulates Twilio call forwarding |
| `scheduleCallback()` | Simulates Hiya callback scheduling |

---

## ⚠️ Error Handling (`ErrorHandler.java`)  
- Handles unclear or partial commands with clarification prompts  
- Implements retry logic (up to 3 attempts before auto-voicemail)  
- Catches simulated API errors with fallback options  
- Confirms low-confidence interpretations  
- Suggests valid commands for user guidance  

---

## ✅ Features Implemented  

1. **Interactive Call Simulation** — Randomized incoming calls with caller ID, category, and spam rating  
2. **Natural Language Command Parsing** — Supports multiple phrasing styles  
3. **Twilio API Integration** — Performs caller legitimacy checks and spam detection  
4. **Mock API Simulation** — Realistic asynchronous behavior and response delays  
5. **Robust Error Handling** — Clarification prompts, retries, and fallbacks  
6. **User-Friendly Feedback** — AI assistant-like messages summarizing every action  
7. **Multi-Scenario Simulation** — Handles spam, family, business, and unknown callers  

---

## 🚀 Future Enhancements (Next Phase)  

1. **Google Calendar Integration** — Automatically schedule callbacks and check user availability.  
2. **Voice Integration** — Connect to real speech recognition APIs for true hands-free experience.  
3. **Real Telephony Integration** — Use live Twilio or Hiya APIs for actual call routing, blocking, and voicemail.  
4. **Web Dashboard** — Build a Spring Boot interface to display call history, blocked numbers, and scheduled callbacks.  
5. **Unit Testing Suite** — Add JUnit coverage for core modules and mock API interactions.  
6. **Command Processing Redesign** — Make the logic scalable and modular by:  
   - Adopting **Natural Language Processing (NLP)** for advanced intent recognition  
   - Moving toward a **microservices-based architecture** for better separation of concerns  
   - Deploying components on **AWS** for cloud scalability, fault tolerance, and performance
  

## 👩‍💻 Developer  

## 👩‍💻 Author

**Kumuda Aggarwal**  
_MS in Computer Science, New York University
[LinkedIn](https://www.linkedin.com/in/kumuda-aggarwal/)

