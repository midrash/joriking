package com.king.yori.image.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.king.yori.repository.dao.ImageDao;
import com.king.yori.repository.entity.Image;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	ImageDao imageDao;
	
	
	final static String privateKey = "C:/ssafy/aws/I4D105T.pem";
	// sftp에 접속하기 위한 보안키(나 이외의 사람들도 설정 편하게 하면 좋으니, 깃랩에 올릴까 고민해보았지만, 보안상 경로만 잡고, 키는 올리지 않는다.)
	final static String URL_ROOT_PATH = "http://i4d105.p.ssafy.io:8000/test/";	
	
	final static String SFTP_SERVER_DOMAIN ="i4d105.p.ssafy.io";
	final static String SFTP_USER_NAME = "ubuntu";
	final static String SFTP_USER_PASSWORD = null;
	final static Integer SFTP_PORT = 22;
	final static String SFTP_ROOT_PATH = "/home/ubuntu/ssafy/static/test";
	
	public boolean uploadImage(int userId,MultipartFile image) throws Exception {
		
		String orgName = image.getOriginalFilename();		// 오리지널 파일 이름
		int idx = orgName.lastIndexOf(".");
		String ext = "";
		if( idx != -1 ) ext = orgName.substring(idx);
		String systemName = UUID.randomUUID().toString() + ext;
		String modulePath = new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
		
		init(SFTP_SERVER_DOMAIN, SFTP_USER_NAME, SFTP_USER_PASSWORD, SFTP_PORT, privateKey);
		mkdir(SFTP_ROOT_PATH + modulePath);
		upload(SFTP_ROOT_PATH + modulePath, image, systemName);
		// sftp 서버로 저장완료!
		disconnection();
		
		Image entity = new Image();
		entity.setOrigName(orgName);
		entity.setUserId(userId);
		entity.setUrl(URL_ROOT_PATH + modulePath + "/" + systemName);
		imageDao.save(entity);
		// db저장 완료!
		
		return true;
	}
	
	//////////////////////// 아래는 sftp 설정 내부에서 사용하는 메소드들이다. 컨트롤러에서 사용하지 않는다! ////////////////////////////////////////////////
	
    private Session session = null;
    
    private Channel channel = null;
 
    private ChannelSftp channelSftp = null;
 
    /**
     * 서버와 연결에 필요한 값들을 가져와 초기화 시킴
     *
     * @param host
     *            서버 주소
     * @param userName
     *            접속에 사용될 아이디
     * @param password
     *            비밀번호
     * @param port
     *            포트번호
     * @param privateKey
     *            키
     */
    public void init(String host, String userName, String password, int port, String privateKey) {
 
        JSch jSch = new JSch();
        try {
            if(privateKey!=null) {//키가 존재한다면
                jSch.addIdentity(privateKey);
            }
 
            session = jSch.getSession(userName, host, port);
 
            if(privateKey==null) {//키가 없다면
                session.setPassword(password);
            }
 
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
 
            channel = session.openChannel("sftp");
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
 
        channelSftp = (ChannelSftp) channel;
 
    }
 
    /**
     * 하나의 폴더를 생성한다.
     *
     * @param dir
     *            이동할 주소
     * @param mkdirName
     *            생상할 폴더명
     */
    public void mkdir(String dir, String mkdirNames) throws SftpException{
 
    	channelSftp.cd(dir);
    	for( String mkdirName : mkdirNames.split("/") ) {
    		System.out.println(mkdirName);
    		try {
    			channelSftp.mkdir(mkdirName);  
    		}
    		catch(Exception e) {}
    		finally {
				channelSftp.cd(mkdirName);
			}
    	}
    }
    
    public void mkdir(String mkdirNames) throws Exception{
    	// 현재 JSch 라이브러리에 특정 폴더가 있는지 확인 할 수 있는 method가 존재하지 않는다.
    	// 만약 값이 예외가 나온다면 특정 폴더가 있다는 소리이고, 이것을 이용하여 다음 폴더를 찾고, 다시 만들며 들어가는 메소드이다.
    	
    	for( String mkdirName : mkdirNames.split("/")) {
    		if(mkdirName.equals("")) continue;		// 빈문자열이 들어와서 에러를 일으켰다. 
    		try {
    			channelSftp.mkdir(mkdirName);
    		}
    		catch(SftpException e) {}
    		finally {
    			channelSftp.cd(mkdirName);
    		}
    	}
    }
    
    
 
    /**
     * 하나의 파일을 업로드 한다.
     *
     * @param dir
     *            저장시킬 주소(서버)
     * @param file
     *            저장할 파일
     */
    public void upload(String dir,MultipartFile image, String fileName) {
        InputStream in = null;
        try {
            in = image.getInputStream();
            channelSftp.cd(dir);
            channelSftp.put(in, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 하나의 파일을 다운로드 한다.
     *
     * @param dir
     *            저장할 경로(서버)
     * @param downloadFileName
     *            다운로드할 파일
     * @param path
     *            저장될 공간
     */
    public void download(String dir, String downloadFileName, String path) {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            channelSftp.cd(dir);
            in = channelSftp.get(downloadFileName);
        } catch (SftpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        try {
            out = new FileOutputStream(new File(path));
            int i;
 
            while ((i = in.read()) != -1) {
                out.write(i);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
 
        }
 
    }
    /**
     * 서버와의 연결을 끊는다.
     */
    public void disconnection() {
        channelSftp.quit();
        session.disconnect();
 
    }
	
	
}
