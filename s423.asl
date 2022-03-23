// initial believes
temp1(15).
temp2(21).
flag(0).

+response_agent2(P)  <- 

	if(P==1){
			
			.println([Reject], " Nothing to do");
	}
	
	if(P==2){
			
			.println([Accept], "Monitoring another room");
			!announce;
	}
.


+!monitor(M) <- 
		
       for ( .range(I,1,10) ) {
    		
          	announce2(M);    
          	.wait(1000);
          	!announce;
       }
.

+!announce : room_number_agent2(N) & room_observation_agent2(O) <-
			
		if(temp1(A)& O<A){
			
			.println([Announce], " ", N, ", ", " temp:  ", O , ", ", "This room is cold");
			.send(s422,achieve,ask1(N,"This room is cold"));
		}
		
	    if(temp1(A)&  temp2(B) & O>=A & O<B){
	    	
			.println([Announce], " ", N, ", ", " temp:  ", O , ", ", "This room is mild");
			.send(s422,achieve,ask1(N,"This room is mild"));
		}
		
	    if(temp2(B) & O>=B){
	    	
            .println([Announce], " ", N, ", ", " temp:  ", O , ", ", "This room is hot");
            .send(s422,achieve,ask1(N,"This room is hot"));
		}
.

+!ask2(X,Y) <-

	if(room_number_agent2(N) & X==N & room_observation_agent2(O) ){
		
		if(Y=="This room is cold" & temp1(A)& O>A){
			
			if(flag(R)& R==0){
				
				.println([Contradiction],' different observation for ', N);
				.println([Offer], " Offering agent1 for monitoring another room");
				.send(s422,achieve,offer1);		
				-+flag(1);		
				
			}else{

				.println([Negotiation], " Asking agent1 to see if it accepts agent2 belief");
				.send(s422,achieve,negotiation);
			}

		}
		
		if(Y=="This room is mild" & temp2(A)& O>A){
			
			if(flag(R)& R==0){
				
				.println([Contradiction],' different observation for ', N);
				.println([Offer], " Offering agent1 for monitoring another room");
				.send(s422,achieve,offer1);		
				-+flag(1);		
				
			}else{

				.println([Negotiation], " Asking agent1 to see if it accepts agent2 belief");
				.send(s422,achieve,negotiation);
			}

		}
				
		if(Y=="This room is hot" & temp2(A)& O<A){
			
			if(flag(R)& R==0){
				
				.println([Contradiction],' different observation for ', N);
				.println([Offer], " Offering agent1 for monitoring another room");
				.send(s422,achieve,offer1);		
				-+flag(1);		
				
			}else{

				.println([Negotiation], " Asking agent1 to see if it accepts agent2 belief");
				.send(s422,achieve,negotiation);
			}

		}
		
	}
	
.

+!offer2 <-
	offer2
.

+!negotiation2 <-
	negotiation2
.

// include of basic plans for handling cartago infra.
{ include("$jacamoJar/templates/common-cartago.asl") }
