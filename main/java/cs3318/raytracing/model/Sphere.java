package cs3318.raytracing.model;

import cs3318.raytracing.utils.Point3D;
import cs3318.raytracing.utils.Vector3D;

/***
 * Represents a sphere in 3D space
 * <p>
 * It implements the {@link Renderable} interface,
 * and provides implementations for methods to calculate if
 * a ray intersects the sphere, compute the normalised surface vector at a given point,
 * return a string representation of the sphere, get the surface object associated with the sphere
 * </p>
 */

class Sphere implements Renderable {
    private final Surface surface;
    private final Point3D center;
    private final float radius;
    private final float radSqr;

    /***
     * Constructs a sphere with a given surface, radius, and center as specified
     * <p>
     *     also initialises the squared value of the radius
     * </p>
     * @param center a {@link Point3D} object representing the center point of the sphere
     * @param radius a float to represent the length of the radius of the sphere
     * @param surface a {@link Surface} object that represents the surface used to construct the sphere
     */
    Sphere(Point3D center, float radius, Surface surface) {
        this.surface = surface;
        this.center = center;
        this.radius = radius;
        radSqr = radius*radius;
    }

    /***
     * Calculates if a ray intersects with the sphere, and if it does, returns the distance to the intersection point
     * @param ray a {@link Ray} object, the ray being traced
     * @param intersectDistance a float that represents the maximum distance to consider for the intersection calculation
     * @return null if the ray does not intersect, the distance to the intersection point if the ray does intersect the sphere
     */
    public Float intersect(Ray ray, float intersectDistance) {
        float dx = center.x - ray.origin.x;
        float dy = center.y - ray.origin.y;
        float dz = center.z - ray.origin.z;
        float v = ray.direction.dot(dx, dy, dz);

        // Do the following quick check to see if there is even a chance
        // that an intersection here might be closer than a previous one
        if (v - radius > intersectDistance)
            return null;

        // Test if the ray actually intersects the sphere
        float t = radSqr + v * v - dx * dx - dy * dy - dz * dz;
        if (t < 0)
            return null;

        // Test if the intersection is in the positive
        // ray direction, and it is the closest so far
        t = v - ((float) Math.sqrt(t));
        if ((t > intersectDistance) || (t < 0))
            return null;

        return t;
    }


    /***
     * Calculates the normalized version of the surface normal vector at a point on the sphere's surface
     * usually an intersection point between the sphere and a ray
     * @param intersectionPoint a specific point on the object, a {@link Point3D} object
     * @return a normalized version of the surface normal vector
     */
    public Vector3D surfaceNormal(Point3D intersectionPoint) {
        return Vector3D.normalize(new Vector3D(intersectionPoint.x - center.x, intersectionPoint.y - center.y, intersectionPoint.z - center.z));
    }

    /***
     * Provides a string representation of the sphere
     * @return a string representation of the object including its center and radius
     */
    public String toString() {
        return ("sphere " + center + " " + radius);
    }

    /***
     * Provides the surface associated with the sphere
     * @return a {@link Surface} object
     */
    public Surface getSurface() {
        return surface;
    }
}