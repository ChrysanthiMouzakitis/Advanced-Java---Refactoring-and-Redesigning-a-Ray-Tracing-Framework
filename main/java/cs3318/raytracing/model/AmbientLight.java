package cs3318.raytracing.model;

import cs3318.raytracing.utils.Vector3D;
import cs3318.raytracing.utils.Point3D;

/***
 * Represents an ambient light
 * <p>
 *     It has no direction so it affects all the objects in the scene equally
 *     It extends the {@link Light} class
 * </p>
 */


public class AmbientLight extends Light {

    /***
     *
     * Constructor for a AmbientLight object with the below red,green,blue intensities
     * @param r the red intensity of the light
     * @param g the green intensity of the light
     * @param b the blue intensity of the light
     */
    AmbientLight(float r, float g, float b){
        super(r, g, b);
    }


    /***
     * Calculates the light vector from the light to a specific intersection point
     * <p>
     *     Always returns null as ambient light has no direction, and therefore no calculation
     * </p>
     * @param intersectionPoint  a {@link Point3D} object that represents the point in 3D space where light and an object intersect
     * @return will always be {@code null} as ambient light has no direction
     */

    @Override
    Vector3D calculateLightVector(Point3D intersectionPoint) {
        return null;
    }
}
