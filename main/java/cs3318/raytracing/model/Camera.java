package cs3318.raytracing.model;

import cs3318.raytracing.utils.Point3D;
import cs3318.raytracing.utils.Vector3D;

/***
 * Represents a camera used in a ray-tracing program
 * <p>
 *     Provides methods on defining the camera's position, the camera's orientation,
 *     the field of view FOV, and the resolution. The camera can also calculate the direction of the rays
 *     from the camera's perspective, a key calculation for the creation of the 3D scene.
 * </p>
 */
public class Camera {

    private final static Point3D DEFAULT_EYE = new Point3D(0, 0, 10);
    private final static Point3D DEFAULT_LOOKAT = new Point3D(0, 0, 0);
    private final static float DEFAULT_FOV = 50;
    private final static Vector3D DEFAULT_UP = new Vector3D(0, 1, 0);

    private Point3D eye;
    private Point3D lookat ;
    private Vector3D up;
    private Vector3D cameraHorizontal, cameraVertical, viewPlaneTopLeft;
    private float fov;
    private int width, height;


    /***
     * Constructs a camera with the specified width and height ie with the
     * specified resolution in pixels
     * <p>
     *     The camera is created with default values for the eye, lookat,
     *     fov, and up
     * </p>
     * @param defaultWidth the width of the camera's viewport in pixels
     * @param defaultHeight the height of the camera's viewport in pixels
     */
    public Camera (int defaultWidth, int defaultHeight){
        eye = DEFAULT_EYE;
        lookat = DEFAULT_LOOKAT;
        width = defaultWidth;
        height = defaultHeight;
        fov = DEFAULT_FOV;
        up = DEFAULT_UP;
        recomputeVectors();
    }

    /***
     * Constructs the camera with specified values for lookat, eye, width, height, and fov
     *
     * <p>
     *     Then recomputes all the camera's vectors used internally for calculations with rays
     * </p>
     * @param lookat a {@link Point3D} object which represents the point the camera is looking at in 3D space
     * @param eye a {@link Point3D} object which represents the camera's position in 3D space
     * @param width the width of the camera's viewport in pixels
     * @param height the height of the camera's viewport in pixels
     * @param fov the camera's field of view in degrees
     */
    public Camera(Point3D lookat, Point3D eye, int width, int height, float fov){
        this.eye = eye;
        this.lookat = lookat;
        this.width = width;
        this.height = height;
        this.fov = fov;
        recomputeVectors();
    }

    /**
     * Gets the current position of the camera.
     *
     * @return a {@link Point3D} object to represent the current eye position of the camera
     */
    public Point3D getEye(){
        return eye;
    }

    /***
     * Sets the position of the camera in 3D space
     * <p>
     *     Then recomputes all the camera's vectors used internally for calculations with rays
     * </p>
     * @param eye a {@link Point3D} object to represent the new eye position of the camera
     */
    public void setEye(Point3D eye) {
        this.eye = eye;
        recomputeVectors();
    }

    /***
     * Set the point that the camera is looking at in 3D space
     * <p>
     *     Then recomputes all the camera's vectors used internally for calculations with rays
     * </p>
     * @param lookat a {@link Point3D} object to represent the new lookat point of the camera
     */
    public void setLookat(Point3D lookat){
        this.lookat = lookat;
        recomputeVectors();
    }

    /***
     * Sets the width and height of the camera's viewport, ie
     * the camera's resolution
     * <p>
     *     Then recomputes all the camera's vectors used internally for calculations with rays
     * </p>
     * @param width the new width of the viewport in pixels
     * @param height the new height of the viewport in pixels
     */
    public void setDimensions(int width, int height){
        this.width = width;
        this.height = height;
        recomputeVectors();
    }

    /***
     *Sets a new field of view FOV for the camera
     * <p>
     *     Then recomputes all the camera's vectors used internally for calculations with rays
     * </p>
     * @param fov the new field of view of the camera in degrees, a float
     */
    public void setFOV(float fov){
        this.fov = fov;
        recomputeVectors();
    }
    /***
     *Sets the new up vector of the camera, the vector which represents the orientation of the camera
     * <p>
     *     Then recomputes all the camera's vectors used internally for calculations with rays
     * </p>
     * @param up a {@link Vector3D} object to represent the new up vector
     */
    public void setUp(Vector3D up){
        this.up = up;
        recomputeVectors();
    }

    /***
     * Recomputes the internal vectors used by the camera in ray calculations
     * <p>
     *     recomputes the look, the horizontal vector of the camera, the vertical vector
     *     of the camera, the focal length, the normalized look and the top-left corner of the view plane
     *
     * </p>
     */
    public void recomputeVectors() {
        // Compute viewing matrix that maps a
        // screen coordinate to a ray direction
        Vector3D look = new Vector3D(lookat.x - eye.x, lookat.y - eye.y, lookat.z - eye.z);
        cameraHorizontal = Vector3D.normalize(look.cross(up));
        cameraVertical = Vector3D.normalize(look.cross(cameraHorizontal));
        float focalLength = (float)(width / (2*Math.tan((0.5*fov)*Math.PI/180)));
        Vector3D normalizedLook = Vector3D.normalize(look);
        viewPlaneTopLeft = new Vector3D(
                normalizedLook.x*focalLength - 0.5f*(width*cameraHorizontal.x + height*cameraVertical.x),
                normalizedLook.y*focalLength - 0.5f*(width*cameraHorizontal.y + height*cameraVertical.y),
                normalizedLook.z*focalLength - 0.5f*(width*cameraHorizontal.z + height*cameraVertical.z));
    }

    /***
     * Calculates the direction of a ray that passes through a pixel at (i,j) in the camera's viewport
     * @param i the x coordinate of the pixel
     * @param j the y coordinate of the pixel
     * @return a {@link Vector3D} object to represent the direction of the ray
     */
    public Vector3D calculateDirection(int i, int j) {
        return new Vector3D(
                i*cameraHorizontal.x + j*cameraVertical.x + viewPlaneTopLeft.x,
                i*cameraHorizontal.y + j*cameraVertical.y + viewPlaneTopLeft.y,
                i*cameraHorizontal.z + j*cameraVertical.z + viewPlaneTopLeft.z);
    }

}
