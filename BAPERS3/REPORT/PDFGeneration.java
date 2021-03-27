package REPORT;

import ADMIN.UserAccount;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//https://github.com/itext/i7js-examples/blob/develop/src/main/java/com/itextpdf/samples/sandbox/tables/ArrayToTable.java

public class PDFGeneration {

    public static final String DEST = "/home/javonne/IdeaProjects/BAPERS-FINAL/BAPERS3/TESTING/PDFDemo/PDFDemo/src/TEST.pdf";
    public static void main(String[] args) throws Exception {

        // this is the code that runs on button press
        File file = new File(DEST);
//        file.getParentFile().mkdirs();
        new PDFGeneration().manipulatePdf(DEST);

    }

    // this should be the method in each report class
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        doc.add(new Paragraph("Customer Report!"));
// By default column width is calculated automatically for the best fit.
// useAllAvailableWidth() method makes table use the whole page's width while placing the content.
        Table table = new Table(UnitValue.createPercentArray(5)).useAllAvailableWidth();
        List<List<String>> dataset = null;

        // change toLoad to decide what you load
        ArrayList<String[]> toLoad = UserAccount.getUserList();
        dataset = convertTypes(toLoad);

        for (List<String> record : dataset) {
            for (String field : record) {
                table.addCell(new Cell ().add(new Paragraph(field)));
            }
        }

        doc.add(table);
        doc.close();
    }

    // convert types
    private static List<List<String>> convertTypes(ArrayList<String[]> oldList){

        List<List<String>> list = new ArrayList<>();


        for (String[] strings : oldList){
            list.add(Arrays.asList(strings));
        }

        return list;
    }


//    private static List<List<String>> getData() throws SQLException {
//
////        ArrayList<String[]> test = new ArrayList<>();
////        test = UserAccount.getUserList();
//
//
//        List<List<String>> data = new ArrayList<> ();
//        String[] tableTitleList = {" Title", " (Re)set", " Obs", " Mean", " Std.Dev", " Min", " Max", "Unit"};
//        data.add(Arrays.asList(tableTitleList));
//        for (int i = 0; i < 10; i++) {
//            List<String> dataLine = new ArrayList<>();
//
//            for (int j = 0; j < tableTitleList.length; j++) {
//                dataLine.add(tableTitleList[j] + " " + (i + 1));
//            }
//            data.add(dataLine);
//        }
//        return data;
//    }
}
