# bibits

Little library for working with numbers in bit representation

You can create bit sequence in many ways, it's one of them:

```BitSequence seq = new BitSequence("12")```

Now ```seq``` keep ```ArrayList<Bit>```.

There are next available options with ```seq```:

1. Present as String of Bits: ```seq.asBitSeqString()```, the result is ```"0011000100110010"```
2. Present as String of Chars: ```seq.asCharSequence()```, the result is ```"12"```
3. Present as BigInteger: ```seq.asBigInteger()```, the result is ```BigInteger``` with value ***12594***
4. Present as ArrayList of Bits: ```seq.asBitList()```, the result is ```ArrayList<Bit>``` like ```{Bit(0), Bit(0), Bit(1), ... , Bit(0)}```
5. Concatenate: ```seq.concatenate(new BitSequence("1"))```, the result is ```BitSequence``` that keep ```"001100010011001000110001"```
6. Xor: ```seq.xor(new BitSequence("11"))```, the result is ```BitSequence``` that keep ```"0000000000000011"```
7. Shift left/right by some bits: ```seq.shiftedLeftBy(2)```, the result is ```BitSequence``` that keep ```"1100010011001000"```
8. Shift left/right cyclically by some bits: ```seq.shiftedLeftCyclicallyBy(3)```, the result is ```BitSequence``` that keep ```"1000100110010001"```

So check this out. I'll be glad if you find some bugs or you tell me what needs to be improved or fixed.
