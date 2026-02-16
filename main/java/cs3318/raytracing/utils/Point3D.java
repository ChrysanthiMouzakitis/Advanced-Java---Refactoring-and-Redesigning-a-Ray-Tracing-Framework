package cs3318.raytracing.utils;

/***
 * Represents a point in 3D space
 * <p>
 *     Maintains x,y,z coordinates
 * </p>
 */
public class Point3D {
    final public float x, y, z;

    /***
     * Constructs a Point with specified x,y,z coordinates
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param z the z coordinate of the point
     * @throws IllegalArgumentException if x or y or z are not a number
     */
    public Point3D(float x, float y, float z) {
        if (Float.isNaN(x) || Float.isNaN(z) || Float.isNaN(y)){
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        }
        this.x = x; this.y = y; this.z = z;
    }

    /***
     * Constructs a point from a given vector
     * @param v the {@link Vector3D} object
     * @throws IllegalArgumentException if v is {@code null}
     */
    public Point3D(Vector3D v) {
        if (v == null) {
            throw new IllegalArgumentException("Vector3D cannot be null");
        }
        x = v.x;
        y = v.y;
        z = v.z;
    }

    /***
     * Creates a copy of another point
     * @param p the {@link Point3D} object to copy
     * @throws IllegalArgumentException if p is {@code null}
     */
    public Point3D(Point3D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point3D cannot be null");
        }
        x = p.x;
        y = p.y;
        z = p.z;
    }
}
