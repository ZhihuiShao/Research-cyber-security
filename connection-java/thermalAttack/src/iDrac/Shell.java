package iDrac;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
/*
implement basic ssh shell for iDracThread and VMThread
 */
public class Shell {
    private String ip;
    private String username;
    private String password;
    public static final int DEFAULT_SSH_PORT = 22;  
    private ArrayList<String> stdout;
    //
    private JSch jsch = null;
    private MyUserInfo userInfo=null;
    private Session session = null;

    /**
     * @param ip
     * @param username
     * @param password
     */
    public Shell(final String ip, final String username, final String password) {
         this.ip = ip;
         this.username = username;
         this.password = password;
         this.stdout = new ArrayList<String>();
         this.jsch = new JSch();
         this.userInfo = new MyUserInfo();
         try {
			this.session = jsch.getSession(username, ip, DEFAULT_SSH_PORT);
			this.session.setPassword(password);
			this.session.setUserInfo(userInfo);
			this.session.connect();
			
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @param command
     * @return
     */

    public int execute(final String command, int second) {
        int returnCode = 0;
        try {
            ChannelExec channelExec = (ChannelExec)session.openChannel("exec");
            channelExec.setCommand(command);

            channelExec.setInputStream(null);
            BufferedReader input = new BufferedReader(new InputStreamReader
                    (channelExec.getInputStream()));

            channelExec.connect();
            //System.out.println("The remote command is :" + command);
          //sleep the thread
      		try {
      			Thread.sleep(second*1000);
      		} catch (InterruptedException e) {
      			// TODO Auto-generated catch block
      			e.printStackTrace();
      		}
            String line;
            while ((line = input.readLine()) != null) {  
                stdout.add(line);  
            }  
            input.close();  

            if (channelExec.isClosed()) {  
                returnCode = channelExec.getExitStatus();  
            }  

            channelExec.disconnect();
//            session.disconnect();

        } catch (JSchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnCode;
    }
    // CLOSE the session
    public void close(){
		if(this.session != null){
			this.session.disconnect();
		}
		
		this.jsch = null;
	}
    /**
     * get stdout
     * @return
     */
    public ArrayList<String> getStandardOutput() {
    		ArrayList<String> temp = new ArrayList<String>();
    		for(int i=0;i<stdout.size();i++){
    			temp.add(stdout.get(i));
    		}
    		stdout.clear();
        return temp;
    } 
}