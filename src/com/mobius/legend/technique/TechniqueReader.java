package com.mobius.legend.technique;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import android.content.Context;

public class TechniqueReader {
	public static final String TECHNIQUE_PATH = "techniques";
	
	static boolean ranBefore = false;
	
	public void readTechniques(Context ctx) {
		if (ranBefore) {
			return;
		} else {
			ranBefore = true;
		}
		TechniqueParser parser = new TechniqueParser();
		String[] files = new String[0];
		try {
			files = ctx.getAssets().list(TECHNIQUE_PATH);
		} catch (Exception e) {
			
		}

		for (String filepath : files) {
			try {
			InputStream stream = ctx.getAssets().open(TECHNIQUE_PATH + "/" + filepath);
			SAXReader reader = new SAXReader();
			
			Document document = null;
			try
			{
				document = reader.read(stream);
			} catch (DocumentException exception)
			{
				exception.printStackTrace();
			}
	        stream.close();
			parser.parseDocument(document.getRootElement());
			} catch (Exception e) {
				
			}
		}
	}
}
