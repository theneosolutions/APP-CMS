//package com.seulah.appdesign.apicontroller.esign.controller;
//
//import com.emdha.esign.DummyHostnameVerifier;
//import com.emdha.esign.DummyTrustManager;
//import com.emdha.esign.EmdhaInput;
//import com.emdha.esign.EmdhaServiceReturn;
//import com.emdha.esign.EmdhaSignerInfo;
//import com.emdha.esign.ReturnDocument;
//import com.emdha.esign.eSignEmdha;
//import esign.text.pdf.codec.Base64;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.security.SecureRandom;
//import java.util.ArrayList;
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.KeyManager;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSocketFactory;
//import javax.net.ssl.TrustManager;
//@RestController
//@RequestMapping("/api/v1/cms/esign")
//public class ESignController {
//
//    @GetMapping("/emad")
//    public ResponseEntity<?> eSign(@RequestParam("eSign64")  String eSign64){
//        try {
//
//            String tempFolderPath = "";
//            // String tempFolderPath = "Temp";
//
//            //In output folder path, signed PDFs are written.
//            String outputFolder = "outputesign";
//
//            //Licence File
//            String licenceFilePath = "certificates/UAT-BFSI-SEULA01.lic";
//
//            //SIP Certificate
//            String pFXFilePath = "certificates/ClientSDKDev.pfx";
//            String pFXPassword = "emdha";
//            String pFXAlias = "fin";
//
//            //SIP ID
//            String SIPID = "UAT-BFSI-SEULA01";
//
//            //eSign URL
//            String eSignURL = "https://esign-dev.emdha.sa/eSign/SignDoc";
//
//            //KYC ID
//            String kycId = "2047111111";
//
//            //Step-1.Convert PDF to Base64 encoded string
//            String pdfPath = "test/Test.pdf";
//            String docBase64 = docBase64(pdfPath);
//
//            String appearanceBackgroundImage = "";
//
//            //Step-2. Construct eSignEmdha object using licenceFile, PFXFile, PFXFile credentials and SIPID. (PFX file is also called certificate file.)
//            //These files are available in the integration kit shared with you. Your SIPID is UAT-BFSI-SNBCA01
//            //eSignEmdha eSign = new eSignEmdha(licenceFilePath, //pFXFilePath, pFXPassword, pFXAlias, SIPID);
//            // by default signature contentEstimated is 40000
//            Resource resource = new ClassPathResource(licenceFilePath);
//            Resource resource2 = new ClassPathResource(pFXFilePath);
//            System.out.println(resource.getFilename()+resource2.getFilename());
//            eSignEmdha eSign = new eSignEmdha(resource.getFilename(), resource2.getFilename(), pFXPassword, pFXAlias, SIPID, 40000); //6th parameter is signature contentEstimated
//            System.out.println(eSign.toString());
//            //Step-3. Construct EmdhaSignerInfo object, the value to the parameters of this constructor is supposed to be obtained from Trusted KYC source with 2 factor authentication.
//            EmdhaSignerInfo signerInfo = new EmdhaSignerInfo("your organization legal name", kycId, "Saurabh", "سوراب", "0536014413",
//                    "hmohiuddin@emdha.sa", "تبوك", "regionProvience", "SA");
//
//            String signerInfox64 = signerInfo.getSignerInfoXMLBase64();
//
//            //Step-4. Create EmdhaInput object.
//
//            //for adding custom image/logo to the signature appearance.
//            //appearanceBackgroundImage is the image of your organization logo. Please provide base64 of the logo.
//            //Please choose eSignEmdha.SignatureAppearanceType.CUSTOM_LOGO in EmdhaInput constructor.
//            //If eSignEmdha.SignatureAppearanceType.EMDHA_LOGO chosen then emdha logo appears. //default
//            //If eSignEmdha.SignatureAppearanceType.NO_IMAGE chosen then no logo appears
//            //appearanceBackgroundImage = docBase64("your organization logo.png"); //uncomment to use this
//
//            EmdhaInput input1 = new EmdhaInput(docBase64, "Saurabh", true, eSignEmdha.PageTobeSigned.All,
//                    eSignEmdha.Coordinates.CentreMiddle, eSignEmdha.AppearanceRunDirection.RUN_DIRECTION_LTR,
//                    eSignEmdha.SignatureAppearanceType.EMDHA_LOGO, appearanceBackgroundImage, "Custom content");
//
//            //EmdhaInput, some other constructor samples
//            EmdhaInput input2 = new EmdhaInput(docBase64, "", true, "1-94,575,244,650;2-75,695,225,770",
//                    eSignEmdha.AppearanceRunDirection.RUN_DIRECTION_LTR, eSignEmdha.SignatureAppearanceType.EMDHA_LOGO, appearanceBackgroundImage, "Signed By: Saurabh سوراب\nReason: Test");
//            //EmdhaInput input3 = new EmdhaInput(docBase64, "Location", "Test", "SignedBy", true, eSignEmdha.Coordinates.BottomRight, "1,2",
//            //eSignEmdha.AppearanceRunDirection.RUN_DIRECTION_LTR, eSignEmdha.SignatureAppearanceType.NO_IMAGE, "appearanceBackgroundImage", "");
//
//            //Preapre a list of documents to be signed. Maximum 10 documents can be signed in a single transaction.
//            ArrayList<EmdhaInput> inputs = new ArrayList<>();
//            inputs.add(input1);
//            inputs.add(input2);
//
//            //Step-5. Generate request XML to post it to EMDHA.
//            //second parameter here is transaction ID. Transaction ID should be unique for each transaction. If passed empty SDK generates a transaction ID.
//            EmdhaServiceReturn serviceReturn = eSign.generateRequestXml(inputs, "", tempFolderPath,
//                    signerInfox64, kycId, true, true, eSignEmdha.KYCIdProvider.SELF_NID);
//
//            if (serviceReturn.getStatus() != 1) {
//            } else {
//                //URL encode the generated request XML.
//                String URLEncodedsignedRequestXML = URLEncoder.encode(serviceReturn.getRequestXML(), "UTF-8");
//
//                //Step-6. Post request XML to emdha CA and get response XML for signing completion
//                String response = postXML(eSignURL, URLEncodedsignedRequestXML);
//
//                //Step-7. call getSignedDocuments method
//                EmdhaServiceReturn eSignServiceReturn = eSign.getSignedDocuments(response, serviceReturn.getReturnValues()); //tempFilePath not required. Please make this path empty or remove it.
//                //EmdhaServiceReturn eSignServiceReturn = eSign.getSignedDocuments(response, tempFolderPath); //temp file path is required. ".Sig" file is created for the transaction and written it to the tempFilePath.
//
//                if (eSignServiceReturn.getStatus() == 1) {
//                    int i = 0;
//
//                    //call getReturnValues method to get signed PDFs
//                    ArrayList<ReturnDocument> docs = eSignServiceReturn.getReturnValues();
//                    for (ReturnDocument doc : docs) {
//                        String pdfToBase64 = doc.getSignedDocument();
//                        byte[] signedBytes = Base64.decode(pdfToBase64);
//                        String pdfOUT = "";
//
//                        //Write signed PDF to output folder path
//                        pdfOUT = outputFolder + File.separator + "Signed_PDF_" + i + ".pdf";
//                        try ( FileOutputStream fos = new FileOutputStream(pdfOUT)) {
//                            fos.write(signedBytes);
//                        } catch (Exception e) {
//                            System.out.println(e);
//                        }
//                        i++;
//
//                    }
//                }
//
//            }
//            return ResponseEntity.ok().body(eSign64);
//        } catch (Exception ex) {
//            System.out.println(ex);
//            return ResponseEntity.badRequest().body(eSign64);
//        }
//    }
//
//
//    //Method to post request XML
//    private static String postXML(String eSignURL, String requestXML) throws Exception {
//        URL url = new URL(eSignURL);
//        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//
//        SSLContext sslcontext = SSLContext.getInstance("TLSv1.2");
//        sslcontext.init(new KeyManager[0], new TrustManager[]{new DummyTrustManager()}, new SecureRandom());
//        SSLSocketFactory factory = sslcontext.getSocketFactory();
//
//        connection.setRequestMethod("POST");
//        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        connection.setRequestProperty("Content-Length", "" + Integer.toString(requestXML.getBytes().length));
//        connection.setRequestProperty("Content-Language", "en-US");
//        connection.setUseCaches(false);
//        connection.setDoInput(true);
//        connection.setDoOutput(true);
//        connection.setSSLSocketFactory(factory);
//        connection.setHostnameVerifier(new DummyHostnameVerifier());
//
//        try ( DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
//            wr.writeBytes(requestXML);
//            wr.flush();
//        }
//        InputStream is = connection.getInputStream();
//        String response;
//        try ( BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
//            String line = null;
//            response = "";
//            while ((line = rd.readLine()) != null) {
//                response = response + line + "\r";
//            }
//        }
//        return response;
//    }
//
//    private static String docBase64(String filePath) throws IOException {
//        ClassPathResource file = new ClassPathResource(filePath);
//        byte[] samplePDF;
//        try (InputStream inputStream = file.getInputStream()) {
//            samplePDF = inputStream.readAllBytes();
//        }
//        return Base64.encodeBytes(samplePDF);
//    }
//
//
//}
