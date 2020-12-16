package main.java.org.bibits;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Byte
 */
public class Byte implements Bits {

    /**
     * Bits
     */
    private final Bits _bits;

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
                    bits[i] = new Bit(false);
                }
                return new ArrayList<>(Arrays.asList(bits));
            }
        );
    }

    /**
     * Ctor
     *
     * @param sequence Bit sequence
     */
    public Byte(final BitSequence sequence) {
        this(() -> {
            if (sequence.length() >= 8) {
                try {
                    return sequence.subSequence(0, 8).asBitList();
                } catch (Exception exception) {
                    return sequence.asBitList();
                }
            } else {
                ArrayList<Bit> bits = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    if (i < 8 - sequence.length()) {
                        bits.add(new Bit(false));
                        continue;
                    }
                    bits.add(sequence.bitAt(i));
                }
                return bits;
            }
        });
    }

    /**
     * Ctor
     */
    public Byte() {
        this(new ArrayList<>(8));
    }

    /**
     * Ctor
     *
     * @param bits ArrayList of Bits
     */
    public Byte(final ArrayList<Bit> bits) {
        this(() -> bits);
    }

    /**
     * Ctor
     *
     * @param bits Bits
     */
    public Byte(final Bits bits) {
        this._bits = bits;
    }

    /**
     * Present as bit sequence
     *
     * @return Bit sequence string
     */
    public String asBitSeqString() {
        return this.asBitSequence().asBitSeqString();
    }

    /**
     * Present as decimal
     *
     * @return decimal presentation
     */
    public int asDecimal() {
        int dec = 0;
        for (int i = 7; i >= 0; i--) {
            dec += this.bitAt(i).intValue() * Math.pow(2, 7 - i);
        }
        return dec;
    }

    /**
     * Present as char
     * @return Char
     */
    public char asChar() {
        return (char) asDecimal();
    }

    /**
     * Present as bit sequence
     * @return {@link BitSequence}
     */
    public BitSequence asBitSequence() {
        return new BitSequence(_bits);
    }

    public Bit bitAt(int index) {
        return this.asBitList().get(index);
    }

    /**
     * XOR
     *
     * @param other 2nd argument
     * @return result of xor as Binary
     */
    public Byte xor(Byte other) {
        return new Byte(() -> {
            ArrayList<Bit> result = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                result.add(this.bitAt(i).xor(other.bitAt(i)));
            }
            return result;
        });
    }

    @Override
    public ArrayList<Bit> asBitList() {
        return _bits.asBitList();
    }
}
