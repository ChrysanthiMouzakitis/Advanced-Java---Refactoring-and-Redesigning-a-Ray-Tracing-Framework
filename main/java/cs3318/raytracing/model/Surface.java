package cs3318.raytracing.model;


import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/***
 * Represents surface properties of a renderable object in 3D space
 * <p>
 *     Maintains the surface's intrinsic colour values, a Phong reflection model, and contains
 *     a series of predefined surfaces for convenience
 * </p>
 */
public class Surface {
    float rIntrinsic, gIntrinsic, bIntrinsic;    // surface's intrinsic color [0-1]
    PhongModel phong; //phong reflection model used to describe the surface's interaction with light

    private static final float I255 = 0.00392156f;  // 1/255, constant factor used for calculations with reflection

    /***
     * A map of predefined surfaces and their properties
     */
    private static final Map<String, Surface> predefinedSurfaces = new HashMap<>();

    static {
        predefinedSurfaces.put("matte black", new Surface(0.0f, 0.0f, 0.0f, 0.2f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f));
        predefinedSurfaces.put("glossy white", new Surface(1.0f, 1.0f, 1.0f, 0.1f, 0.7f, 0.7f, 20.0f, 0.2f, 0.0f, 1.0f));
        predefinedSurfaces.put("mirror", new Surface(1.0f, 1.0f, 1.0f, 0.0f, 0.1f, 0.9f, 50.0f, 1.0f, 0.0f, 1.5f));
        predefinedSurfaces.put("glass", new Surface(0.9f, 0.9f, 0.9f, 0.0f, 0.1f, 0.8f, 30.0f, 0.0f, 0.9f, 1.5f));
        predefinedSurfaces.put("red plastic", new Surface(1.0f, 0.0f, 0.0f, 0.2f, 0.6f, 0.4f, 10.0f, 0.1f, 0.0f, 1.0f));
        predefinedSurfaces.put("blue rubber", new Surface(0.0f, 0.0f, 1.0f, 0.3f, 0.7f, 0.2f, 5.0f, 0.0f, 0.0f, 1.0f));
        predefinedSurfaces.put("gold", new Surface(1.0f, 0.84f, 0.0f, 0.1f, 0.6f, 0.7f, 25.0f, 0.5f, 0.0f, 1.0f));
        predefinedSurfaces.put("silver", new Surface(0.75f, 0.75f, 0.75f, 0.2f, 0.7f, 0.6f, 20.0f, 0.7f, 0.0f, 1.0f));
        predefinedSurfaces.put("emerald", new Surface(0.31f, 0.78f, 0.47f, 0.2f, 0.5f, 0.5f, 25.0f, 0.0f, 0.6f, 1.5f));
        predefinedSurfaces.put("transparent water", new Surface(0.0f, 0.1f, 0.8f, 0.0f, 0.1f, 0.7f, 30.0f, 0.0f, 0.9f, 1.33f));
    }

    /***
     * Constructs a surface with specified colours and reflection properties
     * @param rval the red value of the surface colour in the range [0,1]
     * @param gval the green value of the surface colour in the range [0,1]
     * @param bval the blue value of the surface colour in the range [0,1]
     * @param ambientReflectionCoefficient the coefficient for ambient light reflection in the range [0,1]
     * @param diffuseReflectionCoefficient the coefficient for diffuse light reflection in the range [0,1]
     * @param specularReflectionCoefficient the coefficient for specular light reflection in the range [0,1]
     * @param exponent the specular highlight exponent
     * @param reflectanceCoefficient the coefficient for reflectance in the range [0,1]
     * @param transmissionCoefficient the coefficient for transmission in the range [0,1]
     * @param index the refractive index of the surface
     * @throws IllegalArgumentException if any coefficient or colour value is out of the range [0,1]
     */
    public Surface(float rval, float gval, float bval,
                   float ambientReflectionCoefficient, float diffuseReflectionCoefficient,
                   float specularReflectionCoefficient, float exponent,
                   float reflectanceCoefficient, float transmissionCoefficient, float index) {
        validateColor(rval, gval, bval);
        validateCoefficients(ambientReflectionCoefficient, diffuseReflectionCoefficient, specularReflectionCoefficient,
                reflectanceCoefficient, transmissionCoefficient);
        rIntrinsic = rval;
        gIntrinsic = gval;
        bIntrinsic = bval;
        phong = new PhongModel(ambientReflectionCoefficient, diffuseReflectionCoefficient,
                specularReflectionCoefficient, transmissionCoefficient,
                reflectanceCoefficient * I255, exponent, index);
    }

    /***
     * Returns a predefined surface by name
     * @param name the name of the predefined surface to return
     * @return a {@link Surface} object if it exists, or {@code null} if it does not
     */
    public static Surface getPredefinedSurface(String name) {
        return predefinedSurfaces.getOrDefault(name, null);
    }

    /***
     * Returns all predefined surfaces in an unmodifiable map
     * @return an unmodifiable map of all predefined surfaces
     */
    public static Map<String, Surface> getAllPredefinedSurfaces() {
        return Collections.unmodifiableMap(predefinedSurfaces);
    }

    /***
     * Validates if all given colours are in the allowed range [0-1]
     * @param r the red value to validate
     * @param g the green value to validate
     * @param b the blue value to validate
     * @throws IllegalArgumentException if any of the values are outside the range [0-1]
     */
    private static void validateColor(float r, float g, float b) {
        if (r < 0 || r > 1 || g < 0 || g > 1 || b < 0 || b > 1) {
            throw new IllegalArgumentException("Color values must be in the range [0, 1].");
        }
    }

    /***
     * Validates if all provided coefficients are within the allowed range [0-1]
     * @param values the coefficients to be validated
     * @throws IllegalArgumentException if any of the values are outside the range [0-1]
     */
    private static void validateCoefficients(float... values) {
        for (float value : values) {
            if (value < 0 || value > 1) {
                throw new IllegalArgumentException("Reflection and transmission coefficients must be in the range [0, 1].");
            }
        }
    }
}