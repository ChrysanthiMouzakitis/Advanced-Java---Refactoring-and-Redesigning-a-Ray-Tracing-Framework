package cs3318.raytracing.model;

import cs3318.raytracing.utils.Point3D;
import cs3318.raytracing.utils.Vector3D;

import java.util.List;

/***
 * Represents a ray in a ray tracing program used to render a 3D scene
 * <p>
 *     It is defined by its point of origin, the number of bounces it can perform,
 *     its direction. Includes methods to trace the ray, return the number of bounces it can perform,
 *     calculate points along its path and return the inverse of its direction.
 * </p>
 */
public class Ray {
    static final float MAX_TRACE = Float.MAX_VALUE; //the maximum trace distance for a ray (infinity)
    final Point3D origin; // the point of origin of the ray
    final Vector3D originVector;
    final Vector3D direction;
    final private int bounces;

    /***
     * Constructs a Ray with specified values for its eye, direction, and number
     * of bounces it can perform
     * @param eye a {@link Point3D} object, the origin point of the ray
     * @param dir a {@link Vector3D} object, the direction of the ray
     * @param bounces the number of bounces the ray can perform
     */
    Ray(Point3D eye, Vector3D dir, int bounces) {
        origin = new Point3D(eye);
        direction = Vector3D.normalize(dir);
        originVector = new Vector3D(origin);
        this.bounces = bounces;
    }

    /***
     * Returns the remaining number of bounces the ray can perform
     * @return the number of remaining bounces
     */
    int getBounces(){
        return bounces;
    }

    /***
     * Traces the ray through the 3D scene, through a list of renderable objects in the scene.
     * It then finds the closest intersection point
     * @param objects the list of renderable objects present in the scene to be tested for intersection
     * @return a {@link Intersection} object, the closest intersection found, or {@code null} if none found
     */
    Intersection trace(List<Renderable> objects) {
        if (bounces < 0){
            return null;
        }
        float intersectDistance = MAX_TRACE;
        Renderable intersectObject = null;

        //loop through all objects to test for intersecrion
        for (Renderable object : objects) {
            Float trace = object.intersect(this, intersectDistance);
            if (trace != null && trace < intersectDistance){
                intersectDistance = trace;
                intersectObject = object;
            }
        }
        Intersection intersection = null;
        if (intersectObject != null){
            intersection = new Intersection(this, intersectObject, intersectDistance);
        }

        return intersection;
    }

    /***
     * Returns a point along the ray's path a given distance from the origin
     * @param distance the distance along the ray's path
     * @return a {@link Point3D object}, the point calculated
     */
    Point3D calculatePoint(float distance){
        float px = origin.x + distance*direction.x;
        float py = origin.y + distance*direction.y;
        float pz = origin.z + distance*direction.z;

        return new Point3D(px, py, pz);
    }

    /***
     * Returns a vector that is pointing from the ray's direction back to the origin,
     * i.e. the inverse of the ray's direction vector
     * @return a {@link Vector3D} object, the vector calculated
     */
    Vector3D unitToOrigin(){
        return new Vector3D(-direction.x, -direction.y, -direction.z);
    }

    /***
     * Returns a string representing the ray, defined by its origin and direction
     * @return the string representation of the ray
     */
    public String toString() {
        return ("ray origin = "+origin+"  direction = "+direction);
    }
}


