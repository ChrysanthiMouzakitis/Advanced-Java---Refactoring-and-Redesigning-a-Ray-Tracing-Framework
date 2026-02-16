package cs3318.raytracing.model;

import cs3318.raytracing.utils.Point3D;
import cs3318.raytracing.utils.Vector3D;

/***
 * Represents a 3D plane
 * <p>
 *     Implements the {@link Renderable} interface
 *     and provides implementations for methods to calculate if
 *     a ray intersects the plane, compute the normalised surface vector at a given point,
 *     return a string representation of the sphere, get the surface object associated with the plane
 *
 * </p>
 */

class Plane implements Renderable{

    private final Surface surface;
    private final Vector3D normal;
    /*** The plane constant in the equation Ax +By + Cz + d in the general equation of a plane*/
    private final float d;

    /***
     * Constructs a plan object with the specified surface, normal vector, and
     * a point on the plane
     * <p>
     *     Also initialises the constant d, the distance of the plan from the origin along its normal vector
     * </p>
     * @param surface a {@link Surface} object to represent the material properties of the plane
     * @param normal a {@link Vector3D} object to represent the normalized vector that is perpendicular to the plane
     * @param point a {@link Point3D} object to represent a point on the plane, used to calculate the position of the plane in 3D space
     */
    Plane(Surface surface, Vector3D normal, Point3D point){
        this.surface = surface;
        this.normal = normal;
        d = normal.dot(new Vector3D(point));
    }

    /***
     *      * Calculates if a ray intersects with the plane, and if it does, returns the distance to the intersection point
     * @param ray a {@link Ray} object, the ray being traced
     * @param intersectDistance a float that represents the maximum distance to consider for the intersection calculation
     * @return null if the ray does not intersect, the distance to the intersection point if the ray does intersect the plane
     */
    public Float intersect(Ray ray, float intersectDistance){
        float denominator = normal.dot(ray.direction);
        if (denominator == 0){
            return null;
        }
        float t = (d - normal.dot(ray.originVector))/denominator;
        if (t < 0 || t > intersectDistance){
            return null;
        }
        return t;
    }

    /***
     * Provides a string representation of the plane in the form
     * of the general equation of a plane
     * <p>
     *     Ax+ By + Cz = D
     * where {@code A}, {@code B}, and {@code C} are the components of the plane's normal vector,
     * and {@code D} is the plane constant initialised in the constructor
     * </p>
     * @return a string representing the plane equation
     */
    public String toString(){
        return normal.x + "x + " + normal.y + "y + " + normal.z + "z = " + d;
    }

    /***
     * Calculates the normalized version of the surface normal vector at a point on the plane's surface
     * usually an intersection point between the plane and a ray
     * @param intersectionPoint a specific point on the object, a {@link Point3D} object
     * @return a {@link Vector3D} object representing the normalised version of the surface normal vector at a point on the plane's surface
     */
    public Vector3D surfaceNormal(Point3D intersectionPoint){
        return normal;
    }

    /***
     * Provides the surface associated with the plane
     * @return a {@link Surface} object
     */
    public Surface getSurface(){
        return surface;
    }

}
