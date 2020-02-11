package com.capgemini.poc.ebcdic2ascii;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PGPTransformer {

    @Transformer(inputChannel = "fileInputChannel", outputChannel = "fileToJobProcessor")
    public File transform(File aFile) throws Exception {

        return aFile;
    }
}
