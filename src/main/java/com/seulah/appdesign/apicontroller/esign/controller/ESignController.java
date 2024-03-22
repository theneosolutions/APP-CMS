package com.seulah.appdesign.apicontroller.esign.controller;


import com.amazonaws.util.Base64;
import com.emdha.esign.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.ArrayList;
@RestController
@RequestMapping("/api/v1/cms/esign")
public class ESignController {

    @PostMapping("/emad")
    public void eSign(@RequestBody MultipartFile pdfPath){
        try {
            // Temp Folder Path
            ClassPathResource tempFolderPathRes = new ClassPathResource("certificates/Temp");
            String tempFolderPath = tempFolderPathRes.getPath();

            // Output Folder Path
            ClassPathResource outputFolderRes = new ClassPathResource("certificates");
            String outputFolder = outputFolderRes.getPath();

            // Licence File Path
            ClassPathResource licenceFilePathRes = new ClassPathResource("certificates/UAT-BFSI-SEULA01.lic");
            String licenceFilePath = licenceFilePathRes.getFile().getAbsolutePath();

            // PFX File Path
            ClassPathResource res = new ClassPathResource("certificates/ClientSDKDev.pfx");
           // File file = copyResourceToFile(res);
            String pFXFilePath = res.getFile().getAbsolutePath();



            //SIP Certificate
        //    String pFXFilePath = "C:\\Users\\thene\\Downloads\\New folder (4)\\M1_SDK_SEULAH_Java\\Sample\\ClientSDKDev.pfx";
            String pFXPassword = "emdha";
            String pFXAlias = "fin";

            //SIP ID
            String SIPID = "UAT-BFSI-SEULA01";

            //eSign URL
            String eSignURL = "https://esign-dev.emdha.sa/eSign/SignDoc";

            //KYC ID
            String kycId = "2047111111";

            //Step-1.Convert PDF to Base64 encoded string
         //   String pdfPath = "C:\\Users\\thene\\Downloads\\New folder (4)\\M1_SDK_SEULAH_Java\\Sample\\Test.pdf";
            String docBase64 = docBase64(pdfPath);

            String appearanceBackgroundImage = "";
            //Step-2. Construct eSignEmdha object using licenceFile, PFXFile, PFXFile credentials and SIPID. (PFX file is also called certificate file.)
            //These files are available in the integration kit shared with you. Your SIPID is UAT-BFSI-SNBCA01
            //eSignEmdha eSign = new eSignEmdha(licenceFilePath, //pFXFilePath, pFXPassword, pFXAlias, SIPID); // by default signature contentEstimated is 40000
            eSignEmdha eSign =
                    new eSignEmdha( licenceFilePath,
                            pFXFilePath,
                            "emdha", "fin",
                            SIPID, 40000); //6th parameter is signature contentEstimated

            //Step-3. Construct EmdhaSignerInfo object, the value to the parameters of this constructor is supposed to be obtained from Trusted KYC source with 2 factor authentication.
            EmdhaSignerInfo signerInfo = new EmdhaSignerInfo("Seulah", kycId, "Arham", "سوراب", "03236967488",
                    "arham@theneosolutions.com", "تبوك", "regionProvience", "SA");

            String signerInfox64 = signerInfo.getSignerInfoXMLBase64();

            //Step-4. Create EmdhaInput object.

            //for adding custom image/logo to the signature appearance.
            //appearanceBackgroundImage is the image of your organization logo. Please provide base64 of the logo.
            //Please choose eSignEmdha.SignatureAppearanceType.CUSTOM_LOGO in EmdhaInput constructor.
            //If eSignEmdha.SignatureAppearanceType.EMDHA_LOGO chosen then emdha logo appears. //default
            //If eSignEmdha.SignatureAppearanceType.NO_IMAGE chosen then no logo appears
            //appearanceBackgroundImage = docBase64("your organization logo.png"); //uncomment to use this

            EmdhaInput input1 = new EmdhaInput(docBase64, "Arham", true, eSignEmdha.PageTobeSigned.All,
                    eSignEmdha.Coordinates.BottomRight, eSignEmdha.AppearanceRunDirection.RUN_DIRECTION_LTR,
                    eSignEmdha.SignatureAppearanceType.EMDHA_LOGO, appearanceBackgroundImage, "Signed By: Arham Gillani");


            //Preapre a list of documents to be signed. Maximum 10 documents can be signed in a single transaction.
            ArrayList<EmdhaInput> inputs = new ArrayList<>();
            inputs.add(input1);

            //Step-5. Generate request XML to post it to EMDHA.
            //second parameter here is transaction ID. Transaction ID should be unique for each transaction. If passed empty SDK generates a transaction ID.
            EmdhaServiceReturn serviceReturn = eSign.generateRequestXml(inputs, "", tempFolderPath,
                    signerInfox64, kycId, true, true, eSignEmdha.KYCIdProvider.SELF_NID);
            if (serviceReturn.getStatus() != 1) {
            } else {
                //URL encode the generated request XML.
                String URLEncodedsignedRequestXML = URLEncoder.encode(serviceReturn.getRequestXML(), "UTF-8");
                //Step-6. Post request XML to emdha CA and get response XML for signing completion
                String response = postXML(eSignURL, URLEncodedsignedRequestXML);

                //Step-7. call getSignedDocuments method
                // EmdhaServiceReturn eSignServiceReturn = eSign.getSignedDocuments(response, serviceReturn.getReturnValues()); //tempFilePath not required. Please make this path empty or remove it.
                EmdhaServiceReturn eSignServiceReturn = eSign.getSignedDocuments(response, tempFolderPath); //temp file path is required. ".Sig" file is created for the transaction and written it to the tempFilePath.

                if (eSignServiceReturn.getStatus() == 1) {
                    int i = 0;

                    //call getReturnValues method to get signed PDFs
                    ArrayList<ReturnDocument> docs = eSignServiceReturn.getReturnValues();
                    for (ReturnDocument doc : docs) {
                        String pdfToBase64 = doc.getSignedDocument();
                        byte[] signedBytes = esign.text.pdf.codec.Base64.decode(pdfToBase64);
                        String pdfOUT = "";

                        //Write signed PDF to output folder path
                        pdfOUT = outputFolder + File.separator + "Signed_PDF_" + i + ".pdf";
                        try ( FileOutputStream fos = new FileOutputStream(pdfOUT)) {
                            fos.write(signedBytes);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        i++;

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }

    }

    //Method to post request XML
    private static String postXML(String eSignURL, String requestXML) throws Exception {
        URL url = new URL(eSignURL);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        SSLContext sslcontext = SSLContext.getInstance("TLSv1.2");
        sslcontext.init(new KeyManager[0], new TrustManager[]{new DummyTrustManager()}, new SecureRandom());
        SSLSocketFactory factory = sslcontext.getSocketFactory();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", "" + Integer.toString(requestXML.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setSSLSocketFactory(factory);
        connection.setHostnameVerifier(new DummyHostnameVerifier());

        try ( DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(requestXML);
            wr.flush();
        }
        InputStream is = connection.getInputStream();
        String response;
        try ( BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
            String line = null;
            response = "";
            while ((line = rd.readLine()) != null) {
                response = response + line + "\r";
            }
        }
        return response;
    }

    private static String docBase64(MultipartFile filePath) throws IOException {
        System.out.println("Arham"+filePath.getBytes().toString());
        return Base64.encodeAsString(filePath.getBytes());
    }

    private static File copyResourceToFile(ClassPathResource resource) throws IOException {
        Path tempFile = Files.createTempFile(null, null);
        try (InputStream is = resource.getInputStream(); FileOutputStream os = new FileOutputStream(tempFile.toFile())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
        return tempFile.toFile();
    }

}
