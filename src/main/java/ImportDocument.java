import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ImportDocument {
    // This function extracts the text of an OpenOffice document
    public static String extractString() throws IOException, JDOMException {
        File initialFile = new File("src/main/resources/uploaded_office_doc.odt");
        InputStream in = new FileInputStream(initialFile);
        final ZipInputStream zis = new ZipInputStream(in);
        ZipEntry entry;
        List<Content> content = null;
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.getName().equals("content.xml")) {
                final SAXBuilder sax = new org.jdom2.input.SAXBuilder();
                //sax.setFeature("http://javax.xml.XMLConstants/feature/secure-processing",true);
                Document doc = sax.build(zis);
                content = doc.getContent();
                System.out.println(content);
                zis.close();
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        if (content != null){
            for(Content item : content){
                sb.append(item.getValue());
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception{
        System.out.println(extractString());
    }
}