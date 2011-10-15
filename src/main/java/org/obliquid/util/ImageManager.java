package org.obliquid.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;

/**
 * Class to manage images in JPG format.
 * 
 * @author stivlo
 * 
 */
public class ImageManager {

        /** JPEG quality. */
        private static final float JPEG_QUALITY = 0.90f;

        /**
         * Scale and save an image to disk.
         * 
         * @param sourceFile
         *                original image
         * @param destFile
         *                scaled image to be saved
         * @param width
         *                suggested width
         * @param height
         *                suggested height
         * @throws IOException
         *                 in case of IO problems
         */
        public final void scaleAndSaveImage(final String sourceFile, final String destFile,
                        final int width, final int height) throws IOException {
                BufferedImage sourceImage = ImageIO.read(new File(sourceFile));
                if (sourceImage == null) {
                        throw new IOException("sourceImage='" + sourceFile + "' corrupted or not found");
                }
                ResampleOp resampleOp = new ResampleOp(width, height);
                resampleOp.setFilter(ResampleFilters.getLanczos3Filter());
                resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
                BufferedImage destImage = resampleOp.filter(sourceImage, null);
                writeJpeg(destImage, destFile, JPEG_QUALITY);
        }

        /**
         * Write a JPEG file setting the compression quality.
         * 
         * @param image
         *                a BufferedImage to be saved
         * @param destFile
         *                destination file (absolute or relative path)
         * @param quality
         *                a float between 0 and 1, where 1 means uncompressed.
         * @throws IOException
         *                 in case of problems writing the file
         */
        public final void writeJpeg(final BufferedImage image, final String destFile, final float quality)
                        throws IOException {
                ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
                ImageWriteParam param = writer.getDefaultWriteParam();
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality);
                FileImageOutputStream output = new FileImageOutputStream(new File(destFile));
                writer.setOutput(output);
                IIOImage iioImage = new IIOImage(image, null, null);
                writer.write(null, iioImage, param);
                writer.dispose();
        }

}
