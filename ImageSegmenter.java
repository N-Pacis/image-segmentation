import java.awt.Color;
import java.util.Iterator;

/**
 * A class that segments an image into regions of similar color. This class implements the segment method by
 * taking an array of an image's original colors and returning the array with the colors of the segmented image.
 * 
 * @author Pacis Nkubito
 */
public class ImageSegmenter {
    /**
     * Segments an image into regions of similar color.
     * 
     * @param rgbArray a 2D array of Color objects representing the image
     * @param granularity the granularity parameter for the segmentation algorithm
     * 
     * @return a 2D array of Color objects representing the segmented image
     */
    public static Color[][] segment(Color[][] rgbArray, double granularity) {
        DisjointSetForest forest = new DisjointSetForest(rgbArray);
        
        //Loop through the edges in the forest and union similar edges
        Iterator<Edge> edgeIterator = forest.getEdges().iterator();
        while(edgeIterator.hasNext()){
            Edge edge = edgeIterator.next();
            Pixel pixel1 = edge.getFirstPixel();
            Pixel pixel2 = edge.getSecondPixel(); 

            Node pixelAsNode = forest.findNode(pixel1);
            Node pixel2AsNode = forest.findNode(pixel2);

            Node pixel1Parent = forest.findNodeParent(pixelAsNode);
            Node pixel2Parent = forest.findNodeParent(pixel2AsNode); 

            //if the pixels don't have the same parent, continue with the algoritm
            if (!pixel1Parent.pixel.equals(pixel2Parent.pixel)){ 
                int size1 = pixel1Parent.size; 
                int size2 = pixel2Parent.size;

                double internalDistance1 = pixel1Parent.internalDistance; 
                double internalDistance2 = pixel2Parent.internalDistance; 
                
                if (edge.getWeight() < Math.min(internalDistance1 +(granularity/size1), internalDistance2 + (granularity/size2))){
                    forest.union(pixel1,pixel2,edge.getWeight());
                }
            }
            edgeIterator.remove();
        }
       
        ColorPicker colorGenerator = new ColorPicker();

        //Loop through the forest and update the rgbArray with the new colors
        for(Node node: forest.getForest().values()){
            //get the parent to find the color that represents the segment

            Node parent = forest.findNodeParent(node);
            if(parent.color == null){
                parent.color = colorGenerator.nextColor();
            }
            
            Pixel pixel = node.pixel;
            rgbArray[pixel.getRow()][pixel.getCol()] = parent.color;

        }

        return rgbArray; 

    }
}
