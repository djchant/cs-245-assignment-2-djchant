import java.util.ArrayList;
import java.util.Scanner;

public class A3 {
    public static void main(String[] args) {
        HashGraph graph = new HashGraph();
        String file = "tmdb_5000_credits.csv";

        ArrayList<String> printing = graph.readCSV(file);
        ArrayList<String> actors;

        for(int k = 0; k < printing.size(); k++) {
            actors = graph.conversion(printing.get(k));
            for(int i = 0; i < actors.size(); i++) {
                for(int j = 0; j < i; j++) {
                    graph.addEdge(actors.get(i), actors.get(j));
                }
                for(int j = i + 1; j < actors.size(); j++) {
                    graph.addEdge(actors.get(i), actors.get(j));
                }
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Actor 1: ");
        String actor1 = scanner.nextLine();
        System.out.print("Actor 2: ");
        String actor2 = scanner.nextLine();

        try {
            graph.minEdge(actor1, actor2);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
