package cs3318.raytracing.model;

import cs3318.raytracing.utils.Point3D;
import cs3318.raytracing.utils.Vector3D;

/***
 * Represents a light in 3D space
 * <p>
 * Provides a base for more specific types of light ( directional, ambient, point)
 * Maintains the RGB intensity of the light and includes an abstract method to calculate the light vector
 * based on an intersection point
 * </p>
 */

public abstract class Light {

    /***
     * the red, green, blue intensity components of the light,
     * in the range [0-1]
     */


    final float rIntensity, gIntensity, bIntensity;

    /***
     * Constructs a light source
     * @param r the red intensity of the light
     * @param g the green intensity of the light
     * @param b the blue intensity of the light
     */
    Light(float r, float g, float b) {
        rIntensity = r;
        gIntensity = g;
        bIntensity = b;
    }

    /***
     * an abstract method to calculate the light vector based on
     * an intersection point between the light and an object
     * <p>
     *    Must be implemented by subclasses as it is abstract
     *    and every type of light will have a different calculation
     * </p>
     * @param intersectionPoint a {@link Point3D} object that represents
     *                         the intersection point between an object and the light
     * @return a {@link Vector3D} object representing the light vector
     */
    abstract Vector3D calculateLightVector (Point3D intersectionPoint);
}





