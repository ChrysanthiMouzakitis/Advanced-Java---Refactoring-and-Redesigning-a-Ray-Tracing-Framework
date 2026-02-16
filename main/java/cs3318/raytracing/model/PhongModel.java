package cs3318.raytracing.model;

/***
 * Represents the Phong reflection model
 * <p>
 *     Used for shading and illuminating surfaces based on its properties,
 *     interaction with light, and the view of the surface. It accounts for ambient,
 *     diffuse and specular reflection components, as well as transparency and reflectance
 * </p>
 */
public class PhongModel {

    final float ambientReflectionCoefficient, diffuseReflectionCoefficient, specularReflectionCoefficient,
    transmissionCoefficient, reflectanceCoefficient;
    final float exponent; //used in calculation of the specular reflection, determines the sharpness of the specular highlights
    final float index; //the refractive index

    /***
     * Constructs a Phong reflection model with the given coefficients, properties
     * @param ambientReflectionCoefficient the ambient reflection coefficient in the range [0-1]
     * @param diffuseReflectionCoefficient the diffuse reflection coefficient in the range [0-1]
     * @param specularReflectionCoefficient the specular reflection coefficient in the range [0-1]
     * @param transmissionCoefficient the transmission coefficient in the range [0-1]
     * @param reflectanceCoefficient the reflectance coefficient in the range [0-1]
     * @param exponent the specular exponent, determines the sharpness of the highlights
     * @param index the refractive index, used in calculations to do with light bending
     */
    PhongModel (float ambientReflectionCoefficient, float diffuseReflectionCoefficient, float specularReflectionCoefficient,
                float transmissionCoefficient, float reflectanceCoefficient, float exponent, float index){
        this.ambientReflectionCoefficient = ambientReflectionCoefficient;
        this.diffuseReflectionCoefficient = diffuseReflectionCoefficient;
        this.specularReflectionCoefficient = specularReflectionCoefficient;
        this.transmissionCoefficient = transmissionCoefficient;
        this.reflectanceCoefficient = reflectanceCoefficient;
        this.exponent = exponent;
        this.index = index;
    }
}
