package cc.taketo.demo;

import cc.taketo.util.PathUtil;
import org.hyperledger.fabric.gateway.*;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * @Title: Main
 * @Package: cc.taketo.demo
 * @Description:
 * @Author: zhangp
 * @Date: 2022/11/11 - 17:01
 */

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader certificateReader = Files.newBufferedReader(Paths.get(PathUtil.getCertificatePath()), StandardCharsets.UTF_8);

        X509Certificate certificate = Identities.readX509Certificate(certificateReader);

        BufferedReader privateKeyReader = Files.newBufferedReader(Paths.get(PathUtil.getPrivateKeyPath()), StandardCharsets.UTF_8);

        PrivateKey privateKey = Identities.readPrivateKey(privateKeyReader);

        Wallet wallet = Wallets.newInMemoryWallet();
        wallet.put("user1" , Identities.newX509Identity("Org1MSP",certificate,privateKey));


        Gateway.Builder builder = Gateway.createBuilder()
                .identity(wallet, "user1")
                .networkConfig(Paths.get("src/main/resources/networkConnection.json"));

        Gateway gateway = builder.connect();

        System.out.println(gateway);
    }
}
