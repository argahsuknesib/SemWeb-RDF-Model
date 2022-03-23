// initial believes
temp1(16).
temp2(19).
flag(0).



+response_agent1(Q)  <- 

	if(Q==1){
			
			.println([Reject], " Nothing to do");
	}
	
	if(Q==2){
			
			.println([Accept], "Monitoring another room");
			!announce;
	}
.

+negotiate(J)  <- 

	if(J==1){
			
			.println([Reject], "Rejecting negotiation");
	}
	
	if(J==2){
			
			.println([Accept], "Accepting negotiation");
			.send(s423,tell,flag(0));
	}
.

+!monitor(M) <- 
		
       for ( .range(I,1,10) ) {
    		
          	announce1(M);    
          	.wait(1000);
          	!announce;
       }
.


+!announce : (room_number_agent1(N) & room_observation_agent1(O)) <-
			
		if(temp1(A)& O<A){
			
			.println([Announce], " ", N, ", ", " temp:  ", O , ", ", "This room is cold");
			.send(s423,achieve,ask2(N,"This room is cold"));
			
		}
		
	    if(temp1(A)&  temp2(B) & O>=A & O<B){
	    	
			.println([Announce], " ", N, ", ", " temp:  ", O , ", ", "This room is mild");
			.send(s423,achieve,ask2(N,"This room is mild"));
		}
		
	    if(temp2(B) & O>=B){
	    	
            .println([Announce], " ", N, ", ", " temp:  ", O , ", ", "This room is hot");
            .send(s423,achieve,ask2(N,"This room is hot"));
		}
.

+!ask1(F,G)  <-

	if(room_number_agent1(N) & F==N & room_observation_agent1(O) ){
		
		
		if(G=="This room is cold" & temp1(A)& O>A){
			
			if(flag(R)& R==0){
			
				.println([Contradiction],' different observation for ', N);
				.println([Offer], " Offering agent2 for monitoring another room");
				.send(s423,achieve,offer2);
			
			}else{
				
				.println([Negotiation], " Asking agent2 to see if it accepts agent1 belief");
				.send(s423,achieve,negotiation2);
				
			}
		}
		
		if(G=="This room is mild" & temp2(A)& O>A){
			
			if(flag(R)& R==0){
				
				.println([Contradiction],' different observation for ', N);
				.println([Offer], " Offering agent2 for monitoring another room");
				.send(s423,achieve,offer2);
			
			}else{
				
				.println([Negotiation], " Asking agent2 to see if it accepts agent1 belief");
				.send(s423,achieve,negotiation2);
				
			}
		}
		
		if(G=="This room is hot" & temp2(A)& O<A){
			
			if(flag(R)& R==0){
					
				.println([Contradiction],' different observation for ', N);
				.println([Offer], " Offering agent2 for monitoring another room");
				.send(s423,achieve,offer2);
			
			}else{
				
				.println([Negotiation], " Asking agent2 to see if it accepts agent1 belief");
				.send(s423,achieve,negotiation2);
				
			}
		}
		
	}
	
	
		
.

+!offer1 <-
	offer1
.

+!negotiation1 <-
	negotiation1
.

//+!contradiction :
// include of basic plans for handling cartago infra.
{ include("$jacamoJar/templates/common-cartago.asl") }
