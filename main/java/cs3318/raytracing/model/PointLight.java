package cs3318.raytracing.model;

import cs3318.raytracing.utils.Point3D;
import cs3318.raytracing.utils.Vector3D;

/***
 * represents a Point Light
 * <p>
 *     A point light is a light source from a specific point in 3D space,
 *     the light is emitted in all directions and the point is represented by a {@link Point3D} object
 * </p>
 *
 */


class PointLight extends Light {

    /** the point in 3D space of the position of the light*/
    final Point3D lightPoint;

    /***
     * Constructs a Point light with the specified RGB intensities
     * and the position in 3D space
     * @param r the red intensity of the light
     * @param g the green intensity of the light
     * @param b the blue intensity of the light
     * @param point the 3D position of the light
     */
    PointLight(float r, float g, float b, Point3D point) {
        super(r, g, b);
        lightPoint = point;
    }

    /***
     * Calculates the light vector from the light source to a given intersection point
     *
     * @param intersectionPoint a {@link Point3D} object which represents the point at which the light intersects with an object
     * @return a normalised {@link Vector3D} that points from the light to the intersection point
     */
    @Override
    Vector3D calculateLightVector(Point3D intersectionPoint) {
        return Vector3D.normalize(new Vector3D(lightPoint.x - intersectionPoint.x, lightPoint.y - intersectionPoint.y, lightPoint.z - intersectionPoint.z));
    }

}


