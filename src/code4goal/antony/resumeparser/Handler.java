package code4goal.antony.resumeparser;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;

import org.json.simple.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
/*
 * import java.net.URL;
 * import com.amazonaws.AmazonServiceException;
import java.io.InputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.ToXMLContentHandler;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import com.amazonaws.services.s3.model.S3Object;
*/
import gate.Gate;
import gate.util.GateException;

public class Handler implements RequestHandler<S3Event, Context> {

	public Context handleRequest(S3Event s3event, Context context) {
		//String destBktName = "eastbucketwrite";

		System.out.println("first line, 2:57");

		
		File p = new File("/var/task/./plugins1");
		File prev = new File("/var/task/");
		File creoleFile = new File("file:/var/task/./plugins/ANNIE/");
		
		for(File s:prev.listFiles()) {
			if(s!=null) {
				System.out.println(s.getAbsolutePath());
				if(s.getAbsolutePath().contains("creole")) {
					creoleFile = new File(s.getAbsolutePath());
					System.out.println(s.getAbsolutePath()+"<--- Shes the one?");
				}
			}else {
				System.out.println("shes null");
			}
			
		}
		
		
		


		//S3EventNotification.S3EventNotificationRecord record = (s3event).getRecords().get(0);

		//String bucket_name = record.getS3().getBucket().getName();
		//String key_name = record.getS3().getObject().getUrlDecodedKey();
		

		//final AmazonS3Client s3 = (AmazonS3Client) AmazonS3ClientBuilder.standard().build();
/*
		try {

			S3Object o = s3.getObject(bucket_name, key_name);

			String ext = FilenameUtils.getExtension(key_name);
			String outputFileFormat = "";

			if (ext.equalsIgnoreCase("html") | ext.equalsIgnoreCase("pdf") | ext.equalsIgnoreCase("doc")
					| ext.equalsIgnoreCase("docx")) {
				outputFileFormat = ".html";
				// handler = new ToXMLContentHandler();
			} else if (ext.equalsIgnoreCase("txt") | ext.equalsIgnoreCase("rtf")) {
				outputFileFormat = ".txt";
			} else {
				System.out.println("Input format of the file " + key_name + " is not supported.");
				return null;
			}

			String OUTPUT_FILE_NAME = FilenameUtils.removeExtension(key_name) + outputFileFormat;
			ContentHandler handler = new ToXMLContentHandler();

			InputStream stream = o.getObjectContent();
			String url = "https://" + destBktName + ".s3.amazonaws.com/" + OUTPUT_FILE_NAME;

			AutoDetectParser parser = new AutoDetectParser();
			Metadata metadata = new Metadata();
			try {

				parser.parse(stream, handler, metadata);

				stream.close();

				// s3.putObject(destBktName, OUTPUT_FILE_NAME, handler.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TikaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(1);
				}
			}

		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}
		System.out.println(" after file was made");
*/
		try {

			
			JSONObject parsedJSON = ResumeParserProgram.loadGateAndAnnie(new File("okbeast.com"));
			System.out.println("just got some parsed Json");
			System.out.println(parsedJSON.toJSONString());
			return (Context) parsedJSON;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
