package cs3318.raytracing.api;

import cs3318.raytracing.controller.Controller;
import cs3318.raytracing.model.Surface;
import cs3318.raytracing.utils.Point3D;
import cs3318.raytracing.utils.Vector3D;

import java.awt.Color;

/**
 * A simplified 3D ray-tracing renderer that implements core graphics concepts including
 * Phong illumination, reflections, and shadow calculations.
 * <p>
 * This API provides high-level methods to create and render 3D scenes with:
 * - Spherical objects with configurable surface properties
 * - Multiple light types (ambient, directional, and point lights)
 * - Customizable camera positioning and properties
 * <p>
 * Originally based on the ray tracer written by Leonard McMillan for MIT 6.837 (Fall '98).
 * Modernized and adapted for CS3318 Advanced Java Programming in 2024 with enhanced
 * object-oriented design and current Java practices.
 *
 * @version 2.0
 * @since 11-2024
 */
public class RayTraceAPI {
    private final Controller controller;

    /***
     * Constructs the Ray Tracing API
     * <p>
     *     Initialises the controller and sets the current surface to a default matte black
     * </p>
     */
    public RayTraceAPI() {
        controller = new Controller();
        this.setCurrentSurface("matte black");
    }

    /***
     * Sets the current surface to a predefined surface by name.
     * The current surface is used for creating new objects
     * @param surfaceName string, name of the predefined surface
     */
    public void setCurrentSurface(String surfaceName) {
        Surface surface = Surface.getPredefinedSurface(surfaceName);
        if (surface == null) {
            throw new IllegalArgumentException("Unknown name: " + surfaceName);
        }
        controller.setCurrentSurface(surface);
    }

    /**
     * Adds a sphere to the scene with specified position, size, and color.
     * Surface properties are set to default values.
     *
     * @param centerX X coordinate of sphere center
     * @param centerY Y coordinate of sphere center
     * @param centerZ Z coordinate of sphere center
     * @param radius Radius of the sphere
     * @see Surface
     */
    public void addSphere(float centerX, float centerY, float centerZ, float radius) {
        Point3D center = new Point3D(centerX, centerY, centerZ);
        controller.addSphere(center, radius);
    }

    /**
     * Adds a plane to the scene with specified normal vector, point, and basic surface properties.
     * Surface properties are set to default values.
     *
     * @param normalX X component of plane normal vector
     * @param normalY Y component of plane normal vector
     * @param normalZ Z component of plane normal vector
     * @param pointX X coordinate of a point on the plane
     * @param pointY Y coordinate of a point on the plane
     * @param pointZ Z coordinate of a point on the plane
     * @see Surface
     */
    public void addPlane(float normalX, float normalY, float normalZ,
                         float pointX, float pointY, float pointZ) {
        Vector3D normal = new Vector3D(normalX, normalY, normalZ);
        Point3D point = new Point3D(pointX, pointY, pointZ);
        controller.addPlane(normal, point);
    }

    /**
     * Adds ambient light to the scene.
     *
     * @param r Red intensity (0-1)
     * @param g Green intensity (0-1)
     * @param b Blue intensity (0-1)
     */
    public void addAmbientLight(float r, float g, float b) {
        controller.addLight(r, g, b);
    }

    /**
     * Adds a directional light to the scene.
     *
     * @param r Red intensity (0-1)
     * @param g Green intensity (0-1)
     * @param b Blue intensity (0-1)
     * @param dirX X component of light direction
     * @param dirY Y component of light direction
     * @param dirZ Z component of light direction
     */
    public void addDirectionalLight(float r, float g, float b,
                                    float dirX, float dirY, float dirZ) {
        Vector3D direction = new Vector3D(dirX, dirY, dirZ);
        controller.addLight(r, g, b, direction);
    }

    /**
     * Adds a point light source to the scene.
     *
     * @param r Red intensity (0-1)
     * @param g Green intensity (0-1)
     * @param b Blue intensity (0-1)
     * @param posX X coordinate of light position
     * @param posY Y coordinate of light position
     * @param posZ Z coordinate of light position
     */
    public void addPointLight(float r, float g, float b,
                              float posX, float posY, float posZ) {
        Point3D position = new Point3D(posX, posY, posZ);
        controller.addLight(r, g, b, position);
    }

    /***
     * Sets the maximum number of ray bounces allowed in reflection, refraction
     * @param maxBounces the new maximum number of ray bounces
     */
    public void setMaxBounces(int maxBounces) {
        if (maxBounces < 0) {
            throw new IllegalArgumentException("Maximum bounces must be >= 0");
        }
        controller.setMaxBounces(maxBounces);
    }

