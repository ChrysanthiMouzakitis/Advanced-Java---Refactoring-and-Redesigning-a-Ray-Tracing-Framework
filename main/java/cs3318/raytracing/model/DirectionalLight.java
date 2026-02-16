package cs3318.raytracing.model;

import cs3318.raytracing.utils.Point3D;
import cs3318.raytracing.utils.Vector3D;

/***
 * Represents a directional light source
 * <p>
 *     It is a light that shines in a particular direction, and uses the {@link Vector3D} class to represent this direction.
 *     It extends the {@link Light} class
 * </p>
 */

class DirectionalLight extends Light{

    private final Vector3D lightVector;

    /***
     * Constructs a directional light with the specified RGB intensities and direction vector
     * <p>
     *     It normalises the direction vector to ensure consistent light calculations
     * </p>
     * @param r the red intensity of the light
     * @param g the green intensity of the light
     * @param b the blue intensity of the light
     * @param direction a {@link Vector3D} object representing the direction of the directional light
     */

    DirectionalLight(float r, float g, float b, Vector3D direction) {
        super(r, g, b);
        lightVector = Vector3D.normalize(direction);
    }

    /***
     * Calculates the inverse of the light's directional vector and returns it
     *
     * @param intersectionPoint a {@link Point3D} object which represents the point at which the light intersects with an object
     * @return a {@link Vector3D} object to represent the light's incoming direction relative to an intersection point in the scene
     */


    @Override
    Vector3D calculateLightVector(Point3D intersectionPoint) {
        return new Vector3D(-lightVector.x, -lightVector.y, -lightVector.z);
    }

}


