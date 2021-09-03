package com.example.demo;

import com.github.sarxos.xchange.ExchangeCache;
import com.github.sarxos.xchange.ExchangeException;
import com.github.sarxos.xchange.ExchangeRate;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.io.source.RandomAccessSourceFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.ReaderProperties;
import com.itextpdf.layout.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
public class FormController {
    public static final String Blank_pdf = "./src/main/resources/pdfs/parex_blank.pdf";


    private static final String[] tensNames = {
            "",
            " ten",
            " twenty",
            " thirty",
            " forty",
            " fifty",
            " sixty",
            " seventy",
            " eighty",
            " ninety"
    };

    private static final String[] numNames = {
            "",
            " one",
            " two",
            " three",
            " four",
            " five",
            " six",
            " seven",
            " eight",
            " nine",
            " ten",
            " eleven",
            " twelve",
            " thirteen",
            " fourteen",
            " fifteen",
            " sixteen",
            " seventeen",
            " eighteen",
            " nineteen"
    };



    @GetMapping(value = "/form")
    public String showForm(Model model) {
        Form form = new Form();
        model.addAttribute("form", form);

        List<String> countries = Arrays.asList(new String[]{
                "Europe",
                "Non-EU"
        });

        List<String> currency = Arrays.asList(new String[]{
                "AUD",
                "GBP",
                "EUR",
                "USD"
        });


        model.addAttribute("countries", countries);
        model.addAttribute("currency",currency);
        return "form";
    }


    @PostMapping(value = "/form")
    public String submitForm(@ModelAttribute("form") Form form) {
        System.out.println(form);
        System.out.println(form.getSen_name());
        System.out.println(form.getRec_country());

        SimpleDateFormat formatter = new SimpleDateFormat("HH-mm-ss-dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        String dateString = formatter.format(date);
        String DEST = "./src/main/resources/pdfs/fill_form_field_order " + dateString + ".pdf";


        File file = new File(DEST);
        file.getParentFile().mkdirs();

        try {
            manipulatePdf(DEST, form.getId(), form.getSen_name(), form.getSen_document(), form.getSen_account(), form.getRec_name(), form.getRec_document(), form.getRec_country(), form.getRec_account(), form.getRec_IBAN(), form.getRec_bank(), form.getTrade_date(), form.getTrade_sum(), form.getTrade_details(), form.getTrade_currecy());

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    public int transfer(String text) {
        List searchSpaces = new ArrayList();

        for (int i = 0; i < text.length(); i++) {
            String qwe = text.substring(i, i + 1);
            if (qwe.equals(" ")) {
                searchSpaces.add(i);
            }
        }
        int textLenght = text.length();
        if (textLenght < 50) return textLenght;
        else if (textLenght > 100) return getClosetNumberOfTarget(searchSpaces, textLenght / 2);

        else return getClosetNumberOfTarget(searchSpaces, 50);
    }

    private static int getClosetNumberOfTarget(List dataArray, int target) {

        // Edge case - Check if array length is zero
        if (dataArray.size() == 0)
            System.exit(1);
        // Straight case - If target is smaller or equal to first element in array
        if (target <= (int) dataArray.get(0))
            return (int) dataArray.get(0);
        // Straight case - If target is greater or equal to last element in array
        if (target >= (int) dataArray.get(dataArray.size() - 1))
            return (int) dataArray.get(dataArray.size() - 1);

        // Start binary search algorithm
        int start = 0;
        int end = dataArray.size() - 1;
        int mid;

        // Keep dividing array till further division is not possible
        while (end - start != 1) {
            // Calculate middle index
            mid = (start + end) / 2;
            // Check if middle element is equal to target
            if (target == (int) dataArray.get(mid))
                // If yes return middle element as middle element = target
                return (int) dataArray.get(mid);
            // Check if target is smaller to middle element. If yes, take first half of array including mid
            if (target < (int) dataArray.get(mid))
                end = mid;
            // Check if target is greater to middle element. If yes, take first half of array including mid
            if (target > (int) dataArray.get(mid))
                start = mid;
        }
        // Now you will have only two numbers in array in which target number will fall
        return Math.abs(target - (int) dataArray.get(start)) <= Math.abs(target - (int) dataArray.get(end)) ? (int) dataArray.get(start)
                : (int) dataArray.get(end);
    }

    public String notWeekend(){
        SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat DayofWeek= new SimpleDateFormat("u");
        String dateNeeded;
        Date date = new Date(System.currentTimeMillis());
        if (DayofWeek.format(date)=="7"){
            return formatter.format( System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1l));
        } else if (DayofWeek.format(date)=="6"){

            return formatter.format( System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2l));
        }
        else return formatter.format( System.currentTimeMillis());
    }

    public void manipulatePdf(String dest, UUID id, String sen_name, String sen_document, String sen_account, String rec_name, String rec_document, String rec_country, String rec_account, String rec_IBAN, String rec_bank, String trade_date, BigDecimal trade_sum, String trade_details, String trade_currency) throws Exception {

        // Partially flattened form's byte array with content, which should placed beneath the other content.
        byte[] tempForm = createPartiallyFlattenedForm();
        createResultantPdf(dest, tempForm, id, sen_name, sen_document, sen_account, rec_name, rec_document, rec_country, rec_account, rec_IBAN, rec_bank, trade_date, trade_sum, trade_details, trade_currency);
    }

    public byte[] createPartiallyFlattenedForm() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(Blank_pdf), new PdfWriter(baos));
        com.itextpdf.layout.Document doc = new Document(pdfDoc);
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, false);

        Map<String, PdfFormField> fields = form.getFormFields();


        doc.close();

        return baos.toByteArray();
    }


    public void createResultantPdf(String dest, byte[] src, UUID id, String sen_name, String sen_document, String sen_account, String rec_name, String rec_document, String rec_country, String rec_account, String rec_IBAN, String rec_bank, String trade_date, BigDecimal trade_sum, String trade_details, String trade_currency) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new RandomAccessSourceFactory().createSource(src),
                new ReaderProperties()), new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

        ExchangeCache.setParameter("openexchangerates.org.apikey", "8900dd7938ad485a8ecdb39422449a88");

        // define base currency
        ExchangeCache cache = new ExchangeCache("USD");

        // get the CAD to USD exchange rate
        ExchangeRate rate = null;
        try {
            rate = cache.getRate(trade_currency);
        } catch (ExchangeException e) {
            e.printStackTrace();
        }

        // convert
        BigDecimal converted = rate.convert(trade_sum);


        Map<String, PdfFormField> fields = form.getFormFields();

        fields.get("fill_1").setValue(id.toString());//date
        fields.get("fill_2").setValue(trade_date);//date
        fields.get("fill_3").setValue(sen_name);//customer name
        fields.get("fill_4").setValue(sen_document);//customer doc
        fields.get("fill_5").setValue(sen_account);//custmer account
        fields.get("fill_6").setValue(rec_name);//rec name
        fields.get("fill_7").setValue(rec_document);//rec doc
        fields.get("fill_8").setValue(rec_account);//rec account
        fields.get("fill_9").setValue(rec_country);//rec country
        fields.get("fill_10").setValue(rec_bank);//rec bank
        fields.get("fill_11").setValue(rec_IBAN);//rec bank id
        fields.get("fill_12").setValue(trade_sum.toString());//deal sum
        fields.get("fill_13").setValue(commision(rec_country,rec_bank));//deal commission
        fields.get("fill_14").setValue(convert(trade_sum.longValue()) + " " + toLongCurrency(trade_currency));//deal ammount words
        fields.get("fill_15").setValue(paymentType(rec_country,rec_bank));//deal payment type
        fields.get("fill_16").setValue(converted.toString() + " USD");//deal exchange rate
        fields.get("fill_17").setValue(notWeekend());//deal value date
        fields.get("fill_18").setValue("");//deal code of external payment ??
