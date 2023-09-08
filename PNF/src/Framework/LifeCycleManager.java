/**
 * Copyright(c) 2021 All rights reserved by Jungho Kim in Myungji University.
 */
package Framework;

import java.io.IOException;
import Components.CourseCheck.CourseCheckFilter;
import Components.CourseCheck.isSatisfiedFilter;
import Components.Major.MajorFilter;
import Components.Major.StudentIdFilter;
import Components.Sink.SinkFilter;
import Components.Source.SourceFilter;

public class LifeCycleManager {
	private boolean isMajorTF;
	private boolean isCourseTF;
    public static void main(String[] args) {
        try {
            LifeCycleManager lifeCycleManager = new LifeCycleManager();
            lifeCycleManager.PNFA1();
            lifeCycleManager.PNFA2();
            lifeCycleManager.PNFA3();
            lifeCycleManager.PNFB1();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void PNFA1() throws IOException{
    	CommonFilter sourceFilter = new SourceFilter("Students.txt", 0); //filterSource
        CommonFilter resultFilter = new SinkFilter("OutputA1.txt", 0);
        CommonFilter majorFilter = new MajorFilter("CS", 0, isMajorTF = true);
        CommonFilter courseCheckFilter = new CourseCheckFilter(isCourseTF = true, "12345", "23456");
        
        sourceFilter.connectOutputTo(majorFilter, 0); 
        majorFilter.connectOutputTo(courseCheckFilter, 0); 
        courseCheckFilter.connectOutputTo(resultFilter, 0); 
        
        Thread sourceThread = new Thread(sourceFilter); 
        Thread resultThread = new Thread(resultFilter);
        Thread dptThread = new Thread(majorFilter);
        Thread courseCheckThread = new Thread(courseCheckFilter);
        
        sourceThread.start();
        resultThread.start();
        dptThread.start();
        courseCheckThread.start();
    }
    
    private void PNFA2() throws IOException{
    	CommonFilter sourceFilter = new SourceFilter("Students.txt", 0); //filterSource
        CommonFilter resultFilter = new SinkFilter("OutputA2.txt", 0);
        CommonFilter majorFilter = new MajorFilter("EE", 0, isMajorTF = true);
        CommonFilter courseCheckFilter = new CourseCheckFilter(isCourseTF = true, "23456");
        
        sourceFilter.connectOutputTo(majorFilter, 0); 
        majorFilter.connectOutputTo(courseCheckFilter, 0); 
        courseCheckFilter.connectOutputTo(resultFilter, 0); 
        
        Thread sourceThread = new Thread(sourceFilter); 
        Thread resultThread = new Thread(resultFilter);
        Thread dptThread = new Thread(majorFilter);
        Thread courseCheckThread = new Thread(courseCheckFilter);
        
        sourceThread.start();
        resultThread.start();
        dptThread.start();
        courseCheckThread.start();
    }
    
    private void PNFA3() throws IOException{
    	CommonFilter sourceFilter = new SourceFilter("Students.txt", 0);
        CommonFilter resultFilter = new SinkFilter("OutputA3.txt", 0);
        CommonFilter StudentIdFilter = new StudentIdFilter("2013", 0);
        CommonFilter majorFilter = new MajorFilter("CS", 0, isMajorTF = false);
        CommonFilter courseCheckFilter = new CourseCheckFilter(isCourseTF = false, "17651", "17652");
        
        sourceFilter.connectOutputTo(StudentIdFilter, 0);
        StudentIdFilter.connectOutputTo(majorFilter, 0);
        majorFilter.connectOutputTo(courseCheckFilter, 0);
        courseCheckFilter.connectOutputTo(resultFilter, 0);
        
        Thread thread1 = new Thread(sourceFilter);
        Thread thread2 = new Thread(resultFilter);
        Thread thread3 = new Thread(StudentIdFilter);
        Thread thread4 = new Thread(majorFilter);
        Thread thread5 = new Thread(courseCheckFilter);
        
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
    
    private void PNFB1() throws IOException{
    	CommonFilter studentSourceFilter = new SourceFilter("Students.txt", 0);
    	CommonFilter courseSourceFilter = new SourceFilter("Courses.txt", 1);
    	CommonFilter isSatisfiedFilter = new isSatisfiedFilter();
    	CommonFilter satisfiedFilter = new SinkFilter("satisfiedFile.txt", 0);
    	CommonFilter disSatisfiedFilter = new SinkFilter("disSatisfiedFile.txt", 1);
    	
    	studentSourceFilter.connectOutputTo(isSatisfiedFilter, 0);
    	courseSourceFilter.connectOutputTo(isSatisfiedFilter, 1);
    	
    	isSatisfiedFilter.connectOutputTo(satisfiedFilter, 0);
    	isSatisfiedFilter.connectOutputTo(disSatisfiedFilter, 1);
    	
    	Thread thread1 = new Thread(studentSourceFilter);
        Thread thread2 = new Thread(courseSourceFilter);
        Thread thread3 = new Thread(isSatisfiedFilter);
        Thread thread4 = new Thread(satisfiedFilter);
        Thread thread5 = new Thread(disSatisfiedFilter);
        
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
}
