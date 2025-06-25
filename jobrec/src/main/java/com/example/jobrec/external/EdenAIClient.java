package com.example.jobrec.external;

import com.example.jobrec.entity.ExtractRequestBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class EdenAIClient {
    private static final String EDENAI_TOKEN = "Bearer"+"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiZDNiODZhNGItZGEwZC00M2U1LWEzZDktMTQ1N2I2MDM5YThjIiwidHlwZSI6ImFwaV90b2tlbiJ9.x8hUIqZ4kxUmjF3PjclfG38ZqG7QaRz8zPMoPRQoDuw";

    private static final String EXTRACT_URL = "https://api.edenai.run/v2/text/keyword_extraction";

    public Set<String> extract(String article, int keywords_num) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();
        HttpPost request = new HttpPost(EXTRACT_URL);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Authorization", EDENAI_TOKEN);
        request.setHeader("accept", "application/json");
        ExtractRequestBody body = new ExtractRequestBody(article);

        String jsonBody;
        try {
            jsonBody = mapper.writeValueAsString(body);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return Collections.emptySet();
        }

        try {
            request.setEntity(new StringEntity(jsonBody));
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return Collections.emptySet();
        }

        ResponseHandler<Set<String>> responseHandler = response -> {
            if (response.getStatusLine().getStatusCode() != 200) {
                return Collections.emptySet();
            }
            HttpEntity entity = response.getEntity();
            if (entity==null){
                return Collections.emptySet();
            }
            JsonNode root = mapper.readTree(entity.getContent());
            JsonNode OpenAI = root.get("OpenAI");
            System.out.println(OpenAI.asText());
            JsonNode OpenAIItems = OpenAI.get("items");

            TreeMap<Double, ArrayList<String>> keywords = new TreeMap<>();
            Iterator<JsonNode> itemsIterator = OpenAIItems.elements();
            while (itemsIterator.hasNext()){
                JsonNode item = itemsIterator.next();
                String keyword = item.get("text").asText();
                double importance = item.get("importance").asDouble();
                ArrayList<String> words_list = keywords.getOrDefault(importance, new ArrayList<>());
                words_list.add(keyword);
                keywords.put(importance, words_list);
            }
            Set<String> refined_set = new HashSet<>();
            while (refined_set.size() < keywords_num && !keywords.isEmpty()){
                ArrayList<String> words_list = keywords.pollLastEntry().getValue();
                while (!words_list.isEmpty()&& refined_set.size() < keywords_num){
                    refined_set.add(words_list.remove(0));
                }
            }
            return refined_set;
        };

        try {
            return httpClient.execute(request,responseHandler);
        } catch (IOException e){
            e.printStackTrace();
        }
        return Collections.emptySet();
    }
}
