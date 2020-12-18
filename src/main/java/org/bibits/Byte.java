package main.java.org.bibits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Byte
 */
public class Byte implements Bits, Xor<Byte>, AsString {

    /**
     * Bits
     */
    private final Bits origin;

    /**
     * ctor
     *
     * @param dec Decimal num
     */
    public Byte(final int dec) {
        this(() -> {
            Bit[] bits = new Bit[8];
            int _dec = dec;
            for (int i = 7; i >= 0; i--) {
                if (_dec > 0) {
                    bits[i] = new Bit(_dec % 2 != 0);
                    _dec /= 2;
                    continue;
                }
                bits[i] = new Bit();
            }
            return bits;
        });
    }

    /**
     * Ctor
     *
     * @param sequence Bit sequence
     */
    public Byte(final BitSequence sequence) {
        this(() -> {
            Bit[] bits = new Bit[8];
            if (sequence.length() > 8) {
                try {
                    bits = sequence.subSequence(0, 8).asBits();
                } catch (Exception exception) {
                    System.out.println("Something went wrong");
                }
            } else {
                for (int i = 0; i < 8; i++) {
                    if (i < 8 - sequence.length()) {
                        bits[i] = new Bit();
                        continue;
                    }
                    bits[i] = sequence.bitAt(i);
                }
            }
            return bits;
        });
    }

    /**
     * Ctor
     */
    public Byte() {
        this(new Bit[8]);
    }

    /**
     * Ctor
     *
     * @param source List of Bits
     */
    public Byte(final List<Bit> source) {
        this(source.toArray(new Bit[0]));
    }

    /**
     * Ctor
     *
     * @param source Bits
     */
    public Byte(final Bit[] source) {
        this(new BitSequence(source));
    }

    /**
     * Ctor
     *
     * @param source Bits
     */
    public Byte(final Bits source) {
        this.origin = source;
    }

    /**
     * Present as decimal
     *
     * @return decimal presentation
     */
    public int asDecimal() {
        int dec = 0;
        for (int i = 7; i >= 0; i--) {
            dec += this.bitAt(i).value() * Math.pow(2, 7 - i);
        }
        return dec;
    }

    /**
     * Present as char
     *
     * @return Char
     */
    public char asChar() {
        return (char) asDecimal();
    }

    /**
     * Get bit at {@code index}
     *
     * @param index Index
     * @return {@link Bit} at {@code index}
     */
    public Bit bitAt(int index) {
        return this.asBits()[index];
    }

    @Override
    public Bit[] asBits() {
        return origin.asBits();
    }

    @Override
    public Byte xor(Byte other) {
        Bit[] xored = new Bit[8];
        for (int i = 0; i < 8; i++) {
            xored[i] = this.bitAt(i).xor(other.bitAt(i));
        }
        return new Byte(xored);
    }

    @Override
    public String asString() {
        Bit[] bits = this.origin.asBits();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(bits[i].asString());
        }
        return sb.toString();
    }
}
