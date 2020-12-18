package main.java.org.bibits;

import java.math.BigInteger;
import java.util.*;

/**
 * Bit Sequence
 */
public class BitSequence implements Bits, Xor<BitSequence>, AsString {

    /**
     * Bits
     */
    private final Bits origin;

    /**
     * Length
     */
    private final int length;

    /**
     * Ctor
     */
    public BitSequence() {
        this(new Bit[0]);
    }

    /**
     * Ctor
     *
     * @param bits Bits Array
     */
    public BitSequence(final Bit... bits) {
        this(() -> bits, bits.length);
    }

    /**
     * Ctor
     *
     * @param bytes Bytes array
     */
    public BitSequence(final Byte... bytes) {
        this(() -> {
            Bit[] bits = new Bit[bytes.length * 8];
            int index = 0;
            for (Byte _byte : bytes) {
                for (Bit bit : _byte.asBits()) {
                    bits[index++] = bit;
                }
            }
            return bits;
        }, bytes.length * 8);
    }

    /**
     * Ctor
     *
     * @param bits ArrayList of Bits
     */
    public BitSequence(final ArrayList<Bit> bits) {
        this(bits.toArray(new Bit[0]));
    }

    /**
     * Ctor
     *
     * @param seq {@link String} or {@link CharSequence}
     */
    public BitSequence(final CharSequence seq) {
        this(() -> {
            Bit[] bits = new Bit[seq.length() * 8];
            int index = 0;
            for (Character ch : seq.toString().toCharArray()) {
                Bit[] chBits = new Byte(ch).asBits();
                for (int i = 0; i < 8; i++) {
                    bits[index++] = chBits[i];
                }
            }
            return bits;
        }, seq.length() * 8);
    }

    /**
     * Ctor
     *
     * @param source {@link BigInteger}
     */
    public BitSequence(final BigInteger source) {
        this(() -> {
            int length = source.bitLength() + (8 - source.bitLength() % 8);
            Bit[] bits = new Bit[length];
            BigInteger bi = source;
            BigInteger two = new BigInteger("2");
            for (int i = length - 1; i >= 0; i--) {
                if (bi.compareTo(BigInteger.ZERO) > 0) {
                    bits[i] = new Bit(bi.mod(two).compareTo(BigInteger.ZERO) != 0);
                    bi = bi.divide(two);
                    continue;
                }
                bits[i] = new Bit(false);
            }
            return bits;
        }, source.bitLength() + (8 - source.bitLength() % 8));
    }

    /**
     * Ctor
     *
     * @param bits   Bits
     * @param length Length
     */
    private BitSequence(final Bits bits, final int length) {
        this.origin = bits;
        this.length = length;
    }

    /**
     * Get sub sequence from {@code from} to {@code to}
     *
     * @param from From index
     * @param to   To index
     * @return Subsequence
     * @throws Exception If to < from
     */
    public BitSequence subSequence(int from, int to) throws Exception {
        if (to < from) {
            throw new Exception("Can't get substring from " + from + " to " + to);
        }
        Bit[] subs = new Bit[to - from];
        Bit[] orig = this.asBits();
        int index = 0;
        for (int i = from; i < to; i++) {
            subs[index++] = orig[i];
        }
        return new BitSequence(subs);
    }

    /**
     * Get bit at {@code index} index
     *
     * @param index Index
     * @return Bit at {@code index}
     */
    public Bit bitAt(int index) {
        return this.asBits()[index];
    }

    /**
     * Get length of {@code bits}
     *
     * @return Length of {@code bits}
     */
    public int length() {
        return this.length;
    }

    /**
     * XOR
     *
     * @param other The argument to xor
     * @return XORed {@link BitSequence}
     * @throws Exception If length of arguments aren't equal
     */
    public BitSequence xor(BitSequence other) throws Exception {
        if (this.length != other.length) {
            throw new Exception("XOR requires the same lengths of sequences");
        }
        Bit[] bits = new Bit[this.length];
        Bit[] orig = this.asBits();
        Bit[] oth = other.asBits();
        for (int i = 0; i < this.length; i++) {
            bits[i] = orig[i].xor(oth[i]);
        }
        return new BitSequence(bits);
    }

