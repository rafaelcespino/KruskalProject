// The following import statements are used read the file
import java.io.BufferedReader;
import java.io.FileReader;

// The following import statement is used to handle
// file not found exception.
import java.io.IOException;

///The following import statement is used for array list.
import java.util.ArrayList;

// The following import statement is used for priority queue.
import java.util.PriorityQueue;

// Create class named Kruskals.
public class kruskal
{
    // Making a Edge class which has two vertices as
// strings and distance between them as integer.
    public static class Edge implements Comparable<Edge>
    {
        // Declare the variable
        int distance;
        String vetex1, vetex2;

        // Create a parameter constructor.
        Edge(int d, String s1, String s2)
        {
            this.distance = d;
            this.vetex1 = s1;
            this.vetex2 = s2;
        }

        // Overriding the compareTo in comparable so that
        // it can compare objects
        public int compareTo(Edge e)
        {
            if (this.distance < e.distance)
            {
                return -1;
            }
            else
            if (this.distance > e.distance)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }

    // Create a default constructor.
    public void kruskal()
    {
        ArrayList<Edge> edgelist = new ArrayList<>();
        ArrayList<String> VertexList = new ArrayList<>();

        // String fileToParse = "assn9_data.csv";
        BufferedReader fileReader = null;
        int totalVertices = 0;
        int sumOfAllDistances = 0;

        final String DELIMITER = ",";

        // Create a try block to handle the exception.
        try
        {
            String line = "";

            // Create a file object to read the csv file.
            fileReader = new BufferedReader(new FileReader("flightData.csv"));

            // Create a while loop to read the content in the file.
            while ((line = fileReader.readLine()) != null)
            {
                String[] tokens = line.split(DELIMITER);
                VertexList.add(tokens[0]);
                edgelist.add(new Edge(Integer.parseInt(tokens[2]), tokens[0], tokens[1]));

                for (int i = 3; i < tokens.length; i++)
                {
                    edgelist.add(new Edge(Integer.parseInt(tokens[i + 1]), tokens[0], tokens[i]));
                    i++;
                }
                totalVertices++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fileReader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        // Starting Kruskals Algorithm.
        int edgesAccepted = 0;
        DisjSets ds = new DisjSets(VertexList.size());

        // PriorityQueue
        // Create an object named pQueue for the PriorityQueue.
        PriorityQueue<Edge> pQueue = new PriorityQueue<>();

        for (Edge e : edgelist)
        {
            // Adding each edge in priority queue
            pQueue.add(e);
        }

        while (edgesAccepted < VertexList.size() - 1)
        {
            // Using polling function as a substitute to deleteMin()
            Edge e = pQueue.poll();

            if (ds.find(VertexList.indexOf(new String(e.vetex1))) != ds.find(VertexList.indexOf(new String(e.vetex2))))
            {
                edgesAccepted++;
                ds.union(ds.find(VertexList.indexOf(new String(e.vetex1))), ds.find(VertexList.indexOf(new String(e.vetex2))));
                sumOfAllDistances = sumOfAllDistances + e.distance;
                System.out.println(e.vetex1 + " - " + e.vetex2 + " - Distance between the two cities: " + e.distance);
            }

        }
        System.out.println("\nSum of all distances: " + sumOfAllDistances);

    }

    // Create a main method to execute the program.
    public static void main(String[] args)
    {
        // Create an object named krObj
        // for the class named Kruskals
        kruskal krObj = new kruskal();
        krObj.kruskal();

    }
}