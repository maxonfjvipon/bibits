package main.java.org.bibits;

/**
 * Bit
 */
public class Bit implements Xor<Bit>, AsString {

    /**
     * Value
     */
    private final boolean value;

    /**
     * Ctor
     *
     * @param other Other bit
     */
    public Bit(Bit other) {
        this(other.value);
    }

    /**
     * Ctor
     */
    public Bit() {
        this(false);
    }

    /**
     * Ctor
     *
     * @param value Int value (1 or 0)
     */
    public Bit(int value) {
        this(value > 0);
    }

    /**
     * Ctor
     *
     * @param value Bool value
     */
    public Bit(boolean value) {
        this.value = value;
    }

    /**
     * Present as int
     * @return 1 if {@code value} is true, else 0
     */
    public int value() {
        return value ? 1 : 0;
    }

    @Override
    public Bit xor(Bit other) {
        return new Bit(value ^ other.value);
    }

    @Override
    public String asString() {
        return value ? "1" : "0";
    }
}