    /**
     * Sets basic camera parameters by specifying eye position and look-at point.
     *
     * @param eyeX X coordinate of camera position
     * @param eyeY Y coordinate of camera position
     * @param eyeZ Z coordinate of camera position
     * @param lookAtX X coordinate of look-at point
     * @param lookAtY Y coordinate of look-at point
     * @param lookAtZ Z coordinate of look-at point
     */
    public void setCamera(float eyeX, float eyeY, float eyeZ,
                          float lookAtX, float lookAtY, float lookAtZ) {
        Point3D eye = new Point3D(eyeX, eyeY, eyeZ);
        Point3D lookAt = new Point3D(lookAtX, lookAtY, lookAtZ);
        controller.setCamera(lookAt, eye);
    }

    /**
     * Sets all camera parameters including position, orientation, and viewing properties.
     *
     * @param eyeX X coordinate of camera position
     * @param eyeY Y coordinate of camera position
     * @param eyeZ Z coordinate of camera position
     * @param lookAtX X coordinate of look-at point
     * @param lookAtY Y coordinate of look-at point
     * @param lookAtZ Z coordinate of look-at point
     * @param upX X component of up vector
     * @param upY Y component of up vector
     * @param upZ Z component of up vector
     * @param width Output image width in pixels
     * @param height Output image height in pixels
     * @param fov Field of view angle in degrees
     */
    public void setCamera(float eyeX, float eyeY, float eyeZ,
                              float lookAtX, float lookAtY, float lookAtZ,
                              float upX, float upY, float upZ,
                              int width, int height, float fov) {
        try {
            Point3D eye = new Point3D(eyeX, eyeY, eyeZ);
            Point3D lookAt = new Point3D(lookAtX, lookAtY, lookAtZ);
            Vector3D up = new Vector3D(upX, upY, upZ);
            controller.setCamera(lookAt, eye, width, height, fov);
            controller.setCameraUp(up);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    /***
     * Set the camera's dimensions
     * @param width the new width of the camera in pixels
     * @param height the new height of the camera in pixels
     */
    public void setCameraDimensions(int width, int height) {
        controller.setCameraDimensions(width, height);
    }

    /**
     * Sets the background color for the scene.
     *
     * @param r Red component (0-255)
     * @param g Green component (0-255)
     * @param b Blue component (0-255)
     */
    public void setBackgroundColor(int r, int g, int b) {
        controller.setRenderer(new Color(r, g, b));
    }

    /**
     * Renders the scene using ray tracing.
     */
    public void renderImage() {
        controller.renderImage();
    }

    /**
     * Saves the rendered image to a file in PNG format.
     *
     * @param filename Output filename
     */
    public void saveImage(String filename) {
        // Strip all existing extensions
        int dotIndex = filename.indexOf('.');
        String baseName = (dotIndex != -1) ? filename.substring(0, dotIndex) : filename;
        String finalName = baseName + ".png";

        controller.exportImage(finalName);
    }

    /**
     * Loads a predefined test scene with multiple spheres and lights.
     * Useful for testing and demonstration purposes.
     */
    public void loadTestScene() {
        controller.clearScene();
        this.setMaxBounces(0);
        // Scene Setup
        this.setBackgroundColor(20, 20, 25);
        this.setCamera(-1.4f, 0.3f, 7, -0.5f, 0.7f, -12);

        this.setCurrentSurface("gold");
        this.addSphere(-1.5f, -1, 3, 0.9f);

        this.addSphere(-0.5f, 1.9f, -12, 0.5f);

        this.setCurrentSurface("blue rubber");
        this.addSphere(-0.5f, 0.7f, -12, 0.7f);

        this.addSphere(-0.5f, -5, -12, 5);

        this.setCurrentSurface("mirror");
        this.addSphere(0, 0, -2500, 500);

        this.addSphere(-1.5f, 0.1f, 3, 0.2f);

        // Add plane
        this.setCurrentSurface("emerald");
        this.addPlane(0, 2, 0, 0, -200, -320);


        // Add lights
        this.addAmbientLight(0.8f, 0.8f, 0.8f);
        this.addDirectionalLight(1, 1, 1, 1, -5, -4);

        // Render and save the image
        this.renderImage();
        this.saveImage("test_render.png");
    }

    /**
     * Main method to demonstrate API usage.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Test the API
        RayTraceAPI rayTracer = new RayTraceAPI();
        rayTracer.loadTestScene();
    }
}