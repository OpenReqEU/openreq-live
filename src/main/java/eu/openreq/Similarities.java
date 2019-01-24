package eu.openreq;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Similarities {

    public static Map<String, Float> computeSimilarities(Map<Integer, Set<Integer>> userRatings) {
        int userCounter = 0;
        int totalNumberOfUsers = userRatings.size();
        Set<String> alreadyInvestigatedSimilarityRelations = new HashSet<>();
        Map<String, Float> computedSimilarities = new HashMap<>();

        for (Map.Entry<Integer, Set<Integer>> entry : userRatings.entrySet()) {
            Integer firstUserId = entry.getKey();
            Set<Integer> idsOfItemsRatedByFirstUser = entry.getValue();
            ++userCounter;

            // output progress
            System.out.println("(" + userCounter + "/" + totalNumberOfUsers + ") - Computing similarity for user #" + firstUserId);

            for (Map.Entry<Integer, Set<Integer>> secondEntry : userRatings.entrySet()) {
                Integer secondUserId = secondEntry.getKey();
                Set<Integer> idsOfItemsRatedBySecondUser = secondEntry.getValue();

                String key = firstUserId + "_" + secondUserId;
                String reverseKey = secondUserId + "_" + firstUserId;

                if ((firstUserId.intValue() == secondUserId.intValue())
                        || alreadyInvestigatedSimilarityRelations.contains(key)
                        || alreadyInvestigatedSimilarityRelations.contains(reverseKey)) {
                    continue;
                }

                alreadyInvestigatedSimilarityRelations.add(key);

                Set<Integer> idsOfSameItemsRatedByBoth = new HashSet<>(idsOfItemsRatedByFirstUser);
                idsOfSameItemsRatedByBoth.retainAll(idsOfItemsRatedBySecondUser);

                Set<Integer> idsOfAllItemsRatedByAnyOfBoth = new HashSet<>(idsOfItemsRatedByFirstUser);
                idsOfAllItemsRatedByAnyOfBoth.addAll(idsOfItemsRatedBySecondUser);

                int numberOfSameItemsRatedByBoth = idsOfSameItemsRatedByBoth.size();
                int numberOfAllItemsRatedByAnyOfBoth = idsOfAllItemsRatedByAnyOfBoth.size();

                if (numberOfSameItemsRatedByBoth == 0) { continue; }

                float jaccardSimilarity = ((float) numberOfSameItemsRatedByBoth) / ((float) numberOfAllItemsRatedByAnyOfBoth);
                computedSimilarities.put(key, jaccardSimilarity);
                computedSimilarities.put(reverseKey, jaccardSimilarity);
            }
        }
        return computedSimilarities;
    }
}
