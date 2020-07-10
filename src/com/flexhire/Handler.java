package com.flexhire;

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



//import com.amazonaws.AmazonServiceException;
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


import gate.util.GateException;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
		//String destBktName = "eastbucketwrite";

		APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
		
		
		
		
		byte[] bI = Base64.decodeBase64(event.getBody().getBytes());
		
		
		

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
           
            
            os.close();
        } catch (Exception e) {
        	
            e.printStackTrace();
        }

		

		
		

		

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

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		try {

			
			JSONObject parsedJSON = ResumeParserProgram.loadGateAndAnnie(temp);
			
			System.out.println(clean(parsedJSON.toJSONString()));
			
			response.setBody(clean(parsedJSON.toJSONString()));
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

	private String clean(String json2) {

		
		json2 = json2.replace("&amp;", " ");

		json2 = json2.replace("\\u2022", "");
		json2 = json2.replace("\\u2019", "");
		json2 = json2.replace("$", " ");
		json2 = json2.replace("\\n", "");
		json2 = json2.replace("\\u2013", "-");
		json2 = json2.replace("  ", " ");
		json2 = json2.replace("\\u201C", '"'+"");
		json2 = json2.replace("\\u201D", '"'+"");
		json2 = json2.replace("\\/", " ");
		int sIndex = 0;
		int fIndex = 0;
		while(json2.contains("<")) {
			
			for(int i = 0;i<json2.length();i++) {
				if(json2.charAt(i)=='<') {
					sIndex = i;
					break;
				}
				
			}
			
			for(int i = sIndex;i<json2.length();i++) {
				if(json2.charAt(i)=='>') {
					fIndex = i;
					break;
				}
				if(json2.charAt(i)=='}') {
					json2 = json2.substring(0,i)+">"+json2.substring(i,json2.length());
					fIndex = i;
					break;
				}
			
			}
			
			json2 = json2.substring(0, sIndex) + json2.substring(fIndex+1, json2.length());
			
			if(json2.length()>10000000) {
				break;
			}
		}
		
		return json2;
	}

}
