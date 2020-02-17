package com.capgemini.poc.ebcdic2ascii.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

@Component
public class GenericFormatTransformer implements ItemProcessor<String, String> {

    private CharsetDecoder decoder;
    private CharsetEncoder encoder;


    public GenericFormatTransformer( @Value("${transformed.file.format}") String targetFormat, @Value("${origin.file.format}") String sourceFormat) {
        Charset charset_in = Charset.forName(targetFormat);
        Charset charset_out = Charset.forName(sourceFormat);
        this.decoder = charset_out.newDecoder();
        this.encoder = charset_in.newEncoder();
    }


    @Override
    public String process(String lineContent) throws Exception {

        CharBuffer uCharBuffer = CharBuffer.wrap(lineContent);
        ByteBuffer bbuf = encoder.encode(uCharBuffer);
        CharBuffer cbuf = decoder.decode(bbuf);
        return cbuf.toString();

    }
}
