package logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class TransactionLogger implements Logger {

	private File file;
	private String filePath;
	private FileWriter writer;
	
	public TransactionLogger(String filePath) throws FileNotFoundException, IOException {
		this(new File(filePath), false);
	}
	
	public TransactionLogger(String filePath, boolean create) throws FileNotFoundException, IOException {
		this(new File(filePath), create);
	}
	
	public TransactionLogger(File file) throws FileNotFoundException, IOException {
		this(file, false);
	}
	
	public TransactionLogger(File file, boolean create) throws FileNotFoundException, IOException {
		if (file == null) {
			throw new NullPointerException();
		}
		
		if (!file.exists()) {
			if (create) {
				file.createNewFile();
			} else {
				throw new FileNotFoundException();
			}
		}
		
		if (!file.canWrite()) {
			throw new IOException("cannot write to: " + file.getPath());
		}
		
		this.file = file;
		this.filePath = file.getPath();
		this.writer = new FileWriter(this.file);
	}

	public String getFilePath() {
		return this.filePath;
	}
		
	public boolean close() {
		try {
			this.writer.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}

	public boolean log(String data) {
		try {
			this.writer.write(System.currentTimeMillis() + ":" + data + "\n");
			this.writer.flush();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	public String toString() {
		return "TransactionLog::" + this.filePath;
	}
	
}
