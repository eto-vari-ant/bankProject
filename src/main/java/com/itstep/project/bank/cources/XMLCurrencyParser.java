package com.itstep.project.bank.cources;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.*;
import java.net.URI;

public class XMLCurrencyParser
{
    public XMLCurrencyParser() {}

    private static String CURRENCY_URL = "https://www.nbrb.by/Services/XmlExRates.aspx";

    private static Document loadDocument(String url) {
        Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            InputStream in = new java.net.URL(url).openStream();

            doc = factory.newDocumentBuilder().parse(in);
        } catch (java.net.ConnectException e) {
            System.err.print(" Oops! Something goes wrong! This is Belarus, baby! \nConnection timed out. ");
            System.err.print(CURRENCY_URL + " is not responsible. Please, try again later");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return doc;
    }


    public static String getCurrency(String currencyCode)
    {
        boolean isCurrencyCodeNext = false;
        Document doc = loadDocument(CURRENCY_URL);

        if (doc != null) {
            NodeList nodes = doc.getFirstChild().getChildNodes();


            for (int i = 0; i < nodes.getLength(); i++) {
                Node parent = nodes.item(i);

                if (parent.getNodeType() == 1) {
                    NodeList childs = parent.getChildNodes();

                    for (int ii = 0; ii < childs.getLength(); ii++) {
                        Node child = childs.item(ii);
                        if (child.hasChildNodes()) {
                            if ((child.getNodeName().trim().equalsIgnoreCase("Rate")) && (isCurrencyCodeNext)) {
                                isCurrencyCodeNext = false;
                                return child.getFirstChild().getTextContent();
                            }

                            if (child.getFirstChild().getTextContent().trim().equalsIgnoreCase(currencyCode)) {
                                isCurrencyCodeNext = true;
                            }
                        }
                    }
                }
            }
        }
        return "0.0";
    }

    public static void main(String[] args) throws MalformedURLException, IOException {
        System.out.println(getCurrency("840"));

//   URI u = URI.create("https://www.google.com/");
//     try (InputStream inputStream = u.toURL().openStream()) {
//
//         File file = new File("c:\\google.txt");
//
//         copyInputStreamToFile(inputStream, file);
//    }
    }

    /**
     * The default buffer size
     */
    public static final int DEFAULT_BUFFER_SIZE = 8192;





    private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        // append = false
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }

    }

}


