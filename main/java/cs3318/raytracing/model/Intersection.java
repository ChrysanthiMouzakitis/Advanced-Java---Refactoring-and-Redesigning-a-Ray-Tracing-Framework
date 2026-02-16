package cs3318.raytracing.model;

import cs3318.raytracing.utils.Point3D;
import cs3318.raytracing.utils.Vector3D;

// get intersect to return the intersection point and the distance to it

//   1. the point of intersection (p) - intersection method which returns info to trace method of ray
//   2. a unit-length surface normal (n) - method of the intersected object
//   3. a unit-length vector towards the ray's origin (v) - can get that just from the ray itself

/***
 * Represents the intersection of a renderable object and a ray in a 3D scene
 * <p>
 *     The intersection maintains information on which object intersects with the ray,
 *     the point of intersection, the unit vector that points towards the ray's origin,
 *     the surface normal at the point of intersection, and the number of bounces the ray
 *     has left.
 * </p>
 * <p>
 *     Provides methods to calculate the intersection point, calculate the
 *     reflected vector, provide a string representation of the intersection,
 *     calculate the Lambert reflection coefficient and calculate the specular
 *     reflection coefficient.
 * </p>
 */
class Intersection {

    final Renderable object;
    final Point3D point;
    final Vector3D unitVecToRay;
    final Vector3D surfaceNormal;
    final int bounce;

    /***
     * Constructs an Intersection with specified ray, object and intersection distance
     * @param ray the {@link Ray} object, that is involved in the intersection
     * @param object the {@link Renderable} object that is involved in the intersection
     * @param intersectionDistance the distance between the ray's origin and the intersection point
     */
    Intersection(Ray ray, Renderable object, float intersectionDistance){
        this.object = object;
        point = ray.calculatePoint(intersectionDistance);// calculateIntersectionPoint(intersectionDistance, ray);
        unitVecToRay = ray.unitToOrigin();
        surfaceNormal = object.surfaceNormal(point);
        bounce = ray.getBounces();
    }

    /***
     * Calculates the point of intersection between the renderable object
     * and the ray, using the distance and the ray involved
     * @param intersectionDistance the distance between the ray's origin and the point of intersection
     * @param ray the {@link Ray} object being traced
     * @return a {@link Point3D} object, the point of intersection
     */
    private Point3D calculateIntersectionPoint(float intersectionDistance, Ray ray) {
        float px = ray.origin.x + intersectionDistance * ray.direction.x;
        float py = ray.origin.y + intersectionDistance * ray.direction.y;
        float pz = ray.origin.z + intersectionDistance * ray.direction.z;

        return new Point3D(px, py, pz);
    }

    /***
     * Calculates the reflected vector based on the ray's direction and the surface normal
     * @return a {@link Vector3D} object, the reflected vector, or {@code null} if there is no reflection
     */
    public Vector3D calculateReflect() {
        float t = unitVecToRay.dot(surfaceNormal);
        if (t > 0) {
            t *= 2;
            return new Vector3D(t * surfaceNormal.x - unitVecToRay.x, t * surfaceNormal.y - unitVecToRay.y,
                    t * surfaceNormal.z - unitVecToRay.z);
        }
        return null;
    }

    /***
     * Provides a string representation of the Intersection, comprised of the
     * string representation of the point of intersection, and the string representation
     * of the renderable object
     * @return the string representation of the intersection
     */
    @Override
    public String toString() {
        return point.toString() + object.toString();
    }

    /***
     * Calculates and returns the Lambertian reflection coefficient for a specified light vector
     * based on the surface normal of the intersection. This represents the diffuse reflection intensity.
     * @param lightVector the {@link Vector3D} object, from the intersection point to the light source
     * @return the Lambertian reflection coefficient
     */
    float calculateLambert(Vector3D lightVector){
        return Vector3D.dot(surfaceNormal,lightVector);
    }

    /***
     * Calculates and returns the specular reflection coefficient for a specified light vector
     * This represents the intensity of specular highlights
     * @param lightVector a {@link Vector3D} object, the vector from the intersection point to the light source
     * @param lambert the Lambertian reflection coefficient
     * @return the specular reflection coefficient
     */
    float calculateSpec(Vector3D lightVector, float lambert){
        lambert *= 2;
        return unitVecToRay.dot(lambert*surfaceNormal.x - lightVector.x, lambert*surfaceNormal.y - lightVector.y, lambert*surfaceNormal.z - lightVector.z);
    }

}