//        // All fields will be flattened, because no fields have been explicitly included
//
//
        fields.get("fill_19").setValue(trade_details.substring(0, transfer(trade_details)));//deal payment details
        fields.get("fill_20").setValue(trade_details.substring(transfer(trade_details), trade_details.length()));//deal payment details 2nd row


        // All fields will be flattened, because no fields have been explicitly included
        form.flattenFields();

        pdfDoc.close();
    }

    public String commision(String country, String bank){
        if(country.equals("Europe"))
            return "worse rate";
        else if(bank.equals("Citadele"))
            return "best rate!";
        else return "the worst rate";

    }

    public String paymentType(String country, String bank){
        if(country.equals("Europe"))
            return "inside the EU";
        else if(bank.equals("Citadele"))
            return "inside the bank!";
        else return "outside the EU";

    }




    public static String convert(long number) {
        // 0 to 999 999 999 999
        if (number == 0) { return "zero"; }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0,3));
        // nnnXXXnnnnnn
        int millions  = Integer.parseInt(snumber.substring(3,6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6,9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9,12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1 :
                tradBillions = convertLessThanOneThousand(billions)
                        + " billion ";
                break;
            default :
                tradBillions = convertLessThanOneThousand(billions)
                        + " billion ";
        }
        String result =  tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1 :
                tradMillions = convertLessThanOneThousand(millions)
                        + " million ";
                break;
            default :
                tradMillions = convertLessThanOneThousand(millions)
                        + " million ";
        }
        result =  result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1 :
                tradHundredThousands = "one thousand ";
                break;
            default :
                tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                        + " thousand ";
        }
        result =  result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result =  result + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20){
            soFar = numNames[number % 100];
            number /= 100;
        }
        else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) return soFar;
        return numNames[number] + " hundred" + soFar;
    }

    public String toLongCurrency(String shortCurrency){
        if (shortCurrency.equals("AUD")) return "Australian Dollar";
        else if(shortCurrency.equals("GBP")) return "British Pound Sterling";
        else if(shortCurrency.equals("EUR")) return "Euro";
        else if(shortCurrency.equals("USD")) return "United States Dollar";
        else return "something went wrong";
    }


}
