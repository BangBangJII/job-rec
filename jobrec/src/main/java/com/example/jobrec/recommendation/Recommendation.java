package com.example.jobrec.recommendation;

import com.example.jobrec.db.MySQLConnection;
import com.example.jobrec.entity.Item;
import com.example.jobrec.external.SerpAPIClient;

import java.util.*;

public class Recommendation {
    public List<Item> recommendItems(String userId,String location){
        List<Item> recommendations = new ArrayList<>();

        //Step 1 :get all favorite itemids
        MySQLConnection connection = new MySQLConnection();
        Set<String> favoriteItemIds = connection.getFavoriteItemIds(userId);

        //Step 2 : get all keywords, sort by count
        Map<String,Integer> allKeywords = new HashMap<>();
        for (String itemId:favoriteItemIds){
            Set<String> keywords = connection.getKeywords(itemId);
            for (String keyword:keywords){
                allKeywords.put(keyword,allKeywords.getOrDefault(keyword,0)+1);
            }
        }
        connection.close();

        List<Map.Entry<String,Integer>> keywordList = new ArrayList<>(allKeywords.entrySet());

        keywordList.sort((Map.Entry<String,Integer> e1,Map.Entry<String,Integer> e2)-> Integer.compare(e2.getValue(),e1.getValue()));

        //Cut down search list only top 3
        if (keywordList.size()>=3){
            keywordList = keywordList.subList(0,3);
        }

        //Step 3,search based on keywords,filter out favorite items
        Set<String> visitedItemIds = new HashSet<>();
        SerpAPIClient client = new SerpAPIClient();
        for (Map.Entry<String,Integer> keyword:keywordList){
            List<Item> items = client.search(keyword.getKey(),location);
            for (Item item:items){
                if (!visitedItemIds.contains(item.getId()) && !favoriteItemIds.contains(item.getId())){
                    recommendations.add(item);
                    visitedItemIds.add(item.getId());
                }
            }
        }
        return recommendations;
    }
}
