package cs3318.raytracing.controller;

import cs3318.raytracing.model.*;
import cs3318.raytracing.utils.Point3D;
import cs3318.raytracing.utils.Vector3D;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import java.util.stream.IntStream;

/***
 * Controller class responsible for setting up the scene with lights and objects,
 * setting up the camera and its properties, and rendering and exporting the final image
 */

public class Controller {

    private final static int DEFAULT_WIDTH = 860;
    private final static int DEFAULT_HEIGHT = 640;

    private final Camera camera;
    private final Scene scene;
    private BufferedImage image;
    private final Renderer renderer;
    private Surface currentSurface;

    /***
     * Default constructor for controller,
     * initialises the camera, scene and final image dimensions
     */
    public Controller(){
        camera = new Camera(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setImage(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        scene = new Scene();
        renderer = new Renderer();
    }

    /***
     * Creates the image with a specified width and height
     * then updates the camera's dimensions to be the same
     * @param width the width of the image in pixels
     * @param height the height of the image in pixels
     */
    private void setImage(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        camera.setDimensions(width, height);
    }

    /***
     * Removes all renderable objects and lights from the scene, clears the scene
     */
    public void clearScene() {scene.clearScene();}

    /***
     * Sets the camera's lookat, eye, width, height, field of view to specified values
     * @param lookat a {@link Point3D} object, the point the camera is looking at
     * @param eye a {@link Point3D} object, the position of the camera in 3D space
     * @param width the width of the camera's viewport in pixels, also the width of the final image
     * @param height the height of the camera's viewport in pixels, also the width of the final image
     * @param fov the camera's field of view
     */
    public void setCamera(Point3D lookat, Point3D eye, int width, int height, float fov){
        camera.setLookat(lookat);
        camera.setEye(eye);
        camera.setDimensions(width, height);
        camera.setFOV(fov);
        setImage(width, height);
    }

    /***
     * Sets the camera's position and target point to specified values
     * @param lookat a {@link Point3D} object, the point that the camera is looking at
     * @param eye a {@link Point3D} object, the position of the camera
     */
    public void setCamera(Point3D lookat, Point3D eye){
        camera.setLookat(lookat);
        camera.setEye(eye);

    }

    /***
     * Sets the camera's dimensions to specified values
     * @param width the width of camera's viewport in pixels, also the width of the image
     * @param height the height of the camera's viewport in pixels, also the height of the image
     */
    public void setCameraDimensions(int width, int height){
        camera.setDimensions(width, height);
        setImage(width, height);
    }

    /***
     * Sets the up vector of the camera
     * @param up a {@link Vector3D} object, the up vector for the camera
     */
    public void setCameraUp(Vector3D up){
        camera.setUp(up);
    }

    /***
     * Sets the background colour for the rendered
     * @param background a {@link Color} object, the colour of the background
     */
    public void setRenderer(Color background){
        renderer.setBackground(background);
    }

    /***
     * Renders the scene by colouring every pixel and stores the resulting image.
     *  Uses parallel processing for speed
     */
    public void renderImage() {
        long time = System.currentTimeMillis();

        IntStream.range(0, image.getHeight()).parallel().forEach(j -> {
            for (int i = 0; i < image.getWidth(); i++) {
                Color pixelColor = renderer.renderPixel(i, j, scene, camera);
                image.setRGB(i, j, pixelColor.getRGB());
            }
        });

        time = System.currentTimeMillis() - time;
        System.err.println("Rendered in "+(time/60000)+":"+((time%60000) * 0.001));
    }

    /***
     * Exports the final rendered image to a file with a specified filename
     * @param filename a string, the desired filename
     */
    public void exportImage(String filename) {
        try {
            File outputFile = getFile(filename);

            ImageIO.write(image, "PNG", outputFile);
            System.out.println("Image exported as " + filename);

        } catch (SecurityException e) {
            System.err.println("Security error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    /***
     * Creates a file for saving the image, or retrieves it if it already exists
     * @param filename the name of the file
     * @return the {@link File} object
     * @throws IOException if the file or directory cannot be accessed or created
     */
    private static File getFile(String filename) throws IOException {
        File outputFile = new File(filename);

        // Check if directory exists/can be created
        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException("Failed to create directory: " + parentDir);
        }

        // Check write permissions
        if (outputFile.exists() && !outputFile.canWrite()) {
            throw new IOException("No write permission: " + outputFile);
        }
        return outputFile;
    }

    /***
     * Adds a sphere to the 3D scene, with specified center point and radius length
     * @param center a {@link Point3D} object, the center of the sphere
     * @param radius the length of the radius of the sphere
     */
    public void addSphere(Point3D center, float radius) {
        if (currentSurface == null) {
            throw new IllegalStateException("No current surface available");
        }
        scene.addSphere(center, radius, currentSurface);
    }

    /***
     * Updates the current surface for new objects that will be added to the scene
     * @param surface the {@link Surface} object, the new current surface
     * @throws IllegalStateException if the surface is {@code null}
     */
    public void setCurrentSurface(Surface surface){
        if (surface == null) {
            throw new IllegalStateException("Surface cannot be null");
        }
        currentSurface = surface;
    }

    /***
     * Returns the current surface used for new objects
     * @return a {@link Surface} object, the current surface
     */
    public Surface getCurrentSurface(){ return currentSurface;}

    /***
     * Set the max number of bounces allowed for ray reflections / refractions
     * @param maxBounces the maximum number of bounces allowed
     */
    public void setMaxBounces(int maxBounces) {
        renderer.setMaxBounces(maxBounces);
    }

    /***
     * Returns the max number of ray bounces allowed
     * @return the maximum number of ray bounces allowed
     */
    public int getMaxBounces() { return renderer.getMaxBounces();}


    /***
     * Add a plane into the 3D scene with specified normal vector and a point on the plane
     * @param normal a {@link Vector3D} object, the normal vector of the plane
     * @param point a {@link Point3D} object, a point on the plane, used to calculate the position of the Plane
     */
    public void addPlane(Vector3D normal, Point3D point){scene.addPlane(normal, point, currentSurface);}

    /***
     * Add a light into the 3D scene with specified rgb intensities
     * @param r the red intensity of the light
     * @param g the green intensity of the light
     * @param b the blue intensity of the light
     */
    public void addLight(float r, float g, float b){
        scene.addAmbientLight(r, g, b);
    }

    /***
     * Add a directional light into the 3D scene, with specified rgb intensities and direction
     * @param r the red intensity of the light
     * @param g the green intensity of the light
     * @param b the blue intensity of the light
     * @param direction a {@link Vector3D} object, the direction of the light
     */
    public void addLight(float r, float g, float b, Vector3D direction){
        scene.addDirectionalLight(r, g, b, direction);
    }

    /***
     * Add a point light into the 3D scene with specified rgb intensities and point
     * @param r the red intensity of the light
     * @param g the green intensity of the light
     * @param b the blue intensity of the light
     * @param point a {@link Point3D} object, the point of the point light
     */
    public void addLight(float r, float g, float b, Point3D point){
        scene.addPointLight(r, g, b, point);
    }

}
