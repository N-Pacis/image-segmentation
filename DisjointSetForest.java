import java.awt.Color;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Representation of a Node object that holds a pixel and its parent
 * 
 * @author Pacis Nkubito
 */
class Node {
    public Pixel pixel;
    public Node parent;
    public int size;
    public double internalDistance;
    public Color color;

    /**
     * Initializes the node object
     * 
     * @param pixel the node's corresponding pixel
     * @param color the color to be assigned to the pixel
     */
    public Node(Pixel pixel) {
        this.pixel = pixel;
        this.color = null;
        this.parent = null;
        size = 1;
        internalDistance = 0;
    }

}

/**
 * Constructs the DisjointSetForest by creating edges between all pixels in the
 * image, and puts all images and their corresponding pixels into map forest
 */

public class DisjointSetForest {
    private HashMap<Pixel, Node> forest = new HashMap<Pixel, Node>(); 
                                                                      
    private Set<Edge> edges = new TreeSet<Edge>(); 

    /**
     * Returns the forest map
     * 
     * @return the forest with a pixel as the key and its corresponding node as the
     *         value
     */
    public HashMap<Pixel, Node> getForest() {
        return this.forest;
    }

    /**
     * Returns a set containing all edges
     * 
     * @return a set of edges
     */
    public Set<Edge> getEdges() {
        return this.edges;
    }

    /**
     * Creates edges between all the pixels in the graph and stores all of the
     * pixels and their corresponding nodes with a random color into the forest map
     * 
     * @param colors the input color array
     */
    public DisjointSetForest(Color[][] colors) {

        for (int r = 0; r < colors.length; r++) {
            for (int c = 0; c < colors[r].length; c++) {
                Pixel pixel = new Pixel(r, c, colors[r][c]);
                createEdge(colors, pixel, r + 1, c);
                createEdge(colors, pixel, r, c + 1);
                createEdge(colors, pixel, r + 1, c + 1);
                createEdge(colors, pixel, r + 1, c - 1);

                Node node = new Node(pixel);
                forest.put(pixel, node);
            }
        }
    }

    /**
     * Creates ann edge between an input pixel and a second pixel containing the
     * input row and column if the second pixel is in a valid slot
     * 
     * @param rgbArray the input 2d color array
     * @param pixel    the pixel who's edge we want to connecet
     * @param r        the row of the new pixel to unite with the input pixel
     * @param c        the column of the new pixel to unite with the input pixel
     */
    public void createEdge(Color[][] rgbArray, Pixel pixel, int r, int c) {
        if (r < rgbArray.length && c < rgbArray[0].length && r >= 0 && c >= 0) { 
            Pixel pixel2 = new Pixel(r, c, rgbArray[r][c]);
            Edge edge = new Edge(pixel, pixel2);

            this.edges.add(edge);
        }
    }

    /**
     * Returns the input pixel's parent pixel
     * 
     * @param pixel the input pixel whose parent we want
     * @return the parent in pixel form
     */
    public Pixel find(Pixel pixel) {
        Node pixelAsNode = findNode(pixel);
        if (pixelAsNode != null) {
            Node foundNode = findNodeParent(pixelAsNode);
            if (foundNode != null) {
                return foundNode.pixel;
            }
        }
        return null;

    }

    /**
     * Returns the input node's parent node
     * 
     * @param node the input node whose parent we want
     * @return the parent in node form
     */
    public Node findNodeParent(Node node) { 
        if (node.parent != null) {
            return findNodeParent(node.parent);
        }

        return node;
    }

    /**
     * Unions two input pixels annd tweaks their weight
     * 
     * @param pixel1 pixel1 to be unioned
     * @param pixel2 pixel2 to be unioned
     * @param weight checks to see whether or not the weight is less than either
     *               pixel1 or pixel2's internalDistances
     */
    public void union(Pixel pixel1, Pixel pixel2, double weight) {

        Node pixel1AsNode = findNode(pixel1);
        Node pixel2AsNode = findNode(pixel2);

        Node parent1 = findNodeParent(pixel1AsNode);
        Node parent2 = findNodeParent(pixel2AsNode);

        if (!parent1.pixel.equals(parent2.pixel)) {
            if (parent1.size >= parent2.size) {
                parent2.parent = parent1;
                parent1.size += parent2.size;
                parent1.internalDistance = Math.max(parent1.internalDistance, weight);
            } else {
                parent1.parent = parent2;
                parent2.size += parent1.size;
                parent2.internalDistance = Math.max(parent2.internalDistance, weight);
            }
        }

    }

    /**
     * Convcerts an input pixel to node form using the forest map
     * 
     * @param pixel an input pixel
     * @return the corresponding node
     */
    public Node findNode(Pixel pixel) {
        return forest.get(pixel);
    }

}