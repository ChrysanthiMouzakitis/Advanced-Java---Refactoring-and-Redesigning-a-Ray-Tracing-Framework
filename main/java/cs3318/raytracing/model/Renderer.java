package cs3318.raytracing.model;

import cs3318.raytracing.utils.Point3D;
import cs3318.raytracing.utils.Vector3D;

import java.awt.Color;

/***
 * This class is responsible for rendering the 3D scene, pixel by pixel.
 * <p>
 * * It calculates the colour of each pixel by tracing rays through the scene, and
 * considers the interaction between different light sources and the surface of renderable objects in the scene.
 *</p>
 */
public class Renderer {

    private static final float TINY = 0.001f;
    private Color background = new Color(0, 0, 0); //default background colour, black
    public static final int MAX_BOUNCES = 16;
    private int maxBounces; //max number of bounces for reflection

    /***
     * Constructs the renderer with default settings
     */
    public Renderer () {
        maxBounces = MAX_BOUNCES;
    }

    /***
     * Constructs the renderer with specified background colour and max bounces
     * @param background a {@link Color} object, specifies the colour for the background
     * @param maxBounces the maximum number of reflection bounces
     */
    public Renderer (Color background, int maxBounces) {
        setBackground(background);
        setMaxBounces(maxBounces);
    }

    /***
     * Sets the background colour to a specified colour
     * @param background a {@link Color} object, the new background colour
     */
    public void setBackground(Color background) {
        this.background = background;
    }

    /***
     * Returns the maximum number of refection bounces for rays
     * @return the max number of bounces
     */
    public int getMaxBounces(){
        return maxBounces;
    }

    /***
     * Sets a specified value for the maximum number of bounces allowed for ray reflection
     * @param bounces the new value for max bounces allowed
     */
    public void setMaxBounces(int bounces){
        this.maxBounces = bounces;
    }

    /***
     * Renders a single pixel by calculating its colour by tracing a ray through the scene
     * @param i the x coordinate of the pixel
     * @param j the y coordinate of the pixel
     * @param scene the 3D scene with renderable lights and objects
     * @param camera the camera for this scene
     * @return the {@link Color} object representing the colour of the pixel
     */
    public Color renderPixel(int i, int j, Scene scene, Camera camera){
        Ray ray = new Ray(camera.getEye(), camera.calculateDirection(i, j), maxBounces);
        Intersection intersection = ray.trace(scene.getObjects());
        if (intersection != null) {
            return shadePixel(scene, intersection);
        }
        return background;
    }

    /***
     *Returns if a specified point is in a shadow given a specified light source
     * @param intersection the intersection point on a renderable object
     * @param lightVector the vector that points towards the light source
     * @param scene the 3D scene that contains all the objects and light sources
     * @return {@code true} if the point is in shadow, {@code false} if the point is not
     */
    private boolean inShade(Intersection intersection, Vector3D lightVector, Scene scene){
        Point3D poffset = new Point3D(intersection.point.x + TINY*lightVector.x, intersection.point.y + TINY*lightVector.y, intersection.point.z + TINY*lightVector.z);
        Ray shadowRay = new Ray(poffset, lightVector, 0);
        return (shadowRay.trace(scene.getObjects()) != null);
    }

    /***
     * Accounts for any diffuse lighting when calculating the pixel colour
     * @param surface the surface properties of the object
     * @param light the light source
     * @param lambert the lambertain reflection coefficient
     * @param rgb a {@link RGB} object, the rgb colour accumulator
     */
    private void addDiffuseReflectionCoefficient(Surface surface, Light light, float lambert, RGB rgb){
        if (surface.phong.diffuseReflectionCoefficient > 0) {
            float diffuse = surface.phong.diffuseReflectionCoefficient*lambert;
            rgb.r += diffuse*surface.rIntrinsic*light.rIntensity;
            rgb.g += diffuse*surface.gIntrinsic*light.gIntensity;
            rgb.b += diffuse*surface.bIntrinsic*light.bIntensity;
        }
    }

    /***
     * Accounts for any specular lighting when calculating the pixel colour
     * @param surface the surface properties of the object
     * @param light the light source
     * @param lambert the lambertain reflection coeffficient
     * @param intersection the intersection information
     * @param lightVector the vector pointing towards the light source
     * @param rgb a {@link RGB} object, the rgb colour accumulator
     */
    private void addSpecularReflectionCoefficient(Surface surface, Light light, float lambert, Intersection intersection, Vector3D lightVector, RGB rgb){
        if (surface.phong.specularReflectionCoefficient > 0) {
            float spec = intersection.calculateSpec(lightVector, lambert);
            if (spec > 0) {
                spec = surface.phong.specularReflectionCoefficient*((float) Math.pow(spec, surface.phong.exponent));
                rgb.r += spec*light.rIntensity;
                rgb.g += spec*light.gIntensity;
                rgb.b += spec*light.bIntensity;
            }
        }
    }

