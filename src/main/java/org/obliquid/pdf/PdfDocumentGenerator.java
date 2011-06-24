package org.obliquid.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.obliquid.config.AppConfig;
import org.obliquid.client.ClientFactory;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Base class for Pdf Document Generators (wrapper for iText)
 * 
 * @author stivlo
 * 
 */
public abstract class PdfDocumentGenerator {

    private Document document;
    private ByteArrayOutputStream outStream;
    private PdfContentByte layerOver;
    private String basePathWithSlash;

    public PdfDocumentGenerator() {
        document = new Document();
    }

    /**
     * Open the PDF Invoice file for writing on the disk
     */
    public void open(final String basePath) throws DocumentException {
        PdfWriter writer;
        basePathWithSlash = basePath + "/";
        outStream = new ByteArrayOutputStream();
        writer = PdfWriter.getInstance(document, outStream);
        writer.setPdfVersion(PdfWriter.VERSION_1_7);
        document.open();
        layerOver = writer.getDirectContent();
    }

    /**
     * Close the PDF Invoice file and uploads it to S3
     * 
     * @throws IOException
     */
    public void close() {
        document.close();
    }

    /**
     * Upload the generated SalesDocument from byte array to S3
     * 
     * @param fileName
     * @throws IOException
     */
    public void uploadToS3(String fileName) throws IOException {
        byte[] contents = outStream.toByteArray();
        AmazonS3 client = ClientFactory.createSimpleStorageServiceClient();
        String bucket = AppConfig.getInstance().getProperty("bucket");
        InputStream stream = new ByteArrayInputStream(contents);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(contents.length);
        meta.setContentType("application/pdf");
        client.putObject(bucket, fileName, stream, meta);
        client.setObjectAcl(bucket, fileName, CannedAccessControlList.PublicRead);
    }

    /**
     * Force to render the next elements in a new page. The new page will get created only if we add
     * elements to it.
     */
    public void newPage() {
        document.newPage();
    }

    /**
     * Add a Chunk of text at absolute position. The position 0, 0 is in the bottom left corner of
     * the page
     * 
     * @param text
     *            the text to add
     * @param x
     *            x position
     * @param y
     *            y position
     * @param font
     *            the Font to be used
     * @throws DocumentException
     * @throws IOException
     */
    protected void placeChunck(String text, int x, int y, Font font) throws DocumentException {
        layerOver.saveState();
        layerOver.beginText();
        layerOver.moveText(x, y);
        layerOver.setFontAndSize(font.getBaseFont(), font.getSize());
        layerOver.showText(text);
        layerOver.endText();
        layerOver.restoreState();
    }

    /**
     * Add an Element to the document
     * 
     * @param el
     *            the element to be added
     * @throws DocumentException
     */
    public void add(Element el) throws DocumentException {
        document.add(el);
    }

    public String getBasePath() {
        return basePathWithSlash;
    }

}
