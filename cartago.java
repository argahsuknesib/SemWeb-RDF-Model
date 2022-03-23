package cartago;

import cartago.*;
import java.util.UUID;
import java.util.*;


public class cartago extends Artifact {

  int room0_temp_data = 20;
  int room1_temp_data =  5;
  int room2_temp_data = 10;
  int room3_temp_data = 15;

  
  int val1 ;
  int val2 ;
  
  int value1 ;
  int value2 ;
  int value3 ;
  int value4 ;
	
  // names of observable property as well as actions must start with downcase letter
  void init() {
	
    defineObsProperty("room_number_agent1","");
    defineObsProperty("room_observation_agent1", val1);
    
    defineObsProperty("room_number_agent2","");
    defineObsProperty("room_observation_agent2", val2);
    
    defineObsProperty("response_agent1", value1);
    
    defineObsProperty("response_agent2", value2);
    
    defineObsProperty("negotiate1", value3);
    defineObsProperty("negotiate2", value4);

  }

  @OPERATION void announce1(String msg) {
	  
     	int room_number = new Random().nextInt(4) ;
	    msg += String.valueOf(room_number);
        // System.out.println("[Announce] " + msg);
    	
    	if (msg.equals("room1")) {
        	
        	getObsProperty("room_number_agent1").updateValue(msg);
        	getObsProperty("room_observation_agent1").updateValue(room1_temp_data);

        }else if (msg.equals("room2")) {
        	
        	getObsProperty("room_number_agent1").updateValue(msg);
        	getObsProperty("room_observation_agent1").updateValue(room2_temp_data);
        	
        }else if (msg.equals("room3")) {
        	
        	getObsProperty("room_number_agent1").updateValue(msg);
        	getObsProperty("room_observation_agent1").updateValue(room3_temp_data);

        }else{
        	
        	getObsProperty("room_number_agent1").updateValue(msg);
        	getObsProperty("room_observation_agent1").updateValue(room0_temp_data);

        }   
  }
  
  @OPERATION void announce2(String msg) {
	  
   	int room_number = new Random().nextInt(4) ;
	    msg += String.valueOf(room_number);
      // System.out.println("[Announce] " + msg);
  	
  	if (msg.equals("room1")) {
      	
      	getObsProperty("room_number_agent2").updateValue(msg);
      	getObsProperty("room_observation_agent2").updateValue(room1_temp_data);

      }else if (msg.equals("room2")) {
      	
      	getObsProperty("room_number_agent2").updateValue(msg);
      	getObsProperty("room_observation_agent2").updateValue(room2_temp_data);
      	
      }else if (msg.equals("room3")) {
      	
      	getObsProperty("room_number_agent2").updateValue(msg);
      	getObsProperty("room_observation_agent2").updateValue(room3_temp_data);

      }else{
      	
      	getObsProperty("room_number_agent2").updateValue(msg);
      	getObsProperty("room_observation_agent2").updateValue(room0_temp_data);

      }   
  }

  
  @OPERATION void offer1() {
	  
   	int random_number1 = new Random().nextInt(2) + 1 ;

      	getObsProperty("response_agent1").updateValue(random_number1);
 
  }
  
  @OPERATION void offer2() {
	  
	int random_number2 = new Random().nextInt(2) + 1 ;
 	
	    getObsProperty("response_agent2").updateValue(random_number2);	   
	     
  }
  
  @OPERATION void negotiation1() {
	  
	int random_number3 = new Random().nextInt(2) + 1 ;
 	
	    getObsProperty("negotiat1").updateValue(random_number3);	   
	     
  }
  
  @OPERATION void negotiation2() {
	  
	int random_number4 = new Random().nextInt(2) + 1 ;
 	
	    getObsProperty("negotiate2").updateValue(random_number4);	   
	     
  }
   
}
