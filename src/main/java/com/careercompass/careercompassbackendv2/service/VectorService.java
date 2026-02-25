package com.careercompass.careercompassbackendv2.service;

import com.careercompass.careercompassbackendv2.model.CareerVector;
import com.careercompass.careercompassbackendv2.model.UserProfileRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VectorService {

    private List<Double> idfValues = new ArrayList<>();

    private Map<String, Integer> vocabulary = new HashMap<>();
    private List<CareerVector> careerVectors = new ArrayList<>();

    // ================= LOAD JSON FILES =================

    @PostConstruct
    public void loadVectors() {
        try {

            ObjectMapper mapper = new ObjectMapper();

            // 1️⃣ Load IDF values first
            InputStream idfStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("tfidf_idf_v2.json");

            idfValues = mapper.readValue(
                    idfStream,
                    new TypeReference<List<Double>>() {}
            );

            // 2️⃣ Load stored vectors
            InputStream vectorStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("career_vectors_v2.json");

            careerVectors = mapper.readValue(
                    vectorStream,
                    new TypeReference<List<CareerVector>>() {}
            );

            // 3️⃣ Load vocabulary
            InputStream vocabStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream("tfidf_vocabulary_v2.json");

            vocabulary = mapper.readValue(
                    vocabStream,
                    new TypeReference<Map<String, Integer>>() {}
            );

            System.out.println("Loaded vectors: " + careerVectors.size());
            System.out.println("Loaded vocabulary size: " + vocabulary.size());
            System.out.println("Loaded IDF size: " + idfValues.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= BUILD USER TEXT =================

    private String buildUserText(UserProfileRequest request) {
        return "Skills: " + request.getSkills() + ". " +
                "Education: " + request.getEducation() + ". " +
                "Interests: " + request.getInterests() + ". " +
                "Experience: " + request.getExperience() + " years.";
    }

    // ================= TEXT → VECTOR =================

    private List<Double> convertTextToVector(String text) {

        List<Double> vector = new ArrayList<>(
                Collections.nCopies(vocabulary.size(), 0.0)
        );

        String[] words = text.toLowerCase().split("\\W+");

        Map<String, Integer> termFreq = new HashMap<>();

        for (String word : words) {
            if (vocabulary.containsKey(word)) {
                termFreq.put(word, termFreq.getOrDefault(word, 0) + 1);
            }
        }

        for (Map.Entry<String, Integer> entry : termFreq.entrySet()) {
            int index = vocabulary.get(entry.getKey());
            double tf = entry.getValue();
            double idf = idfValues.get(index);
            vector.set(index, tf * idf);
        }

        return vector;
    }

    private List<Double> normalizeVector(List<Double> vector) {

        double norm = 0.0;

        for (double v : vector) {
            norm += v * v;
        }

        norm = Math.sqrt(norm);

        if (norm == 0) return vector;

        for (int i = 0; i < vector.size(); i++) {
            vector.set(i, vector.get(i) / norm);
        }

        return vector;
    }

    // ================= MAIN RECOMMEND METHOD =================

    public List<String> recommendFromProfile(UserProfileRequest request, int topK) {

        String userText = buildUserText(request);

        List<Double> inputVector = convertTextToVector(userText);
        inputVector = normalizeVector(inputVector);

        return findTopMatches(inputVector, topK);
    }

    // ================= FIND TOP MATCHES =================

    public List<String> findTopMatches(List<Double> inputVector, int topK) {

        return careerVectors.stream()
                .map(career -> new AbstractMap.SimpleEntry<>(
                        career.getJob_role(),
                        cosineSimilarity(inputVector, career.getVector())
                ))
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .map(Map.Entry::getKey)
                .distinct()
                .limit(topK)
                .collect(Collectors.toList());
    }

    // ================= COSINE SIMILARITY =================

    private double cosineSimilarity(List<Double> v1, List<Double> v2) {

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < v1.size(); i++) {
            dotProduct += v1.get(i) * v2.get(i);
            normA += v1.get(i) * v1.get(i);
            normB += v2.get(i) * v2.get(i);
        }

        if (normA == 0 || normB == 0) return 0;

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}