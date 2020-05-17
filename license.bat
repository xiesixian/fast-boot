@E:

@mkdir  key

@cd key

@keytool -genkeypair -keysize 1024 -validity 3650 -alias "privateKey" -keystore "privateKeys.store" -storepass "136305973@qq.com" -keypass "xiesx@888" -dname "CN=ZEDU, OU=ZCJY, O=ZCJY, L=WUHAN, ST=HUBEI, C=CN"

@keytool -exportcert -alias "privateKey" -keystore "privateKeys.store" -storepass "136305973@qq.com" -file "certfile.cer"

echo y | keytool -import -alias "publicCert" -file "certfile.cer" -keystore "publicCerts.store" -storepass "136305973@qq.com"

@pause