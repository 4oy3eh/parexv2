//package com.itextpdf.samples.sandbox.acroforms;
//
//import com.itextpdf.forms.PdfAcroForm;
//import com.itextpdf.forms.fields.PdfFormField;
//import com.itextpdf.io.source.ByteArrayOutputStream;
//import com.itextpdf.io.source.RandomAccessSourceFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfReader;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.kernel.pdf.ReaderProperties;
//import com.itextpdf.layout.Document;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//
//public class FillFormFieldOrder {
//    public static final String DEST = "./src/main/resources/pdfs/fill_form_field_order.pdf";
//
//    public static final String SRC = "./src/main/resources/pdfs/parex_blank.pdf";
//
//    public static void main(String[] args) throws Exception {
//        File file = new File(DEST);
//        file.getParentFile().mkdirs();
//
//        new FillFormFieldOrder().manipulatePdf(DEST);
//    }
//
//
//
//    protected void manipulatePdf(String dest) throws Exception {
//
//        // Partially flattened form's byte array with content, which should placed beneath the other content.
//        byte[] tempForm = createPartiallyFlattenedForm();
//        createResultantPdf(dest, tempForm);
//    }
//
//    public byte[] createPartiallyFlattenedForm() throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PdfDocument pdfDoc = new PdfDocument(new PdfReader(SRC), new PdfWriter(baos));
//        Document doc = new Document(pdfDoc);
//        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, false);
//
//        Map<String, PdfFormField> fields = form.getFormFields();
//
//
//
//
//
//
//        doc.close();
//
//        return baos.toByteArray();
//    }
//
//    public void createResultantPdf(String dest, byte[] src) throws IOException {
//        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new RandomAccessSourceFactory().createSource(src),
//                new ReaderProperties()), new PdfWriter(dest));
//        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
//
//        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
//
//        Date date = new Date(System.currentTimeMillis());
//
//        //по стране проживания вид платежа -->
//
//        //вид платежа в ците/европа/мир -->
//
//        //комиссия зависит от вида платежа
//
//
//        //метод сумма текстом
//
//        //по коду банка название если можно
//
//        //по счету код банка если можно
//
//        //po api dobavljatj libo srazu delatj get metodom
//
//        //naiti metod kak posilatj po email
//
//        Map<String, PdfFormField> fields = form.getFormFields();
//        fields.get("fill_1").setValue("text");//customer id
//
//
//        pdfDoc.close();
//    }
//
//    public String notSunday(){
//        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
//        SimpleDateFormat DayofWeek= new SimpleDateFormat("u");
//        String dateNeeded;
//        Date date = new Date(System.currentTimeMillis());
//        if (DayofWeek.format(date)=="7"){
//            dateNeeded = formatter.format( System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1l));
//        }
//        else dateNeeded = formatter.format( System.currentTimeMillis());
//        return dateNeeded;
//    }
//    public int transfer(String text){
//        List searchSpaces = new ArrayList();
//
//        for(int i=0;i<text.length();i++){
//            String qwe = text.substring(i, i+1);
//            if (qwe.equals(" ")){
//                searchSpaces.add(i);
//            }
//        }
//        int textLenght = text.length();
//        if (textLenght < 50) return textLenght;
//        else if (textLenght > 100)  return getClosetNumberOfTarget(searchSpaces, textLenght/2);
//
//        else return getClosetNumberOfTarget(searchSpaces, 50);
//    }
//
//    private String stringCropper(String getString)[]{//position ne praviljno rabotaet re: ja oboltus DansGame
//        String test = "[{\"receiver_person\":\"";
//        String[] keywords = {"[{\"receiver_person\":\"","\",\"receiver_doc_id\":\"","\",\"receiver_country\":\"","\",\"receiver_account\":\"","\",\"receiver_bank_id\":\"","\",\"trade_date\":\"","\",\"trade_sum\":",",\"trade_details\":\"","\",\"id\":\"","\"}]"};
//
//        int[] difference = {21,21,22,22,22,16,14,18,8,3};//dlina musora mezdu 9 ellementami
//        int[] position = new int[10]; //na4alo musora
//
//
//
//        int i;
//
//        // pozicii na4ala
//        for(i=0;i<10;i++) {
//            position[i] = getString.indexOf(keywords[i]);
//        }
//        String[] crop = new String[9];
//        int lenght=0;
//        for (i=0;i<9;i++) {
//            crop[i] = getString.substring(lenght+difference[i], position[i + 1]);
//            lenght = position[i + 1];
//        }
//        return crop;
//    }
//
//    private static int getClosetNumberOfTarget(List dataArray, int target) {
//
//        // Edge case - Check if array length is zero
//        if (dataArray.size() == 0)
//            System.exit(1);
//        // Straight case - If target is smaller or equal to first element in array
//        if (target <= (int)dataArray.get(0))
//            return (int)dataArray.get(0);
//        // Straight case - If target is greater or equal to last element in array
//        if (target >= (int) dataArray.get(dataArray.size()-1))
//            return (int) dataArray.get(dataArray.size()-1);
//
//        // Start binary search algorithm
//        int start = 0;
//        int end = dataArray.size()-1;
//        int mid;
//
//        // Keep dividing array till further division is not possible
//        while (end - start != 1) {
//            // Calculate middle index
//            mid = (start + end) / 2;
//            // Check if middle element is equal to target
//            if (target == (int)dataArray.get(mid))
//                // If yes return middle element as middle element = target
//                return (int) dataArray.get(mid);
//            // Check if target is smaller to middle element. If yes, take first half of array including mid
//            if (target < (int) dataArray.get(mid))
//                end = mid;
//            // Check if target is greater to middle element. If yes, take first half of array including mid
//            if (target > (int) dataArray.get(mid))
//                start = mid;
//        }
//        // Now you will have only two numbers in array in which target number will fall
//        return Math.abs(target - (int) dataArray.get(start)) <= Math.abs(target - (int) dataArray.get(end)) ? (int) dataArray.get(start)
//                : (int) dataArray.get(end);
//    }
//
//}
