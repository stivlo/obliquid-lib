package org.obliquid.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.io.IOException;

import org.junit.Test;

/**
 * Class under test MyImage.
 * 
 * @author stivlo
 * 
 */
public class MyImageShould {

        /** Where to find the test image files. */
        private static final String RESOURCE_DIR = "src/test/resources/";

        /** First test image. */
        private static final String[] IMAGE = { RESOURCE_DIR + "image01.jpg",
                        RESOURCE_DIR + "image02.jpg", RESOURCE_DIR + "image03.jpg" };

        /** First test thumbnail. */
        private static final String[] IMAGE_THUMB = { RESOURCE_DIR + "image01s.jpg",
                        RESOURCE_DIR + "image02s.jpg", RESOURCE_DIR + "image03s.jpg",
                        RESOURCE_DIR + "image04s.jpg" };

        /**
         * Check the dimensions of Image01.
         * 
         * @throws IOException
         *                 in case of problems reading the image
         */
        @Test
        public final void checkDimensionsOfImage01() throws IOException {
                final int expectedWidth = 500;
                final int expectedHeight = 375;
                MyImage image = new MyImage();
                image.read(IMAGE[0]);
                assertEquals(expectedWidth, (int) image.getSourceDimension().getWidth());
                assertEquals(expectedHeight, (int) image.getSourceDimension().getHeight());
        }

        /**
         * Scale Image 0 by width.
         * 
         * @throws IOException
         *                 in case of problems reading or saving
         */
        @Test
        public final void scaleAndSaveByWidthImage01() throws IOException {
                final int newWidth = 109;
                final int newHeight = 82; // 375 * 109 / 500
                MyImage image = new MyImage();
                image.read(IMAGE[0]); //500x375
                image.scaleByWidth(newWidth);
                image.writeJpeg(IMAGE_THUMB[0]);
                image.read(IMAGE_THUMB[0]);
                assertEquals(newWidth, (int) image.getDestinationDimension().getWidth());
                assertEquals(newHeight, (int) image.getDestinationDimension().getHeight());
        }

        /**
         * Scale Image 1 by height.
         * 
         * @throws IOException
         *                 in case of problems reading or saving
         */
        @Test
        public final void scaleAndSaveByHeightImage02() throws IOException {
                final int newHeight = 276;
                final int newWidth = 442; // 1280 * 276 / 800
                MyImage image = new MyImage();
                image.read(IMAGE[1]); //1280x800
                image.scaleByHeight(newHeight);
                image.writeJpeg(IMAGE_THUMB[1]);
                image.read(IMAGE_THUMB[1]);
                assertEquals(newWidth, (int) image.getDestinationDimension().getWidth());
                assertEquals(newHeight, (int) image.getDestinationDimension().getHeight());
        }

        /**
         * Scale with bounding box a vertical image.
         * 
         * @throws Exception
         *                 in case of problems reading or saving
         */
        @Test
        public final void scaleAndSaveBoundingBoxVertical() throws Exception {
                final Dimension newDimension = new Dimension(120, 80);
                MyImage image = new MyImage();
                image.read(IMAGE[2]);
                image.scaleBoundingBox(newDimension);
                image.writeJpeg(IMAGE_THUMB[2]);
                assertEquals(newDimension.height, image.getDestinationDimension().height);
                assertTrue(newDimension.width > image.getDestinationDimension().width);
        }

        /**
         * Scale with bounding box a vertical image in an even more vertical
         * bounding box.
         * 
         * @throws Exception
         *                 in case of problems reading or saving
         */
        @Test
        public final void scaleAndSaveBoundingBoxHorizontal() throws Exception {
                final int resultThumb = 3;
                final Dimension newDimension = new Dimension(120, 500);
                MyImage image = new MyImage();
                image.read(IMAGE[2]);
                image.scaleBoundingBox(newDimension);
                image.writeJpeg(IMAGE_THUMB[resultThumb]);
                assertEquals(newDimension.width, image.getDestinationDimension().width);
                assertTrue(newDimension.height > image.getDestinationDimension().height);
        }

        /**
         * Scale with white bands a vertical image.
         * 
         * @throws IOException
         *                 in case of problems reading or saving
         */
        @Test
        public final void scaleWhiteBandVertical() throws IOException {
                final Dimension newDimension = new Dimension(120, 80);
                MyImage image = new MyImage();
                image.read(IMAGE[2]);
                image.scaleWhiteBand(newDimension);
                image.writeJpeg(RESOURCE_DIR + "verticalband.jpg");
                assertEquals(newDimension.height, image.getDestinationDimension().height);
                assertEquals(newDimension.width, image.getDestinationDimension().width);
        }

        /**
         * Scale with white bands a horizontal image.
         * 
         * @throws Exception
         *                 in case of problems reading or saving
         */
        @Test
        public final void scaleWhiteBandHorizontal() throws Exception {
                final Dimension newDimension = new Dimension(120, 500);
                MyImage image = new MyImage();
                image.read(IMAGE[2]);
                image.scaleWhiteBand(newDimension);
                image.writeJpeg(RESOURCE_DIR + "horizontalband.jpg");
                assertEquals(newDimension.height, image.getDestinationDimension().height);
                assertEquals(newDimension.width, image.getDestinationDimension().width);
        }

}
