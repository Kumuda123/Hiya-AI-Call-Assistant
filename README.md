#  AI Call Assistant for Hiya - Proof of Concept
## Overview
A Java console-based Proof of Concept demonstrating an AI Call Assistant for Hiya. This application simulates intelligent call handling through natural language commands, allowing users to manage incoming calls without touching their phone.

## Purpose & Goals
- Demonstrate conversational AI logic for call management
- Showcase tool-calling design patterns with mock telephony APIs
- Illustrate robust error management and user feedback
- Provide a foundation for voice-based call assistant development

## Supported Commands
- **Pickup**: "pick it up", "answer", "answer it", "take it", "accept"
- **Reject**: "cut it", "cut", "reject", "hang up", "decline" - Hang up this call without blocking
- **Block**: "block it", "block", "spam", "block this number" - Block the number permanently
- **Voicemail**: "send to voicemail", "voicemail", "vm", "leave a message"
- **Forward**: "forward", "transfer", "redirect" (interactively asks for destination: work/home/assistant)
- **Callback**: "call me back", "callback", "call me back after 7pm", "schedule"
- **ShowBlocked**: "show blocked", "blocked"

## Command Examples
- `"pick it up"` → Answers the call and connects you
- `"cut it"` → Hangs up this call (they can call back later)
- `"block it"` → Blocks the number permanently (they can't call again)
- `"send to voicemail"` → Redirects to voicemail
- `"forward"` → Asks where to forward (work/home/assistant) or a number, then forwards the call
- `"call me back after 7pm"` → Schedules callback at 7pm
- `"Show blocked"` → List of all the blocked users 
- `"quit"` → Exits the application

## Third-Party Integration (Twilio Lookup API)
This project integrates Twilio’s Lookup API to simulate real-world number validation and caller insights.  
When a random incoming number is generated, the application checks whether it is legitimate, identifies the carrier type, and applies a simple spam heuristic.

### Functionality Overview
- Caller Validation: Fetches caller name and carrier data using Twilio Lookup API.  
- Spam Heuristic: 
  - Marks Spam Likely if the caller name contains the word *spam*.  
  - Flags Potential Spam” if the carrier type is *VOIP*.  
  - Otherwise labels the number as Legitimate
  - 💡 You can replace or augment this functionality using Hiya’s API for more advanced reputation scoring and spam classification.

## Mock API Endpoints
- `pickupCall()` - Simulates Twilio call answer/connect
- `rejectCall()` - Simulates hanging up without blocking (caller can call back)
- `blockNumber()` - Simulates Hiya block API (permanent block)
- `sendToVoicemail()` - Simulates Twilio voicemail redirect
- `forwardCall()` - Simulates Twilio call forwarding
- `scheduleCallback()` - Simulates Hiya callback scheduling
  
## ErrorHandler.java
- Manages unclear command inputs with clarification prompts
- Handles API action failures with user-friendly fallback messages
- Implements retry logic (max 3 attempts before auto-voicemail)
- Provides confidence-based confirmation for ambiguous commands
- Displays helpful command suggestions

## Features Implemented
### :white_check_mark: Core Features
1. **Interactive Call Simulation** - Random incoming calls with caller ID, category, and spam rating
2. **Natural Language Processing** - Flexible command parsing supporting multiple phrasings
3. **Twilio API Integration** - Checks spam/fraud numbers
4. **Mock API Integration** - Realistic API call simulation with delays and response logging
5. **Comprehensive Error Handling**:
   - Unclear command clarification (with retry limits)
   - Low confidence confirmation prompts
   - API failure handling with fallback options
6. **User-Friendly Feedback** - Clear AI assistant responses summarizing each action
7. **Multiple Call Scenarios** - Various caller types (spam, legitimate, family, business)

## Future Enhancements (Next Phase)

1. **Google Calendar Integration** — Automatically schedule callbacks and check user availability before booking.
2. **Voice Integration** — Connect to real speech recognition APIs for hands-free operation.
3. **Real Telephony Integration** — Use actual Twilio or Hiya APIs for live call routing, blocking, and spam detection.
4. **Web Interface** — Develop a Spring Boot web dashboard to view call history, blocked numbers, and scheduled callbacks.
5. **Unit Testing Suite** - JUnit tests for each component
6. **Command Processing Improvements** — Refactor current in-code logic into a scalable and modular design:
   - Explore **Natural Language Processing (NLP)** for robust intent recognition  
   - Move toward a **microservices architecture** for independent command handling  
   - Deploy on **AWS** for scalability, reliability, and real-time performance


