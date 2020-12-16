package main.java.org.bits;

import java.math.BigInteger;
import java.util.*;

/**
 * Bit Sequence
 */
public class BitSequence implements Bits {

    /**
     * Bits
     */
    private final Bits _bits;

    /**
     * Ctor
     */
    public BitSequence() {
        this(ArrayList::new);
    }

    /**
     * Ctor
     *
     * @param bits Bits Array
     */
    public BitSequence(final Bit... bits) {
        this(new ArrayList<>(Arrays.asList(bits)));
    }

    /**
     * Ctor
     *
     * @param bytes Bytes array
     */
    public BitSequence(final Byte... bytes) {
        this(() -> {
            ArrayList<Bit> bits = new ArrayList<>();
            for (Byte _byte : bytes) {
                bits.addAll(_byte.asBitList());
            }
            return bits;
        });
    }

    /**
     * Ctor
     *
     * @param bits ArrayList of Bits
     */
    public BitSequence(final ArrayList<Bit> bits) {
        this(() -> bits);
    }

    /**
     * Ctor
     *
     * @param seq {@link String} or {@link CharSequence}
     */
    public BitSequence(final CharSequence seq) {
        this(() -> {
            ArrayList<Bit> bits = new ArrayList<>();
            for (Character ch : seq.toString().toCharArray()) {
                Byte _byte = new Byte(ch);
                bits.addAll(_byte.asBitList());
            }
            System.out.println("ctor");
            return bits;
        });
    }

    /**
     * Ctor
     *
     * @param bits {@link Bits}
     */
    public BitSequence(final Bits bits) {
        this._bits = bits;
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
        ArrayList<Bit> subs = new ArrayList<>();
        for (int i = from; i < to; i++) {
            subs.add(this.bitAt(i));
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
        return this.asBitList().get(index);
    }

    /**
     * Get length of {@code bits}
     *
     * @return Length of {@code bits}
     */
    public int length() {
        return this.asBitList().size();
    }

    /**
     * XOR
     *
     * @param other The argument to xor
     * @return XORed {@link BitSequence}
     * @throws Exception If length of arguments aren't equal
     */
    public BitSequence xor(BitSequence other) throws Exception {
        if (this.length() != other.length()) {
            throw new Exception("XOR requires the same lengths of sequences");
        }
        ArrayList<Bit> bits = new ArrayList<>();
        for (int i = 0; i < this.length(); i++) {
            bits.add(this.bitAt(i).xor(other.bitAt(i)));
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
            second = this.subSequence(bitCount, this.length());
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
            second = this.subSequence(bitCount, this.length());
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
            first = this.subSequence(0, this.length() - bitCount);
            second = this.subSequence(this.length() - bitCount, this.length());
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
            first = this.subSequence(0, this.length() - bitCount);
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
        return this.length() == 0;
    }

    /**
     * Concatenate 2 {@link BitSequence}
     *
     * @param other The 2nd {@link BitSequence}
     * @return The result of concatenation
     */
    public BitSequence concatenate(BitSequence other) {
        BitSequence concatenated = new BitSequence(this.asBitList());
        concatenated.asBitList().addAll(other.asBitList());
        return concatenated;
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
        int exp = this.length() - 1;
        for (Bit bit : this.asBitList()) {
            biFromBit = bit.intValue() == 1 ? BigInteger.ONE : BigInteger.ZERO;
            result = result.add(biFromBit.multiply(two.pow(exp--)));
        }
        return result;
    }

    /**
     * Present as string of bit sequence (like "101010100100...)
     *
     * @return Bit sequence string
     */
    public String asBitSeqString() {
        StringBuilder sb = new StringBuilder();
        for (Bit bit : this.asBitList()) {
            sb.append(bit.asString());
        }
        return sb.toString();
    }

    /**
     * Present as char sequence ("00110001" -> "1")
     *
     * @return Char sequence string
     * @throws Exception If length of sequence (mod 8) != 0, because length of {@link Byte} must be 8
     */
    public String asCharSequence() throws Exception {
        if (this.length() % 8 != 0) {
            throw new Exception("Cannot turn into char seq");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.length() / 8; i++) {
            result.append(new Byte(subSequence(i * 8, i * 8 + 8)).asChar());
        }
        return result.toString();
    }

    @Override
    public ArrayList<Bit> asBitList() {
        return this._bits.asBitList();
    }
}