    /***
     * Accounts for reflected light when calculating the pixel colour
     * @param surface the surface properties of the object
     * @param intersection the intersection information
     * @param scene the scene that contains all the renderable objects and lights
     * @param rgb a {@link RGB} object, the rgb colour accumulator
     */
    private void addReflectance(Surface surface, Intersection intersection, Scene scene, RGB rgb) {
        if (surface.phong.reflectanceCoefficient > 0) {
            Vector3D reflect = intersection.calculateReflect();
            if (reflect != null) {
                Point3D poffset = new Point3D(
                        intersection.point.x + TINY*reflect.x,
                        intersection.point.y + TINY*reflect.y,
                        intersection.point.z + TINY*reflect.z);
                Ray reflectedRay = new Ray(poffset, reflect, intersection.bounce-1);
                Intersection reflectedIntersection = reflectedRay.trace(scene.getObjects());
                if (reflectedIntersection != null) {
                    Color rcolor = shadePixel(scene, reflectedIntersection);
                    rgb.r += surface.phong.reflectanceCoefficient*rcolor.getRed();
                    rgb.g += surface.phong.reflectanceCoefficient*rcolor.getGreen();
                    rgb.b += surface.phong.reflectanceCoefficient*rcolor.getBlue();
                } else {
                    rgb.r += surface.phong.reflectanceCoefficient*background.getRed();
                    rgb.g += surface.phong.reflectanceCoefficient*background.getGreen();
                    rgb.b += surface.phong.reflectanceCoefficient*background.getBlue();
                }
            }
        }
    }

    /***
     * Accounts for ambient light in calculating the colour of the pixel
     * @param surface the surface of the object being rendered
     * @param light the light source
     * @param rgb a {@link RGB} object, the rgb colour accumulator
     */
    private void addAmbientLightColor(Surface surface, Light light, RGB rgb){
        rgb.r += surface.phong.ambientReflectionCoefficient*surface.rIntrinsic*light.rIntensity;
        rgb.g += surface.phong.ambientReflectionCoefficient*surface.gIntrinsic*light.gIntensity;
        rgb.b += surface.phong.ambientReflectionCoefficient*surface.bIntrinsic*light.bIntensity;
    }

    /***
     * Calculates the contribution of all light sources in the scene for a specified intersection to the
     *  pixel's colour. It accounts for all types of light such as ambient, diffuse , specular.
     * @param scene the 3D scene conatining all lights and renderable objects
     * @param surface the surface properties of the object being rendered
     * @param intersection the information on the specified intersection
     * @param rgb a {@link RGB} object, the rgb colour accumulator
     */
    private void addLighting(Scene scene, Surface surface, Intersection intersection, RGB rgb) {
        for (Light light : scene.getLights()) {
            if (light instanceof AmbientLight) {
                addAmbientLightColor(surface, light, rgb);
            }
            else {
                Vector3D lightVector = light.calculateLightVector(intersection.point);
                if (inShade(intersection, lightVector, scene))
                    break;
                float lambert = intersection.calculateLambert(lightVector);
                if (lambert > 0) {
                    addDiffuseReflectionCoefficient(surface, light, lambert, rgb);
                    addSpecularReflectionCoefficient(surface, light, lambert, intersection, lightVector, rgb);
                }
            }
        }
    }

    /***
     * Shades a single pixel a colour based on the intersection point and the scene lighting
     * @param scene the 3D scene that contains all the light sources and renderable objects
     * @param intersection the information of the intersection
     * @return a {@link Color} object, the colour of the pixel
     */
    private Color shadePixel(Scene scene, Intersection intersection){
        RGB rgb = new RGB();
        Surface surface = intersection.object.getSurface();

        addLighting(scene, surface, intersection, rgb);
        addReflectance(surface, intersection, scene, rgb);

        if (intersection.bounce <= 0) { addReflectance(surface, intersection, scene, rgb); }

        rgb.boundValues();
        return new Color(rgb.r, rgb.g, rgb.b);
    }

    /**
     * Class created to manage RGB values, ensuring they are within the valid range [0-1]
     */
    private static class RGB {
        float r = 0;
        float g = 0;
        float b = 0;

        void boundValues(){
            r = Math.min(r, 1f);
            g = Math.min(g, 1f);
            b = Math.min(b, 1f);

            r = (r < 0) ? 0 : r;
            g = (g < 0) ? 0 : g;
            b = (b < 0) ? 0 : b;
        }
    }
}

