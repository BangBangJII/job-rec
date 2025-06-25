package com.example.jobrec.external;

import com.example.jobrec.entity.Item;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.Closeable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class SerpAPIClient {
    private static final String URL_TEMPLATE = "https://serpapi.com/search.json?engine=google_jobs&q=%s&hl=en&gl=ca&api_key=%s";
    private static final String API_KEY = "64036de09d8d059a5b260460e1aa814b4063131c1ae166afdb94fef38378ed7b";
    private static final String DEFAULT_KEYWORD = "developer";
    private static final String DEFAULT_LOCATION = "vancouver";

    public List<Item> search(String keyword, String location) {
        if (keyword == null || keyword.isEmpty()) {
            keyword = DEFAULT_KEYWORD;
        } else if (location == null || location.isEmpty()) {
            location = DEFAULT_LOCATION;
        }

        try {
            keyword = URLEncoder.encode(keyword, "UTF-8");
            location = URLEncoder.encode(location,"UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        String url = String.format(URL_TEMPLATE, keyword,location,API_KEY);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //create a custom response handler
        ResponseHandler<List<Item>> responseHandler = response -> {
            if (response.getStatusLine().getStatusCode() != 200) {
                return Collections.emptyList();
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return Collections.emptyList();
            }
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(entity.getContent());
            JsonNode results = root.get("jobs_results");
            Iterator<JsonNode> result = results.elements();

            List<Item> items = new ArrayList<>();
            while (result.hasNext()) {
                JsonNode itemNode = result.next();
                Item item = extract(itemNode);
                System.out.println(item.toString());
                items.add(item);
            }

            extractKeywords(items);

            return items;
        };

        try {
            return httpClient.execute(new HttpGet(url),responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    private static Item extract (JsonNode itemNode) {
        String job_id = itemNode.get("job_id").asText();
        String title = itemNode.get("title").asText();
        String companyName = itemNode.get("company_name").asText();
        String location = itemNode.get("location").asText();
        String via = itemNode.get("via").asText();
        String description = itemNode.get("description").asText();
        List<String> highlights = new ArrayList<String>();
        String url = "";
        Set<String> keywords = new HashSet<>();

        //Store all job highlights to highlights
        JsonNode highlights_node = itemNode.get("job_highlights");
        Iterator<JsonNode> highlight = highlights_node.elements();
        while (highlight.hasNext()) {
            Iterator<JsonNode> item = highlight.next().get("items").elements();
            while (item.hasNext()) {
                highlights.add(item.next().asText());
            }
        }

        //Get a link for application
        Iterator<JsonNode> url_it = itemNode.get("related_links").elements();
        if (url_it.hasNext()) {
            url = url_it.next().get("link").asText();
        }

        //Store extension(keywords) to keywords
        Iterator<JsonNode> extension_it = itemNode.get("extensions").elements();
        while (extension_it.hasNext()) {
            keywords.add(extension_it.next().asText());
        }

        return new Item(job_id, title, companyName, location, via, description, highlights, url, keywords, false);
    }
    private static void extractKeywords(List<Item> items){
        EdenAIClient client = new EdenAIClient();
        for (Item item : items){
            String article = item.getDescription().replace("."," ");
            Set<String> keywords = new HashSet<>();
            keywords.addAll(client.extract(article, 3));
            keywords.addAll(item.getKeywords());
            item.setKeywords(keywords);
        }
    }

}
