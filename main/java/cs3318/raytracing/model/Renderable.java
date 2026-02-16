package cs3318.raytracing.model;

import cs3318.raytracing.utils.Point3D;
import cs3318.raytracing.utils.Vector3D;

/***
 * Defines behaviour for all objects in a 3D ray-tracing system
 * <p>
 * Implemented by objects such as {@link Sphere} and {@link Plane}
 * as they can be rendered in a 3D space.
 * Contains methods to determine if a ray intersects with the object,
 * provide a string representation of the object,
 * retrieve the surface associated with the object,
 * calculate the surface normal vector at a given point on the object
 * </p>
 */

public interface Renderable {

    /***
     * Calculates if a ray intersects with the renderable object
     * @param r a {@link Ray} object, the ray being traced
     * @param intersectDistance a float that represents the maximum distance to consider for the intersection calculation
     * @return a float, null if no intersection, or the distance along the ray to the intersection point
     */
    Float intersect(Ray r, float intersectDistance);

    /***
     * Creates a string representation of the renderable object
     * @return a string description of the object
     */
    String toString();

    /***
     * Calculates the surface normal vector at a given point on the object
     * @param intersectionPoint a specific point on the object, a {@link Point3D} object
     * @return a {@link Vector3D} object representing the normalized surface normal at the specified point
     */
    Vector3D surfaceNormal(Point3D intersectionPoint);

    /***
     * Returns the surface associated with the object
     * @return {@link Surface} object
     */
    Surface getSurface();
}

