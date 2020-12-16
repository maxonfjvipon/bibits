package main.java.org.bits;

/**
 * Bit
 */
public class Bit {

    /**
     * Value
     */
    private final boolean value;

    /**
     * Ctor
     *
     * @param value Bool value
     */
    public Bit(boolean value) {
        this.value = value;
    }

    /**
     * Ctor
     *
     * @param other Other bit
     */
    public Bit(Bit other) {
        this.value = other.value;
    }

    /**
     * XOR
     *
     * @param other The 2nd argument
     * @return Result of XOR({@code this}, {@code other})
     */
    public Bit xor(Bit other) {
        return new Bit(value ^ other.value);
    }

    /**
     * Present as String
     * @return "1" if {@code value} is true, else "0"
     */
    public String asString() {
        return value ? "1" : "0";
    }

    /**
     * Present as int
     * @return 1 if {@code value} is true, else 0
     */
    public int intValue() {
        return value ? 1 : 0;
    }

    /**
     * Get bool value
     * @return Bool value
     */
    public boolean boolValue() {
        return value;
    }
}
