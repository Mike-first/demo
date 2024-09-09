package com.hill.api.soap;

import javax.xml.soap.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class SoapClientExample {
    public static void main(String[] args) throws Exception {
        SOAPMessage soapResponse = sendSOAPRequest(createSOAPRequest(),
                "http://users.bugred.ru/tasks/soap/WrapperSoapServer.php");

        //print
        System.out.println("SOAP Response:");
        soapResponse.writeTo(System.out);
    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        //factory
        MessageFactory messageFactory = MessageFactory.newInstance();
        //message
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        //SOAP-Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/")
                .addNamespaceDeclaration("wrap", "http://foo.bar/wrappersoapserver");
        //SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("doRegister", "wrap");
        soapBodyElem.addChildElement("email").addTextNode("test@test.ru");
        soapBodyElem.addChildElement("name").addTextNode("John Doe");
        soapBodyElem.addChildElement("password").addTextNode("12345");
        //save
        soapMessage.saveChanges();
        //print
        System.out.println("SOAP Request:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

    private static SOAPMessage sendSOAPRequest(SOAPMessage soapMessage, String endpointUrl) throws Exception {
        //create HTTP-connection
        URL url = new URL(endpointUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
//        connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        connection.setRequestProperty("Content-Type", "application/xml");
        connection.setRequestProperty("SOAPAction", "doRegister");
        //send request
        soapMessage.writeTo(connection.getOutputStream());
        //get response
        SOAPMessage soapResponse = MessageFactory.newInstance().createMessage(null, connection.getInputStream());
        return soapResponse;
    }
}

