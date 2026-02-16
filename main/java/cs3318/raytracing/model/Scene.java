package cs3318.raytracing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import cs3318.raytracing.utils.*;

/***
 * Represents a scene in 3D space that includes a collection of
 * lights and objects
 * <p>
 *     Maintains a list of renderable objects such as spheres are planes, and a list
 *     of different types of light such as directional, point, ambient.
 * </p>
 * <p>
 *     Provides methods to add or remove renderable objects to the scene and
 *     add or remove lights
 * </p>
 */
public class Scene {
    final static private int CHUNKSIZE = 100;
    final private List<Renderable> objectList;
    final private List<Light> lightList;


    /***
     * Constructs a scene with an empty object list of size chunksize and
     * an empty light list of size chunksize. CHUNKSIZE is hardcoded above
     * and is the max number of lights / objects to be in the scene
     */
    public Scene (){
        objectList = new ArrayList<>(CHUNKSIZE);
        lightList = new ArrayList<>(CHUNKSIZE);
    }

    /***
     * Returns a list of all the lights in the scene, the list is unmodifiable
     * @return an unmodifiable list of the lights in the scene
     */
    public List<Light> getLights() {return Collections.unmodifiableList(lightList);}

    /***
     * Returns a list of all the renderable objects in the scene, the list is unmodifiable
     * @return an unmodifiable list of the renderable objects in the scene
     */
    public List<Renderable> getObjects() {return Collections.unmodifiableList(objectList);}

    /***
     * Adds a sphere into the scene with specified center point, radius and associated surface object
     * @param center a {@link Point3D} object to represent the center point of the sphere
     * @param radius the radius of the sphere, a float
     * @param surface a {@link Surface} object that represents properties about the surface of the sphere
     */
    public void addSphere(Point3D center, float radius, Surface surface){
        objectList.add(new Sphere(center, radius, surface));
    }

    /***
     * Adds a plane into the scene
     * @param normal a {@link Vector3D} object that represents the normal vector of the plane
     * @param point a {@link Point3D} object that represents a point on the plane, used to calculate the position of the plane in 3D space
     * @param surface a {@link Surface} object that represents the surface properties of the plane
     */
    public void addPlane(Vector3D normal, Point3D point, Surface surface){
        objectList.add(new Plane(surface, normal, point));
    }

    /***
     * Add an ambient light into the scene
     * @param r the red intensity of the light [0-1]
     * @param g the green intensity of the light [0-1]
     * @param b the blue intensity of the light [0-1]
     */
    public void addAmbientLight(float r, float g, float b) {
        lightList.add(new AmbientLight(r, g, b));
    }

    /***
     * Add a directional light into the scene
     * @param r the red intensity of the light [0-1]
     * @param g the green intensity of the light [0-1]
     * @param b the blue intensity of the light [0-1]
     * @param direction a {@link Vector3D} object to represent the direction of the directional light
     */
    public void addDirectionalLight(float r, float g, float b, Vector3D direction) {
        lightList.add(new DirectionalLight(r, g, b, direction));
    }

    /***
     * Add a point light into the scene
     * @param r the red intensity of the light [0-1]
     * @param g the green intensity of the light [0-1]
     * @param b the blue intensity of the light [0-1]
     * @param point a {@link Point3D} object to represent the 3D position of the light
     */
    public void addPointLight(float r, float g, float b, Point3D point) {
        lightList.add(new PointLight(r, g, b, point));
    }

    /***
     * Remove all objects in the object list
     */
    public void clearObjects() { objectList.clear();}

    /***
     * Remove all lights in the light list
     */
    public void clearLights() { lightList.clear();}

    /***
     * Remove all lights and objects from the light list and object list
     */
    public void clearScene() { clearLights(); clearObjects();}
}
