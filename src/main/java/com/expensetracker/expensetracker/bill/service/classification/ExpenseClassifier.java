package com.expensetracker.expensetracker.bill.service.classification;

import com.expensetracker.expensetracker.model.ExpenseRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseClassifier {

    // Direct merchant-to-category mappings
    private Map<String, String> merchantToCategoryMap = new HashMap<>();

    // Category to keyword mapping for fallback classification
    private static final Map<String, List<String>> CATEGORY_KEYWORDS = new HashMap<>();

    static {
        CATEGORY_KEYWORDS.put("food_and_dining", Arrays.asList("restaurant", "cafe", "kitchen", "dine", "meal", "bar", "menu", "food"));
        CATEGORY_KEYWORDS.put("transportation", Arrays.asList("taxi", "uber", "bus", "train", "fare", "transport", "lyft"));
        CATEGORY_KEYWORDS.put("housing", Arrays.asList("rent", "mortgage", "apartment", "maintenance", "house", "home"));
        CATEGORY_KEYWORDS.put("entertainment", Arrays.asList("cinema", "movie", "concert", "theatre", "tickets", "amusement", "entertainment"));
        CATEGORY_KEYWORDS.put("healthcare", Arrays.asList("hospital", "clinic", "pharmacy", "medical", "prescription", "healthcare"));
        CATEGORY_KEYWORDS.put("personal_care", Arrays.asList("salon", "spa", "barber", "beauty", "wellness", "personal care"));
        CATEGORY_KEYWORDS.put("shopping", Arrays.asList("store", "mall", "retail", "purchase", "clothing", "merchandise", "shopping"));
        CATEGORY_KEYWORDS.put("travel", Arrays.asList("hotel", "flight", "airline", "travel", "tour", "booking"));
        CATEGORY_KEYWORDS.put("education", Arrays.asList("tuition", "school", "course", "books", "university", "education"));
        CATEGORY_KEYWORDS.put("miscellaneous", Collections.emptyList());
    }

    public String classifyExpense(String ocrText, ExpenseRequest expenseRequest) {
        String merchantName = extractMerchantName(ocrText);
        String category = null;

        if (merchantName != null) {
            expenseRequest.setDescription(merchantName);
            // Try exact match
            category = merchantToCategoryMap.get(merchantName.toLowerCase());

            if (category == null) {
                // Try fuzzy matching against known merchants
                category = fuzzyMerchantLookup(merchantName);

                if (category == null) {
                    // No direct or fuzzy match found, fallback to keyword-based
                    category = classifyByKeywords(ocrText);

                    // Store this merchant for faster next time
                    merchantToCategoryMap.put(merchantName.toLowerCase(), category);
                }
            }
        } else {
            // If no merchant line found, fallback directly to keyword-based classification
            category = classifyByKeywords(ocrText);
            expenseRequest.setDescription(category);
        }
        expenseRequest.setCategory(category);
        return category;
    }

    private String extractMerchantName(String ocrText) {
        if (ocrText == null || ocrText.trim().isEmpty()) return null;
        String[] lines = ocrText.split("\\r?\\n");
        for (String line : lines) {
            if (isLikelyMerchantName(line)) {
                return line.trim();
            }
        }
        return null;
    }

    // To guess a merchant name line
    private boolean isLikelyMerchantName(String line) {
        if (line == null || line.trim().isEmpty()) return false;
        String trimmed = line.trim();
        if (trimmed.length() < 3) return false;
        // Avoid purely numeric lines
        if (trimmed.matches("\\d+.*")) return false;
        // Check if contains alphabets
        return trimmed.matches(".*[a-zA-Z].*");
    }

    private String classifyByKeywords(String ocrText) {
        if (ocrText == null || ocrText.trim().isEmpty()) {
            return "miscellaneous";
        }

        String normalizedText = ocrText.toLowerCase();
        String bestCategory = "miscellaneous";
        int maxMatches = 0;

        for (Map.Entry<String, List<String>> entry : CATEGORY_KEYWORDS.entrySet()) {
            String category = entry.getKey();
            List<String> keywords = entry.getValue();
            int count = 0;

            for (String keyword : keywords) {
                if (normalizedText.contains(keyword.toLowerCase())) {
                    count++;
                }
            }

            if (count > maxMatches) {
                maxMatches = count;
                bestCategory = category;
            }
        }

        return bestCategory;
    }

    // Fuzzy lookup: find best match merchant in merchantToCategoryMap keys
    private String fuzzyMerchantLookup(String merchantName) {
        String lowerMerchant = merchantName.toLowerCase();
        String bestMerchant = null;
        int bestDistance = Integer.MAX_VALUE;
        int threshold = 3; // adjust threshold based on experimentation

        for (String knownMerchant : merchantToCategoryMap.keySet()) {
            int dist = levenshteinDistance(lowerMerchant, knownMerchant);
            if (dist < bestDistance && dist <= threshold) {
                bestDistance = dist;
                bestMerchant = knownMerchant;
            }
        }

        if (bestMerchant != null) {
            return merchantToCategoryMap.get(bestMerchant);
        }
        return null;
    }

    // Levenshtein Distance for fuzzy matching
    private int levenshteinDistance(String a, String b) {
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j <= b.length(); j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cost = (a.charAt(i-1) == b.charAt(j-1)) ? nw : nw + 1;
                nw = costs[j];
                costs[j] = Math.min(1 + Math.min(costs[j], costs[j - 1]), cost);
            }
        }
        return costs[b.length()];
    }
}

