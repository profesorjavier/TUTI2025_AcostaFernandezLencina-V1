package com.tutiveinteveinteveinticinco.acostafernandezlencina.servicios;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Genera un PDF a partir de una plantilla Thymeleaf y un modelo de datos.
     *
     * @param templateName nombre de la plantilla sin extensión (ej: "familias/listado")
     * @param variables      mapa con los atributos del modelo
     * @return byte[] con el contenido del PDF
     * @throws Exception si hay problemas al procesar la plantilla o generar el PDF
     */
    public byte[] generarPdfDesdePlantilla(String nombrePlantilla, Map<String, Object> variables) throws Exception {
        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process(nombrePlantilla, context);

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(pdfOutputStream);
        pdfOutputStream.close();

        return pdfOutputStream.toByteArray();
    }
}