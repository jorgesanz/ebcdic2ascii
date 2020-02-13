package com.capgemini.poc.ebcdic2ascii.processor;

import com.capgemini.poc.ebcdic2ascii.dto.LineContent;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

@Component
public class GenericFormatTransformer implements ItemProcessor<LineContent, LineContent> {


//    @Value("${target.format}")
//    private String targetFormat;
//
//    @Value("${source.format}")
//    private String sourceFormat;


    private CharsetDecoder decoder;
    private CharsetEncoder encoder;


    public GenericFormatTransformer( @Value("${target.format}") String targetFormat, @Value("${source.format}") String sourceFormat) {
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
