package com.capgemini.poc.ebcdic2ascii.processor;

import com.capgemini.poc.ebcdic2ascii.dto.LineContent;
import org.springframework.batch.item.ItemProcessor;

public class Ebcdic2AsciiTransformer implements ItemProcessor<LineContent, LineContent> {

    // Translate Ebcdic char to ASCII
    final static char[] E2A_table = new char[] {
            //        0    1    2    3    4    5    6    7    8    9    A    B    C    D    E    F
            /* 0 */ '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            /* 1 */ '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            /* 2 */ '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            /* 3 */ '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            /* 4 */ ' ', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '.', '<', '(', '+', '?',
            /* 5 */ '&', '?', '?', '?', '?', '?', '?', '?', '?', '?', '!', '$', '*', ')', ';', '?',
            /* 6 */ '-', '/', '?', '?', '?', '?', '?', '?', '?', '?', '|', ',', '%', '_', '>', '?',
            /* 7 */ '?', '?', '?', '?', '?', '?', '?', '?', '?', '`', ':', '#', '@', '\'', '=', '"',
            /* 8 */ '?', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', '?', '?', '?', '?', '?', '?',
            /* 9 */ '?', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', '?', '?', '?', '?', '?', '?',
            /* A */ '?', '~', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '?', '?', '?', '?', '?', '?',
            /* B */ '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '?',
            /* C */ '{', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', '?', '?', '?', '?', '?', '?',
            /* D */ '}', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', '?', '?', '?', '?', '?', '?',
            /* E */ '\\', '?', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '?', '?', '?', '?', '?', '?',
            /* F */ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '?', '?', '?', '?', '?', '?'};

    static {    // test valid table size
        if(E2A_table.length != 256)
            throw new Error("Invalid E2A_Table lengtgh=" + E2A_table.length);
    }

    final static int StripSign = 0xFF;  // to strip off leading sign bits


    @Override
    public LineContent process(LineContent lineContent) throws Exception {

        lineContent.setContent(toASCII(lineContent.getContent().getBytes()));
        return lineContent;

    }

    // Convert EBCDIC to ASCII byte by byte
    public static String  toASCII(byte[] ebcdic) {
        StringBuffer sb = new StringBuffer(ebcdic.length);
        for(int i=0; i < ebcdic.length; i++)
            sb.append(E2A_table[ebcdic[i] & StripSign]);
        sb.append(System.lineSeparator());
        return sb.toString();
    }
}
