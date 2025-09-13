# Little Shop - User Stories (Development Backlog)

## Epic: Store Inventory Management System
**As a** store owner  
**I want** a simple desktop application to manage my inventory and sales  
**So that** I can track products and transactions without paper records

---

## User Stories

### Story 1: Add Products to Inventory
**As a** store owner  
**I want** to add new products with name, price, and stock quantity  
**So that** I can build my inventory catalog  

**Acceptance Criteria:**
- I can enter product name, price, and stock
- System validates that product names are unique
- System validates that price and stock are valid numbers
- System prevents empty product names
- Product gets assigned a unique ID automatically

---

### Story 2: View All Products
**As a** store owner  
**I want** to view all products in my inventory  
**So that** I can see what items I have available  

**Acceptance Criteria:**
- I can see all products with ID, name, price, and stock
- If no products exist, system shows appropriate message
- Information displays in a clear, readable format

---

### Story 3: Process Customer Purchases
**As a** store owner  
**I want** to sell products to customers and update stock automatically  
**So that** my inventory stays accurate after each sale  

**Acceptance Criteria:**
- I can select products by name for purchase
- I can specify quantity to purchase
- System validates product exists and has sufficient stock
- Stock automatically decreases after purchase
- System prevents purchasing more than available stock
- I can add multiple products to the same transaction

---

### Story 4: View Sales History
**As a** store owner  
**I want** to see all completed transactions  
**So that** I can track my daily sales  

**Acceptance Criteria:**
- I can view all purchase records with details
- Each record shows product name, quantity, unit price, and total
- If no sales exist, system shows appropriate message

---

### Story 5: Find Specific Products
**As a** store owner  
**I want** to search for products by name  
**So that** I can quickly check product details  

**Acceptance Criteria:**
- I can search by product name (case insensitive)
- System shows complete product information if found
- System shows error message if product doesn't exist

---

### Story 6: View Price Analysis
**As a** store owner  
**I want** to see my highest and lowest priced products  
**So that** I can analyze my pricing strategy  

**Acceptance Criteria:**
- System shows the most expensive product with details
- System shows the least expensive product with details
- Works correctly even with single product in inventory

---

### Story 7: Exit Application Safely
**As a** store owner  
**I want** to safely exit the application  
**So that** I can close it when done working  

**Acceptance Criteria:**
- System asks for confirmation before closing
- I can cancel the exit if I change my mind
- Application closes completely when confirmed
