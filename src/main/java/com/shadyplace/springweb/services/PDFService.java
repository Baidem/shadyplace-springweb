package com.shadyplace.springweb.services;

import com.lowagie.text.DocumentException;
import com.shadyplace.springweb.models.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


@Service
public class PDFService {
    public String output =
            "src/main/resources/static/pdf/spring_pdf.pdf";

    @Autowired
    TemplateEngine templateEngine;

    public String thymeleafToString(Command command){
        Context context = new Context();
        context.setVariable("command", command);

        return templateEngine.process("pdf/bill", context);
    }

    public void generatePdf(Command command) throws IOException, DocumentException {
        String html = this.thymeleafToString(command);

        String outputFolder = output;

        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
    }

}
