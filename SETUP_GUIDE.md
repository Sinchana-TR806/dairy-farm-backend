# Sri Dairy Farm ERP — Complete Backend Setup Guide
## Java Spring Boot + Your Existing HTML Frontend

---

## 📁 PROJECT FILE STRUCTURE

```
dairy-farm-backend/
│
├── pom.xml                          ← Maven config (dependencies)
├── api.js                           ← Copy this next to your index.html
│
└── src/
    └── main/
        ├── java/com/sridairy/
        │   ├── DairyFarmApplication.java        ← Main entry point
        │   ├── config/
        │   │   └── CorsConfig.java              ← Allows frontend to call backend
        │   ├── model/
        │   │   ├── Client.java                  ← Client database table
        │   │   ├── MilkPurchase.java            ← Milk purchase table
        │   │   └── Product.java                 ← Products table
        │   ├── repository/
        │   │   ├── ClientRepository.java        ← Database queries for clients
        │   │   ├── MilkPurchaseRepository.java  ← Database queries for purchases
        │   │   └── ProductRepository.java       ← Database queries for products
        │   ├── service/
        │   │   ├── ClientService.java           ← Business logic for clients
        │   │   └── MilkPurchaseService.java     ← Reward calculations
        │   └── controller/
        │       ├── ClientController.java        ← API endpoints for clients
        │       ├── MilkPurchaseController.java  ← API endpoints for purchases
        │       └── ProductController.java       ← API endpoints for products
        └── resources/
            └── application.properties           ← DB + server config
```

---

## 🚀 STEP-BY-STEP SETUP IN INTELLIJ

### STEP 1 — Install Requirements

Before anything else, make sure these are installed:

1. **Java 17 or later**
   - Check: open Command Prompt → type `java -version`
   - Download from: https://adoptium.net

2. **IntelliJ IDEA (Community Edition is free)**
   - Download from: https://www.jetbrains.com/idea/download/

3. **Maven** (usually bundled with IntelliJ, no separate install needed)

---

### STEP 2 — Create Project in IntelliJ

**Option A — Using Spring Initializr website (easiest):**

1. Go to https://start.spring.io
2. Fill in:
   - Project: **Maven**
   - Language: **Java**
   - Spring Boot: **3.2.0**
   - Group: `com.sridairy`
   - Artifact: `dairy-farm-erp`
   - Java version: **17**
3. Click "Add Dependencies" and add:
   - ✅ Spring Web
   - ✅ Spring Data JPA
   - ✅ H2 Database
   - ✅ Lombok
4. Click **Generate** → it downloads a ZIP file
5. Unzip it → Open IntelliJ → File → Open → select the unzipped folder

**Option B — Directly in IntelliJ:**

1. Open IntelliJ → Click **New Project**
2. Select **Spring Boot** from left sidebar
3. Fill in the same details as above
4. Click **Next**, add the same 4 dependencies
5. Click **Create**

---

### STEP 3 — Copy the Java Files

After your project is created, copy each file provided into the correct folder:

| File | Where to put it in IntelliJ |
|------|-----------------------------|
| `DairyFarmApplication.java` | `src/main/java/com/sridairy/` |
| `CorsConfig.java` | `src/main/java/com/sridairy/config/` |
| `Client.java` | `src/main/java/com/sridairy/model/` |
| `MilkPurchase.java` | `src/main/java/com/sridairy/model/` |
| `Product.java` | `src/main/java/com/sridairy/model/` |
| `ClientRepository.java` | `src/main/java/com/sridairy/repository/` |
| `MilkPurchaseRepository.java` | `src/main/java/com/sridairy/repository/` |
| `ProductRepository.java` | `src/main/java/com/sridairy/repository/` |
| `ClientService.java` | `src/main/java/com/sridairy/service/` |
| `MilkPurchaseService.java` | `src/main/java/com/sridairy/service/` |
| `ClientController.java` | `src/main/java/com/sridairy/controller/` |
| `MilkPurchaseController.java` | `src/main/java/com/sridairy/controller/` |
| `ProductController.java` | `src/main/java/com/sridairy/controller/` |
| `application.properties` | `src/main/resources/` (replace existing) |
| `pom.xml` | Root folder (replace existing) |

**How to create a new folder in IntelliJ:**
- Right-click on `com/sridairy` in the project panel
- New → Package → type `config` (or `model`, `repository`, etc.)

**How to create a new Java file:**
- Right-click on the package folder
- New → Java Class → paste the code

---

### STEP 4 — Enable Lombok in IntelliJ

Lombok generates getters/setters automatically. You need to enable it:

1. Go to **File → Settings** (or IntelliJ IDEA → Preferences on Mac)
2. Go to **Plugins**
3. Search for **Lombok** → Install if not installed → Restart IntelliJ
4. Go to **File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors**
5. Check ✅ **Enable annotation processing**
6. Click OK

---

### STEP 5 — Run the Backend

1. In the Project panel, find `DairyFarmApplication.java`
2. Click the **green play button ▶** next to the `main()` method
3. OR right-click → **Run 'DairyFarmApplication'**
4. Watch the Console panel at the bottom

**You should see:**
```
✅ Sri Dairy Farm ERP Backend is running!
📡 API ready at: http://localhost:8080
🗄️  H2 Console:  http://localhost:8080/h2-console
```

If you see errors, read TROUBLESHOOTING section below.

---

### STEP 6 — Test the Backend (before connecting frontend)

