package main.java.org.bibits;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BitSequenceTest {

    @Test
    public void asBitSeqStringTest() {
        BitSequence sequence = new BitSequence("1");
        assertThat(sequence.asString(), is("00110001"));
    }

    @Test
    public void lengthTest() {
        BitSequence sequence = new BitSequence("12");
        assertThat(sequence.length(), is(16));
    }

    @Test
    public void asCharSeqTest() throws Exception {
        BitSequence sequence = new BitSequence("34");
        assertThat(sequence.asCharSequence(), is("34"));
    }

    @Test
    public void xorTest() throws Exception {
        BitSequence sequence = new BitSequence("1");
        BitSequence sequence1 = new BitSequence("2");
        assertThat(sequence.xor(sequence1).asString(), is("00000011"));
    }

    @Test
    public void concatenateTest() {
        BitSequence sequence1 = new BitSequence("1");
        BitSequence sequence2 = new BitSequence("2");
        BitSequence concatenated = sequence1.concatenate(sequence2);
        assertThat(concatenated.asString(), is("0011000100110010"));
    }

    @Test
    public void shiftLeftTest() {
        BitSequence sequence = new BitSequence("1");
        assertThat(sequence.shiftedLeftCyclicallyBy(3).asString(), is("10001001"));
    }

    @Test
    public void shiftRightTest() {
        BitSequence sequence = new BitSequence("1");
        assertThat(sequence.shiftedRightCyclicallyBy(4).asString(), is("00010011"));
    }


}
