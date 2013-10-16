This is new (release) branch of rosjava_core https://github.com/rosjava/rosjava_core , java-based implementation of ROS.

My version solves the unresolved (probably some bug in gradle) testCompile dependency of rosjava on mockito (mockito is placed into the folder lib/).


works:
	./gradlew install

does not work:
	./gradew build
	
	-> 140 tests completed, 32 failed
	
	
So for now, my version of the old (and discarded) hydro-devel branch is used in the nengoros (called nr-hydro-devel). 

Author of this version Jaroslav Vitku [vitkujar@fel.cvut.cz]