# Image Segmentation
Image segmentation is a fundamental task in computer vision, aiming to group pixels that share similar characteristics, such as color, intensity, or texture. This segmentation simplifies the image, making it easier to analyze and extract useful information, such as identifying objects, boundaries, or regions of interest. 

# Implementation

The objective of image segmentation is to divide an image into regions that are visually similar. This is achieved by treating the image as a graph where each pixel is a vertex, and edges connect neighboring pixels. The weight of each edge is determined by the difference in luminance between the connected pixels. The algorithm merges segments of pixels based on their visual similarity, resulting in a segmented image where each region is represented by a unique color.

This main algorithm of the program is constructing a disjoint set forest from the image pixels, and iteratively merge segments based on the segmentation algoritm. The algorithm starts by treating each pixel as its own individual segment, with no connections between them. As the algorithm proceeds, neighboring pixels that are visually similar, based on their color or luminance, are progressively merged into larger segments. This process is guided by a sorting of edge weights, where edges represent the connections between neighboring pixels. These edges are sorted based on the color similarity between connected pixels, and the algorithm merges the smallest, most similar segments first. 

As a result, regions with homogeneous colors are grouped together, and boundaries between distinct regions emerge naturally, effectively segmenting the image into meaningful parts. The final output is a segmented image that highlights different regions or objects based on color similarity.
