package org.jredfoot.utgrid.cert.pki;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CA
{
  private X509Certificate cert;
  private PrivateKey key;
  private X509CRL crl;
  public static final Provider PROVIDER = new BouncyCastleProvider();
  public static final String SIGNATURE_ALGORITHM = "MD5WithRSAEncryption";
  public static final String PASSWORD = "@tch!m";
  public static final String DEFAULT_CA_DN = "O=Universidade Tecnológica Federal do Paraná,OU=Cornélio Procópio,CN=Certificate Authority";
  private KeyStore keyStore;

  public CA()
    throws Exception
  {
    this("O=Universidade Tecnológica Federal do Paraná,OU=Cornélio Procópio,CN=Certificate Authority");
  }

  public CA(String dn) throws Exception
  {
    Security.addProvider(PROVIDER);
    Calendar c = new GregorianCalendar();
    Date now = c.getTime();
    c.add(1, 5);
    Date expires = c.getTime();
    KeyPair pair = KeyUtil.generateRSAKeyPair512(PROVIDER.getName());
    this.key = pair.getPrivate();
    this.cert = CertUtil.generateCACertificate(PROVIDER.getName(), new X509Name(dn), now, expires, pair, "MD5WithRSAEncryption");

    if (PROVIDER.getName().equals("ERACOM")) {
      this.keyStore = KeyStore.getInstance("CRYPTOKI", PROVIDER.getName());
      this.keyStore.load(null, "caGrid@bmi".toCharArray());
      this.keyStore.deleteEntry("CA");
      this.keyStore.setKeyEntry("CA", this.key, null, new X509Certificate[] { this.cert });
      this.key = ((PrivateKey)this.keyStore.getKey("CA", null));
    }
  }

  public CA(String dn, Date start, Date expires)
    throws Exception
  {
    KeyPair pair = KeyUtil.generateRSAKeyPair512(PROVIDER.getName());
    this.key = pair.getPrivate();
    this.cert = CertUtil.generateCACertificate(PROVIDER.getName(), new X509Name(dn), start, expires, pair, "MD5WithRSAEncryption");
  }

  public CA(X509Certificate cert, PrivateKey key, X509CRL crl)
  {
    this.cert = cert;
    this.key = key;
    this.crl = crl;
  }

  public X509Certificate getCertificate()
  {
    return this.cert;
  }

  public Credential createIdentityCertificate(String id) throws Exception
  {
    String dn = getCertificate().getSubjectDN().getName();
    int index = dn.indexOf("CN=");
    dn = dn.substring(0, index + 3) + id;
    KeyPair pair = KeyUtil.generateRSAKeyPair512(PROVIDER.getName());
    Date now = new Date();
    Date end = getCertificate().getNotAfter();
    Credential cred = new Credential(CertUtil.generateCertificate(PROVIDER.getName(), new X509Name(dn), now, end, pair.getPublic(), getCertificate(), getPrivateKey(), "MD5WithRSAEncryption", null), pair.getPrivate());

    if (PROVIDER.getName().equals("ERACOM")) {
      this.keyStore.deleteEntry(id);
      this.keyStore.setKeyEntry(id, cred.getPrivateKey(), null, new X509Certificate[] { cred.getCertificate() });
      cred.setPrivateKey((PrivateKey)this.keyStore.getKey(id, null));
    }
    return cred;
  }

  public X509CRL getCRL()
  {
    return this.crl;
  }

  public PrivateKey getPrivateKey()
  {
    return this.key;
  }

  public X509CRL updateCRL(CRLEntry entry) throws Exception
  {
    CRLEntry[] entries = new CRLEntry[1];
    entries[0] = entry;
    this.crl = CertUtil.createCRL(PROVIDER.getName(), this.cert, this.key, entries, this.cert.getNotAfter(), "MD5WithRSAEncryption");
    return this.crl;
  }

  public X509CRL updateCRL(CRLEntry[] entries) throws Exception
  {
    this.crl = CertUtil.createCRL(PROVIDER.getName(), this.cert, this.key, entries, this.cert.getNotAfter(), "MD5WithRSAEncryption");
    return this.crl;
  }
}