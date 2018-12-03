import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.*;
import java.security.cert.*;

public class VerifyCertificate {
    public static FileInputStream caFile;
    public static FileInputStream userFile;
    public static X509Certificate caCer;
    public static X509Certificate userCert;
    public static String caSubjectDN;
    public static String caIssuerDN;
    public static String userSubjectDN;
    public static String userIssuerDN;
    public static PublicKey caCerPublicKey;
    public static PublicKey userCerPublicKey;
    public static void main(String[] args) throws FileNotFoundException, CertificateException {
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        caFile = new FileInputStream (args[0]);
        userFile = new FileInputStream(args[1]);
        caCer = (X509Certificate) fact.generateCertificate(caFile);
        userCert = (X509Certificate) fact.generateCertificate(userFile);
        caSubjectDN = caCer.getSubjectDN().toString();
        caIssuerDN = caCer.getIssuerDN().toString();
        userSubjectDN = userCert.getSubjectDN().toString();
        userIssuerDN = userCert.getIssuerDN().toString();
        caCerPublicKey = caCer.getPublicKey();
        userCerPublicKey = userCert.getPublicKey();
        System.out.println("CA Subject DN: " + caSubjectDN);
        System.out.println("User Subject DN: " + userSubjectDN);
        if (verifyCertificates()){
            System.out.println("pass");
        } else{
            System.out.println("fail");
        }
    }

    public static boolean verifyCertificates() {
        // check dates
        try{
            caCer.checkValidity();
            caCer.verify(caCerPublicKey);
            userCert.checkValidity();
            userCert.verify(caCerPublicKey);
            return true;
        } catch(CertificateNotYetValidException e) {
            e.printStackTrace();
        } catch (CertificateExpiredException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return false;
    }

}
