package homework_5;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Define {

	List<MonitoredData> monitoredData; 
	
	Define() {
		monitoredData = new ArrayList<>(); 
	}
	
	void readData() {
		List<String> list_data = new ArrayList<>();
			
		String fileName = "Activities.txt";
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			list_data = stream.collect(Collectors.toList());
			
			List<String> list_data_new = new ArrayList<>();
			for(String line: list_data) {
				for (String part : line.split("\\		")) {
		        	
		        	list_data_new.add(part);
		        }
				 monitoredData.add(new MonitoredData(list_data_new.get(0), list_data_new.get(1), list_data_new.get(2)));
				 list_data_new.clear();
			}
			
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void displayData() {
		for(MonitoredData m: monitoredData) {
			System.out.println(m.startTime + " | " + m.endTime + " | " + m.activityLabel);
		}
	}
	
	void countDistinctDays() {
		long count = monitoredData.stream().map(sc -> sc.startTime.substring(0, 10)).distinct().count();
		System.out.println(count);
	}
	
	void getDistinctActivities() {
		List<String> attributes = monitoredData.stream().map(sc -> sc.activityLabel).collect(Collectors.toList());
		
		Map<String, Long> map = attributes.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

		 MonitoredData.writeToFile(map, "2_distinct_activities.txt");	 		
	}
	
	void distinctActivitiesByDay() {
		Map<Integer, Map<String, Long>> map = monitoredData.stream()
	            .collect(Collectors.groupingBy(	e -> Integer.parseInt(e.startTime.substring(8, 10)),
	            								Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.counting())
	            								));
		
		 MonitoredData.writeToFileMapMap(map, "3_distinct_activities_by_day.txt");					
	}
		
	public void activityDuration() {
		Map<Integer, Map<String, Long>> map = monitoredData.stream()
	            .collect(Collectors.groupingBy(e -> Integer.parseInt(e.startTime.substring(8, 10)),
	                    Collectors.groupingBy(MonitoredData::getActivityLabel, Collectors.counting())));
	
		
			 for (Entry<Integer, Map<String, Long>> entry : map.entrySet()) {
				    Object key = entry.getKey();
				    Object value = entry.getValue();
				    
				    System.out.println(key + " " + value);
				}			
	}
	
	void activitiesGreater() {
		Map<String, Long> map
					= monitoredData.stream()
                    .collect(Collectors.groupingBy(MonitoredData::getActivityLabel,
                                                  Collectors.summingLong(MonitoredData::dif)))
					.entrySet()
					.stream()
					.filter(p -> p.getValue() > 10)
					.collect(Collectors.toMap(p->p.getKey(),
                             				  p->p.getValue()));
		
		
		 MonitoredData.writeToFile(map, "4_activities_greater_than_10.txt");
		 
	  
	   // map
	   // .forEach((time, a) -> System.out.format("%s %s\n", time, a));	    	

	}
	
	void precentaje() {
		Map<String, Object> map = monitoredData.parallelStream()
				.filter(p -> p.calculateFrequency(p.calculateOccurrences(monitoredData,p.activityLabel), p.calculateLower(monitoredData,p.activityLabel)) >= 90)
				//.forEach((p) -> System.out.format("%s %d\n", p.activityLabel, p.calculateFrequency(p.calculateOccurrences(monitoredData,p.activityLabel), p.calculateLower(monitoredData,p.activityLabel))));	  
				.collect(Collectors.toMap(p -> p.activityLabel, 
						p -> p.calculateFrequency(p.calculateOccurrences(monitoredData,p.activityLabel), p.calculateLower(monitoredData,p.activityLabel)),
						(address1, address2) -> {
		  						return address1;
						}
						));
		List<String> list = map.entrySet()
						        .stream()
						        .map(p -> p.getKey())
						        .collect(Collectors.toList());				
		// map
		//    .forEach((time, a) -> System.out.format("%s %s\n", time, a));	  
		 
		 System.out.println();
		 list
		    .forEach((a) -> System.out.format("%s\n", a));	
		 
		 MonitoredData.writeToFile(list, "5_percentage.txt");	
	}

}