Open your browser and go to these URLs to confirm the backend works:

| URL | What you should see |
|-----|---------------------|
| `http://localhost:8080/api/clients` | `[]` (empty array, that's correct) |
| `http://localhost:8080/api/products` | `[]` |
| `http://localhost:8080/h2-console` | H2 database admin panel |

**To test creating a client:**
- Install Postman (free): https://www.postman.com/downloads/
- Send a POST request to `http://localhost:8080/api/clients`
- Body (raw JSON):
```json
{
  "name": "Kumar",
  "phone": "9876543210",
  "scheme": "gold",
  "smsEnabled": true
}
```
- You should get back the saved client with an auto-generated `id`

---

### STEP 7 — Connect Your Frontend (index.html)

1. Copy `api.js` to the **same folder** as your `index.html`

2. Add this line to your `index.html` just before `</body>`:
```html
<script src="api.js"></script>
```

3. Now replace your hardcoded `loadClients()` function with:
```javascript
async function loadClients() {
    try {
        clientData = await apiGetClients();  // fetch from backend
        renderClients();                      // your existing render function
    } catch (e) {
        showToast('Could not load clients from server', 'error');
    }
}
```

4. Replace your `openAddClient()` save button click with:
```javascript
async function saveClient() {
    const data = {
        name:       document.getElementById('add-client-name').value,
        phone:      document.getElementById('add-client-phone').value,
        scheme:     document.getElementById('add-client-scheme').value,
        smsEnabled: document.getElementById('add-client-sms').checked,
        address:    document.getElementById('add-client-address').value
    };
    const saved = await apiCreateClient(data);
    clientData.push(saved);
    renderClients();
    showToast('Client added!', 'success');
}
```

5. Load dashboard metrics on page start:
```javascript
async function loadDashboard() {
    const summary = await apiGetTodaySummary();
    // Update the metric cards in the dashboard
    // Find the "Milk today" card and update it:
    document.querySelector('#page-dashboard .metric-card.accent .metric-value')
        .textContent = summary.litres + ' L';

    const stats = await apiGetClientStats();
    document.getElementById('total-clients-count').textContent = stats.total;
    document.getElementById('gold-count').textContent  = stats.gold;
    document.getElementById('silver-count').textContent = stats.silver;
    document.getElementById('sms-count').textContent    = stats.smsEnabled;
}

// Call this at bottom of your script instead of loadClients():
loadDashboard();
loadClients();
```

---

### STEP 8 — Open Frontend

**Don't just double-click index.html!**
Use Live Server in VS Code, or IntelliJ's built-in server, because
opening `file://` directly may have CORS restrictions.

**Using VS Code:**
1. Install "Live Server" extension
2. Right-click `index.html` → **Open with Live Server**
3. It opens at `http://127.0.0.1:5500`

**Using IntelliJ:**
1. Open `index.html` in IntelliJ
2. Click the browser icon in the top right
3. It opens at `http://localhost:63342`

---

## 🔌 COMPLETE API REFERENCE

### CLIENTS
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/clients` | Get all clients |
| GET | `/api/clients/{id}` | Get one client |
| POST | `/api/clients` | Create client |
| PUT | `/api/clients/{id}` | Update client |
| DELETE | `/api/clients/{id}` | Delete client |
| GET | `/api/clients/search?name=xxx` | Search by name |
| GET | `/api/clients/scheme/gold` | Get gold members |
| GET | `/api/clients/stats` | Dashboard counts |

### MILK PURCHASES
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/purchases` | All purchases |
| POST | `/api/purchases` | Save purchase (auto-calculates rewards) |
| GET | `/api/purchases/today` | Today's purchases |
| GET | `/api/purchases/date?d=2025-06-05` | By date |
| GET | `/api/purchases/client/{id}` | By client |
| GET | `/api/purchases/summary` | Today's totals |

### PRODUCTS
| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/products` | All products |
| POST | `/api/products` | Add product |
| PUT | `/api/products/{id}` | Update product |
| DELETE | `/api/products/{id}` | Delete product |
| GET | `/api/products/low-stock` | Low stock alert |

---

## 🐛 TROUBLESHOOTING

**Error: "Port 8080 already in use"**
- Another program is using port 8080
- In `application.properties`, change `server.port=8080` to `server.port=8081`
- Update `BASE_URL` in `api.js` to `http://localhost:8081/api`

**Error: "CORS policy blocked"**
- Make sure `CorsConfig.java` is in the project
- Add your frontend's exact URL to `allowedOrigins` in CorsConfig.java
- Restart the backend after changing

**Error: "Could not autowire. No beans of type ClientRepository"**
- Make sure Lombok is installed and annotation processing is enabled (Step 4)
- Rebuild: Build → Rebuild Project

**Error: "Cannot find symbol: @Data"**
- Lombok annotation processing not enabled. Re-do Step 4.

**Data disappears when backend restarts**
- That's normal with H2 in-memory database (it's for development)
- To persist data: change `spring.jpa.hibernate.ddl-auto=update`
  and use `jdbc:h2:file:./dairydb` instead of `mem:dairydb`
- For production: switch to MySQL (see application.properties comments)

---

## ➡️ NEXT STEPS (After Everything Works)

1. **Switch to MySQL** for persistent data — see `application.properties` comments
2. **Add Spring Security** for login/password protection
3. **Add invoice generation** using iText PDF library
4. **Add SMS sending** using Twilio Java SDK
5. **Deploy to a server** so it works from any device on your network
