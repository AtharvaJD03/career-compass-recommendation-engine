# Career Compass – Recommendation Engine

A TF-IDF based Career Recommendation Engine built using Spring Boot and Cosine Similarity.

This backend system takes user profile input and recommends the Top 3 most relevant career roles based on vector similarity against a precomputed dataset.

---

## ⚙️ How To Run Locally

Follow these steps to run the project on your local machine.


### 1️⃣ Prerequisites

Make sure you have the following installed:

- Java 21
- Maven
- IntelliJ IDEA (recommended)

Check your Java version:

```bash
java -version
```

### 2️⃣ Clone the Repository
```bash
git clone https://github.com/AtharvaJD03/career-compass-recommendation-engine.git
```

Move into the project directory:
```bash
cd career-compass-recommendation-engine
```

### 3️⃣ Open in IntelliJ IDEA

Open IntelliJ IDEA

Click Open

Select the cloned project folder

Wait for Maven dependencies to download automatically


### 4️⃣ Run the Application

Locate:
```bash
CareerCompassBackendV2Application.java
```

Right-click → Run

OR run using Maven from terminal:
```bash
mvn spring-boot:run
```
### 5️⃣ Access the Application

Once the application starts successfully, open your browser:
```bash
http://localhost:8080
```

OR use the API endpoint:
```bash
POST http://localhost:8080/api/recommend
```
---

## 🚀 Features

- TF-IDF vectorization (precomputed in Google Colab)
- Cosine similarity scoring
- Unique Top-3 job role recommendations
- REST API integration ready
- JSON-based vector database
- Frontend-ready architecture
- CORS enabled for cross-origin integration

---

## 🧠 How It Works

1. Career dataset is processed in Google Colab.
2. TF-IDF vectors are generated.
3. Vocabulary and IDF values are saved as JSON.
4. Spring Boot loads:
   - `career_vectors_v2.json`
   - `tfidf_vocabulary_v2.json`
   - `tfidf_idf_v2.json`
5. User profile input is:
   - Converted to text
   - Transformed into TF-IDF vector
   - Normalized
   - Compared using Cosine Similarity
6. Top 3 unique job roles are returned.

---

## 🛠 Tech Stack

- Java 21
- Spring Boot 4
- Maven
- Jackson (JSON Parsing)
- Cosine Similarity Algorithm
- TF-IDF (Scikit-learn in Google Colab)

---
