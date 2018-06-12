/**
 * 
 */
package cn.net.cobot.cobot_web.web;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author DELL
 *
 */
public class GitClone {
    private static final String REMOTE_URL = "https://github.com/***/GitTest.git";
    private static String login = "****@qq.com";
    private static String password = "123456";
    private static String branchName = "master";
    
    public static void cloneRepository() throws IOException, InvalidRemoteException, TransportException, GitAPIException {

        // prepare a new folder for the cloned repository
        File localPath = new File("E:/temp/TestGit");
        System.out.println(localPath);
        if (!localPath.exists() && !localPath.isDirectory()) {
            localPath.mkdir();
        }
        localPath.delete();

        // then clone .setBranchesToClone(Arrays.asList("refs/heads/master"))
        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
        CloneCommand clone = Git.cloneRepository().setURI(REMOTE_URL).setDirectory(localPath).setBranch(branchName);
        if (REMOTE_URL.contains("ssh")) {
			SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
				@Override
				protected void configure(Host hc, Session session) {
					//使用公钥使用ssh时设置如下密保
					//session.setPassword("password");
				}
				//使用私钥时要重写下面的方法
				@Override
				protected JSch createDefaultJSch(FS fs) throws JSchException {
					JSch defaultJSch = super.createDefaultJSch(fs);
					defaultJSch.addIdentity("/path/to/private_key");
					return defaultJSch;
				}	
			};
			clone.setTransportConfigCallback(new TransportConfigCallback() {
				@Override
				public void configure(Transport transport) {
					 SshTransport sshTransport = ( SshTransport )transport;
					 sshTransport.setSshSessionFactory(sshSessionFactory);
				}
			});
        }
        if (REMOTE_URL.toString().contains("http") || REMOTE_URL.toString().contains("https")) {
            UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider(login, password);
            clone.setCredentialsProvider(user);
        }

        Git repo1 = clone.call();
        repo1.close();
    }
}
