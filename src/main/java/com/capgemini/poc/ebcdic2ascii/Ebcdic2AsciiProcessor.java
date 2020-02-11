package com.capgemini.poc.ebcdic2ascii;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.item.ItemProcessor;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class Ebcdic2AsciiProcessor implements ItemProcessor<LineContent, LineContent> {

    private CharsetDecoder decoder;
    private CharsetEncoder encoder;

    public Ebcdic2AsciiProcessor( String sourceFormat, String targetFormat) {
        Charset charset_in = Charset.forName(targetFormat);
        Charset charset_out = Charset.forName(sourceFormat);
        this.decoder = charset_out.newDecoder();
        this.encoder = charset_in.newEncoder();
    }


    @Override
    public LineContent process(LineContent lineContent) throws Exception {

        CharBuffer uCharBuffer = CharBuffer.wrap(lineContent.getContent());
        ByteBuffer bbuf = encoder.encode(uCharBuffer);
        CharBuffer cbuf = decoder.decode(bbuf);
        lineContent.setContent(cbuf.toString());
        return lineContent;

    }
}
