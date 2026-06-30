/**
 * ═══════════════════════════════════════════════════════════════════
 *  Sri Dairy Farm ERP — Frontend API Helper
 *  Place this file next to your index.html
 *  Add <script src="api.js"></script> BEFORE the closing </body> tag
 * ═══════════════════════════════════════════════════════════════════
 *
 * HOW IT WORKS:
 * Every function below calls your Java Spring Boot backend running at
 * http://localhost:8080 using the browser's built-in fetch() API.
 *
 * fetch() sends an HTTP request and returns a Promise.
 * We use async/await to wait for the result without freezing the page.
 */

// ─── BASE URL ──────────────────────────────────────────────────────────────────
// Change this if your backend runs on a different port or server
const BASE_URL = "http://localhost:8080/api";


// ═══════════════════════════════════════════════════════════════════
//  UTILITY: central fetch function with error handling
// ═══════════════════════════════════════════════════════════════════
async function apiCall(endpoint, options = {}) {
    try {
        // Default headers: tell backend we're sending JSON
        options.headers = {
            "Content-Type": "application/json",
            ...options.headers
        };

        const response = await fetch(BASE_URL + endpoint, options);

        // If the server returned an error (4xx or 5xx), throw it
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`HTTP ${response.status}: ${errorText}`);
        }

        // Parse and return the JSON response body
        return await response.json();

    } catch (error) {
        console.error("API Error:", error.message);
        showToast("Error: " + error.message, "error"); // uses your existing toast
        throw error; // re-throw so calling code can handle it
    }
}


// ═══════════════════════════════════════════════════════════════════
//  CLIENTS API
// ═══════════════════════════════════════════════════════════════════

/** Get all clients from backend */
async function apiGetClients() {
    return await apiCall("/clients");
    // Returns: [{id:1, name:"Kumar", scheme:"gold", ...}, ...]
}

/** Get clients filtered by scheme */
async function apiGetClientsByScheme(scheme) {
    return await apiCall(`/clients/scheme/${scheme}`);
}

/** Search clients by name */
async function apiSearchClients(name) {
    return await apiCall(`/clients/search?name=${encodeURIComponent(name)}`);
}

/** Get dashboard stats (counts) */
async function apiGetClientStats() {
    return await apiCall("/clients/stats");
    // Returns: { total: 38, gold: 6, silver: 18, smsEnabled: 24 }
}

/** Create a new client
 *  @param {Object} clientData - { name, phone, scheme, address, smsEnabled }
 */
async function apiCreateClient(clientData) {
    return await apiCall("/clients", {
        method: "POST",
        body: JSON.stringify(clientData)
        // JSON.stringify converts JS object → JSON string
        // e.g. { name: "Kumar" } → '{"name":"Kumar"}'
    });
}

/** Update an existing client
 *  @param {number} id - client ID
 *  @param {Object} clientData - fields to update
 */
async function apiUpdateClient(id, clientData) {
    return await apiCall(`/clients/${id}`, {
        method: "PUT",
        body: JSON.stringify(clientData)
    });
}

/** Delete a client
 *  @param {number} id - client ID to delete
 */
async function apiDeleteClient(id) {
    return await apiCall(`/clients/${id}`, {
        method: "DELETE"
    });
}


// ═══════════════════════════════════════════════════════════════════
//  MILK PURCHASES API
// ═══════════════════════════════════════════════════════════════════

/** Get all purchases */
async function apiGetPurchases() {
    return await apiCall("/purchases");
}

/** Get today's purchases */
async function apiGetTodayPurchases() {
    return await apiCall("/purchases/today");
}

/** Get purchases for a specific client */
async function apiGetClientPurchases(clientId) {
    return await apiCall(`/purchases/client/${clientId}`);
}

/** Get today's summary (total litres + revenue for dashboard metrics) */
async function apiGetTodaySummary() {
    return await apiCall("/purchases/summary");
    // Returns: { date: "2025-06-05", litres: 1240.0, revenue: 48320.0 }
}

/** Save a new milk purchase
 *  Backend will auto-calculate rewards and update client balance.
 *  @param {Object} purchaseData - { client: {id: 1}, litres, grade, pricePerLitre }
 */
async function apiCreatePurchase(purchaseData) {
    return await apiCall("/purchases", {
        method: "POST",
        body: JSON.stringify(purchaseData)
    });
}


// ═══════════════════════════════════════════════════════════════════
//  PRODUCTS API
// ═══════════════════════════════════════════════════════════════════

/** Get all products */
async function apiGetProducts() {
    return await apiCall("/products");
}

/** Create a product */
async function apiCreateProduct(productData) {
    return await apiCall("/products", {
        method: "POST",
        body: JSON.stringify(productData)
    });
}

/** Update a product (e.g. update stock after a sale) */
async function apiUpdateProduct(id, productData) {
    return await apiCall(`/products/${id}`, {
        method: "PUT",
        body: JSON.stringify(productData)
    });
}

/** Delete a product */
async function apiDeleteProduct(id) {
    return await apiCall(`/products/${id}`, { method: "DELETE" });
}

/** Get low-stock products */
async function apiGetLowStockProducts() {
    return await apiCall("/products/low-stock");
}


// ═══════════════════════════════════════════════════════════════════
//  USAGE EXAMPLES (copy these patterns into your HTML as needed)
// ═══════════════════════════════════════════════════════════════════
/*

EXAMPLE 1 — Load clients when page opens:
─────────────────────────────────────────
async function loadClients() {
    const clients = await apiGetClients();
    // clients is now a JS array like your existing clientData[]
    clients.forEach(client => {
        // render each client row in your table
    });
}

EXAMPLE 2 — Save a new client from "Add Client" form:
───────────────────────────────────────────────────────
async function saveNewClient() {
    const data = {
        name:       document.getElementById('add-client-name').value,
        phone:      document.getElementById('add-client-phone').value,
        scheme:     document.getElementById('add-client-scheme').value,
        smsEnabled: document.getElementById('add-client-sms').checked
    };
    const saved = await apiCreateClient(data);
    showToast('Client ' + saved.name + ' added!', 'success');
    loadClients(); // refresh the list
}

EXAMPLE 3 — Load dashboard metrics on page load:
─────────────────────────────────────────────────
async function loadDashboard() {
    const summary = await apiGetTodaySummary();
    document.querySelector('.metric-value').textContent = summary.litres + ' L';

    const stats = await apiGetClientStats();
    document.getElementById('total-clients-count').textContent = stats.total;
    document.getElementById('gold-count').textContent  = stats.gold;
    document.getElementById('silver-count').textContent = stats.silver;
}

EXAMPLE 4 — Save a daily milk purchase:
────────────────────────────────────────
async function savePurchase() {
    const data = {
        client:        { id: selectedClientId },  // just the id is enough
        litres:        parseFloat(document.getElementById('purchase-litres').value),
        grade:         document.getElementById('purchase-grade').value,
        pricePerLitre: parseFloat(document.getElementById('purchase-price').value)
    };
    const saved = await apiCreatePurchase(data);
    showToast('Purchase saved! Gold earned: ' + saved.goldEarned.toFixed(3) + 'g', 'success');
}

*/
