package com.pack.billingsystem.controllers;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.pack.billingsystem.models.Appointment;
import com.pack.billingsystem.models.Medicament;
import com.pack.billingsystem.models.OrdonnanceTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PdfController {
    public void generatePdf(String filePath, int patientID, String nom, String prixTotal, String prixAssurance) throws IOException, SQLException {
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        document.setMargins(30, 30, 30, 30);

        // Ajouter le logo
        String logoPath = getClass().getResource("/com/pack/billingsystem/images/logo2.png").getPath();
        ImageData imageData = ImageDataFactory.create(logoPath);
        Image logo = new Image(imageData).setWidth(120).setHeight(40);
        logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(logo);

        // Ajouter le titre principal
        Paragraph title = new Paragraph("Facture Patient")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18)
                .setBold()
                .setFontColor(ColorConstants.BLACK);
        document.add(title);

        // Ajouter les informations du patient
        document.add(new Paragraph("Nom du Patient : " + nom)
                .setFontSize(12)
                .setMarginBottom(10));

        document.add(new Paragraph("Date : " + java.time.LocalDate.now().toString())
                .setFontSize(12)
                .setMarginBottom(20));

        // Section : Appointments
        document.add(new Paragraph("Appointements")
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLACK)
                .setMarginBottom(10));

        Table infoTable = new Table(new float[]{3, 3, 2, 2});
        infoTable.setWidth(UnitValue.createPercentValue(100));

        infoTable.addHeaderCell(createStyledCell("Docteur", ColorConstants.WHITE, ColorConstants.GRAY));
        infoTable.addHeaderCell(createStyledCell("Service", ColorConstants.WHITE, ColorConstants.GRAY));
        infoTable.addHeaderCell(createStyledCell("Prix", ColorConstants.WHITE, ColorConstants.GRAY));
        infoTable.addHeaderCell(createStyledCell("Date", ColorConstants.WHITE, ColorConstants.GRAY));

        List<Appointment> appointments = AppointmentController.getAllAppointments(patientID);
        for (Appointment appointment : appointments) {
            infoTable.addCell(createStyledCell(appointment.getDoctor().getNom() + " " + appointment.getDoctor().getPrenom(), ColorConstants.BLACK, null));
            infoTable.addCell(createStyledCell(appointment.getService(), ColorConstants.BLACK, null));
            infoTable.addCell(createStyledCell(String.format("%.2f DH", appointment.getPrice()), ColorConstants.BLACK, null));
            infoTable.addCell(createStyledCell(appointment.getAppointmentDate().toString(), ColorConstants.BLACK, null));
        }

        document.add(infoTable);

        // Section : Tests de laboratoire
        document.add(new Paragraph("\nTests de Laboratoire")
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLACK)
                .setMarginBottom(10));

        Table labTable = new Table(new float[]{3, 2, 2, 3});
        labTable.setWidth(UnitValue.createPercentValue(100));
        labTable.addHeaderCell(createStyledCell("Nom du Test", ColorConstants.WHITE, ColorConstants.GRAY));
        labTable.addHeaderCell(createStyledCell("Date", ColorConstants.WHITE, ColorConstants.GRAY));
        labTable.addHeaderCell(createStyledCell("Prix", ColorConstants.WHITE, ColorConstants.GRAY));
        labTable.addHeaderCell(createStyledCell("Résultat", ColorConstants.WHITE, ColorConstants.GRAY));

        List<OrdonnanceTest> tests = TestController.getNonPayeTestsByPatient(patientID);
        for (OrdonnanceTest test : tests) {
            labTable.addCell(createStyledCell(test.getTestNom(), ColorConstants.BLACK, null));
            labTable.addCell(createStyledCell(test.getDate().toString(), ColorConstants.BLACK, null));
            labTable.addCell(createStyledCell(String.format("%.2f DH", test.getPrix()), ColorConstants.BLACK, null));
            labTable.addCell(createStyledCell(String.format("%d", test.getResultat()), ColorConstants.BLACK, null));
        }

        document.add(labTable);

        // Section : Médicaments
        document.add(new Paragraph("\nMédicaments")
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLACK)
                .setMarginBottom(10));

        Table medsTable = new Table(new float[]{3, 2});
        medsTable.setWidth(UnitValue.createPercentValue(100));
        medsTable.addHeaderCell(createStyledCell("Médicament", ColorConstants.WHITE, ColorConstants.GRAY));
        medsTable.addHeaderCell(createStyledCell("Prix", ColorConstants.WHITE, ColorConstants.GRAY));

        List<Medicament> meds = MedicamentController.getMedicamentsByPatient(patientID);
        for (Medicament med : meds) {
            medsTable.addCell(createStyledCell(med.getNom(), ColorConstants.BLACK, null));
            medsTable.addCell(createStyledCell(String.format("%.2f DH", med.getPrix()), ColorConstants.BLACK, null));
        }

        document.add(medsTable);

        // Section : Total
        document.add(new Paragraph("\nPaiement")
                .setFontSize(14)
                .setBold()
                .setFontColor(ColorConstants.BLACK)
                .setMarginBottom(10));

        Table summaryTable = new Table(new float[]{4, 2});
        summaryTable.setWidth(UnitValue.createPercentValue(100));
        summaryTable.addCell(createStyledCell("Prix Total", ColorConstants.BLACK, ColorConstants.LIGHT_GRAY));
        summaryTable.addCell(createStyledCell(prixTotal + " ", ColorConstants.BLACK, ColorConstants.LIGHT_GRAY));
        summaryTable.addCell(createStyledCell("Prix Réduit (Assurance)", ColorConstants.BLACK, ColorConstants.LIGHT_GRAY));
        summaryTable.addCell(createStyledCell(prixAssurance +" ", ColorConstants.BLACK, ColorConstants.LIGHT_GRAY));

        document.add(summaryTable);

        document.close();
        System.out.println("PDF généré avec succès : " + filePath);
    }

    // Méthode utilitaire pour créer des cellules avec couleur de fond
    private static com.itextpdf.layout.element.Cell createStyledCell(String content, Color fontColor, Color backgroundColor) {
        com.itextpdf.layout.element.Cell cell = new com.itextpdf.layout.element.Cell()
                .add(new Paragraph(content).setFontSize(10).setFontColor(fontColor));
        if (backgroundColor != null) {
            cell.setBackgroundColor(backgroundColor);
        }
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        return cell;
    }

}

