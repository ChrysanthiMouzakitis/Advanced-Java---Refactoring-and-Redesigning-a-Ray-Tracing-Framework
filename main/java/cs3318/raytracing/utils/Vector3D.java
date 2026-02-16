package cs3318.raytracing.utils;

/***
 * Represents a 3D vector with x, y, z components
 * <p>
 *     Provides methods for vector calculations such as dot product,
 *     cross product, normalisation, and calculating the length of the vector
 * </p>
*/
public class Vector3D {
    public final float x, y, z;

    /***
     * Constructs a 3D vector with specified x,y,z values
     * @param x the x coordinate of the vector
     * @param y the y coordinate of the vector
     * @param z the z coordinate of the vector
     * @throws  IllegalArgumentException if x or y or z is not a number
     */
    public Vector3D(float x, float y, float z) {
        if (Float.isNaN(x) || Float.isNaN(z) || Float.isNaN(y)){
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        }
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /***
     * Constructs a copy of another 3D vector
     * @param v the {@link Vector3D} object to be copied
     * @throws IllegalArgumentException if v is {@code null}
     */
    public Vector3D(Vector3D v) {
        if (v == null) {
            throw new IllegalArgumentException("Vector3D cannot be null");
        }
        x = v.x;
        y = v.y;
        z = v.z;
    }

    /***
     * Constructs a 3D vector from a {@link Point3D} object
     * @param p the {@link Point3D} object
     * @throws IllegalArgumentException if p is {@code null}
     */
    public Vector3D(Point3D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point3D cannot be null");
        }
        x = p.x;
        y = p.y;
        z = p.z;
    }

    /***
     * Calculate the dot product of this vector with another vector B
     * @param B the {@link Vector3D} object, the other vector
     * @return the dot product
     * @throws IllegalArgumentException if B is {@code null}
     */
    public float dot(Vector3D B) {
        if (B == null) {
            throw new IllegalArgumentException("Vector3D cannot be null");
        }return (x * B.x + y * B.y + z * B.z);
    }

    /***
     * Calculates the dot product of this vector and specified x,y,z values
     * @param Bx the x component of the other vector
     * @param By the y component of the other vector
     * @param Bz the z component of the other vector
     * @return the dot product
     */
    public float dot(float Bx, float By, float Bz) {
        return (x * Bx + y * By + z * Bz);
    }

    /***
     * Calculates the dot product between two given vectors
     * @param A the first {@link Vector3D} object
     * @param B the second {@link Vector3D} object
     * @return the dot product
     * @throws IllegalArgumentException if A or B are {@code null}
     */
    public static float dot(Vector3D A, Vector3D B) {
        if (A == null || B == null) {
            throw new IllegalArgumentException("Vector3D cannot be null");
        }
        return (A.x * B.x + A.y * B.y + A.z * B.z);
    }

    /***
     * Calculates the cross product of this vector with another specified vector
     * @param B the other {@link Vector3D} object
     * @return the cross product
     * @throws IllegalArgumentException if B is {@code null}
     */
    public Vector3D cross(Vector3D B) {
        if (B == null) {
            throw new IllegalArgumentException("Vector3D cannot be null");
        }
        return new Vector3D(y * B.z - z * B.y, z * B.x - x * B.z, x * B.y - y * B.x);
    }

    /***
     * Calculates the cross product of this vector and specified x,y,z values
     * @param Bx the x component of the other vector
     * @param By the y component of the other vector
     * @param Bz the z component of the other vector
     * @return the cross product
     */
    public final Vector3D cross(float Bx, float By, float Bz) {
        return new Vector3D(y * Bz - z * By, z * Bx - x * Bz, x * By - y * Bx);
    }

    /***
     * Calculates the cross product between two given vectors
     * @param A the first {@link Vector3D} object
     * @param B the second {@link Vector3D} object
     * @return the cross product
     * @throws IllegalArgumentException if A or B are {@code null}
     */
    public static Vector3D cross(Vector3D A, Vector3D B) {
        if (A == null || B == null) {
            throw new IllegalArgumentException("Vector3DS cannot be null");
        }
        return new Vector3D(A.y*B.z - A.z*B.y, A.z*B.x - A.x*B.z, A.x*B.y - A.y*B.x);
    }

    /***
     *  Calculates the length of the vector, also called the magnitude
     * @return the length, a float
     */
    public float length() {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    /***
     * The length of a given vector A
     * @param A the {@link Vector3D} object to get the length of
     * @return the length, a float
     */
    public static float length(Vector3D A) {
        if (A == null) {
            throw new IllegalArgumentException("Vector3D cannot be null");
        }
        return (float) Math.sqrt(A.x*A.x + A.y*A.y + A.z*A.z);
    }

    /***
     * Normalises the given vector
     * @param A the {@link Vector3D} object to normalised
     * @return a new {@link Vector3D} object that is normalised
     * @throws IllegalArgumentException if A is {@code null}
     */
    public static Vector3D normalize(Vector3D A) {
        if (A == null) {
            throw new IllegalArgumentException("Vector3D cannot be null");
        }
        float t = A.x * A.x + A.y * A.y + A.z * A.z;
        if (t != 0 && t != 1) t = (float) (1 / Math.sqrt(t));
        return new Vector3D(A.x * t, A.y * t, A.z * t);
    }

    /***
     * Provides the string representation of the vector
     * <p>
     *     returns the vector's x, y, z values
     * </p>
     * @return the string representation of the vector
     */
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }
}
