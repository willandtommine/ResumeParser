package code4goal.antony.resumeparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;



import org.json.simple.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;



import com.amazonaws.AmazonServiceException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.codec.binary.Base64;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.ToXMLContentHandler;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;


import gate.util.GateException;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
		//String destBktName = "eastbucketwrite";

		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		
		
		byte[] bI = Base64.decodeBase64(event.getBody().getBytes());
		
		
		System.out.println("first line, 2:57");

		File storage = new File("../.././tmp/store.txt");
		if(storage.exists()) {
			try {
				new PrintWriter(storage).close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		File temp = new File("../.././tmp/temp.txt");
		if(temp.exists()) {
			try {
				new PrintWriter(temp).close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		try {
			 
			OutputStream os = new FileOutputStream(storage);
            os.write(bI);
            System.out.println("Write bytes to file.");
            
            os.close();
        } catch (Exception e) {
        	
            e.printStackTrace();
        }

		

		
		

		final AmazonS3Client s3 = (AmazonS3Client) AmazonS3ClientBuilder.standard().build();

		try {

		

		

			
			ContentHandler handler = new ToXMLContentHandler();

			InputStream stream = new FileInputStream(storage);
			//String url = "https://" + destBktName + ".s3.amazonaws.com/" + OUTPUT_FILE_NAME;

			AutoDetectParser parser = new AutoDetectParser();
			Metadata metadata = new Metadata();
			try {

				parser.parse(stream, handler, metadata);
				FileWriter htmlFileWriter = new FileWriter(temp);
				htmlFileWriter.write(handler.toString());
				htmlFileWriter.flush();
				htmlFileWriter.close();
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
				}
			}

		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		try {

			
			JSONObject parsedJSON = ResumeParserProgram.loadGateAndAnnie(temp);
			System.out.println("just got some parsed Json");
			System.out.println(parsedJSON.toJSONString());
			
			response.setBody(parsedJSON.toJSONString());
			System.gc();
			return response;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.gc();
		return response;

	}

}
