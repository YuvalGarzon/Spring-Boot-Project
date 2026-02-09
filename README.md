# Spring Boot Project – Gym Backend + Simulation Integration

This repository contains a **Spring Boot backend** that manages the core “gym” domain (users / objects / commands) and also supports **real-time integration** with a separate Unity-based simulator project.

It includes:
- REST API controllers (admin/user/object/command)
- Persistence layer (MongoDB integration via Spring Data)
- A **TCP listener service** for receiving simulation events
- A **WebSocket** layer for broadcasting updates in real time (optional)

---

## Related Repository (Unity Simulation)

The Unity simulator that connects to this backend is here:

➡️ https://github.com/YuvalGarzon/Gym-simulation

### How it’s connected
The Unity project acts like a “gym exercise simulator”:
- When the user performs an action (e.g., pressing the **Rep** button), the simulator sends a TCP message to this backend.
- This backend receives that message via `TcpListenerService`, handles it in the server logic, and can optionally push updates to clients via WebSocket.

Example message (from the Unity repo README):
- `rep_done:<rep_number>`  (e.g., `rep_done:3`)

> In other words:  
> **Gym-simulation = client/simulator** → sends events  
> **Spring-Boot-Project = server/backend** → processes events + manages data

---

## Tech Stack
- Java + Spring Boot
- Gradle
- MongoDB (via Spring Data)
- Docker Compose (for local dependencies)
- WebSocket (server-side support)
- TCP listener (simulation events)

---

## Project Structure (High Level)

Common packages you’ll see:
- `Controller/` – REST controllers (e.g., AdminController, UserController, ObjectController, CommandController)
- `BusinessLogic/` – services + converters (business rules & transformations)
- `Repository/` – entities + CRUD repositories (Mongo persistence)
- `Web/` – TCP listener + WebSocket configuration/handlers + simulation entry points

---

## Run Locally

### 1) Start dependencies (MongoDB)
If your `compose.yaml` includes MongoDB (recommended), run:

```bash
docker compose up -d
```

### 2) Run the Spring Boot server
From the repository root:

```bash
./gradlew bootRun
```

(Windows)
```bash
gradlew.bat bootRun
```

---

## Running With the Unity Simulator

### Step-by-step
1. Start this backend (Spring Boot).
2. Open and run the Unity project from:
   https://github.com/YuvalGarzon/Gym-simulation
3. In the simulator, perform actions (e.g., Rep button).
4. The simulator sends TCP messages (like `rep_done:1`) to this backend.
5. The backend receives and handles the event via the TCP listener service.

### TCP Connection (expected by the simulator)
The Unity repo expects the server to listen on:
- `127.0.0.1:5050`

If you changed the TCP port/host on the backend side, make sure it matches what the Unity simulator is configured to use.

---

## Real-Time Updates (WebSocket)
This backend includes WebSocket configuration and handlers.  
If you connect a WebSocket client (web / app), you can broadcast simulation events and/or state changes live (depending on your current implementation).

---

## What This Backend Manages (Domain)
At a high level the backend persists and manages:
- **Users** (roles, permissions, etc.)
- **Objects** (gym equipment / status fields)
- **Commands** (actions/events that can represent operations in the system)

Exact request/response formats and routes are implemented in the controllers under `Controller/`.

---

## Notes for Development
- If you add/change the simulation protocol (TCP message format), update both:
  - `TcpListenerService` / simulation handling in this repo
  - the Unity sender logic in the Gym-simulation repo

---

## Link Back to Simulator
Unity simulator repo: https://github.com/YuvalGarzon/Gym-simulation


### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.3/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.3/gradle-plugin/packaging-oci-image.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.4.3/reference/using/devtools.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.3/reference/web/servlet.html)
* [Storing Data in memory using Key-Value Repository](https://docs.spring.io/spring-data/keyvalue/reference/)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [Spring Data KeyValue Project at GitHub](https://github.com/spring-projects/spring-data-keyvalue)
