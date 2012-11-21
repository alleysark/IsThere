/*
 * This file is part of the LIRe project: http://www.semanticmetadata.net/lire
 * LIRe is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * LIRe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LIRe; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * We kindly ask you to refer the following paper in any publication mentioning Lire:
 *
 * Lux Mathias, Savvas A. Chatzichristofis. Lire: Lucene Image Retrieval –
 * An Extensible Java CBIR Library. In proceedings of the 16th ACM International
 * Conference on Multimedia, pp. 1085-1088, Vancouver, Canada, 2008
 *
 * http://doi.acm.org/10.1145/1459359.1459577
 *
 * Copyright statement:
 * ~~~~~~~~~~~~~~~~~~~~
 * (c) 2002-2011 by Mathias Lux (mathias@juggle.at)
 *     http://www.semanticmetadata.net/lire
 */
package net.semanticmetadata.lire.impl;

import net.semanticmetadata.lire.AbstractDocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.CEDD;
import net.semanticmetadata.lire.utils.ImageUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;

/**
 * Provides a faster way of searching based on byte arrays instead of Strings. The method
 * {@link net.semanticmetadata.lire.imageanalysis.CEDD#getByteArrayRepresentation()} is used
 * to generate the signature of the descriptor much faster.
 * User: Mathias Lux, mathias@juggle.at
 * Date: 12.03.2010
 * Time: 13:21:35
 *
 * @see GenericFastDocumentBuilder
 * @deprecated use GenericFastDocumentBuilder instead.
 */
public class CEDDDocumentBuilder extends AbstractDocumentBuilder {
    private Logger logger = Logger.getLogger(getClass().getName());
    public static final int MAX_IMAGE_DIMENSION = 1024;

    public Document createDocument(BufferedImage image, String identifier) {
        assert (image != null);
        BufferedImage bimg = image;
        // Scaling image is especially with the correlogram features very important!
        // All images are scaled to guarantee a certain upper limit for indexing.
        if (Math.max(image.getHeight(), image.getWidth()) > MAX_IMAGE_DIMENSION) {
            bimg = ImageUtils.scaleImage(image, MAX_IMAGE_DIMENSION);
        }
        Document doc = null;
        logger.finer("Starting extraction from image [CEDD - fast].");
        CEDD vd = new CEDD();
        vd.extract(bimg);
        logger.fine("Extraction finished [CEDD - fast].");

        doc = new Document();
        doc.add(new Field(FIELD_NAME_CEDD, vd.getByteArrayRepresentation()));
        if (identifier != null)
            doc.add(new Field(FIELD_NAME_IDENTIFIER, identifier, Field.Store.YES, Field.Index.NOT_ANALYZED));

        return doc;
    }
}
