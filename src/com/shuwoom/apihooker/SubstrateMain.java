package com.shuwoom.apihooker;

import com.shuwoom.apihooker.hookers.NetworkHooker;

public class SubstrateMain {

	public static void initialize(){
		new NetworkHooker().hook();
	}

}
