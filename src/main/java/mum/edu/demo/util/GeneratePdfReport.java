package mum.edu.demo.util;

import com.itextpdf.text.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import mum.edu.demo.demain.UserOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class GeneratePdfReport {

    private static final Logger logger = LoggerFactory.getLogger(GeneratePdfReport.class);

    public static ByteArrayInputStream citiesReport(UserOrder order) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(90);
            table.setWidths(new int[]{1, 1, 1});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Product Name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Price", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("status", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);

            PdfPCell cell;

            cell = new PdfPCell(new Phrase(order.getProduct().getName().toString()));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("$" + order.getProduct().getPrice()));
            cell.setPaddingLeft(5);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(order.getStatus()));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPaddingRight(5);
            table.addCell(cell);

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            document.add( Chunk.NEWLINE );
            document.add( Chunk.NEWLINE );
            Paragraph user = new Paragraph("User", font);
            document.add(user);
            Paragraph name = new Paragraph("Name:" + order.getUser().getFirstName().toString(), font);
            document.add(name);
            Paragraph email = new Paragraph("Email:" + order.getUser().getEmail().toString(), font);
            document.add(email);
            document.add( Chunk.NEWLINE );
            Paragraph address = new Paragraph("Address", font);
            document.add(address);
            Paragraph street = new Paragraph("Street: " + order.getAddress().getStreet().toString(), font);
            document.add(street);
            Paragraph state = new Paragraph("State: " + order.getAddress().getState().toString(), font);
            document.add(state);
            Paragraph zipcode = new Paragraph("ZipCode: " + order.getAddress().getZipCode().toString(), font);
            document.add(zipcode);


            document.close();

        } catch (DocumentException ex) {

            logger.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