    /**
     * Cyclical shit left by {@code bitCount}
     *
     * @param bitCount Bit count to shit
     * @return Shifted {@link BitSequence}
     */
    public BitSequence shiftedLeftCyclicallyBy(int bitCount) {
        BitSequence first, second, result;
        try {
            first = this.subSequence(0, bitCount);
            second = this.subSequence(bitCount, this.length);
            result = second.concatenate(first);
        } catch (Exception exception) {
            result = new BitSequence();
        }
        return result;
    }

    /**
     * Cyclical shit left by {@code bitCount}
     *
     * @param bitCount Bit count to shit
     * @return Shifted {@link BitSequence}
     */
    public BitSequence shiftedLeftBy(int bitCount) {
        BitSequence second;
        try {
            second = this.subSequence(bitCount, this.length);
        } catch (Exception exception) {
            second = new BitSequence();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bitCount; i++) {
            sb.append("0");
        }
        BitSequence nulls = new BitSequence(sb.toString());
        return second.concatenate(nulls);
    }

    /**
     * Cyclical shit right by {@code bitCount}
     *
     * @param bitCount Bit count to shit
     * @return Shifted {@link BitSequence}
     */
    public BitSequence shiftedRightCyclicallyBy(int bitCount) {
        BitSequence first, second, result;
        try {
            first = this.subSequence(0, this.length - bitCount);
            second = this.subSequence(this.length - bitCount, this.length);
            result = second.concatenate(first);
        } catch (Exception exception) {
            result = new BitSequence();
        }
        return result;
    }

    /**
     * Cyclical shit right by {@code bitCount}
     *
     * @param bitCount Bit count to shit
     * @return Shifted {@link BitSequence}
     */
    public BitSequence shiftedRightBy(int bitCount) {
        BitSequence first;
        try {
            first = this.subSequence(0, this.length - bitCount);
        } catch (Exception exception) {
            first = new BitSequence();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bitCount; i++) {
            sb.append("0");
        }
        BitSequence nulls = new BitSequence(sb.toString());
        return nulls.concatenate(first);
    }

    /**
     * Check if sequence is empty
     *
     * @return true if length of sequence is 0
     */
    public boolean isEmpty() {
        return this.length == 0;
    }

    /**
     * Concatenate 2 {@link BitSequence}
     *
     * @param other The 2nd {@link BitSequence}
     * @return The result of concatenation
     */
    public BitSequence concatenate(BitSequence other) {
        Bit[] concatenated = new Bit[this.length + other.length];
        Bit[] orig = this.asBits();
        Bit[] oth = this.asBits();
        System.arraycopy(orig, 0, concatenated, 0, orig.length);
        System.arraycopy(oth, 0, concatenated, orig.length, oth.length);
        return new BitSequence(concatenated);
    }

    /**
     * Present {@link BitSequence} as {@link BigInteger}
     *
     * @return Big integer
     */
    public BigInteger asBigInteger() {
        BigInteger result = BigInteger.ZERO;
        BigInteger two = new BigInteger("2");
        BigInteger biFromBit;
        int exp = this.length - 1;
        for (Bit bit : this.asBits()) {
            biFromBit = bit.value() == 1 ? BigInteger.ONE : BigInteger.ZERO;
            result = result.add(biFromBit.multiply(two.pow(exp--)));
        }
        return result;
    }

    /**
     * Present as char sequence ("00110001" -> "1")
     *
     * @return Char sequence string
     * @throws Exception If length of sequence (mod 8) != 0, because length of {@link Byte} must be 8
     */
    public String asCharSequence() throws Exception {
        if (this.length % 8 != 0) {
            throw new Exception("Cannot turn into char sequence");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.length / 8; i++) {
            result.append(new Byte(this.subSequence(i * 8, i * 8 + 8)).asChar());
        }
        return result.toString();
    }

    @Override
    public Bit[] asBits() {
        return this.origin.asBits();
    }

    @Override
    public String asString() {
        StringBuilder sb = new StringBuilder();
        for (Bit bit : this.asBits()) {
            sb.append(bit.asString());
        }
        return sb.toString();
    }
}
