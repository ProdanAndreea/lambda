package homework_5;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MonitoredData {

	public String startTime;
	public String endTime;
	public String activityLabel;
	
	MonitoredData(String startTime, String endTime, String activityLabel) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.activityLabel = activityLabel;
	}
	
	
	public String getActivityLabel() {
		return activityLabel;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public String getEndTime() {
		return endTime;
	}
	
	public long dif() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		//System.out.println(startTime + " " + endTime + " = " +ChronoUnit.HOURS.between(LocalDateTime.parse(startTime, formatter), LocalDateTime.parse(endTime, formatter)));
		
		return ChronoUnit.HOURS.between(LocalDateTime.parse(startTime, formatter), LocalDateTime.parse(endTime, formatter));	
	}
	
	public String cat(String a, String b) {
		return a + " " + b;
	}
	
		
	 public long calculateLower(List<MonitoredData> data, String activity) {
	        long occurrences = data.stream()
	                .filter(p -> p.activityLabel.equalsIgnoreCase(activity)).filter(p -> p.dif() < 5).count();
	        return occurrences;
	 }
	 
	 public long calculateOccurrences(List<MonitoredData> data, String activity) {
	        long occurrences = data.stream()
	                .filter(p -> p.activityLabel.equalsIgnoreCase(activity)).count();
	        return occurrences;
	 }

	 public long calculateFrequency(long occurences, long lower) {
	        return (lower * 100) / occurences;     
	 }
	
	
	 public static void writeToFile(Map<String, Long> map, String file_name) {
		 try {
			 FileWriter fstream;
			 BufferedWriter out;
			 fstream = new FileWriter(file_name);
			 out = new BufferedWriter(fstream);
			 
			 for (Map.Entry<String, Long> entry : map.entrySet()) {
				    String key = entry.getKey();
				    Object value = entry.getValue();
				  
				    out.write(key + " " + value);
				    out.newLine();
			}			
			 out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}		
	 } 
	 	 
	 public static void writeToFileMapMap(Map <Integer, Map<String, Long>> map, String file_name) {   
		 try {
			 FileWriter fstream;
			 BufferedWriter out;
			 fstream = new FileWriter(file_name);
			 out = new BufferedWriter(fstream);
			 
			 for (Entry<Integer, Map<String, Long>> entry : map.entrySet()) {
				    Object key = entry.getKey();
				    Object value = entry.getValue();
				    
				    out.write(key + " " + value);
				    out.newLine();
	 
				}
			
			 out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}		 
	 }
	 
	 public static void writeToFile(List<String> list, String file_name) {   
		 try {
			 FileWriter fstream;
			 BufferedWriter out;
			 fstream = new FileWriter(file_name);
			 out = new BufferedWriter(fstream);
			 
			 for (String s: list) {
				    out.write(s);
				    out.newLine();
				}				
			 out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}    	 
	 }
 
	 
}
