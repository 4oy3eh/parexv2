//package com.example.demo;
//
//
//import com.itextpdf.forms.PdfAcroForm;
//import com.itextpdf.forms.fields.PdfFormField;
//import com.itextpdf.io.source.ByteArrayOutputStream;
//import com.itextpdf.io.source.RandomAccessSourceFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfReader;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.kernel.pdf.ReaderProperties;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//
//public class PDFcreator {
//
//
//    public static void main(String[] args) throws Exception {
//
//
//        SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss-dd-MM-yyyy");
//        Date date = new Date(System.currentTimeMillis());
//        String dateString = formatter.format(date);
//        String DEST = "./src/main/resources/pdfs/fill_form_field_order " + dateString + ".pdf";
//        String SRC = "./src/main/resources/pdfs/parex_blank.pdf";
//
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//
//        byte[] tempForm = new ByteArrayOutputStream().toByteArray();
//
//
//        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new RandomAccessSourceFactory().createSource(tempForm),
//                new ReaderProperties()), new PdfWriter(DEST));
//        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
//
//        Map<String, PdfFormField> fields = form.getFormFields();
//        fields.get("fill_1").setValue("text");//customer id
//        fields.get("fill_2").setValue("text");//date
//        fields.get("fill_3").setValue("text");//customer name
//        fields.get("fill_4").setValue("text");//customer doc
//        fields.get("fill_5").setValue("text");//custmer account
//        fields.get("fill_6").setValue("text");//rec name
//        fields.get("fill_7").setValue("text");//rec doc
//        fields.get("fill_8").setValue("text");//rec account
//        fields.get("fill_9").setValue("text");//rec country
//        fields.get("fill_10").setValue("text");//rec bank
//        fields.get("fill_11").setValue("text");//rec bank id
//        fields.get("fill_12").setValue("text");//deal sum
//        fields.get("fill_13").setValue("text");//deal commission
//        fields.get("fill_14").setValue("text");//deal ammount words
//        fields.get("fill_15").setValue("text");//deal payment type
//        fields.get("fill_16").setValue("text");//deal exchange rate
//        fields.get("fill_17").setValue("text");//deal value date
//        fields.get("fill_18").setValue("text");//deal code of external payment
//        // All fields will be flattened, because no fields have been explicitly included
//        form.flattenFields();
//
//        pdfDoc.close();
//
//
//    }
//}
//
//
//
//
