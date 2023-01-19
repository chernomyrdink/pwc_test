package com.pwc.test.pwctest.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class GraphSearchUtil {

    //returns path from origin to destination
    public static LinkedList<String> getShortestDistance(
            Map<String, List<String>> countriesGraph,
            String origin, String destination) {
        Map<String, String> pred = new HashMap<>();

        if (!breadthFirstSearch(countriesGraph, origin, destination, pred)) {
            return null;
        }

        LinkedList<String> path = new LinkedList<>();
        String crawl = destination;
        path.add(destination);
        while (!pred.get(crawl).isEmpty()) {
            path.add(pred.get(crawl));
            crawl = pred.get(crawl);
        }

        Collections.reverse(path);

        return path;
    }

    //implementation of breath-first searching algorithm
    private static boolean breadthFirstSearch(Map<String, List<String>> countriesGraph, String src,
                               String dest, Map<String, String> pred) {

        LinkedList<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        visited.add(src);
        queue.add(src);
        pred.put(src, "");

        while (!queue.isEmpty()) {
            String u = queue.remove();
            for (String neighbour : countriesGraph.get(u)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    pred.put(neighbour, u);
                    queue.add(neighbour);

                    if (neighbour.equals(dest)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
