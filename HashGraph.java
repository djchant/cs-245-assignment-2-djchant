import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HashGraph {
    public ArrayList<HashNode> bucket;
    private int numBuckets;
    private CSVReader reader = null;

    public HashGraph() {
        numBuckets = 40000000;
        bucket = new ArrayList<>(numBuckets);

        for(int i = 0; i < numBuckets; i++) {
            bucket.add(null);
        }
    }

    //gives an int after hashing the key
    public int getHash(String key) {
        return(Math.abs(key.hashCode() % numBuckets));
    }

    //reading the csv file and return a list of json formatted strings
    public ArrayList<String> readCSV(String fileName) {
        String[] nextLine;
        ArrayList<String> toBeReturned = new ArrayList<>();
        try{
            reader = new CSVReader(new FileReader(fileName));
        } catch(Exception e) {
            e.printStackTrace(); //opens file
        }

        try {
            while((nextLine = reader.readNext()) != null) {
                if(!nextLine[2].trim().equals("cast")) {
                    toBeReturned.add(nextLine[2]);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        //returns a list of different json items as a string
        return toBeReturned;
    }

    //takes in a json formatted string, returns a list of string actor names
    public ArrayList<String> conversion(String printing) {
        ArrayList<String> actors = new ArrayList<>();

        try{
            String temp = printing;
            Matcher m = Pattern.compile("(?=(\"name\": \"))").matcher(temp);
            while(m.find()) {
                int startPos = m.start() + 9;
                int end = temp.indexOf("\"", startPos);
                String actor = temp.substring(startPos, end).toLowerCase();
                actors.add(actor);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return actors;
    }

    //creating the graph with hashing to be able to store and find string keys easily
    public void addEdge(String key, String value) {
        HashNode head = bucket.get(getHash(key));
        if(head == null) {
            bucket.set(getHash(key), new HashNode(key, value));
        } else {
            HashNode node = new HashNode(key, value);
            node.next = bucket.get(getHash(key));
            bucket.set(getHash(key), node);
        }
    }

    //finding the minimum path from start to end
    public void minEdge(String start1, String end1) throws Exception{
        String start = start1.toLowerCase();
        String end = end1.toLowerCase();
        boolean found = false;


        boolean[] visited = new boolean[numBuckets];
        //distance used to determine the shortest path
        int[] distance = new int[numBuckets];
        //stores a path used to find the shortest path
        String[] previous = new String[numBuckets];

        //bfs
        ArrayQueue<String> q = new ArrayQueue<>(numBuckets);
        q.enqueue(start);
        distance[getHash(start)] = 0;
        visited[getHash(start)] = true;

        while(!q.empty()) {
            String n = q.dequeue(); //a string key
            HashNode head = bucket.get(getHash(n));

            while(head != null) {
                if(!visited[getHash(head.value)]) {
                    distance[getHash(head.value)] = distance[getHash(n)] + 1;
                    previous[getHash(head.value)] = n;
                    visited[getHash(head.value)] = true;
                    q.enqueue(head.value);

                    if(head.value.equals(end)) {
                        found = true;
                        break;
                    }
                } else {
                    head = head.next;
                }
            }
        }

        //looking through previous to print the path
        if(found) {
            String crawl = end;
            while(previous[getHash(crawl)] != start) {
                System.out.print(crawl + " --> ");
                crawl = previous[getHash(crawl)];
            }

            System.out.print(crawl + " --> " + start);
            return;
        }
    }
}
